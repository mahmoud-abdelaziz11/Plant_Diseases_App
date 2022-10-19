package com.example.plantdiseasesapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.plantdiseasesapp.ml.PepperModel;
import com.example.plantdiseasesapp.ml.PotatoModel;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PepperActivity extends AppCompatActivity {
    Button selectBtn,predictBtn,captureBtn,aboutBtn;
    TextView result,title;
    ImageView imageView;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheat);

        getWindow().setStatusBarColor(ContextCompat.getColor(PepperActivity.this, R.color.green));

        getPermission();

        String [] labels = new String[10];
        int cnt = 0;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getAssets().open("pepperlabels.txt")));
            String line = bufferedReader.readLine();
            while (line!=null){
                labels[cnt] = line;
                cnt++;
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        selectBtn=findViewById(R.id.selectBtn);
        captureBtn= findViewById(R.id.captureBtn);
        predictBtn=findViewById(R.id.predictBtn);
        aboutBtn=findViewById(R.id.aboutBtn);
        result=findViewById(R.id.result);
        title=findViewById(R.id.title);
        imageView=findViewById(R.id.imageView);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 10);
            }
        });

        captureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,12);
            }
        });

        predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    PepperModel model = PepperModel.newInstance(PepperActivity.this);

                    // Creates inputs for reference.
                    TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 256, 256, 3}, DataType.FLOAT32);
                    bitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true);
                    TensorImage image=new TensorImage(DataType.FLOAT32);
                    image.load(bitmap);
                    inputFeature0.loadBuffer(image.getBuffer());

                    // Runs model inference and gets result.
                    PepperModel.Outputs outputs = model.process(inputFeature0);
                    TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
                    result.setText(labels[getMax(outputFeature0.getFloatArray())] +"") ;
                    text = String.valueOf(result.getText());

                    // Releases model resources if no longer used.
                    model.close();
                } catch (IOException e) {
                    // TODO Handle the exception
                }



            }
        });


    }

    String text;
    int getMax(float[] arr){
        int max = 0;
        for(int i=0; i<arr.length;i++){
            if(arr[i]>arr[max]) max = i;
        }
        return max;
    }

    void getPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(PepperActivity.this,new String[]{Manifest.permission.CAMERA},11);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 11) {
            if(grantResults.length>0){
                if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 10) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), (uri));
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==12){
            bitmap=(Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

