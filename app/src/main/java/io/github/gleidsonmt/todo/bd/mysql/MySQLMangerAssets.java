package io.github.gleidsonmt.todo.bd.mysql;

import javafx.concurrent.Task;

import java.util.logging.Logger;

/**
 *
 * @author Gleidson Neves da Silveira | <gleidisonmt@gmail.com>
 * Created on  29/04/2026
 */
public class MySQLMangerAssets extends Task<Boolean> {

    @Override
    protected void failed() {
        Logger.getGlobal().severe("Error on managing mysql files: " + this.getException());
        super.failed();
    }

    @Override
    protected Boolean call() {

        updateMessage("Verificando pasta de banco de dados...");
        Logger.getGlobal().fine("Starting mysql folder verification...");
        MySQLFolder mySQLFolder = new MySQLFolder();

        // used to fast delete directory to test if is creating or not
//        mySQLFolder.delete();
        updateMessage("Pasta de banco de dados deletada...");

        if (!mySQLFolder.exists()) {
            Logger.getGlobal().finer("Creating mysql files and directories...");
            mySQLFolder.create();

            MySQLFile mySQLFile = new MySQLFile();
            Logger.getGlobal().finer("Creating init.sql config file...");
            mySQLFile.createInitFile(mySQLFolder.getDBFolder());
            Logger.getGlobal().finer("Creating my.ini config file...");
            mySQLFile.createMyIniFile(mySQLFolder.getDBFolder());
            updateMessage("Criando arquivos de configuração...");
            return true;
        }
        updateMessage("Arquivos configurados com sucesso!");
        Logger.getGlobal().config("MySQL folder verification finished...");
        return false;
    }

    public boolean filesCreated() {
        return this.getValue();
    }

}
