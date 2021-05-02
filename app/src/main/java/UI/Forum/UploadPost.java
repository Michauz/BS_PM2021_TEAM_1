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
import android.text.format.DateFormat;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import Adapters.Authentication;
import Adapters.CloudFireStore;
import Adapters.FireBaseStorage;
import Adapters.Permissions;
import app.msda.qna.R;

public class UploadPost extends AppCompatActivity {
    private final int CAMERA_REQUEST = 1888, REQUEST_CODE = 1;
    private ImageView image;
    private EditText title, content;
    private boolean photoTaken;
    private double postID;
    public static String forum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_post);

        image = findViewById(R.id.imageView);
        title = findViewById(R.id.post_title);
        content = findViewById(R.id.content);

        photoTaken = false;

        CloudFireStore.getInstance().collection("vars").document("counter").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) // get the post ID from DB
                                postID = document.getDouble("counter");
                        }
                    }
                });
    }

    public void postQuestion(View view) {
        if (title.getText().toString().equals("")) {
            Toast.makeText(this, "Title is empty!", Toast.LENGTH_SHORT).show();
            return;
        } else if (content.getText().toString().equals("")) {
            Toast.makeText(this, "Content is empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> post = new HashMap<>();
        post.put("title", title.getText().toString());
        post.put("content", content.getText().toString());
        post.put("postID", postID);
        post.put("email", Authentication.getCurrentUser().getEmail());
        post.put("date", (new Date()).getTime());
        post.put("forum", forum);

        CloudFireStore.getInstance().collection("posts")
                .document(Authentication.getUserID() + "-" + (int) postID)
                .set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (photoTaken)
                            uploadImage();
                        updatePostsCounter();
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

        Map<String,Object> counter=new HashMap<>();
        counter.put("counter",0); // groundings for replies (collection of them + counter)
        CloudFireStore.getInstance().collection("posts")
                .document(Authentication.getUserID() + "-" + (int) postID)
                .collection("replies").document("reply_counter").set(counter);
    }

    private void msg(boolean isSucceeded) {
        if (isSucceeded)
            Toast.makeText(this, "Post uploaded successfully", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Post upload failed", Toast.LENGTH_SHORT).show();
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

    private void updatePostsCounter() {
        Map<String, Object> counter = new HashMap<>();
        counter.put("counter", ++postID);

        CloudFireStore.getInstance().collection("vars").document("counter")
                .set(counter).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private void uploadImage() {
        // Get the data from an ImageView as bytes
        image.setDrawingCacheEnabled(true);
        image.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference mountainsRef = FireBaseStorage.getInstance().getReference().child((int) postID + ".jpg");

        UploadTask uploadTask = mountainsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });
    }
}