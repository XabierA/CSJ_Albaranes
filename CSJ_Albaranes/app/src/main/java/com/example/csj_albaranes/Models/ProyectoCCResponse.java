package com.example.csj_albaranes.Models;

public class ProyectoCCResponse {
    public String description;
    public String[] errors;
    public String date_creation;
    public String[] merkle_root_ids;
    public String[] merkle_root_list;
    public String oid;
    public String owner;
    public String state;

    public String getDescription() {
        return description;
    }

    public String[] getErrors() {
        return errors;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public String[] getMerkle_root_ids() {
        return merkle_root_ids;
    }

    public String[] getMerkle_root_list() {
        return merkle_root_list;
    }

    public String getOid() {
        return oid;
    }

    public String getOwner() {
        return owner;
    }

    public String getState() {
        return state;
    }
}
