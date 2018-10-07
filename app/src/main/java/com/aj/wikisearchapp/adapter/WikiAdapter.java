package com.aj.wikisearchapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aj.wikisearchapp.R;
import com.aj.wikisearchapp.model.Page;
import com.aj.wikisearchapp.view.MainActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WikiAdapter extends RecyclerView.Adapter<WikiAdapter.WikiHolder> {

    private List<Page> notes = new ArrayList<>();
    private Context context;

    public WikiAdapter(Context context) {
        this.context = context;

    }

    @NonNull
    @Override
    public WikiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wiki_item,parent,false);
        return new WikiHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WikiHolder holder, int position) {
       Page currentNote = notes.get(position);
       holder.txtTitle.setText(currentNote.getTitle());
        String desc = "", url = "";
        if(currentNote.getTerms() != null)
            desc = currentNote.getTerms().getDescription() == null? "":currentNote.getTerms().getDescription().get(0).toString();
       holder.txtDescription.setText(desc);
       if(currentNote.getThumbnail() != null)
            url = currentNote.getThumbnail().getSource() == null? "":currentNote.getThumbnail().getSource();

           Glide.with(context)
                   .setDefaultRequestOptions(new RequestOptions().placeholder(R.drawable.wiki).centerCrop())
                   .load(url)
                   .into(holder.ivWiki);
    }


    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Page> notes){
        this.notes = notes;
        notifyDataSetChanged();
    }

    class WikiHolder extends RecyclerView.ViewHolder{
        private TextView txtTitle, txtDescription;
        ImageView ivWiki;

        public WikiHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=  itemView.findViewById(R.id.tv_title);
            txtDescription= itemView.findViewById(R.id.tv_description);
            ivWiki = itemView.findViewById(R.id.iv_wiki);
        }
    }
}
