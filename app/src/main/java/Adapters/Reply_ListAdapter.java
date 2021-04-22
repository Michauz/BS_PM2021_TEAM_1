package Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

        TextView username = (TextView) rowView.findViewById(R.id.title);
        TextView date = (TextView) rowView.findViewById(R.id.title);
        ImageView image = (ImageView) rowView.findViewById(R.id.icon);
        TextView content = (TextView) rowView.findViewById(R.id.content);

        username.setText(replies.get(position).getUsername());
        date.setText(replies.get(position).getDate().toString());
       // image.setImageResource(replies.get(position).getImg());
        content.setText(replies.get(position).getContent());

        return rowView;
    }
}