

package io.github.gleidsonmt.todo.bd.mysql;

import io.github.gleidsonmt.todo.App;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <a href="mailto:gleidisonmt@gmail.com">gleidisonmt@gmail.com</a> <br>
 * Created on  30/04/2026
 */
@Deprecated
public enum MySQLProperties   {
    INSTANCE;

    private final Properties properties = new Properties();

    public void read() {
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String getUser() {
        return get("user");
    }

    public String getPassword() {
        return get("password");
    }

    public String getDatabase() {
        return get("database");
    }

}
