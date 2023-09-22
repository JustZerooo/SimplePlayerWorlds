package me.quadrato.simpleplayerworlds.mysql;

import java.sql.Connection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mysql {
    private ExecutorService exec = Executors.newSingleThreadExecutor();
    private Connection con;
    private String database;
    private String host;
    private String password;
    private int port;
    private String username;

    public mysql() {

    }
}
