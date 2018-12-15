package com.example.exitpoll.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.exitpoll.R;
import com.example.exitpoll.model.VoteItem;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class VoteListAdapter extends ArrayAdapter<VoteItem> {

    private Context mContext;
    private int mResource;
    private List<VoteItem> mVoteItemList;

    public VoteListAdapter(@NonNull Context context,
                            int resource,
                            @NonNull List<VoteItem> phoneItemList) {
        super(context, resource, phoneItemList);
        this.mContext = context;
        this.mResource = resource;
        this.mVoteItemList = phoneItemList;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResource, parent, false);

        TextView numberTextView = view.findViewById(R.id.vote_count);
        ImageView imageView = view.findViewById(R.id.no_image);

        VoteItem voteItem = mVoteItemList.get(position);
        int number = voteItem.number;
        String filename = voteItem.image;

        numberTextView.setText(String.valueOf(number));

        AssetManager am = mContext.getAssets();
        try {
            InputStream is = am.open(filename);
            Drawable drawable = Drawable.createFromStream(is, "");
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {

            e.printStackTrace();
        }

        return view;
    }

}
