package io.github.gleidsonmt.todo.view.panel;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.glad.base.responsive.Container;
import io.github.gleidsonmt.glad.controls.button.Button;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.utils.IconUtils;
import io.github.gleidsonmt.todo.view.nav.SideNav;
import io.github.gleidsonmt.todo.view.panel.input.InputField;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    // The actual list in the editor at the moment.

    private final StringProperty iconName = new SimpleStringProperty();
    private final Label label;
    private final ObjectProperty<Node> icon = new SimpleObjectProperty<>();

    public Panel() {
        this.label = createLabelIcon();
        this.scroll = createScroll();
        this.container = createContainer();
        this.bar = createHeader();
        this.inputContainer = new InputField();

        title.setOnMouseClicked(e -> title.requestFocus());
        title.getStyleClass().add("inside-field");

        label.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

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



        this.iconName.addListener((observable, oldValue, newValue) -> {
            updateIcon(!title.isEditable(), newValue);
        });

        actualListProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) return;
            label.setDisable(newValue.isFixed());
            title.setEditable(!newValue.isFixed());
            textInfo(newValue.getType().equals(ListType.DAILY));
            title.setText(newValue.getName());

        });
    }

    private void updateIcon(boolean svg, String iconName) {
        if (svg) {
            icon.set(new SVGIcon(Icon.valueOf(iconName.toUpperCase()), 1.5));
        } else {
            icon.set(new ImageView(Assets.getIcon(iconName + ".png", 32)));

        }
    }

    private Label createLabelIcon() {
        var label = new Label();
        label.getStyleClass().add("icon-label");
        label.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> showIconChoose());
        return label;
    }

    private void showIconChoose() {
        IconGrid grid = new IconGrid();
        grid.selectedProperty().addListener((observable, oldValue, newValue) -> {
            ( (ImageView) icon.get() ).setImage(newValue);
            actualList.get().setIconName(IconUtils.getIconName(newValue));
            actualList.get().update();
        });
        Root root = (Root) getScene().getRoot();
        root.flow()
                .content(grid)
                .width(200)
                .pos(Pos.BOTTOM_LEFT)
                .insets(new Insets(0, 0, 10, 100))
                .show((Region) icon.get().getParent());

        grid.setOnMouseExited(e -> {
            root.flow().hide();
        });
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

    private final Text info = new Text(DateTimeFormatter.ofPattern("EEEE, dd LLLL").format(LocalDate.now()));

    private void textInfo(boolean active) {
        if (active) {
            GridPane.setColumnIndex(info, 1);
            GridPane.setColumnSpan(info, 2);
            GridPane.setRowIndex(info, 1);
            info.setTranslateX(15);
            bar.getChildren().add(info);
        } else {
            bar.getChildren().remove(info);
        }
    }

    private GridPane createHeader() {

        GridPane grid = new GridPane();

        title.getStyleClass().addAll("text-accent", "h2", "bold");

        title.setStyle("-fx-text-fill: -fx-accent; ");
        GridPane.setHgrow(title, Priority.ALWAYS);

        grid.add(label, 0, 0);
        grid.add(title, 1, 0);

        label.graphicProperty().bind(icon);

        grid.setMaxHeight(15);
        StackPane.setMargin(grid, new Insets(0, 20, 20, 20));
        StackPane.setAlignment(grid, Pos.TOP_LEFT);

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
