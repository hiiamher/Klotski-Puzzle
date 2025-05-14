package controller;
import SaveAndRead.SavaAndRead;
import java.io.File;
import SaveAndRead.SavaAndRead;
import java.util.List;

import static SaveAndRead.SavaAndRead.Read;
import static SaveAndRead.SavaAndRead.isExist;

public class UserController {

    public static boolean validateUser(String username, String password) {
        String mappath = String.format("save/%s/mapdata.txt", username);
        String passwordpath = String.format("save/%s/passworddata.txt", username);
        List<String> passwordLines = Read(passwordpath);
        StringBuilder sb = new StringBuilder();
        for (String line : passwordLines) {
            sb.append(line);
        }
        String storedPassword = sb.toString();
        System.out.println(storedPassword);
        if(isExist(passwordpath)){
            if(storedPassword.equals(password)){
                System.out.println("Username and password are the same");
                return true;
            }
        }
        System.out.println("Username and password are not the same");
        return false;
    }









}