package io.github.gleidsonmt.todo.view.panel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.github.gleidsonmt.glad.base.responsive.Container;
import io.github.gleidsonmt.glad.controls.button.Button;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.view.panel.input.InputField;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BoxBlur;
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
 *         Created On: Feb 26, 2026
 * 
 *         Version History: Initial version
 */
@SuppressWarnings("unused")
public class Panel extends Container {

    private final ScrollPane scroll;
    private final VBox container;
    private final GridPane bar;

    private InputField inputContainer;

    private final int borderLimitBottom = 110;

    // Since panel has absolute parts that intercalated, this border and
    // padding properties will help to mantain the elements properly anchor.
    private final DoubleProperty borderTop = new SimpleDoubleProperty(80);
    private final DoubleProperty borderPadding = new SimpleDoubleProperty(50);

    // The title of the panel
    private final Text title = new Text("Title");
    // The actual list in the editor at the momment.
    private final ObjectProperty<List> actualList = new SimpleObjectProperty<>();

    private final SVGIcon svgIcon = new SVGIcon();

    public Panel() {
        this.scroll = createScroll();
        this.container = createContainer();
        this.bar = createHeader();
        this.inputContainer = new InputField();

        configLayout();
        bind();
    }

    private void bind() {

    }

    /**
     * Set the content of the panel as a new ListRoot component.
     * 
     * @param listRoot The list root.
     */
    public void setContent(ListRootNew listRoot) {
        // remove and set all children for this list root
        this.container.getChildren().setAll(listRoot);
        // This ensure that if the list selected is not a fixed list you only
        // add the task a custom list not the tasks, tasks is a fixed list with
        // no id.
        // listRoot.actualListProperty().addListener((_, _, newValue) -> inputContainer.addTasksItem(newValue.isFixed()));
    }

    public ListRoot getListRoot() {
        return (ListRoot) this.container.getChildren().get(0);
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

        // this.addBreakpoint((event) -> {
        // // body.setLeft(null);
        // bar.getChildren().add(hamb);
        // bar.getChildren().forEach(el -> {
        // GridPane.setRowIndex(el, 1);
        // });
        // bar.addRow(0, hamb);
        // // GridPane.setColumnIndex(hamb, 0);
        // }, "<MD");

        // this.addBreakpoint((event) -> {
        // // body.setLeft(sideNav);
        // bar.getChildren().remove(hamb);
        // bar.getChildren().forEach(el -> {
        // GridPane.setRowIndex(el, 0);
        // });
        // }, ">MD");

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
        grid.setHgap(10);

        Text info = new Text(DateTimeFormatter.ofPattern("EEEE, dd LLLL").format(LocalDate.now()));

        title.getStyleClass().addAll("text-accent", "h2", "bold");

        title.setStyle("-fx-text-fill: -fx-accent; ");

        grid.add(svgIcon, 0, 0);
        grid.add(title, 1, 0);

        svgIcon.setScale(1.8);

        grid.setMaxHeight(15);
        StackPane.setMargin(grid, new Insets(0, 20, 20, 20));
        StackPane.setAlignment(grid, Pos.TOP_LEFT);
        GridPane.setColumnSpan(info, 2);

        actualList.addListener((observable, oldValue, newValue) -> {
            if (newValue.getType().equals(ListType.DAILY)) {
                grid.add(info, 0, 1);
                borderTop.set(80);
            } else {
                grid.getChildren().remove(info);
                borderTop.set(60);
            }
        });
        return grid;
    }

    public StringProperty titleProperty() {
        return this.title.textProperty();
    }

    public ObjectProperty<List> actualListProperty() {
        return actualList;
    }

    public ObjectProperty<Icon> titleIconProperty() {
        return this.svgIcon.iconProperty();
    }

}
