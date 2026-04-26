package io.github.gleidsonmt.todo.view.nav;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.view.panel.menu.ListContextMenu;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created On: Mar 20, 2026
 */
public class DrawerItem extends ToggleButton {
    // Components
    private final Pane iconSelector = new Pane();
    private final TextField title = new TextField();
    private final Label number = new Label();
    private final Label label = new Label();
    // Containers
    private final GridPane container = new GridPane();
    // Properties
    private final IntegerProperty numberOfNotifications = new SimpleIntegerProperty(0);
    private final BooleanProperty editable = new SimpleBooleanProperty(false);
    private final ObjectProperty<Node> icon;
    // Models
    private final ListViewModel viewModel;

    public DrawerItem(ListViewModel viewModel) {
        this.viewModel = viewModel;
        this.icon = new SimpleObjectProperty<>();

        if (viewModel.isFixed()) {
            icon.set(new SVGIcon(Icon.valueOf(viewModel.getIconName().toUpperCase())));
        } else {
            icon.set(new ImageView(Assets.getIcon(viewModel.getIconName().toLowerCase() + ".png")));
        }


        this.setGraphic(container);

        init();
        configLayout();
        bind();
        registerListeners();
        setActions();
    }

    public ListViewModel getViewModel() {
        return this.viewModel;
    }

    private void init() {
        this.setPrefWidth(300);
        this.number.setMaxWidth(this.number.getText().length() * 25);
        this.container.setPadding(new Insets(0, 5, 0, 0));
        //
        this.getStyleClass().addAll("custom-drawer-item");
        container.getStyleClass().addAll("list-container");
        title.getStyleClass().addAll("font-instagram-medium", "h5");
        number.getStyleClass().addAll("h5", "bold");
        number.setStyle("-fx-font-weight: bold; -fx-fill: -fx-accent;");

        iconSelector.setMaxSize(5, 30);
        iconSelector.getStyleClass().add("icon-selector");

        label.getStyleClass().add("label-icon");

        container.getChildren().setAll(iconSelector, label, title, number);
    }

    private void configLayout() {

        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        GridPane.setColumnIndex(iconSelector, 0);
        GridPane.setHalignment(iconSelector, HPos.LEFT);
        GridPane.setColumnIndex(label, 1);
        GridPane.setHalignment(label, HPos.CENTER);
        GridPane.setColumnIndex(title, 2);
        GridPane.setColumnIndex(number, 3);
        //
        GridPane.setHalignment(number, HPos.CENTER);
        //
        container.setHgap(0);
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

        GridPane.setHgrow(title, Priority.ALWAYS);
    }

    private void bind() {
        label.graphicProperty().bind(icon);
        number.textProperty().bind(Bindings.convert(this.numberOfNotifications));
        number.visibleProperty().bind(this.numberOfNotifications.greaterThan(0));
        this.title.textProperty().bindBidirectional(viewModel.nameProperty());
        title.disableProperty().bind(this.editable.not());
    }

    private void registerListeners() {
        this.number.textProperty().addListener((_, _, newValue) -> {
            if (newValue != null) {
                number.setMaxWidth(number.getText().length() * 25);
            }
        });

        this.editable.addListener((_, _, newVal) -> {
            if (newVal) {
                getScene().addPreLayoutPulseListener(() -> {
                    if (this.isSelected()) {
                        title.requestFocus();
                    }
                });
            }
        });

        this.focusWithinProperty().addListener((_, _, newVal) -> {
            if (!newVal && !this.getViewModel().isFixed()) {
                viewModel.setName(this.title.getText());
                this.setEditable(false);
                this.getViewModel().update();
            }
        });

        viewModel.iconNameProperty().addListener((_, _, newValue) -> {
            if (getViewModel().isFixed()) {
                icon.set(new SVGIcon(Icon.valueOf(newValue.toUpperCase()), 1.5));
            } else {
                icon.set(new ImageView(Assets.getIcon(newValue + ".png")));
            }
        });
    }

    private void setActions() {
        var context = new ListContextMenu(this);
        this.setOnContextMenuRequested(_ -> {

            var drawer = (SideNav) getScene().lookup("#drawer");
            drawer.select(viewModel);

            context.show(this, Side.TOP, 10, 0);

        });

        this.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (isSelected()) {
                e.consume();
            }
        });

        this.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            if (e.getCode() == KeyCode.ENTER) {
                this.setEditable(false);
                e.consume();
            }
        });
    }

    public Node getIcon() {
        return this.icon.get();
    }

    public IntegerProperty numberOfNotificationsProperty() {
        return this.numberOfNotifications;
    }

    public void setEditable(boolean val) {
        this.editable.set(val);
    }

    @Override
    public String toString() {
        return "DrawerItem{"
               + "\n\tviewModel=" + getViewModel() +
               "\n\teditable=" + editable +
               "\n}";
    }
}
