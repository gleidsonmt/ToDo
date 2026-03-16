package io.github.gleidsonmt.todo.view.nav;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.view.ViewList;
import io.github.gleidsonmt.todo.view.panel.menu.ListContextMenu;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 *         Created On: Feb 27, 2026
 * 
 *         Version History: Initial version
 */
public class CustomDrawerItem extends ToggleButton {

    // Components
    private final Pane iconSelector = new Pane();
    private final SVGIcon svgIcon = new SVGIcon();
    private final TextField text = new TextField();
    private final Label number = new Label();

    // bindable items
    private final BooleanProperty fixed = new SimpleBooleanProperty(false);

    private final ObjectProperty<Icon> icon = new SimpleObjectProperty<>();
    private final GridPane container = new GridPane();

    private final IntegerProperty numberOfNotifications = new SimpleIntegerProperty(0);

    private final BooleanProperty editable = new SimpleBooleanProperty(false);

    public CustomDrawerItem(ViewList viewList) {
        this(viewList, false);
    }

    public CustomDrawerItem(ViewList viewList, boolean fixed) {
        this.setUserData(viewList);
        this.fixed.set(fixed);
        this.text.setText(viewList.getList().getName());
        this.setGraphic(container);
        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        svgIcon.iconProperty().bind(this.icon);
        svgIcon.setFocusTraversable(false);
        this.icon.bind(viewList.getList().iconProperty());

        init();
        bind();
        configLayout();
        registerListeners();
        setActions();
    }

    private void init() {
        this.setPrefWidth(300);
        this.number.setMaxWidth(this.number.getText().length() * 25);
        this.container.setPadding(new Insets(0, 5, 0, 0));
        //
        this.getStyleClass().addAll("custom-drawer-item");
        container.getStyleClass().addAll("list-container");
        text.getStyleClass().addAll("font-instagram-medium", "h5");
        number.getStyleClass().addAll("sub", "h5", "bold", "font-instagram-medium");

        iconSelector.setMaxSize(5, 30);
        iconSelector.getStyleClass().add("icon-selector");

        container.getChildren().setAll(iconSelector, svgIcon, text, number);
    }

    private void bind() {
        number.textProperty().bind(Bindings.convert(this.numberOfNotifications));
        number.visibleProperty().bind(this.numberOfNotifications.greaterThan(0));
        text.disableProperty().bind(this.editable.not());
    }

    private void registerListeners() {
        this.number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                number.setMaxWidth(number.getText().length() * 25);
            }
        });

        this.editable.addListener((_, _, newVal) -> {
            System.out.println("newVal = " + newVal);
            if (newVal) {
                Platform.runLater(() -> {
                    text.requestFocus();
                    text.requestLayout();
                    text.selectAll();
                });
            }
        });

        this.focusWithinProperty().addListener((_, _, newVal) -> {
            if (!newVal) {
                this.setEditable(newVal);
            }
        });
    }

    private void configLayout() {
        GridPane.setColumnIndex(iconSelector, 0);
        GridPane.setHalignment(iconSelector, HPos.LEFT);
        GridPane.setColumnIndex(svgIcon, 1);
        GridPane.setHalignment(svgIcon, HPos.CENTER);
        GridPane.setColumnIndex(text, 2);
        GridPane.setColumnIndex(number, 3);
        //
        GridPane.setHalignment(number, HPos.CENTER);
        //
        container.setHgap(10);
        //
        ColumnConstraints colOne = new ColumnConstraints();
        colOne.setHalignment(HPos.CENTER);
        colOne.setPercentWidth(5);
        ColumnConstraints colTwo = new ColumnConstraints();
        colTwo.setPercentWidth(10);
        ColumnConstraints colThree = new ColumnConstraints();
        colThree.setPercentWidth(75);
        ColumnConstraints colFour = new ColumnConstraints();
        colFour.setPercentWidth(15);

        RowConstraints rowOne = new RowConstraints();
        rowOne.setFillHeight(true);
        rowOne.setPercentHeight(100);

        container.getColumnConstraints().addAll(colOne, colTwo, colThree, colFour);
        container.getRowConstraints().addAll(rowOne);

        GridPane.setHgrow(text, Priority.ALWAYS);
    }

    private void setActions() {
        var context = new ListContextMenu(this);
        this.setOnContextMenuRequested(e -> {

            var current = (SideNav) getScene().lookup("#drawer");
            current.select((ViewList) this.getUserData());

            context.show(this, Side.TOP, 10, 0);

        });
    }

    public ViewList getViewList() {
        return (ViewList) getUserData();
    }

    public Icon getIcon() {
        return icon.get();
    }

    public ObjectProperty<Icon> iconProperty() {
        return icon;
    }

    public IntegerProperty numberOfNotificationsProperty() {
        return this.numberOfNotifications;
    }

    public void setEditable(boolean val) {
        this.editable.set(val);
    }
}