package com.example.catfeedingapp.db;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catfeedingapp.R;
import com.example.catfeedingapp.photomodel.photo;

public class photoAdapter extends CursorAdapter {

    public photoAdapter(Context context, Cursor cursor, boolean autoRequery) {
        super(context, cursor, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.photo_list_item, viewGroup, false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder)view.getTag();

        photo Photo = new photo(cursor);
        holder.titleTextView.setText(Photo.getTitle());
        holder.imageView.setImageBitmap(Photo.getImage());

    }

    private class ViewHolder {
        final ImageView imageView;
        final TextView titleTextView;

        ViewHolder(View view) {
            imageView = view.findViewById(R.id.image_view);
            titleTextView = view.findViewById(R.id.text_view);
        }
    }
}
