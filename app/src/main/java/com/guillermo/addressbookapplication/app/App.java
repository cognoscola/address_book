package com.guillermo.addressbookapplication.app;

import android.app.Application;

import com.guillermo.addressbookapplication.libraries.ImageLoader.ImageLoader;

/**
 * Created by alvaregd on 12/05/16.
 */
public class App extends Application {

    /**This object needs to be treated as a singleton **/
    private static ImageLoader imageLoader;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public  ImageLoader getImageLoader(){

        if (imageLoader == null) {
            imageLoader = new ImageLoader(this);
        }
        return imageLoader;
    }
}
