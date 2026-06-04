

package io.github.gleidsonmt.todo.view.login.signup;

import io.github.gleidsonmt.glad.controls.icon.Icon;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.view.login.Footer;
import io.github.gleidsonmt.todo.view.login.Header;
import io.github.gleidsonmt.todo.view.login.HomeLayout;
import io.github.gleidsonmt.todo.view.login.LoginView;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Create on 16/02/2026
 */
public class SignupView extends StackPane {

    private final Header header;
    private final Footer footer;

    public SignupView() {
        this(true);
    }

    public SignupView(boolean needsFooter) {
        var body = new GridPane();
        body.setPrefWidth(600);
        body.setMaxWidth(600);
        body.setPrefHeight(550);
        body.setMaxHeight(550);

        getChildren().add(body);
        body.setPadding(new Insets(20));
        this.header = new Header(Icon.APP_REGISTRATION, "Sign In",
                "Enter your details below to create your perfil and get started.");
        body.getChildren().add(this.header);
        //
        footer = new Footer("Sign In", needsFooter);
        footer.setLinkText("Log In");
        footer.setInfoText("Already have an account?");
        body.getChildren().add(this.footer);

        footer.setLinkAction(e -> {
            ((HomeLayout)getScene().lookup("#home-layout")).setLeft(new LoginView());
        });

        //
        SignUpForm form = createForm();

        body.getChildren().add(form);
        GridPane.setVgrow(form, Priority.ALWAYS);
        GridPane.setMargin(form, new Insets(10));
        //
        GridPane.setRowIndex(this.header, 0);
        GridPane.setRowIndex(form, 1);
        GridPane.setRowIndex(this.footer, 2);
        //
        GridPane.setHgrow(header, Priority.ALWAYS);
    }

    private SignUpForm createForm() {
        SignUpForm form = new SignUpForm();
        footer.setButtonAction(e -> {
            if (form.validate()) {
                form.persist();
//                    Dialog
            }

//            var test = new Button("Continue");
//
//            Root root = (Root) this.getScene().getRoot();
//
//            root.behavior()
//                    .alert()
//                    .width(500)
//                    .height(300)
//                    .block()
////                    .title("Well Just one thing, 🙂")
//                    .title("Apenas uma coisinha")
//                    .type("info")
//                    .content(createTermsFlow())
//                    .buttons(test)
//                    .show();
//        });
        // footer.setButtonAction(e -> {
        // if (form.validate()) {
        // if (form.persist()) {
        // Root root = (Root) this.getScene().lookup("#root");
        // root.setLayout(new MainView(form.get()));
        // } else {
        // DialogUtils.openErrorDialog((View) this.getScene().getRoot(),
        // form.getError());
        // }
        //
        // }
        // });
        // footer.setLinkAction(e -> {
        // View view = (View) this.getScene().getRoot();
        // view.setLayout(new LoginWrapper(new LoginContainer()));
         });
        return form;
    }

    public TextFlow createTermsFlow() {
        Text texto1 = new Text("Ao cadastrar-se, você concorda com os ");

        Hyperlink linkTerms = new Hyperlink("Termos de Uso");
//        linkTermos.setOnAction(e -> abrirNavegador("https://seuapp.com"));
//        linkTerms.setOnAction(e -> Global.openLink("https://github.com/gleidsonmt/ToDo?tab=GPL-3.0-1-ov-file"));

        Text texto2 = new Text(" e a licença ");

        Hyperlink linkGpl = new Hyperlink("GNU GPLv3");
        // Link oficial da GPLv3 para o usuário ler as regras de software livre
        linkGpl.setOnAction(e -> Global.openLink("https://github.com/gleidsonmt/ToDo?tab=GPL-3.0-1-ov-file"));

        Text texto3 = new Text(".");

        // Junta tudo em uma linha fluida
        TextFlow textFlow = new TextFlow(texto1, linkTerms, texto2, linkGpl, texto3);
        textFlow.setMaxWidth(300); // Ajuste conforme o layout da sua tela
        textFlow.setTextAlignment(TextAlignment.CENTER);
        return textFlow;
    }
}
