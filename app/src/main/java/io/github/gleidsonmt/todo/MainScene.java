package io.github.gleidsonmt.todo;

import io.github.gleidsonmt.glad.base.Root;
import io.github.gleidsonmt.todo.events.LoginEvent;
import io.github.gleidsonmt.todo.view.MainView;
import io.github.gleidsonmt.todo.view.login.HomeLayout;
import io.github.gleidsonmt.todo.view_model.UserConverter;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on 15/06/2026
 */
public class MainScene extends Scene {

    public MainScene(Parent root, double w, double h) {
        super(root, w, h);

        addEventFilter(LoginEvent.LOGIN_SUCCESS, e -> {
            Root rt = (Root) this.getRoot();
//            rt.setContent(new MainView(new UserModel().getConverter().toViewModel(e.getUser())));
            rt.setContent(new MainView(new UserConverter().toViewModel(e.getUser())));
        });

        addEventFilter(LoginEvent.LOGIN_FAILED, e -> {
            Root rt = (Root) this.getRoot();
            rt.setContent(new HomeLayout(e.getUser()));
        });
    }
}
