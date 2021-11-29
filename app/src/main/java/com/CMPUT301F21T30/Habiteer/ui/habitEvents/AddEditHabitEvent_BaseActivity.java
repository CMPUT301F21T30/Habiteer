package com.CMPUT301F21T30.Habiteer.ui.habitEvents;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.CMPUT301F21T30.Habiteer.R;
import com.CMPUT301F21T30.Habiteer.Session;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddEditHabitEvent_BaseActivity extends AppCompatActivity {
    private ImageView selectedImage;
    private Uri uriSelectedImage;

    private String uploadUri;
    private Uri UriOfImage;
    private String currentPhotoPath;

    private StorageReference storageReference; //to store the image in firebase storage

    public static final int CAM_PERM_CODE = 101;
    public static final int CAM_REQUEST_CODE = 102;
    public static final int GALLERY_REQUEST_CODE = 105;

    public void handlePhotograph(){
        storageReference = FirebaseStorage.getInstance().getReference(); //initialize the storage reference

        selectedImage = findViewById(R.id.event_image);
        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectOptions();
            }
        });

    }

    /**
     * click on the image to open an alert dialog to display options
     * alert dialog has options for camera, gallery and cancel
     */
    private void selectOptions() {
        final CharSequence[] items = {"Camera", "Gallery"};

        MaterialAlertDialogBuilder builder= new MaterialAlertDialogBuilder(this);
        builder.setTitle("Add an Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")){
                    askCameraPermission();

                }
                else if (items[i].equals("Gallery")){
                    openGallery();

                }
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Canceled", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    /**
     * asks the user for camera permission
     */
    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAM_PERM_CODE);
        }
        else {
            dispatchTakePictureIntent();
        }
    }

    /**
     * checks if permission is granted to the camera or not
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAM_PERM_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                dispatchTakePictureIntent();
            }
            else{
                Toast.makeText(this, " Camera Permission is required to use camera", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAM_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){
                File picFileName = new File(currentPhotoPath);

                //selectedImage.setImageURI(Uri.fromFile(picFileName));

                Log.d("tag", "Absolute Url of image is" + Uri.fromFile(picFileName));

                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(picFileName);
                mediaScanIntent.setData(contentUri);
                this.sendBroadcast(mediaScanIntent);

                UriOfImage = Session.getInstance().uploadImageToFirebase(picFileName.getName(), contentUri, storageReference);
                Picasso.get().load(UriOfImage).into(selectedImage);
                uploadUri = UriOfImage.toString(); //changes Uri to string
            }

        }

        if (requestCode == GALLERY_REQUEST_CODE){
            if (resultCode == Activity.RESULT_OK){

                Uri contentUri = data.getData();

                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "JPEG_" + "." + getFileExt (contentUri);


                selectedImage.setImageURI(contentUri);

                //upload image to db
                UriOfImage = Session.getInstance().uploadImageToFirebase(imageFileName, contentUri, storageReference);
                uploadUri = UriOfImage.toString(); //changes the Uri to string

            }

        }


    }

    /**
     * @param contentUri
     * @return the extension of the image that is selected from the gallery
     */

    private String getFileExt(Uri contentUri) {
        ContentResolver c = getContentResolver();
        MimeTypeMap mine = MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(c.getType(contentUri));
    }

    /**
     * creates an image file name and stores the image in an storage directory
     * @return image
     * @throws IOException
     */

    private File createImageFile() throws IOException {
        //Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir =  getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */

        );
        //Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    /**
     * opens the camera intent to take picture
     * stores the Uri of the image
     */

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        //create the file where the photo should go
        File photoFile = null;
        try{
            photoFile = createImageFile();

        } catch (IOException e) {
            //Error occurred while creating the file
            //e.printStackTrace();
        }
        //Continue if the file was successfully created
        if (photoFile != null){
            Uri photoURI = FileProvider.getUriForFile(this, "com.CMPUT301F21T30.android.fileprovider", photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, CAM_REQUEST_CODE);
        }

    }

    /**
     * opens gallery
     */
    public void openGallery (){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallery, GALLERY_REQUEST_CODE);

    }

    public String getUploadUri() {
        return uploadUri;
    }
}
