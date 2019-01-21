package sar.upmc.fr.crossingroads;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    private static String BUNDLE_NAME_USER = "";
    private static String BUNDLE_MOTTO_USER = "";
    private static Drawable BUNDLE_IMAGE_USER = null;
    public static final int GET_FROM_GALLERY = 3;

    private EditText editName;
    private EditText editMoto;
    private FloatingActionButton saveButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        android.support.v7.app.ActionBar tb = getSupportActionBar();
        tb.setHomeButtonEnabled(true);
        tb.setDisplayHomeAsUpEnabled(true);

        editName = findViewById(R.id.editText1);
        editMoto = findViewById(R.id.editText2);
        saveButton = findViewById(R.id.saveButton);
        imageView = findViewById(R.id.imageView);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUNDLE_NAME_USER = editName.getText().toString();
                BUNDLE_MOTTO_USER = editMoto.getText().toString();
                BUNDLE_IMAGE_USER = imageView.getDrawable();
                //Log.d("kk", "username " + BUNDLE_NAME_USER);
                Toast.makeText(getApplicationContext(),"Saved successfully", Toast.LENGTH_SHORT).show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, null);
                galleryIntent.setType("image/*");

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                Intent chooser = new Intent(Intent.ACTION_CHOOSER);
                chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);
                chooser.putExtra(Intent.EXTRA_TITLE, "Take a picture from");

                Intent[] intentArray =  {cameraIntent};
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                startActivityForResult(chooser, GET_FROM_GALLERY);
                //startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Detects request codes
        if(requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap;
            if(data.getData()==null){
                bitmap = (Bitmap)data.getExtras().get("data");
                imageView.setImageBitmap(bitmap);
            }else{
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    imageView.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

        editName.setText(BUNDLE_NAME_USER);
        editMoto.setText(BUNDLE_MOTTO_USER);
        if(BUNDLE_IMAGE_USER != null)
            imageView.setImageDrawable(BUNDLE_IMAGE_USER);
    }
}
