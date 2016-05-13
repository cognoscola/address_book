package com.guillermo.addressbookapplication.network;

import com.guillermo.addressbookapplication.libraries.RandomUserApi.entities.Result;

/**
 * Created by alvaregd on 12/05/16.
 * A item representing a contact.
 */
public class ContactData {

    private Result data;
    private String id;

    public ContactData(String id, Result data) {
        this.id = id;
        this.data = data;
    }

    public Result getData() {
        return data;
    }

    public String getId() {
        return id;
    }
}
