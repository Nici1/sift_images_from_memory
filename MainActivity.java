package com.example.sift_app_15;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    final int PICK_IMAGE_1 = 15;
    final int PICK_IMAGE_2 = 16;

    private ImageView imgSpecimentPhoto;
    private ImageView imgSpecimentPhoto2;
    private Button takePhoto3;
    public Uri imageUri = null;
    public Uri imageUri2 = null;

    OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgSpecimentPhoto = findViewById(R.id.imageView);
        imgSpecimentPhoto2 = findViewById(R.id.imgSpecimentPhoto2);
        takePhoto3 = findViewById(R.id.takePhoto3);

        //OpenCVLoader.initDebug();

        takePhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSecond();
            }
        });

    }


    public void btnTakePhotoClicked(View v) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE_1);
    }

    public void btnTakePhotoClicked2(View v) {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE_2);
    }


    public void startSecond() {

        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
/*
        BitmapDrawable drawable = (BitmapDrawable) imgSpecimentPhoto.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        BitmapDrawable drawable2 = (BitmapDrawable) imgSpecimentPhoto2.getDrawable();
        Bitmap bitmap2 = drawable2.getBitmap();

        Mat mat = new Mat();
        Bitmap bmp32 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32, mat);

        Mat mat2 = new Mat();
        Bitmap bmp32_2 = bitmap2.copy(Bitmap.Config.ARGB_8888, true);
        Utils.bitmapToMat(bmp32_2, mat2);

*/
        intent.putExtra("picture", imageUri.toString()+"@"+imageUri2.toString());
        startActivity(intent);

        /*
        ImageProcessor obj = new ImageProcessor();
        Bundle b = new Bundle();
        b.putSerializable("picture", (Serializable) obj.run(mat,mat2));
        intent.putExtras(b );
        startActivity(intent);

         */


        /*
        BitmapDrawable drawable = (BitmapDrawable) imgSpecimentPhoto.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath()+"/Demo/");
        dir.mkdir();
        File file = new File(dir,"mace.jpg");
        try{
            outputStream = new FileOutputStream(file);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        Toast.makeText(getApplicationContext(),"Image saved",Toast.LENGTH_SHORT).show();
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
   */

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE_1) {

                imageUri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                    imgSpecimentPhoto.setImageBitmap(bitmap);
                }
                catch(IOException e){
                    Context context = getApplicationContext();
                    CharSequence text = "IOException!";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }
            }
                if (requestCode == PICK_IMAGE_2) {
                    imageUri2 = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri2);

                        imgSpecimentPhoto2.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Context context = getApplicationContext();
                        CharSequence text = "IOException!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }

            }
        }
    }
