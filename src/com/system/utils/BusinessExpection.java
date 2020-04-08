package com.system.utils;

public class BusinessExpection extends RuntimeException {

    public BusinessExpection() {
    }

    public BusinessExpection(String errorMessage){
        super(errorMessage);
    }

}
