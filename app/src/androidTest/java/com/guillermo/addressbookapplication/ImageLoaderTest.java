package com.guillermo.addressbookapplication;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.widget.ImageView;

import com.guillermo.addressbookapplication.libraries.ImageLoader.ImageLoader;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

;

/**
 * Created by alvaregd on 13/05/16.
 */
public class ImageLoaderTest  extends AndroidTestCase{

   /* private CountDownLatch lock = new CountDownLatch(1);
    private ImageView imageViewResult = new ImageView(getContext());
    private final ImageLoader loader = new ImageLoader(getContext());
    private final String URL = "http://i.imgur.com/y7Hm9.jpg";

    public ImageLoaderTest() {
        super();
    }

    @SmallTest
    public void testImageLoaderFetch() throws Exception {

//        loader.DisplayImage(URL,imageViewResult);
//        lock.countDown();
//
//        lock.await(2000, TimeUnit.MILLISECONDS);
        assertNotNull(imageViewResult.getDrawable());
    }*/

}
