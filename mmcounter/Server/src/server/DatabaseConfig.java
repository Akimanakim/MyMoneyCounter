package server;

import java.io.Serializable;

public class DatabaseConfig implements Serializable{

    private String host = "127.0.0.1";
    private int port = 5432;
    private String base = "mmcounter";
    private String login = "postgres";
    private String password = "4589";

    public DatabaseConfig() {

    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getBase() {
        return base;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
