package com.guillermo.addressbookapplication.network;

/**
 * Created by alvaregd on 12/05/16.
 * To listen for when we are done retrieving data from the Internet
 */
public interface ContactRetrieverListener {

    void onDataReceived();
}
