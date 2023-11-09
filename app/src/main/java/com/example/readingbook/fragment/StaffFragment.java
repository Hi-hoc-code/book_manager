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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbook.R;
import com.example.readingbook.adapter.CustomerRcvAdapter;
import com.example.readingbook.adapter.StaffAdapter;
import com.example.readingbook.dao.CustomerDAO;
import com.example.readingbook.dao.StaffDAO;
import com.example.readingbook.model.Customer;
import com.example.readingbook.model.Staff;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class StaffFragment extends Fragment {
    private StaffDAO staffDAO;
    private ArrayList<Staff> list;
    private RecyclerView rcvNhanVien;
    private StaffAdapter staffAdapter;
    private ActivityResultLauncher<String> pickImageLauncher;
    private ShapeableImageView imgAvatarNhanVien;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_staff_fragment,container,false);
        addControls(view);
        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgAvatarNhanVien = new ShapeableImageView(requireContext());
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        imgAvatarNhanVien.setImageURI(result);
                    }
                }
        );
    }


    private void addControls(View view) {
        rcvNhanVien = view.findViewById(R.id.rcvListNhanVien);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvNhanVien.setLayoutManager(layoutManager);
        staffDAO = new StaffDAO(getContext());
        list = staffDAO.getAll();
        staffAdapter = new StaffAdapter(getContext(), list, pickImageLauncher);
        rcvNhanVien.setAdapter(staffAdapter);
        FloatingActionButton btnAddNhanVien = view.findViewById(R.id.btnAddNhanVien);
        btnAddNhanVien.setOnClickListener(v -> showDialogToAddNhanVien());
    }
    private void showDialogToAddNhanVien() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_add_nhan_vien);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        EditText edtTenNhanVien, edtSdtNhanVien, edtEmailNhanVien, edtPassNhanVien;
        edtTenNhanVien = dialog.findViewById(R.id.edtTenNhanVienDig);
        edtSdtNhanVien = dialog.findViewById(R.id.edtSdtNhanVienDig);
        edtEmailNhanVien = dialog.findViewById(R.id.edtEmailNhanVienDig);
        edtPassNhanVien = dialog.findViewById(R.id.edtPassNhanVienDig);

        imgAvatarNhanVien = dialog.findViewById(R.id.imgAvatarNhanVienDig);
        imgAvatarNhanVien.setOnClickListener(v -> {
            pickImageLauncher.launch("image/*");
        });

        Button btnSubmitNhanVien = dialog.findViewById(R.id.btnSubmitNhanVienDig);
        Button btnHuyDiaNhanVien = dialog.findViewById(R.id.btnCancelNhanVienDig);

        btnSubmitNhanVien.setOnClickListener(v -> {
            String ten = edtTenNhanVien.getText().toString();
            String sdt = edtSdtNhanVien.getText().toString();
            String email = edtEmailNhanVien.getText().toString();
            String pass = edtPassNhanVien.getText().toString();

            // Convert the selected image to Base64
            Drawable drawable = imgAvatarNhanVien.getDrawable();
            if (drawable != null) {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                String avatar = Base64.encodeToString(byteArray, Base64.DEFAULT);

                Staff item = new Staff(ten, sdt, email, pass, avatar);
                if (staffDAO.insert(item)) {
                    Toast.makeText(getContext(), "Thêm tài khoản khách hàng thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(staffDAO.getAll());
                    staffAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Thêm tài khoản khách hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getContext(), "Chọn hình ảnh trước khi thêm", Toast.LENGTH_SHORT).show();
            }
        });

        btnHuyDiaNhanVien.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}