package com.example.csj_albaranes.Models;

import java.io.Serializable;

public class ItemHashesAndMetadata implements Serializable {
    public String item_hash;
    public Metadata metadata;

    public ItemHashesAndMetadata(String item_hash, Metadata metadata) {
        this.item_hash = item_hash;
        this.metadata = metadata;
    }

    public String getItem_hash() {
        return item_hash;
    }

    public void setItem_hash(String item_hash) {
        this.item_hash = item_hash;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }
}
