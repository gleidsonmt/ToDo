package io.github.gleidsonmt.todo.bd.mysql;

import javafx.concurrent.Task;

import java.util.logging.Logger;

/**
 * Task to create and validate mysql files and directories.

 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Created on  29/04/2026
 */
@Deprecated
public class MySQLAssets  {

    private boolean exists = false;

    public boolean filesAlreadyExist() {
        return exists;
    }

    public void validateFilesAndDirectories() {

        Logger.getGlobal().fine("Starting mysql folder verification...");
        MySQLFolder mySQLFolder = new MySQLFolder();

        // used to fast delete directory to test if is creating or not
        mySQLFolder.delete();
        exists = mySQLFolder.exists();
        if (!exists) {
            Logger.getGlobal().finer("Creating mysql files and directories...");
            mySQLFolder.create();

            MySQLFile mySQLFile = new MySQLFile();

            Logger.getGlobal().finer("Creating init.sql config file...");
            mySQLFile.createInitFile(mySQLFolder.getDBFolder());

            Logger.getGlobal().finer("Creating my.ini config file...");
            mySQLFile.createMyIniFile(mySQLFolder.getDBFolder());

        }

        Logger.getGlobal().config("MySQL folder verification finished...");
    }
}
