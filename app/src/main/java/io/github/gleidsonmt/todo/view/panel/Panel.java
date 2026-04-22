package io.github.gleidsonmt.todo.view.panel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.github.gleidsonmt.glad.base.responsive.Container;
import io.github.gleidsonmt.glad.controls.button.Button;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.view.panel.input.InputField;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Description: The main panel for the main view.
 * every change of the lists will be tigger here.
 * the place where you see the tasks and update them.
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created On: Feb 26, 2026
 * <p>
 * Version History: Initial version
 */
@SuppressWarnings("unused")
public class Panel extends Container {

    private final ScrollPane scroll;
    private final VBox container;
    private final GridPane bar;

    private final InputField inputContainer;

    private final int borderLimitBottom = 110;

    // Since panel has absolute parts that intercalated, this border and
    // padding property will help to maintain the elements properly anchor.
    private final DoubleProperty borderTop = new SimpleDoubleProperty(80);
    private final DoubleProperty borderPadding = new SimpleDoubleProperty(50);

    // The title of the panel
    private final TextField title = new TextField("Title");

    private final ObjectProperty<ListViewModel> actualList = new SimpleObjectProperty<>();
    // The actual list in the editor at the momment.

    private final StringProperty iconName = new SimpleStringProperty();

    public Panel() {
        this.scroll = createScroll();
        this.container = createContainer();
        this.bar = createHeader();
        this.inputContainer = new InputField();

        title.setOnMouseClicked(e -> title.requestFocus());
        title.getStyleClass().add("inside-field");
        title.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (actualList.get().isFixed()) return;
            if (!newValue) {
                actualList.get().setName(title.getText());
                actualList.get().update();
            } else {
                if (title.getSelectedText() == null) title.selectAll();
            }
        });
        configLayout();

        actualListProperty().addListener((observable, oldValue, newValue) -> {
            title.setEditable(!newValue.isFixed());
            title.setText(newValue.getName());
        });

        ObjectProperty<Node> icon = new SimpleObjectProperty<>(new SVGIcon());
        bar.getChildren().add(icon.get());

    }

    /**
     * Set the content of the panel as a new ListRoot component.
     *
     * @param listRoot The list root.
     */
    public void setContent(ListRoot listRoot) {
        // remove and set all children for this list root
        this.container.getChildren().setAll(listRoot);
        listRoot.actualListProperty().addListener((observable, oldValue, newValue) -> this.inputContainer.reset());
    }

    public ListRoot getListRoot() {
        return (ListRoot) this.container.getChildren().getFirst();
    }

    private ScrollPane createScroll() {
        var scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true); // change
        return scrollPane;
    }

    private VBox createContainer() {
        var _container = new VBox();
        _container.setPadding(new Insets(borderTop.get(), 50, borderLimitBottom, 50));
        _container.setSpacing(10);

        this.borderTop.addListener((observable, oldValue, newValue) -> _container.setPadding(
                new Insets(newValue.doubleValue(), borderPadding.get(), borderLimitBottom, borderPadding.get())));

        this.borderPadding.addListener((observable, oldValue, newValue) -> _container.setPadding(
                new Insets(borderTop.get(), newValue.doubleValue(), borderLimitBottom, newValue.doubleValue())));

        return _container;
    }

    private void configLayout() {
        this.setId("panel");

        this.scroll.setContent(this.container);
        VBox.setVgrow(this.container, Priority.ALWAYS);

        Pane regionLimitTop = createRegionLimit(VPos.TOP, borderTop.get());
        Pane regionLimitBottom = createRegionLimit(VPos.BOTTOM, borderLimitBottom);

        this.getChildren().setAll(scroll, regionLimitTop, regionLimitBottom, bar, inputContainer);

        Button hamb = new Hamburger();

        this.addBreakpoint((event) -> {
            // body.setLeft(null);
            if (getChildren().contains(hamb))
                bar.getChildren().add(hamb);
            bar.getChildren().forEach(el -> {
                GridPane.setRowIndex(el, 1);
            });
            if (!bar.getChildren().contains(hamb))
                bar.addRow(0, hamb);
            // GridPane.setColumnIndex(hamb, 0);
        }, "<MD");

        this.addBreakpoint((event) -> {
            // body.setLeft(sideNav);
            bar.getChildren().remove(hamb);
            bar.getChildren().forEach(el -> {
                GridPane.setRowIndex(el, 0);
            });
        }, ">MD");

    }

    private Pane createRegionLimit(VPos pos, double borderLimit) {
        Pane regionLimit = new Pane();
        regionLimit.setMaxHeight(borderLimit);
        regionLimit.setMaxWidth(Region.USE_PREF_SIZE);
        regionLimit.prefWidthProperty().bind(this.widthProperty().subtract(15));

        regionLimit.setPrefHeight(borderLimit);
        // regionLimit.setMaxHeight(30);
        regionLimit.maxHeightProperty().bind(borderTop);

        regionLimit.setEffect(new BoxBlur());

        regionLimit.setBackground(
                new Background(new BackgroundFill(Color.web("white", 0.89), CornerRadii.EMPTY, Insets.EMPTY)));
        if (pos == VPos.BOTTOM) {
            StackPane.setAlignment(regionLimit, Pos.BOTTOM_LEFT);
        } else if (pos == VPos.TOP) {
            StackPane.setAlignment(regionLimit, Pos.TOP_LEFT);
        }

        return regionLimit;
    }

    private GridPane createHeader() {
        GridPane grid = new GridPane();

        Text info = new Text(DateTimeFormatter.ofPattern("EEEE, dd LLLL").format(LocalDate.now()));

        title.getStyleClass().addAll("text-accent", "h2", "bold");

        title.setStyle("-fx-text-fill: -fx-accent; ");

        iconName.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                grid.getChildren().removeLast();
                var icon = new ImageView(Assets.getIcon(newValue + ".png", 32));
                grid.getChildren().add(icon);
                GridPane.setColumnIndex(icon, 0);
                GridPane.setColumnIndex(title, 1);
            } else {
                GridPane.setColumnIndex(title, 0);
            }
            GridPane.setHgrow(title, Priority.ALWAYS);
        });

        grid.setHgap(5);
        grid.add(title, 1, 0);

//        svgIcon.setScale(1.8);

        grid.setMaxHeight(15);
        StackPane.setMargin(grid, new Insets(0, 20, 20, 20));
        StackPane.setAlignment(grid, Pos.TOP_LEFT);
        GridPane.setColumnSpan(info, 2);

        // actualList.addListener((observable, oldValue, newValue) -> {
        // if (newValue.getType().equals(ListType.DAILY)) {
        // grid.add(info, 0, 1);
        // borderTop.set(80);
        // } else {
        // grid.getChildren().remove(info);
        // borderTop.set(60);
        // }
        // });
        return grid;
    }

    public StringProperty titleProperty() {
        return this.title.textProperty();
    }

    public StringProperty titleIconNameProperty() {
        return this.iconName;
    }

    public ObjectProperty<ListViewModel> actualListProperty() {
        return this.actualList;
    }

}
