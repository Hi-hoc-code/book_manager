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
    import com.example.readingbook.model.Customer;
    import com.google.android.material.imageview.ShapeableImageView;

    import java.io.ByteArrayOutputStream;
    import java.util.ArrayList;

    public class CustomerRcvAdapter extends RecyclerView.Adapter<CustomerRcvAdapter.MyViewHoler> {
        Context context;
        ArrayList<Customer> list;
        ActivityResultLauncher<String> pickImageLauncher;

        public CustomerRcvAdapter(Context context, ArrayList<Customer> list, ActivityResultLauncher<String> pickImageLauncher) {
            this.context = context;
            this.list = list;
            this.pickImageLauncher = pickImageLauncher;
        }

        @NonNull
        @Override
        public MyViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            View view = inflater.inflate(R.layout.layout_item_customer, parent, false);
            MyViewHoler holder = new MyViewHoler(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHoler holder, int position) {

            holder.imgKhachHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pickImageLauncher.launch("image/*");
                }
            });

            //get
            byte[] decodedString = Base64.decode(list.get(holder.getAdapterPosition()).getImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imgKhachHang.setImageBitmap(decodedByte);
            holder.tvTenKhachHang.setText(list.get(holder.getAdapterPosition()).getTen());
            holder.tvSdtKhachHang.setText(list.get(holder.getAdapterPosition()).getSdt());
            holder.tvIdKhachHang.setText("KH0" + String.valueOf(list.get(holder.getAdapterPosition()).getId()));
            CustomerDAO customerDAO = new CustomerDAO(context);

            holder.btnEditKhachHang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_add_khach_hang);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                    EditText edtTenKhachHang, edtSdtKhachHang, edtEmailKhachHang, edtPassKhachHang;
                    Button btnSubmit, btnCancel;
                    ShapeableImageView imgAvatarKhachHang;

                    edtTenKhachHang = dialog.findViewById(R.id.edtTenKhachHang);
                    edtSdtKhachHang = dialog.findViewById(R.id.edtSdtKhachHang);
                    edtEmailKhachHang = dialog.findViewById(R.id.edtEmailKhachHang);
                    edtPassKhachHang = dialog.findViewById(R.id.edtPassKhachHang);
                    imgAvatarKhachHang = dialog.findViewById(R.id.imgAvatarKhachHang);

                    btnSubmit = dialog.findViewById(R.id.btnSubmitKhachHang);
                    btnCancel = dialog.findViewById(R.id.btnHuyDigKhachHang);

                    edtTenKhachHang.setText(list.get(holder.getAdapterPosition()).getTen());
                    edtSdtKhachHang.setText(list.get(holder.getAdapterPosition()).getSdt());
                    edtEmailKhachHang.setText(list.get(holder.getAdapterPosition()).getEmail());
                    edtPassKhachHang.setText(list.get(holder.getAdapterPosition()).getPassword());

                    imgAvatarKhachHang.setOnClickListener(v -> {
                        pickImageLauncher.launch("image/*");
                    });
                    byte[] decodedString = Base64.decode(list.get(holder.getAdapterPosition()).getImage(), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imgAvatarKhachHang.setImageBitmap(decodedByte);

                    btnSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String ten = edtTenKhachHang.getText().toString();
                            String sdt = edtSdtKhachHang.getText().toString();
                            String email = edtEmailKhachHang.getText().toString();
                            String pass = edtPassKhachHang.getText().toString();
                            Drawable drawable = imgAvatarKhachHang.getDrawable();
                            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                            byte[] byteArray = byteArrayOutputStream.toByteArray();
                            String avatar = Base64.encodeToString(byteArray, Base64.DEFAULT);

                            Customer updatedCustomer = new Customer(list.get(holder.getAdapterPosition()).getId(), ten, sdt, email, pass, avatar);
                            if (customerDAO.update(updatedCustomer)) {
                                Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                                list.set(holder.getAdapterPosition(), updatedCustomer); // Update the item in the list
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
                            if (customerDAO.delete(holder.getAdapterPosition())) {
                                Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(customerDAO.getAll());
                                notifyDataSetChanged();
                                dialogInterface.dismiss();
                            }else {
                                Toast.makeText(context, "Xóa ko thành công", Toast.LENGTH_SHORT).show();
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
            ImageView imgKhachHang;
            TextView tvTenKhachHang, tvSdtKhachHang, tvIdKhachHang;
            ImageView btnEditKhachHang, btnDeleteKhachHang;

            public MyViewHoler(@NonNull View view) {
                super(view);
                imgKhachHang = view.findViewById(R.id.imgKhachHang);
                tvTenKhachHang = view.findViewById(R.id.tvTenKhachHang);
                tvSdtKhachHang = view.findViewById(R.id.tvSdtKhachHang);
                tvIdKhachHang = view.findViewById(R.id.tvIdKhachHang);
                btnEditKhachHang = view.findViewById(R.id.btnEditKhachHang);
                btnDeleteKhachHang = view.findViewById(R.id.btnDeleteKhachHang);
            }
        }
    }
