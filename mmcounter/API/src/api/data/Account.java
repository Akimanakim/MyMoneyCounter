package api.data;

import java.io.Serializable;

public class Account implements Serializable {

    private String id;

    private String name;

    private String password;

    public Account(){}
    public Account(String id, String name, String password){
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getPassword(){
        return password;
    }

    public String getId() {
        return id;
    }
}
