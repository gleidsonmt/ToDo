package io.github.gleidsonmt.todo.view.nav;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.glad.controls.icon.SVGIcon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.ListPresenter;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.view.panel.menu.ListContextMenu;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;

/**
 * Description:
 *
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 *         Created On: Mar 20, 2026
 * 
 *         Version History: Initial version
 */
public class CustomDrawerItemNew extends ToggleButton {

    // Components
    private Pane iconSelector = new Pane();
    private SVGIcon svgIcon = new SVGIcon();
    private TextField title = new TextField();
    private Label number = new Label();

    private ObjectProperty<Icon> icon = new SimpleObjectProperty<>();

    private final GridPane container = new GridPane();

    private final BooleanProperty fixed = new SimpleBooleanProperty(false);
    private final IntegerProperty numberOfNotifications = new SimpleIntegerProperty(0);
    private final BooleanProperty editable = new SimpleBooleanProperty(false);

    // test
    private BooleanProperty update = new SimpleBooleanProperty(false);

    private ListViewModel viewModel;

    public CustomDrawerItemNew(ListViewModel viewModel) {
        this(viewModel, false);
    }

    public CustomDrawerItemNew(ListViewModel viewModel, boolean fixed) {
        this.viewModel = viewModel;
        this.fixed.set(fixed);
        this.setGraphic(container);

        init();
        configLayout();
        bind();
        registerListeners();
        setActions();
    }

    public void updateNotifications() {
        ListPresenter presenter = (ListPresenter) Global.get(List.class);
        this.numberOfNotifications.set(presenter.size(this.getViewModel().getId()));
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
        number.getStyleClass().addAll("sub", "h5", "bold", "font-instagram-medium");

        iconSelector.setMaxSize(5, 30);
        iconSelector.getStyleClass().add("icon-selector");

        container.getChildren().setAll(iconSelector, svgIcon, title, number);
    }

    private void configLayout() {

        this.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

        GridPane.setColumnIndex(iconSelector, 0);
        GridPane.setHalignment(iconSelector, HPos.LEFT);
        GridPane.setColumnIndex(svgIcon, 1);
        GridPane.setHalignment(svgIcon, HPos.CENTER);
        GridPane.setColumnIndex(title, 2);
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

        GridPane.setHgrow(title, Priority.ALWAYS);

    }

    private void bind() {
        svgIcon.iconProperty().bind(viewModel.iconProperty());
        number.textProperty().bind(Bindings.convert(this.numberOfNotifications));
        number.visibleProperty().bind(this.numberOfNotifications.greaterThan(0));
        this.title.textProperty().bindBidirectional(viewModel.nameProperty());
        title.disableProperty().bind(this.editable.not());
    }

    private void registerListeners() {
        this.number.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                number.setMaxWidth(number.getText().length() * 25);
            }
        });

        this.editable.addListener((_, _, newVal) -> {
            if (newVal) {
                getScene().addPreLayoutPulseListener(() -> {
                    title.requestFocus();
                });
            }
        });

        this.focusWithinProperty().addListener((_, _, newVal) -> {
            if (!newVal) {
                viewModel.setName(this.title.getText());
                this.setEditable(newVal);
                // getViewList().getList().setName(this.text.getText());
                // ListPresenter presenter = (ListPresenter)
                // Global.get(List.class);
                // presenter.update(getViewList().getList());
            }
        });
    }

    private void setActions() {
        var context = new ListContextMenu(this);
        this.setOnContextMenuRequested(e -> {

            var drawer = (SideNavNew) getScene().lookup("#drawer");
            drawer.select(viewModel);

            context.show(this, Side.TOP, 10, 0);

        });

        this.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            if (isSelected()) {
                e.consume();
            }
        });
    }

    public void setEditable(boolean val) {
        this.editable.set(val);
    }

}
