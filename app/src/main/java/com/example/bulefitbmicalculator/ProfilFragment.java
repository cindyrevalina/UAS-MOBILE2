package com.example.bulefitbmicalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfilFragment extends Fragment {

    private ImageView ivPanahKanan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profil, container, false);

        ivPanahKanan = view.findViewById(R.id.iv_panah_kanan);

        ivPanahKanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    // Cara paksa: Langsung menukar fragment profil menjadi fragment home secara mandiri
                    // Catatan: Pastikan R.id.fragment_container sesuai dengan id FrameLayout di activity_main.xml kamu
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new HomeFragment())
                            .commit();
                }
            }
        });

        return view;
    }
}