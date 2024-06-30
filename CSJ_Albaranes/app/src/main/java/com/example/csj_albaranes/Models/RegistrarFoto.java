package com.example.csj_albaranes.Models;

import java.io.Serializable;

public class RegistrarFoto implements Serializable {
    public ItemHashesAndMetadata item_hashes_and_metadata;
    public String smart_check_merkle_id;

    public RegistrarFoto(ItemHashesAndMetadata item_hashes_and_metadata, String smart_check_merkle_id) {
        this.item_hashes_and_metadata = item_hashes_and_metadata;
        this.smart_check_merkle_id = smart_check_merkle_id;
    }

    public ItemHashesAndMetadata getItem_hashes_and_metadata() {
        return item_hashes_and_metadata;
    }

    public void setItem_hashes_and_metadata(ItemHashesAndMetadata item_hashes_and_metadata) {
        this.item_hashes_and_metadata = item_hashes_and_metadata;
    }

    public String getSmart_check_merkle_id() {
        return smart_check_merkle_id;
    }

    public void setSmart_check_merkle_id(String smart_check_merkle_id) {
        this.smart_check_merkle_id = smart_check_merkle_id;
    }
}
