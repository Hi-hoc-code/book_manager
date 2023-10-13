package com.example.readingbook.adapter;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.Layout;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbook.R;
import com.example.readingbook.dao.ThanhVienDAO;
import com.example.readingbook.model.KhachHang;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class CustomerRcvAdapter extends RecyclerView.Adapter<CustomerRcvAdapter.MyViewHoler> {
    Context context;
    ArrayList<KhachHang> list;

    public CustomerRcvAdapter(Context context, ArrayList<KhachHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_customer,parent,false);
        MyViewHoler holder = new MyViewHoler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {
        KhachHang item = list.get(position);
        byte[] decodedString = Base64.decode(item.getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imgKhachHang.setImageBitmap(decodedByte);
        holder.tvTenKhachHang.setText(item.getTen());
        holder.tvSdtKhachHang.setText(item.getSdt());
        if(item.getId()<10){
            holder.tvIdKhachHang.setText("KH0"+String.valueOf(item.getId()));
        }else if(item.getId()<10){
            holder.tvIdKhachHang.setText("KH"+String.valueOf(item.getId()));
        }

        ThanhVienDAO dao = new ThanhVienDAO(context);
        Integer index = list.get(position).getId();
        holder.btnEditKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });


        holder.btnDeleteKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                        if (dao.delete(index)){
                            Toast.makeText(context,"Xóa thành công",Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(dao.getAll());
                            notifyDataSetChanged();
                            dialogInterface.dismiss();
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void showDigLogEdit() {
        Toast.makeText(context,"Mở cửa sổ dialog lên nà",Toast.LENGTH_SHORT).show();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHoler extends RecyclerView.ViewHolder{
        ImageView imgKhachHang;
        TextView tvTenKhachHang, tvSdtKhachHang,tvIdKhachHang;
        ImageView btnEditKhachHang, btnDeleteKhachHang;
        public MyViewHoler(@NonNull View view) {
            super(view);
            imgKhachHang= view.findViewById(R.id.imgKhachHang);
            tvTenKhachHang= view.findViewById(R.id.tvTenKhachHang);
            tvSdtKhachHang= view.findViewById(R.id.tvSdtKhachHang);
            tvIdKhachHang= view.findViewById(R.id.tvIdKhachHang);
            btnEditKhachHang= view.findViewById(R.id.btnEditKhachHang);
            btnDeleteKhachHang= view.findViewById(R.id.btnDeleteKhachHang);
        }
    }
}
