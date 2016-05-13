package com.guillermo.addressbookapplication.views;

//Taken from http://stackoverflow.com/questions/16208365/create-a-circular-image-view-in-android

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by alvaregd on 12/05/16.
 * Displays an Image in a Circle and also adds a white border
 */
public class RoundedImageView extends ImageView {

    private final static String Tag = "RoundedImageView ";
    private final static boolean d_onSizeChanged = false;

    public RoundedImageView(Context context) {
        super(context);
    }
    public RoundedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public RoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private int mRadius = 10;
    private int mHeight;

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();

        //  Log.e("onDraw()","Bitmap size:" + b.getByteCount());
        // Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();
        b = getRectangleBitmap(b);
        b  = getCroppedBitmap(b, mRadius);
        canvas.drawBitmap(b, 0, 0, null);
    }

    public static Bitmap getRectangleBitmap(Bitmap srcBmp){

        Bitmap dstBmp;
        try{
            if(srcBmp.getWidth() ==  srcBmp.getHeight() ){
                return srcBmp;
            }
            if (srcBmp.getWidth() >= srcBmp.getHeight()){

                dstBmp = Bitmap.createBitmap(
                        srcBmp,
                        srcBmp.getWidth()/2 - srcBmp.getHeight()/2,
                        0,
                        srcBmp.getHeight(),
                        srcBmp.getHeight()
                );
            }else{
                dstBmp = Bitmap.createBitmap(
                        srcBmp,
                        0,
                        srcBmp.getHeight()/2 - srcBmp.getWidth()/2,
                        srcBmp.getWidth(),
                        srcBmp.getWidth()
                );
            }
        }catch (OutOfMemoryError e){
            Log.e("OutofMemoryError",""+e.toString());
            dstBmp = srcBmp;
        }
        return dstBmp;
    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius )
            sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, true);
        else
            sbmp = bmp;
        Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
                sbmp.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xffa19774;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());
        //COnfigure border

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(false);

        //Make everything transparent
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(sbmp.getWidth() / 2 , sbmp.getHeight() / 2 ,
                sbmp.getWidth() / 2  - 5, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas.drawBitmap(sbmp, rect, rect, paint);

        //set the paint properties for the border
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(10);

        canvas.drawCircle(sbmp.getWidth() / 2, sbmp.getHeight() / 2 ,
                sbmp.getWidth() / 2 - 5, paint);
        return output;
    }

    public void setRadius(int radius){
        this.mRadius = radius;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(h, h, oldw, oldh);

        //    mHeight = h;'
        mRadius = h;
        if(d_onSizeChanged)Log.e(Tag, "onSizeChanged() W = " + w + ", h = " + h + ", oldw = " + oldw + ", oldh = " + oldh);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    public int getDiameter(){return this.mRadius;}
}
