package sar.upmc.fr.crossingroads;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    public static String BUNDLE_NAME_USER = "";
    public static String BUNDLE_MOTO_USER = "";

    private EditText editName;
    private EditText editMoto;
    private FloatingActionButton saveButton;

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

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BUNDLE_NAME_USER = editName.getText().toString();
                BUNDLE_MOTO_USER = editMoto.getText().toString();
                //Log.d("kk", "username " + BUNDLE_NAME_USER);
                Toast.makeText(getApplicationContext(),"Saved successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.finish();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        editName.setText(BUNDLE_NAME_USER);
        editMoto.setText(BUNDLE_MOTO_USER);
    }
}
