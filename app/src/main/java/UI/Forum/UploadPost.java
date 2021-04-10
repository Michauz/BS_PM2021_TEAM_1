package UI.Forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import Adapters.Authentication;
import Adapters.CloudFireStore;
import Adapters.Permissions;
import app.msda.qna.R;

public class UploadPost extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888, REQUEST_CODE = 1;
    private ImageView image;
    private EditText title, context;
    private boolean photoTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        image = findViewById(R.id.imageView);
        title = findViewById(R.id.post_title);
        context = findViewById(R.id.context);

        photoTaken = false;
    }

    public void postQuestion(View view) {
        if (title.getText().toString().equals("")) {
            Toast.makeText(this, "Title is empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (context.getText().toString().equals("")) {
            Toast.makeText(this, "Context is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> post = new HashMap<>();
        post.put("title", title.getText().toString());
        post.put("context", context.getText().toString());
        post.put("postID", 2);
        post.put("username", Authentication.getUsername());
        if (photoTaken)
        {
            // Create a storage reference from our app
            StorageReference storageRef = ;

            // Create a reference to "mountains.jpg"
            StorageReference mountainsRef = storageRef.child("mountains.jpg");

            // Create a reference to 'images/mountains.jpg'
            StorageReference mountainImagesRef = storageRef.child("images/mountains.jpg");

            // While the file names are the same, the references point to different files
            mountainsRef.getName().equals(mountainImagesRef.getName());    // true
            mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false
        }
        CloudFireStore.getInstance().collection("posts").document(Authentication.getUserID()).set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        msg(true);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        msg(false);
                    }
                });
    }

    private void msg(boolean isSucceeded)
    {
        if(isSucceeded)
            Toast.makeText(this, "DocumentSnapshot successfully written!",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "DocumentSnapshot failed!",Toast.LENGTH_SHORT).show();
    }
    public void addPhoto(View view) {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else {
            Toast.makeText(this, "Camera permission not granted.", Toast.LENGTH_LONG).show();
            Permissions.askForCameraPerm(this);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(photo);
            photoTaken = true;
        } else
            Toast.makeText(this, "Camera permission not granted.", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE)
            // output for user (to inform him if the permission is granted or denied)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT);

    }
}