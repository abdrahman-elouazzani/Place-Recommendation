package elouazzani.ma.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NavUtils;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import elouazzani.ma.myapplication.DAO.PlaceDAOImp;
import elouazzani.ma.myapplication.Model.Place;

public class AddPlaceActivity extends AppCompatActivity {

    private TextInputEditText inputTitle,inputCity,inputType,inputAddress,inputDescription;
    private TextInputLayout inputLayoutTitle,inputLayoutCity,inputLayoutType,inputLayoutAddress,inputLayoutDescription;
    private Button confirm,loadImage;
    private TextView file_name;
    Bitmap bitmap;

    final int REQUEST_CODE_GALLERY=999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        Toolbar toolbar=findViewById(R.id.toolbar);
        toolbar.setTitle("Add Place");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavUtils.navigateUpTo(AddPlaceActivity.this,
                        new Intent(AddPlaceActivity.this,MainActivity.class));
            }
        });


        instancesObjs();

    }

    private void instancesObjs() {

        inputTitle=findViewById(R.id.input_title);
        inputCity=findViewById(R.id.input_city);
        inputType=findViewById(R.id.input_type);
        inputAddress=findViewById(R.id.input_address);
        inputDescription=findViewById(R.id.input_description);
        inputLayoutTitle=findViewById(R.id.input_layout_title);
        inputLayoutCity=findViewById(R.id.input_layout_city);
        inputLayoutType=findViewById(R.id.input_layout_select_type);
        inputLayoutAddress=findViewById(R.id.input_layout_address);
        inputLayoutDescription=findViewById(R.id.input_layout_description);
        confirm=findViewById(R.id.confirm);
        loadImage=findViewById(R.id.import_image);
        file_name=findViewById(R.id.image_file_name);
    }

    //
    public void onClickConfirm(View v) {
        if(v==confirm) {

            if(validateForm() && bitmap!=null) {

                Place place=new Place(
                        inputTitle.getText().toString().trim(),
                        inputCity.getText().toString().trim(),
                        inputType.getText().toString().trim(),
                        inputAddress.getText().toString().trim(),
                        getString(R.string.description),
                        bitmapToBytes(bitmap)
                );
                PlaceDAOImp placeDAOImp=new PlaceDAOImp(getApplicationContext());
                boolean statu= placeDAOImp.AddPlaceItem(place);
                if(statu)
                    Toast.makeText(this, "Form Valid ", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(this, "Form Not Valid ", Toast.LENGTH_LONG).show();
            }
        }


    }

    private boolean validateForm() {

        if(inputTitle.getText().toString().trim().isEmpty()) {
            inputLayoutTitle.setError(getString(R.string.err_msg_title));
            requestFocus(inputTitle);
            return false;
        }
        else {
            inputLayoutTitle.setErrorEnabled(false);
        }

        if(inputCity.getText().toString().trim().isEmpty()) {
            inputLayoutCity.setError(getString(R.string.err_msg_city));
            requestFocus(inputCity);
            return false;
        }
        else {
            inputLayoutCity.setErrorEnabled(false);
        }

        if(inputType.getText().toString().trim().isEmpty()) {
            inputLayoutType.setError(getString(R.string.err_msg_type));
            requestFocus(inputType);
            return false;
        }
        else {
            inputLayoutType.setErrorEnabled(false);
        }

        if(inputAddress.getText().toString().trim().isEmpty()) {
            inputLayoutAddress.setError(getString(R.string.err_msg_address));
            requestFocus(inputAddress);
            return false;
        }
        else {
            inputLayoutAddress.setErrorEnabled(false);
        }
        /*
        if(inputDescription.getText().toString().length()<80 ) {
            inputLayoutDescription .setError(getString(R.string.err_msg_description));
            requestFocus(inputDescription);
            return false;
        }
        else {
            inputLayoutDescription.setErrorEnabled(false);
        }
        */
        return true;
    }

    private void requestFocus(View view) {

        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

     public void onClickLoad(View view) {
        if(view==loadImage)
        {
            ActivityCompat.requestPermissions(
                    AddPlaceActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_CODE_GALLERY
            );

        }
     }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode==REQUEST_CODE_GALLERY)
        {
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(),"You Dont Have the Permission !!",Toast.LENGTH_LONG).show();
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_CODE_GALLERY && resultCode==RESULT_OK && data!=null) {
            Uri uri=data.getData();
            try {
                InputStream inputStream=getContentResolver().openInputStream(uri);
                 bitmap= BitmapFactory.decodeStream(inputStream);
                 file_name.setText(uri.toString());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private byte[] bitmapToBytes(Bitmap bitmap) {

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        byte[] bytesArray=outputStream.toByteArray();
        return bytesArray;
    }
}
