package com.example.readingbook.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingbook.R;

import java.util.ArrayList;

public class QuanLiFragment extends Fragment {
    private Button[] buttons;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.quan_li_fragment, container, false);
        addControls(view);
        addEvents();
        return view;
    }

    private void addEvents() {
        for (Button button : buttons) {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = null;
                    int viewId = view.getId();
                    if (viewId == R.id.btnQuanLiNhanVien) {
                        fragment = new Nhan_Vien_Fragment();
                    } else if (viewId == R.id.btnQuanLiKhachHang) {
                        fragment = new Customer_Fragment();
                    } else if (viewId == R.id.btnQuanLiLoaiSach) {
                        fragment = new Loai_Sach_Fragment();
                    } else if (viewId == R.id.btnQuanLiSach) {
                        fragment = new Sach_Fragment();
                    } else if (viewId == R.id.btnQuanLiPhieuMuon) {
                        fragment = new PhieuMuon_Fragment();
                    }

                    if (fragment != null) {
                        replaceFragment(fragment);
                    }
                }
            });
        }
    }

    private void addControls(View view) {
        buttons = new Button[5];
        buttons[0] = view.findViewById(R.id.btnQuanLiNhanVien);
        buttons[1] = view.findViewById(R.id.btnQuanLiKhachHang);
        buttons[2] = view.findViewById(R.id.btnQuanLiLoaiSach);
        buttons[3] = view.findViewById(R.id.btnQuanLiSach);
        buttons[4] = view.findViewById(R.id.btnQuanLiPhieuMuon);
    }

    private void replaceFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .addToBackStack(null)
                .commit();
    }
}
