    package com.example.readingbook.fragment;

    import android.app.Activity;
    import android.app.Dialog;
    import android.content.Intent;
    import android.graphics.Bitmap;
    import android.graphics.drawable.BitmapDrawable;
    import android.graphics.drawable.Drawable;
    import android.net.Uri;
    import android.os.Bundle;
    import android.provider.MediaStore;
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
    import com.example.readingbook.dao.ThanhVienDAO;
    import com.example.readingbook.model.KhachHang;
    import com.google.android.material.floatingactionbutton.FloatingActionButton;
    import com.google.android.material.imageview.ShapeableImageView;

    import java.io.ByteArrayOutputStream;
    import java.io.IOException;
    import java.util.ArrayList;

    public class Customer_Fragment extends Fragment {
        private ThanhVienDAO thanhVienDAO;
        private ArrayList<KhachHang> list;
        private RecyclerView rcvKhachHang;
        private CustomerRcvAdapter customerRcvAdapter;
        private ActivityResultLauncher<String> pickImageLauncher;
        private ShapeableImageView imgAvatarKhachHang;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.customer_fragment, container, false);
            addControls(view);
            return view;
        }
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            imgAvatarKhachHang = new ShapeableImageView(requireContext());
            pickImageLauncher = registerForActivityResult(
                    new ActivityResultContracts.GetContent(),
                    result -> {
                        if (result != null) {
                            imgAvatarKhachHang.setImageURI(result);
                        }
                    }
            );
        }

        private void addControls(View view) {
            rcvKhachHang = view.findViewById(R.id.rvcLisstKhachHang);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            rcvKhachHang.setLayoutManager(layoutManager);
            thanhVienDAO = new ThanhVienDAO(getContext());
            list = thanhVienDAO.getAll();
            customerRcvAdapter = new CustomerRcvAdapter(getContext(), list);
            rcvKhachHang.setAdapter(customerRcvAdapter);

            FloatingActionButton btnAddKhachHang = view.findViewById(R.id.btnAddKhachHang);
            btnAddKhachHang.setOnClickListener(v -> showDialogToAddKhachHang());
        }
        private void showDialogToAddKhachHang() {
            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.dialog_add_khach_hang);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            EditText edtTenKhachHang, edtSdtKhachHang, edtEmailKhachHang, edtPassKhachHang;

            edtTenKhachHang = dialog.findViewById(R.id.edtTenKhachHang);
            edtSdtKhachHang = dialog.findViewById(R.id.edtSdtKhachHang);
            edtEmailKhachHang = dialog.findViewById(R.id.edtEmailKhachHang);
            edtPassKhachHang = dialog.findViewById(R.id.edtPassKhachHang);
            imgAvatarKhachHang = dialog.findViewById(R.id.imgAvatarKhachHang);

            imgAvatarKhachHang.setOnClickListener(v -> {
                pickImageLauncher.launch("image/*");
            });

            Button btnSubmitKhachHang = dialog.findViewById(R.id.btnSubmitKhachHang);
            Button btnHuyDigKhachHang = dialog.findViewById(R.id.btnHuyDigKhachHang);

            btnSubmitKhachHang.setOnClickListener(v -> {
                String ten = edtTenKhachHang.getText().toString();
                String sdt = edtSdtKhachHang.getText().toString();
                String email = edtEmailKhachHang.getText().toString();
                String pass = edtPassKhachHang.getText().toString();

                // Convert the selected image to Base64
                Drawable drawable = imgAvatarKhachHang.getDrawable();
                if (drawable != null) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String avatar = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    KhachHang item = new KhachHang(ten, sdt, email, pass, avatar);
                    if (thanhVienDAO.insert(item)) {
                        Toast.makeText(getContext(), "Thêm tài khoản khách hàng thành công", Toast.LENGTH_SHORT).show();
                        list.clear();
                        list.addAll(thanhVienDAO.getAll());
                        customerRcvAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Thêm tài khoản khách hàng thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Chọn hình ảnh trước khi thêm", Toast.LENGTH_SHORT).show();
                }
            });

            btnHuyDigKhachHang.setOnClickListener(v -> dialog.dismiss());

            dialog.show();
        }
    }
