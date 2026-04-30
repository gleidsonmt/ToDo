package io.github.gleidsonmt.todo.bd.mysql;

import io.github.gleidsonmt.todo.App;
import io.github.gleidsonmt.todo.bd.DatabaseConnection;
import io.github.gleidsonmt.todo.global.Global;
import io.github.gleidsonmt.todo.global.UserPresenter;
import io.github.gleidsonmt.todo.model.User;
import javafx.application.Platform;
import javafx.concurrent.Task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  27/04/2026
 */
public class Setup extends Task<User> {


    private static final String APP_DIR = System.getProperty("user.home") + File.separator + "todo";
    private static final String DB_CONFIG_FILE = APP_DIR + File.separator + "my.ini";
    private static final String CONFIG_FILE = APP_DIR + File.separator + "config.properties";

    private Logger logger = Logger.getGlobal();

    private User user;

    public static Properties getDatabaseProperties() {

        Properties properties = new Properties();
        try {
            // Loading properties
            InputStream file = App.class.getResourceAsStream("properties/db.properties");

            if (file == null) {
                Logger.getGlobal().severe("File properties/db.properties not found");
                throw new RuntimeException("File properties/db.properties not found.");
            }

            properties.load(file);

            if (properties.isEmpty()) {
                Logger.getGlobal().severe("File propertis/db.properties is empty");
                throw new RuntimeException("Loading database properties");
            }
            return properties;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected User call() {

        MySQLManagerService mySqlManager = new MySQLManagerService();
        mySqlManager.setOnFailed(e -> {
            System.out.println("Failed" + e.getSource().getException());
        });

        mySqlManager.setOnSucceeded(e -> {
//            messageProperty().unbind();
            MySQLStartService startService = new MySQLStartService();
            startService.start();
//
            startService.setOnFailed(ex -> {
                System.out.println("ex = " + ex);
            });
            ;
//
            startService.setOnSucceeded(e2 -> {
                MySqlConnectService connect = new MySqlConnectService();
                connect.setOnRunning(x -> {
                    if (DatabaseConnection.INSTANCE.hasConnection()) {

                        UserPresenter presenter = (UserPresenter) Global.get(User.class);
                        Optional<User> optional = presenter.getLogged();
                        if (optional.isPresent()) {
                            user = optional.get();
//                            return user.get();
                            Platform.runLater(() -> {
//                                Event.fireEvent(HomeView, LoginEvent.ALL);
                            });
                        }
                        System.out.println("user = " + user);
//                        Root root = new Root(user.isPresent() ? new MainView(user.get()) : new HomeLayout());
                    }
                });
                connect.start();

//
            });


        });
        mySqlManager.start();

        Platform.runLater(() -> {
//            messageProperty().bind(mySqlManager.messageProperty());

        });
        return null;
    }


}