package com.Day8.SpringSecurityApp.SpringSecurityApp.exceptions;

public class ResourseNotFound extends  RuntimeException{

    public ResourseNotFound(String message){
        super(message);
    }

}
