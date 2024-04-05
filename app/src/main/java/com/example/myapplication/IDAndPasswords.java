package com.example.myapplication;
import java.util.HashMap;

public class IDAndPasswords {
    HashMap<String,String> originalLoginInfo = new HashMap<>();

    IDAndPasswords(){
        originalLoginInfo.put("Pizza", "Burger");
        originalLoginInfo.put("SEECS", "nerd");
        originalLoginInfo.put("Hello", "World");
    }

    protected HashMap<String, String> getLoginInfo(){
        return originalLoginInfo;
    }
}
