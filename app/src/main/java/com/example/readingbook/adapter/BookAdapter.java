package com.example.readingbook.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.readingbook.R;
import com.example.readingbook.dao.BookDAO;
import com.example.readingbook.model.Book;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
    Context context;
    ArrayList<Book> list;
    ActivityResultLauncher<String> pickImageLauncher;

    public BookAdapter(Context context, ArrayList<Book> list, ActivityResultLauncher<String> pickImageLauncher) {
        this.context = context;
        this.list = list;
        this.pickImageLauncher = pickImageLauncher;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_item_book_manager, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookDAO bookDAO = new BookDAO(context);
        Book item = list.get(holder.getAdapterPosition());
        holder.imgBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageLauncher.launch("image/*");
            }
        });
        byte[] decodedString = Base64.decode(item.getImage(), Base64.DEFAULT);
        Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        holder.imgBook.setImageBitmap(decodedBitmap);
        holder.tvNameBook.setText(item.getName());
        holder.tvAuthor.setText("Tác giả: " + item.getAuthor());
        holder.tvKindOfBook.setText("Thể loại: " + item.getLoai());
        holder.btnEditBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dig_add_book_update);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                EditText edtTen, edtTacGia, edtLoai;
                Button btnSubmit, btncancel;
                ShapeableImageView imgBook;

                edtTen = dialog.findViewById(R.id.edtTenBookDig);
                edtTacGia = dialog.findViewById(R.id.edtTacGiaDig);
                edtLoai = dialog.findViewById(R.id.edtLoaiSachDiaUpdate);


                btnSubmit = dialog.findViewById(R.id.btnSubmitBookDig);
                btncancel = dialog.findViewById(R.id.btnCancelBookDig);
                imgBook = dialog.findViewById(R.id.imgBookDig);
//
                Book book = list.get(holder.getAdapterPosition());
                edtTen.setText(book.getName());
                edtTacGia.setText(book.getAuthor());
                edtLoai.setText(book.getLoai());
                imgBook.setOnClickListener(v -> {
                    pickImageLauncher.launch("image/*");
                });
                byte[] decodedString = Base64.decode(book.getImage(), Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                imgBook.setImageBitmap(decodedByte);
                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ten = edtTen.getText().toString();
                        String tacgia = edtTacGia.getText().toString();
                        String loai = edtLoai.getText().toString();

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        decodedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String imgBookS = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        Book item = new Book(book.getId(),ten, tacgia, loai, imgBookS);
                        if (bookDAO.update(item)) {
                            Toast.makeText(context, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(bookDAO.getAllBook());
                            notifyDataSetChanged();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Cập nhật sách thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btncancel.setOnClickListener(v -> dialog.dismiss());
                dialog.show();
            }
        });
        holder.btnDeleteBook.setOnClickListener(new View.OnClickListener() {
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
                        BookDAO bookDAO = new BookDAO(view.getContext());
                        if (bookDAO.delete(list.get(holder.getAdapterPosition()).getId())) {
                            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(bookDAO.getAllBook());
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

    class MyViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView imgBook;
        TextView tvNameBook, tvAuthor, tvKindOfBook;
        ImageView btnEditBook, btnDeleteBook;

        public MyViewHolder(@NonNull View view) {
            super(view);
            imgBook = view.findViewById(R.id.imgBook);
            tvNameBook = view.findViewById(R.id.tvNameBook);
            tvAuthor = view.findViewById(R.id.tvAuthorBook);
            tvKindOfBook = view.findViewById(R.id.tvKindOfBook);
            btnEditBook = view.findViewById(R.id.btnEditBook);
            btnDeleteBook = view.findViewById(R.id.btnDeleteBook);

        }
    }
}
