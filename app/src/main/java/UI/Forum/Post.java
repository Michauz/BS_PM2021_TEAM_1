package UI.Forum;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ceylonlabs.imageviewpopup.ImagePopup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Adapters.Authentication;
import Adapters.CloudFireStore;
import Adapters.FireBaseStorage;
import Adapters.Permissions;
import Adapters.Reply_ListAdapter;
import app.msda.qna.R;

public class Post extends AppCompatActivity {
    public static Adapters.Post post;
    private EditText reply;
    private int replyID;
    private ImageView postImage;
    private ListView list;
    private final int CAMERA_REQUEST = 1888, REQUEST_CODE = 1;
    private Bitmap image;
    private Context context;
    private Activity thisActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        context = this;
        setPost();
        reply = findViewById(R.id.replyContent);
        postImage = findViewById(R.id.postImg);
        ((TextView) findViewById(R.id.postContent)).setMovementMethod(new ScrollingMovementMethod());
        thisActivity=this;

        post.getPost().getReference().collection("replies").document("reply_counter").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) // get the reply ID from DB
                                replyID = document.getDouble("counter").intValue();
                        }
                    }
                });

        setPostImage();

        list = findViewById(R.id.postReplies);
        list.setAdapter(new Reply_ListAdapter(this, post.getReplies()));
    }

    private void setPostImage() {
        FireBaseStorage.getInstance().getReference().child(post.getPostID() + ".jpg")
                .getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                postImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, postImage.getWidth(), postImage.getHeight(), false));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                postImage.getLayoutParams().width = 0;
                postImage.getLayoutParams().height = 0;

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) ((TextView) findViewById(R.id.postContent)).getLayoutParams();
                params.width = 888;
                ((TextView) findViewById(R.id.postContent)).setLayoutParams(params);
            }
        });

        postImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ImagePopup imagePopup = new ImagePopup(context);
                imagePopup.initiatePopup(postImage.getDrawable()); // Load Image from Drawable
                imagePopup.viewPopup(); // view popup of the image
            }
        });

    }

    private void setPost() {
        ((TextView) findViewById(R.id.postTitle)).setText(post.getTitle());
        ((TextView) findViewById(R.id.postAuthor)).setText(post.getAuthor());
        ((TextView) findViewById(R.id.postDate)).setText(post.getDateToString());
        ((TextView) findViewById(R.id.postContent)).setText(post.getContent());
    }

    public void reply(View view) {
        if (reply.getText().toString().equals("")) {
            Toast.makeText(this, "The content is empty!", Toast.LENGTH_SHORT);
            return;
        }

        Map<String, Object> reply = new HashMap<>();
        reply.put("username", Authentication.getCurrentUser().getEmail().split("@")[0]);
        reply.put("content", this.reply.getText().toString());
        reply.put("ID", ++replyID);
        reply.put("date", (new Date()).getTime());

        post.getPost().getReference().collection("replies").document("reply " + replyID).set(reply)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        updateRepliesCounter();
                        post.setReplies(); // update the replies list
                    }
                });

        // upload the image if exist
        if (image == null) {
            finish();
            return;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference mountainsRef = FireBaseStorage.getInstance()
                .getReference().child(post.getPostID() + "_" + replyID + ".jpg");

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

        finish();
    }

    private void updateRepliesCounter() {
        Map<String, Object> counter = new HashMap<>();
        counter.put("counter", replyID);

        post.getPost().getReference().collection("replies").document("reply_counter")
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

    public void addReplyImage(View view) {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } else {
            Toast.makeText(this, "Camera permission not granted.", Toast.LENGTH_LONG).show();
            Permissions.askForCameraPerm(this);
        }
    }

    public static Adapters.Post getPost() {
        return post;
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            image = (Bitmap) data.getExtras().get("data");
            Toast.makeText(this, "Photo has been taken successful!", Toast.LENGTH_SHORT);
            ((Button) findViewById(R.id.addImage)).setText("Image Added");
            ((Button) findViewById(R.id.addImage)).setBackgroundColor(Color.GREEN);
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

    public void deletePost(View view) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked - delete post
                        post.getPost().getReference().delete(); //delete the document of the post from the firebase
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public void finishActivity()
    {
        finish();
    }
}