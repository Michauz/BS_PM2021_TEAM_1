package Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ceylonlabs.imageviewpopup.ImagePopup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import UI.Forum.Post;
import app.msda.qna.R;

import static android.graphics.Typeface.BOLD;

public class Reply_ListAdapter extends ArrayAdapter<Reply> {
    private final Activity context;
    private final ArrayList<Reply> replies;

    public Reply_ListAdapter(Activity context, ArrayList<Reply> replies) {
        super(context, R.layout.reply, replies);
        this.context = context;
        this.replies = replies;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.reply, null, true);

        TextView username = rowView.findViewById(R.id.username);
        TextView date = rowView.findViewById(R.id.date);
        ImageView replyImage = rowView.findViewById(R.id.replyIMG);
        TextView content = rowView.findViewById(R.id.content);

        username.setText(replies.get(position).getUsername());
        date.setText(getDateToString(replies.get(position).getDate()));
        content.setText(replies.get(position).getContent());
        content.setMovementMethod(new ScrollingMovementMethod());

        if (replies.get(position).getReplyImage() != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(replies.get(position).getReplyImage(), 0, replies.get(position).getReplyImage().length);
            replyImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, (int) rowView.getResources().getDimension(R.dimen.image_width), (int) rowView.getResources().getDimension(R.dimen.image_height), false));

            replyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** Initiate Popup view **/
                    final ImagePopup imagePopup = new ImagePopup(context);
                    imagePopup.initiatePopup(replyImage.getDrawable()); // Load Image from Drawable
                    imagePopup.viewPopup(); // view popup of the image
                }
            });
            date.setTextSize(11);
        } else {
            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (323 * scale + 0.5f);
            ((RelativeLayout) (rowView.findViewById(R.id.box))).getLayoutParams().width = pixels;
            replyImage.getLayoutParams().height = 0;
            replyImage.getLayoutParams().width = 0;
            replyImage.requestLayout();
        }

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked - delete post
                                Post.post.getPost().getReference().collection("replies").document("reply "+getItem(position).getReplyID()).delete();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete the reply?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        return rowView;
    }

    private String getDateToString(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Israel"));
        return dateFormat.format(date);
    }

    @Override
    public Reply getItem(int position) {
        return replies.get(position);
    }
}