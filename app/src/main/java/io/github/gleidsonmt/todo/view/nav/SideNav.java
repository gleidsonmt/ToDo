package io.github.gleidsonmt.todo.view.nav;

import io.github.gleidsonmt.todo.bd.dao.DaoList;
import io.github.gleidsonmt.todo.model.List;
import io.github.gleidsonmt.todo.model.ListType;
import io.github.gleidsonmt.todo.view.panel.items.options.Option;
import io.github.gleidsonmt.todo.view_model.ListViewModel;
import io.github.gleidsonmt.todo.view_model.TaskViewModel;
import io.github.gleidsonmt.todo.view_model.UserViewModel;
import io.github.gleidsonmt.todo.view_model.map.ListMapper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.Optional;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created On: Mar 20, 2026
 */
public class SideNav extends VBox {

    private ToggleGroup group;
    private ObjectProperty<DrawerItem> selected;
    private VBox smartListsContainer;
    private VBox container;

    private final Header header;

    private Label itemSendoArrastado = null;

    private int index = 0;

    public SideNav(UserViewModel user) {
        this.header = new Header(user);
        init();
        configLayout();
        bind();


//
        container.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
//                    // Aqui você recupera o item original ou cria um novo
//                    Label novoItem = criarItem(db.getString());
//
//                    int indexDrop = container.getChildren().indexOf(placeholder);
//                    container.getChildren().remove(placeholder); // Remove o espaço
//
//                    if (indexDrop != -1) {
//                        container.getChildren().add(indexDrop, novoItem);
//                    } else {
//                        container.getChildren().add(novoItem);
//                    }
                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });

        WritableImage imgTransparente = new WritableImage(1, 1);
        ImageCursor cursorInvisivel = new ImageCursor(imgTransparente);
//
        container.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();

                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }

                if (event.getDragboard().hasString()) {
//                    event.acceptTransferModes(TransferMode.MOVE);
//
//                    // Descobre o índice correto com base na posição Y do mouse
                    int targetIndex = calcularIndicePorY(container, event.getY());

                    Optional<Node> optional = container.getChildren().stream().filter(node -> node instanceof Label).findAny();
                    if (optional.isEmpty()) return;

                    if (index != targetIndex) {

                        container.getChildren().remove(optional.get());
                        if (targetIndex > container.getChildren().size()) {
                            container.getChildren().add(container.getChildren().size(), optional.get());
                            index = container.getChildren().size()-1 ;

                        } else {
                            container.getChildren().add(targetIndex, optional.get());
                            index = targetIndex;

                        }
//                        container.getChildren().set(index, optional.get());
                    }
//
//                    // Evita recalcular e mover se já estiver na posição correta
//                    if (!container.getChildren().contains(placeholder) || container.getChildren().indexOf(placeholder) != targetIndex) {
//                        container.getChildren().remove(placeholder);
//
//                        // Garante que o índice não estoure os limites atuais
//                        int safeIndex = Math.min(targetIndex, container.getChildren().size());
//
//                        container.getChildren().add(safeIndex, placeholder);
//                    }
                }
                event.consume();

            }
        });

        setOnDragExited(event -> {
        });

        container.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean sucesso = false;

            Optional<Node> optional = container.getChildren().stream().filter(node -> node instanceof Label).findAny();
            if (optional.isEmpty()) return;
            Label label = (Label) optional.get();
            int index = container.getChildren().indexOf(optional.get());

            DrawerItem drawerItem = (DrawerItem) label.getLabelFor();
            drawerItem.setIndex(index);
            container.getChildren().remove(optional.get());
            container.getChildren().add(index, drawerItem);
            event.setDropCompleted(sucesso);
            event.consume();
        });

    }

    final Region placeholder = new Region();

    private int calcularIndicePorY(VBox vbox, double mouseY) {
        double acumuladoY = vbox.getPadding().getTop();
        int spacing = (int) vbox.getSpacing();

        for (int i = 0; i < vbox.getChildren().size(); i++) {
            var node = vbox.getChildren().get(i);

            // Ignora o placeholder e o item invisível no cálculo de posições reais
            if (node == placeholder || node == itemSendoArrastado) continue;

            double nodeHeight = node.getBoundsInParent().getHeight();

            // Se o mouse passou da metade do item atual, a intenção é inserir ali
            if (mouseY < acumuladoY + (nodeHeight / 2)) {
                return i;
            }
            acumuladoY += nodeHeight + spacing;
        }
        return vbox.getChildren().size();
    }

    private void init() {
        this.group = new ToggleGroup();
        this.container = new VBox();
        this.smartListsContainer = new VBox();
        this.selected = new SimpleObjectProperty<>();
    }

    private void configLayout() {
        this.setId("drawer");
        this.getChildren().setAll(header, smartListsContainer, new Separator(), container);
    }

    private void bind() {
        selected.bind(group.selectedToggleProperty().map(e -> (DrawerItem) e));
        VBox.setVgrow(container, Priority.ALWAYS);
    }

    public void load() {
        DaoList dao = new DaoList();
        Task<ObservableList<List>> task = dao.fetch(FXCollections.observableArrayList());

        new Thread(task).start();

        task.setOnSucceeded(_ -> {
            task.getValue().forEach(this::createItem);


            this.getChildren().add(new Footer());
            selectFirst();
        });
    }

    public void select(ListViewModel list) {
        Optional<DrawerItem> optional = group.getToggles().stream().map(e -> (DrawerItem) e)
                .filter(el -> el.getViewModel().getId() == list.getId()).findFirst();

        optional.ifPresent(drawerItem -> group.selectToggle(drawerItem));
    }

    public void selectFirst() {
        group.selectToggle(group.getToggles().getFirst());
    }

    @Deprecated
    public DrawerItem get(TaskViewModel model) {
        Optional<DrawerItem> optional = group.getToggles().stream().map(e -> (DrawerItem) e)
                .filter(el -> el.getViewModel().getId() == model.getListID()).findFirst();

        return optional.orElse(null);
    }

    public ListViewModel get(long id) {
        Optional<ListViewModel> optional = group.getToggles().stream().map(e -> (DrawerItem) e)
                .map(DrawerItem::getViewModel).filter(viewModel -> viewModel.getId() == id).findFirst();

        return optional.orElse(null);
    }

    public ListViewModel get(ListType type) {
        Optional<ListViewModel> optional = group.getToggles().stream().map(e -> (DrawerItem) e)
                .map(DrawerItem::getViewModel).filter(viewModel -> viewModel.getType() == type).findFirst();

        return optional.orElse(null);
    }

    public java.util.List<ListViewModel> getModels() {
        return group.getToggles().stream().filter(el -> el instanceof DrawerItem)
                .map(e -> (DrawerItem) e).map(DrawerItem::getViewModel).toList();
    }


    public ListViewModel add(ListViewModel model) {
        return createItem(model);
    }

    public void addAndSelect(ListViewModel model) {
        var drawerItem = createDrawerItem(model);
        group.getToggles().add(drawerItem);
        container.getChildren().add(drawerItem);
        drawerItem.setSelected(true);
        drawerItem.setEditable(!model.isFixed());
    }

    public void remove(ListViewModel model) {
        Optional<DrawerItem> optional = group.getToggles().stream().map(e -> (DrawerItem) e)
                .filter(el -> el.getViewModel().getId() == model.getId()).findFirst();

        optional.ifPresent(e -> container.getChildren().remove(e));
    }

    private void createItem(List list) {
        createItem(ListMapper.INSTANCE.toViewModel(list, new ListViewModel()));
    }

    private DrawerItem createDrawerItem(ListViewModel viewModel) {
        DrawerItem drawerItem = new DrawerItem(viewModel);
        drawerItem.numberOfNotificationsProperty().bind(viewModel.sizeProperty());
        return drawerItem;
    }

    private ListViewModel createItem(ListViewModel viewModel) {
        DrawerItem drawerItem = createDrawerItem(viewModel);

        group.getToggles().add(drawerItem);

        if (viewModel.isFixed()) {
            // // List completed = new List(I18n.get("drawer.list.completed"),
// ListType.COMPLETED, Icon.DONE_CIRCLE, true);
// // List all = new List(I18n.get("drawer.list.all"), ListType.ALL,
// Icon.DONE_ALL, true);
            smartListsContainer.getChildren().add(drawerItem);
//            drawerItem.graphicProperty().bind(viewModel.iconProperty());

        } else {
            container.getChildren().add(drawerItem.getIndex() > container.getChildren().size()-1 ? container.getChildren().size() : drawerItem.getIndex(), drawerItem);
//            drawerItem.graphicProperty().bind(viewModel.iconProperty());
        }

        // drawerItem.setEditable(!viewModel.isFixed());

        return viewModel;
    }

    public ObjectProperty<DrawerItem> itemSelectedProperty() {
        return selected;
    }
}