package com.example.gymquest.Language;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gymquest.R;

import java.util.List;

public class LanguageAdapter extends BaseAdapter {
    private Context context;
    private List<Language> languages;

    public LanguageAdapter(Context context, List<Language> languages){
        this.context = context;
        this.languages = languages;
    }

    @Override
    public int getCount() {
        return languages ==  null ? 0 : languages.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View rootView = LayoutInflater.from(context)
                .inflate(R.layout.item_language, parent, false);
        TextView txtName = rootView.findViewById(R.id.text_language);
        ImageView img = rootView.findViewById(R.id.language_image);

        txtName.setText(languages.get(position).getName());
        img.setImageResource(languages.get(position).getImage());
        return rootView;
    }
}
