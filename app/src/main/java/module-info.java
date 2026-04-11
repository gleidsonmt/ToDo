module io.github.gleidsonmt.todo {

    requires transitive javafx.controls;
    requires transitive javafx.graphics;
    requires transitive javafx.fxml;

    requires org.yaml.snakeyaml;
    requires java.sql;

    // Requer o módulo das anotações apenas durante a compilação
    requires static org.jetbrains.annotations;

    // Tools (only runtime) remove when not needed

    requires io.github.gleidsonmt.glad;

    requires javafx.swing;
    requires com.dlsc.gemsfx;

    requires com.github.weisj.jsvg;
    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.bootstrapicons;

    requires java.instrument;

    requires java.logging;
    requires java.base;
    requires javafx.base;

    opens io.github.gleidsonmt.todo to javafx.graphics, javafx.fxml, java.sql;
    opens io.github.gleidsonmt.todo.view to javafx.graphics, javafx.fxml, java.sql;
    opens io.github.gleidsonmt.todo.view.presentation to org.yaml.snakeyaml, javafx.graphics, javafx.fxml, java.sql;

//    opens io.github.gleidsonmt.todo.view.panel to org.scenicview.scenicview;
//
//    opens io.github.gleidsonmt.todo.view.nav to org.scenicview.scenicview;
//    opens io.github.gleidsonmt.todo.view.panel.items to org.scenicview.scenicview;
//    opens io.github.gleidsonmt.todo.view.panel.actions to org.scenicview.scenicview;
//    opens io.github.gleidsonmt.todo.view.panel.input to org.scenicview.scenicview;
//    opens io.github.gleidsonmt.todo.view.panel.sections to org.scenicview.scenicview;

    opens io.github.gleidsonmt.todo.model to javafx.base;

    exports io.github.gleidsonmt.todo;
    exports io.github.gleidsonmt.todo.view;
    exports io.github.gleidsonmt.todo.model;
    exports io.github.gleidsonmt.todo.view_model;
    exports io.github.gleidsonmt.todo.view.nav;
    exports io.github.gleidsonmt.todo.view.panel;
    exports io.github.gleidsonmt.todo.view.panel.items;
    exports io.github.gleidsonmt.todo.view.panel.containers;

}
