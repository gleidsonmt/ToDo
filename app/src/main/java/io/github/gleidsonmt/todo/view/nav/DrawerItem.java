

package io.github.gleidsonmt.todo.view.nav;

import io.github.gleidsonmt.glad.base.Drawer;
import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.utils.Assets;
import io.github.gleidsonmt.todo.utils.I18n;
import io.github.gleidsonmt.todo.view.panel.input.menu.ListContextMenu;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.converter.ListNameConverter;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.util.StringConverter;

import java.util.Locale;
import java.util.Properties;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
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
    private final ObjectProperty<Node> icon = new SimpleObjectProperty<>();
    // Models
    private final ListViewModel viewModel;

    private ObjectProperty<Integer> index = new SimpleObjectProperty<>();

    private final Preferences prefs = Preferences.userNodeForPackage(DrawerItem.class);

    public DrawerItem(ListViewModel viewModel) {
        this.viewModel = viewModel;
        try {
            prefs.sync();
        } catch (BackingStoreException e) {
            throw new RuntimeException(e);
        }
        setIndex(prefs.getInt("index-" + viewModel.getId(), 0));

        icon.setValue(
                viewModel.isFixed() ?
                new SVGIcon(Icon.valueOf(viewModel.getIcon().toUpperCase())) :
                Assets.getIcon(viewModel.getIcon().toLowerCase() , 22)
        );

        this.setGraphic(container);

        init();
        configLayout();
        bind();
        registerListeners();
        setActions();

        indexProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
                prefs.put("index-" + viewModel.getId(), newValue.toString());
                try {
                    prefs.flush();
                    prefs.sync();
                } catch (BackingStoreException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        this.snapshot(new SnapshotParameters(), new WritableImage(100, 100));

        Label empty = new Label();
        empty.setMaxWidth(Double.MAX_VALUE);
        VBox.setMargin(empty, new Insets(10));
//        empty.setPrefWidth(Double.MAX_VALUE);
        empty.getStyleClass().addAll("custom-drawer-item", "rounded");
//        DrawerItem empty = new DrawerItem(new ListViewModel());
        empty.setStyle("-fx-background-color: derive(gray, 100%);");
        empty.setLabelFor(this);

        WritableImage imgTransparente = new WritableImage(1, 1);
        ImageCursor cursorInvisivel = new ImageCursor(imgTransparente);

        setOnDragDone(_ -> {
            // if the drag isn't finished successfully, return this to the container.
            if (empty.getParent() != null) {
                VBox container = (VBox) empty.getParent();
                int index = container.getChildren().indexOf(empty);
                container.getChildren().remove(empty);
                container.getChildren().add(index, this);
            }
        });

        setOnDragDetected(event -> {

            if (getViewModel().isFixed()) return;

            Dragboard db = startDragAndDrop(TransferMode.COPY_OR_MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(getParent().getChildrenUnmodifiable().indexOf(DrawerItem.this)));

            db.setDragView(snapshot(new SnapshotParameters(), new WritableImage(300, 35)), 50, 0);

            db.setContent(content);

            Pane region = (Pane) getParent();
            int index = region.getChildren().indexOf(DrawerItem.this);
            region.getChildren().removeIf(node -> node == DrawerItem.this);
            region.getChildren().add(index, empty);
            event.consume();
        });
    }

    public ListViewModel getViewModel() {
        return this.viewModel;
    }

    public void setIndex(Integer index) {
        this.index.set(index);
    }

    public Integer getIndex() {
        return index.get();
    }

    public ObjectProperty<Integer> indexProperty() {
        return index;
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
        title.textProperty().bindBidirectional(viewModel.nameProperty(), new ListNameConverter());
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

        viewModel.iconProperty().addListener((_, _, newValue) -> {
            if (getViewModel().isFixed()) {
                icon.set(new SVGIcon(Icon.valueOf(newValue.toUpperCase()), 1.5));
            } else {
                icon.set(Assets.getIcon(newValue , 22));
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
