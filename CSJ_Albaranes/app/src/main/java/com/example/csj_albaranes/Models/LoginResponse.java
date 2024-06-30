package com.example.csj_albaranes.Models;

public class LoginResponse {
    public boolean success;
    public Data data;

    public boolean isSuccess() {
        return success;
    }

    public Data getData() {
        return data;
    }
}
