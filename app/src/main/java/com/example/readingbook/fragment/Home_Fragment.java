package com.example.readingbook.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbook.R;
import com.example.readingbook.adapter.RecycleViewBookManagerAdapter;
import com.example.readingbook.dao.SachDAO;
import com.example.readingbook.model.Book;

import java.util.ArrayList;

public class Home_Fragment extends Fragment {
    Context context;
    RecyclerView rcvBook;
    SachDAO bookDAO;
    RecycleViewBookManagerAdapter recycleViewBookManagerAdapter;
    ArrayList<Book> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private void addEvents() {
        bookDAO = new SachDAO(context);
        list = bookDAO.getAllBook();
        recycleViewBookManagerAdapter = new RecycleViewBookManagerAdapter(context, list);
        rcvBook.setAdapter(recycleViewBookManagerAdapter);
    }

    private void addControls(View view) {
        rcvBook = view.findViewById(R.id.rvcBook);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvBook.setLayoutManager(linearLayoutManager);
        if (rcvBook == null) {
            Toast.makeText(context, "RecyclerView not found", Toast.LENGTH_SHORT).show();
        }
    }

}
