package com.example.aninterface;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import android.media.ExifInterface;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class CameraRecognition extends AppCompatActivity {

    private final int IMAGE_PICK = 100;
    ImageView imageView;
    Bitmap bitmap;
    Yolov5TFLiteDetector yolov5TFLiteDetector;
    Paint boxPaint = new Paint();
    Paint textPain = new Paint();
    TextView textview;
    Button camera;
    Button gallery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        requestNecessaryPermissions();
        imageView = findViewById(R.id.imageViewforcamera);
        textview = findViewById(R.id.textViewforcamera);
        camera = findViewById(R.id.Cameraforcamera);
        gallery = findViewById(R.id.Storageforcamera);
        yolov5TFLiteDetector = new Yolov5TFLiteDetector();
        yolov5TFLiteDetector.setModelFile("yolov5s-fp16.tflite");
        yolov5TFLiteDetector.initialModel(this);

        boxPaint.setStrokeWidth(5);
        boxPaint.setStyle(Paint.Style.STROKE);
        boxPaint.setColor(Color.RED);

        textPain.setTextSize(50);
        textPain.setColor(Color.GREEN);
        textPain.setStyle(Paint.Style.FILL);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    Uri photoURI = null;
                    try {
                        photoURI = createImageFile();
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                        // Explicitly check for camera availability
                        if (takePictureIntent.resolveActivity(getPackageManager()) == null) {
                            // No camera app available to handle the intent
                            return; // Early return if no camera is available
                        }

                        startActivityForResult(takePictureIntent, 12);
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        // Handle the error
                    }
                } else {
                    // No camera app available to handle the intent
                    // Optionally, alert the user that no camera app is available
                }
            }
        });



    }

    public void selectImage(View view){
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK);
    }

    private static final int REQUEST_PERMISSIONS = 1;

    private void requestNecessaryPermissions() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.CAMERA}, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // Permissions granted, you can continue with opening camera
            } else {
                // Permissions denied, handle accordingly
            }
        }
    }


    public void predict(View view){
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<Recognition> recognitions = yolov5TFLiteDetector.detect(bitmap);
        Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mutableBitmap);

        if (recognitions.isEmpty()) {
            // Update TextView to indicate that no objects were detected
            textview.setText("No objects detected.");
        } else {
            // Proceed with drawing bounding boxes and labels for detected objects
            for(Recognition recognition: recognitions){
                if(recognition.getConfidence() > 0.02){
                    RectF location = recognition.getLocation();
                    canvas.drawRect(location, boxPaint);
                    canvas.drawText(recognition.getLabelName() + ":" + recognition.getConfidence(), location.left, location.top, textPain);
                }
            }

            StringBuilder s = new StringBuilder();
            for (Recognition r : recognitions) {
                String labelId = r.getLabelName();
                if (map.containsKey(labelId)) {
                    map.put(labelId, map.get(labelId) + 1);
                } else {
                    map.put(labelId, 1);
                }
            }

            // Append information to StringBuilder for detected objects
            for (String key : map.keySet()) {
                Integer value = map.get(key);
                s.append(key).append(", Amount: ").append(value).append("\n");
            }

            // Set the processed image and the summary of detected objects
            imageView.setImageBitmap(mutableBitmap);
            textview.setText(s.toString());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK && data != null) {
                Uri uri = data.getData();
                try {
                    // Efficiently decode bitmap from selected image URI
                    bitmap = decodeSampledBitmapFromUri(getContentResolver(), uri, 800, 600); // Example size, adjust as needed
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == 12) {
                try {
                    // Efficiently decode bitmap from camera capture URI
                    bitmap = decodeSampledBitmapFromUri(getContentResolver(), photoURI, 800, 600); // Example size
                    bitmap = rotateImageIfRequired(CameraRecognition.this, bitmap, photoURI);
                    imageView.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private Uri photoURI;

    private Uri createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", image);
        return photoURI;
    }
    public static Bitmap decodeSampledBitmapFromUri(ContentResolver resolver, Uri uri, int reqWidth, int reqHeight) throws IOException {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream stream = resolver.openInputStream(uri);
        BitmapFactory.decodeStream(stream, null, options);
        if (stream != null) stream.close();

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        stream = resolver.openInputStream(uri);
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
        if (stream != null) stream.close();

        return bitmap;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap rotateImageIfRequired(Context context, Bitmap img, Uri selectedImage) throws IOException {

        InputStream input = context.getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    private static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        Bitmap rotatedImg = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
        img.recycle();
        return rotatedImg;
    }



}