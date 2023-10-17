package com.example.readingbook.fragment;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbook.R;
import com.example.readingbook.adapter.BookAdapter;
import com.example.readingbook.dao.BookDAO;
import com.example.readingbook.model.Book;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {
    private String kindBookSelected;
    FloatingActionButton btnAddBookManager;
    RecyclerView rcvBookManager;
    ActivityResultLauncher<String> pickImageLauncher;
    ShapeableImageView imgKindBook;
    BookDAO bookDAO;
    BookAdapter bookAdapter;
    ArrayList<Book> list ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.book_fragment, container, false);
        addControls(view);
        return view;
    }


    private void addControls(View view) {

        btnAddBookManager = view.findViewById(R.id.btnAddBookManager);
        rcvBookManager = view.findViewById(R.id.rcvBookManager);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rcvBookManager.setLayoutManager(layoutManager);

        bookDAO = new BookDAO(getContext());
        list= bookDAO.getAllBook();
        bookAdapter = new BookAdapter(getContext(),list,pickImageLauncher);
        rcvBookManager.setAdapter(bookAdapter);
        btnAddBookManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dig_add_book);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                EditText edtBook, edtAuthor;
                Button btnSubmitDigBook, btnCancleDigBook;
                Spinner spKindBook;
                List listKind = new ArrayList<>();
                listKind.add("Truyện tranh");
                listKind.add("Đời sống");
                listKind.add("Chính trị & lịch sử");
                listKind.add("Nét đẹp cuộc sống");

                edtBook = dialog.findViewById(R.id.edtTenBookDig);
                edtAuthor = dialog.findViewById(R.id.edtTacGiaDig);
                spKindBook = dialog.findViewById(R.id.spKindBook);
                btnSubmitDigBook = dialog.findViewById(R.id.btnSubmitBookDig);
                btnCancleDigBook = dialog.findViewById(R.id.btnCancelBookDig);

                ArrayAdapter<String> listKindBook = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,listKind);
                spKindBook.setAdapter(listKindBook);

                imgKindBook = dialog.findViewById(R.id.imgBookDig);
//                imgKindBook.setOnClickListener(v -> {
//                    pickImageLauncher.launch("image/*");
//                });

                spKindBook.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        kindBookSelected = (String) adapterView.getItemAtPosition(i);
                        Toast.makeText(requireContext(), kindBookSelected, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {
                    }
                });
                btnSubmitDigBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String ten = edtBook.getText().toString();
                        String tacGia = edtAuthor.getText().toString();

                        imgKindBook.setOnClickListener(v -> {
                            pickImageLauncher.launch("image/*");
                        });

                        Drawable drawable = imgKindBook.getDrawable();
                        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                        byte[] byteArray = byteArrayOutputStream.toByteArray();
                        String avatar = Base64.encodeToString(byteArray, Base64.DEFAULT);


                        Log.d(kindBookSelected, "checkKindBook: ");
                        if(kindBookSelected!=null){
                            Book item = new Book(ten, tacGia, kindBookSelected, avatar);
                            if(bookDAO.insert(item)){
                                Toast.makeText(getContext(),"Thêm sách thành công",Toast.LENGTH_SHORT).show();
                                list.clear();
                                list.addAll(bookDAO.getAllBook());
                                bookAdapter.notifyDataSetChanged();
                                dialog.dismiss();
                            }else{
                                Toast.makeText(getContext(),"Thêm sách thất bại",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getContext(),"Null",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnCancleDigBook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imgKindBook = new ShapeableImageView(requireContext());
        pickImageLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                result -> {
                    if (result != null) {
                        imgKindBook.setImageURI(result);
                    }
                }
        );
    }
}
