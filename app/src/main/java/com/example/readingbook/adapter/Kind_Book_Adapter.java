package com.example.readingbook.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbook.R;
import com.example.readingbook.dao.KindBookDAO;
import com.example.readingbook.model.KindBook;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class Kind_Book_Adapter extends RecyclerView.Adapter<Kind_Book_Adapter.MyViewHoler>{
    Context context;
    ArrayList<KindBook> list;
    ActivityResultLauncher<String> pickImageLauncher;

    public Kind_Book_Adapter(Context context, ArrayList<KindBook> list, ActivityResultLauncher<String> pickImageLauncher) {
        this.context = context;
        this.list = list;
        this.pickImageLauncher = pickImageLauncher;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_loai_sach, parent, false);
        MyViewHoler holer = new MyViewHoler(view);
        return holer;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        holder.tvTenLoai.setText(list.get(holder.getAdapterPosition()).getName());
//        Integer index = holder.getAdapterPosition();
        holder.imgLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở sự kiện chọn ảnh mới
                pickImageLauncher.launch("image/*");
            }
        });

        //get
        byte[] decodedString = Base64.decode(list.get(holder.getAdapterPosition()).getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imgLoai.setImageBitmap(decodedByte);
        KindBookDAO kindBookDAO = new KindBookDAO(context);
        holder.imgLoai.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Thông báo xóa");
                builder.setMessage("Bạn có chắc chắn muốn xóa không ?");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (kindBookDAO.delete(list.get(holder.getAdapterPosition()).getId())) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(kindBookDAO.getAll());
                            notifyDataSetChanged();
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHoler extends RecyclerView.ViewHolder{
        TextView tvTenLoai;
        ShapeableImageView imgLoai;
        public MyViewHoler(@NonNull View view) {
            super(view);
            tvTenLoai = view.findViewById(R.id.tvTenLoai);
            imgLoai = view.findViewById(R.id.imgLoai);
        }

    }

}
