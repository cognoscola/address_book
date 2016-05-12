package com.guillermo.addressbookapplication.network;

/**
 * Created by alvaregd on 12/05/16.
 */

import android.graphics.Bitmap;

import com.guillermo.addressbookapplication.libraries.RandomUserApi.entities.Result;

/**
 * A item representing a contact.
 */
public class ContactData {

    private Result data;
    private Bitmap thumbnailData;
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
