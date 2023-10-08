package com.example.readingbook.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbook.R;
import com.example.readingbook.model.Book;

import java.util.ArrayList;

public class RecycleViewBookManagerAdapter extends RecyclerView.Adapter<RecycleViewBookManagerAdapter.MyViewHolder> {
    Context context;
    ArrayList<Book> list;

    public RecycleViewBookManagerAdapter(Context context, ArrayList<Book> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_book_item_horizontal,parent,false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book book = list.get(position);
        holder.name.setText(book.getName());
        holder.author.setText(book.getAuthor());
        holder.image.setImageResource(book.getImage());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView image,btnEdit, btnDelete;
        TextView author, name;


        public MyViewHolder(@NonNull View view) {
            super(view);
            image = view.findViewById(R.id.imgBook);
            author = view.findViewById(R.id.tvAuthor);
            name = view.findViewById(R.id.tvNameBook);
            btnEdit = view.findViewById(R.id.btnEdit);
            btnDelete = view.findViewById(R.id.btnDelete);
        }
    }
}
