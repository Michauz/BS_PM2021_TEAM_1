package Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Reply {
    private String username, content;
    private long date;
    private byte[] replyImage;
    private int replyID, postID;

    public Reply(DocumentSnapshot reply, int postID) {
        try {
            username = (String) reply.get("username");
            content = (String) reply.get("content");
            date = (long) reply.get("date");
            replyID = reply.getDouble("ID").intValue();
            this.postID = postID;
            setImg();
        } catch (Exception e) {
        }
    }

    public int getReplyID() {
        return replyID;
    }

    public int getPostID() {
        return postID;
    }

    private void setImg() {
        FireBaseStorage.getInstance().getReference().listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult result) { // LOADING SCREEN ???
                for (StorageReference fileRef : result.getItems())
                    if (fileRef.getName().equals(postID + "_" + replyID + ".jpg"))
                        fileRef.getBytes(1024 * 1024).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                            @Override
                            public void onSuccess(byte[] bytes) {
                                replyImage = bytes;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                replyImage = null;
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                // Handle any errors
            }
        });
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    public long getDate() {
        return date;
    }

    public byte[] getReplyImage() {
        return replyImage;
    }
}
