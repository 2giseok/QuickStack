package com.project.admin.security.handler;

public record LoginResponse(boolean success, int statusCode ,String message) {

    public static LoginResponse ok() {
        return new LoginResponse(true,200,"Sign-in was successful.");
    }

    public static LoginResponse fall() {
        return new LoginResponse(false,400,"Invalid password");
    }


}
