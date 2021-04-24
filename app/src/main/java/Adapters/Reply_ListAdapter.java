package Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
            date.setTextSize(13);
        } else {
            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (323 * scale + 0.5f);
            ((RelativeLayout) (rowView.findViewById(R.id.box))).getLayoutParams().width = pixels;
            replyImage.getLayoutParams().height = 0;
            replyImage.getLayoutParams().width = 0;
            replyImage.requestLayout();
        }

        return rowView;
    }

    private String getDateToString(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Israel"));
        return dateFormat.format(date);
    }
}