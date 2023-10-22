package com.ras.pustakgrah.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ras.pustakgrah.Models.BookModels;
import com.ras.pustakgrah.R;
import com.ras.pustakgrah.Reading_Activity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {

    ArrayList<BookModels> list;
    Context context;

    public BookAdapter(ArrayList<BookModels> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_pustak, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookModels models = list.get(position);
//        holder.book_img.setImageResource(models.getImage());  WAY TO GET DATA FROM SOURCE...
        Picasso.get()
                .load(models.getImage())
                .into(holder.book_img);

        holder.book_txt.setText(models.getBookName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Reading_Activity.class);
                intent.putExtra("url", models.getUrl());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView book_img;
        TextView book_txt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            book_img = itemView.findViewById(R.id.book1);
            book_txt = itemView.findViewById(R.id.book_text1);
        }
    }

}
