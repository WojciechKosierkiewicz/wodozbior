package com.example.wodozbior.repository;

public class DBFacade {
    static DBFacade instance;
    private DBFacade(){
        //TODO
    }

    public static DBFacade getInstance(){
        if(instance == null){
            instance = new DBFacade();
        }
        return instance;
    }
}
