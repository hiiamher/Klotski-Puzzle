package user;

import model.MapModel;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private MapModel model;
    //该变量用于记录用户最后的存储步数，与游戏实时步数无关
    private int steps;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getSteps() {
        return steps;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MapModel getModel() {
        return model;
    }

    public void setModel(MapModel model) {
        this.model = model;
    }


}
