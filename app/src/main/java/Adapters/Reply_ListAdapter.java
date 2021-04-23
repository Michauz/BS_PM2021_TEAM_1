package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import app.msda.qna.R;

public class Reply_ListAdapter extends ArrayAdapter<Reply> {
    private final Activity context;
    private final ArrayList<Reply> replies;

    public Reply_ListAdapter(Activity context, ArrayList<Reply> replies) {
        super(context, R.layout.reply, replies);
        this.context = context;
        this.replies=replies;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.reply, null, true);

        TextView username = rowView.findViewById(R.id.username);
        TextView date = rowView.findViewById(R.id.date);
        ImageView image = rowView.findViewById(R.id.icon);
        TextView content = rowView.findViewById(R.id.content);

        username.setText(replies.get(position).getUsername());
        date.setText(getDateToString(replies.get(position).getDate()));
       // image.setImageResource(replies.get(position).getImg());
        content.setText(replies.get(position).getContent());

        return rowView;
    }

    private String getDateToString(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Israel"));
        return dateFormat.format(date);
    }

}