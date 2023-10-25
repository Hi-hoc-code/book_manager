package com.example.readingbook.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbook.R;
import com.example.readingbook.dao.CustomerDAO;
import com.example.readingbook.dao.StaffDAO;
import com.example.readingbook.model.Customer;
import com.example.readingbook.model.Staff;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.MyViewHoler> {
    Context context;
    ArrayList<Staff> list;
    ActivityResultLauncher<String> pickImageLauncher;

    public StaffAdapter(Context context, ArrayList<Staff> list, ActivityResultLauncher<String> pickImageLauncher) {
        this.context = context;
        this.list = list;
        this.pickImageLauncher = pickImageLauncher;
    }

    @NonNull
    @Override
    public StaffAdapter.MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_staff, parent, false);
        StaffAdapter.MyViewHoler holder = new StaffAdapter.MyViewHoler(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaffAdapter.MyViewHoler holder, int position) {

        holder.imgNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageLauncher.launch("image/*");
            }
        });

        //get
        byte[] decodedString = Base64.decode(list.get(holder.getAdapterPosition()).getImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imgNhanVien.setImageBitmap(decodedByte);
        holder.tvTenNhanVien.setText(list.get(holder.getAdapterPosition()).getTen());
        holder.tvSdtNhanVien.setText(list.get(holder.getAdapterPosition()).getSdt());
        holder.tvIdNhanVien.setText("KH0" + String.valueOf(list.get(holder.getAdapterPosition()).getId()));
        StaffDAO staffDAO = new StaffDAO(context);

        holder.btnEditNhanVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.layout_item_staff);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                EditText edtTenNhanVien, edtSdtNhanVien, edtEmailNhanVien, edtPassNhanVien;
                Button btnSubmit, btnCancel;
                ShapeableImageView imgAvatarNhanVien;

                edtTenNhanVien = dialog.findViewById(R.id.edtTenNhanVien);
                edtSdtNhanVien = dialog.findViewById(R.id.edtSdtNhanVien);
                edtEmailNhanVien = dialog.findViewById(R.id.edtEmailNhanVien);
                edtPassNhanVien = dialog.findViewById(R.id.edtPassNhanVien);
                imgAvatarNhanVien = dialog.findViewById(R.id.imgAvatarNhanVien);

                btnSubmit = dialog.findViewById(R.id.btnSubmitKhachHang);
                btnCancel = dialog.findViewById(R.id.btnHuyDigKhachHang);

                edtTenNhanVien.setText(list.get(holder.getAdapterPosition()).getTen());
                edtSdtNhanVien.setText(list.get(holder.getAdapterPosition()).getSdt());
                edtEmailNhanVien.setText(list.get(holder.getAdapterPosition()).getEmail());
                edtPassNhanVien.setText(list.get(holder.getAdapterPosition()).getPassword());

                imgAvatarNhanVien.setOnClickListener(v -> {
                    pickImageLauncher.launch("image/*");
                });
                byte[] decodedString = Base64.decode(list.get(holder.getAdapterPosition()).getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgAvatarNhanVien.setImageBitmap(decodedByte);

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ten = edtTenNhanVien.getText().toString();
                        String sdt = edtSdtNhanVien.getText().toString();
                        String email = edtEmailNhanVien.getText().toString();
                        String pass = edtPassNhanVien.getText().toString();
                        Drawable drawable = imgAvatarNhanVien.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String avatar = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        Staff staff = new Staff(list.get(holder.getAdapterPosition()).getId(), ten, sdt, email, pass, avatar);
                        if (staffDAO.update(staff)) {
                            Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                            list.set(holder.getAdapterPosition(), staff); // Update the item in the list
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Cập nhật thông tin thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


        holder.btnDeleteNhanVien.setOnClickListener(new View.OnClickListener() {
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
                        if (staffDAO.delete(holder.getAdapterPosition())) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(staffDAO.getAll());
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


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHoler extends RecyclerView.ViewHolder {
        ImageView imgNhanVien;
        TextView tvTenNhanVien, tvSdtNhanVien, tvIdNhanVien;
        ImageView btnEditNhanVien, btnDeleteNhanVien;

        public MyViewHoler(@NonNull View view) {
            super(view);
            imgNhanVien = view.findViewById(R.id.imgNhanVien);
            tvTenNhanVien = view.findViewById(R.id.tvTenNhanVien);
            tvSdtNhanVien = view.findViewById(R.id.tvSdtNhanVien);
            tvIdNhanVien = view.findViewById(R.id.tvIdNhanVien);
            btnEditNhanVien = view.findViewById(R.id.btnEditNhanVien);
            btnDeleteNhanVien = view.findViewById(R.id.btnDeleteNhanVien);
        }
    }
}
