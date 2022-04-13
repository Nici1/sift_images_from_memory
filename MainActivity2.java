package com.example.sift_app_15;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity2 extends AppCompatActivity {

    public ImageView imageView;
    public Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imageView=findViewById(R.id.imageView);



            if (OpenCVLoader.initDebug()) {

                Log.d("Check", "OpenCv configured successfully");

            } else {

                Log.d("heck", "OpenCv doesn’t configured successfully");

            }


        String uris [] =getIntent().getExtras().getString("picture").split("@",2);

        try {
            Bitmap refImage1 = getBitmapFromUri(Uri.parse(uris[0]));
            Bitmap refImage2 = getBitmapFromUri(Uri.parse(uris[1]));
            // Resizing an image
            int width1 = refImage1.getWidth();
            int height1 = refImage1.getHeight();
            int width2 = refImage2.getWidth();
            int height2 = refImage2.getHeight();
            Bitmap img_1 = Bitmap.createScaledBitmap(refImage1,width1/2,height1/2,true);
            Bitmap img_2 = Bitmap.createScaledBitmap(refImage2,width2/2,height2/2,true);
            Mat refImage1_mat = new Mat();
            Mat refImage2_mat = new Mat();
            Bitmap bmp32_1 = img_1.copy(Bitmap.Config.ARGB_8888, true);
            Bitmap bmp32_2 = img_2.copy(Bitmap.Config.ARGB_8888, true);
            Utils.bitmapToMat(bmp32_1, refImage1_mat);
            Utils.bitmapToMat(bmp32_2, refImage2_mat);

            Mat pic=ImageProcessor.run(refImage1_mat,refImage2_mat);
            Bitmap display = convertMatToBitMap(pic);

            imageView.setImageBitmap(display);
            //imageView.setImageURI(Uri.parse(uris[0]));
        } catch (Exception e) {
            Context context = getApplicationContext();
            CharSequence text = "Exception!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            e.printStackTrace();
        }

    }

    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

    public static Bitmap decodeUriToBitmap(Context mContext, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = mContext.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }

    private static Bitmap convertMatToBitMap(Mat input){
        Bitmap bmp = null;
        Mat rgb = new Mat();
        Imgproc.cvtColor(input, rgb, Imgproc.COLOR_BGR2RGB);

        try {
            bmp = Bitmap.createBitmap(rgb.cols(), rgb.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(rgb, bmp);
        }
        catch (CvException e){
            Log.d("Exception",e.getMessage());
        }
        return bmp;
    }




}