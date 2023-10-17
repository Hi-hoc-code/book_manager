package com.example.readingbook.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbook.R;
import com.example.readingbook.adapter.Kind_Book_Adapter;
import com.example.readingbook.dao.KindBookDAO;
import com.example.readingbook.model.KindBook;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class KindBookFragment extends Fragment {
    ActivityResultLauncher<String> pickImageLauncher;
    ShapeableImageView imgLoaiDig;
    FloatingActionButton btnAddLoaiSach;
    RecyclerView rcvLoaiSach;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kind_of_book_fragment,container,false);
        addControls(view);
        addEvents(view);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgLoaiDig = new ShapeableImageView(requireContext());
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        imgLoaiDig.setImageURI(result);
                    }
                }
        );
    }
    private void addEvents(View view) {
        KindBookDAO kindBookDAO = new KindBookDAO(getContext());
        ArrayList<KindBook> list = kindBookDAO.getAll();
        Kind_Book_Adapter kindBookAdapter = new Kind_Book_Adapter(getContext(), list,pickImageLauncher);
        rcvLoaiSach.setAdapter(kindBookAdapter);
        btnAddLoaiSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_loai_sach);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                EditText edtTenLoai;
                Button btnSubmitLoai, btnCancelLoai;
                btnSubmitLoai = dialog.findViewById(R.id.btnSubmitDigLoai);
                btnCancelLoai = dialog.findViewById(R.id.btnHuyDigLoai);
                edtTenLoai = dialog.findViewById(R.id.edtTenLoaiDig);
                imgLoaiDig = dialog.findViewById(R.id.imgLoaiSachDig);
//                Drawable drawable = imgLoaiDig.getDrawable();

                imgLoaiDig.setOnClickListener(v -> {
                    pickImageLauncher.launch("image/*");
                });
                btnSubmitLoai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String tenLoai = edtTenLoai.getText().toString();

                        Drawable drawable = imgLoaiDig.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String avatar = Base64.encodeToString(byteArray, Base64.DEFAULT);

                        KindBook item = new KindBook(tenLoai,avatar);
                        if(kindBookDAO.add(item)){
                            Toast.makeText(getContext(),"Thêm loại sách thành công",Toast.LENGTH_SHORT).show();
                            list.clear();
                            list.addAll(kindBookDAO.getAll());
                            kindBookAdapter.notifyDataSetChanged();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(getContext(),"Thêm loại sách thất bại",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnCancelLoai.setOnClickListener( view1 -> dialog.dismiss());
                dialog.show();
            }
        });
    }
    private void addControls(View view) {
        rcvLoaiSach = view.findViewById(R.id.rcvLoaiSach);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2, LinearLayoutManager.VERTICAL,false);
        rcvLoaiSach.setLayoutManager(layoutManager);
        rcvLoaiSach.setHasFixedSize(true);
        btnAddLoaiSach = view.findViewById(R.id.btnFloatAddLoaiSach);

    }
}