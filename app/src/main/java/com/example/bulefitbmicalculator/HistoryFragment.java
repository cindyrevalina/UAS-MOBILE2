package com.example.bulefitbmicalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private ListView listViewHistory;
    private TextView tvEmptyHistory;
    private Button btnClearHistory;
    private ArrayList<String> historyList;
    private ArrayAdapter<String> adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        listViewHistory = view.findViewById(R.id.listViewHistory);
        tvEmptyHistory = view.findViewById(R.id.tvEmptyHistory);
        btnClearHistory = view.findViewById(R.id.btnClearHistory);

        // Aksi ketika tombol Hapus Semua diklik
        btnClearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hapusSemuaRiwayat();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        muatDataRiwayat();
    }

    private void muatDataRiwayat() {
        if (getActivity() == null) return;

        historyList = new ArrayList<>();

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BuleFitPrefs", Context.MODE_PRIVATE);
        String riwayatMentah = sharedPreferences.getString("riwayat_bmi", "");

        if (riwayatMentah.isEmpty()) {
            tvEmptyHistory.setVisibility(View.VISIBLE);
            listViewHistory.setVisibility(View.GONE);
            btnClearHistory.setVisibility(View.GONE); // Sembunyikan tombol jika kosong
        } else {
            tvEmptyHistory.setVisibility(View.GONE);
            listViewHistory.setVisibility(View.VISIBLE);
            btnClearHistory.setVisibility(View.VISIBLE); // Tampilkan tombol jika ada data

            String[] barisRiwayat = riwayatMentah.split("\n");

            for (String baris : barisRiwayat) {
                String[] data = baris.split("\\|");

                if (data.length >= 5) {
                    String tanggal = data[0];
                    String skor = data[1];
                    String status = data[2];
                    String tinggi = data[3];
                    String berat = data[4];

                    String itemTeks = "Waktu Perhitungan: " + tanggal + "\n" +
                            "Skor BMI: " + skor + " (" + status + ")\n" +
                            "Detail Tubuh: " + tinggi + " | " + berat;

                    historyList.add(itemTeks);
                }
            }

            // Menggunakan R.layout.item_history dan R.id.tvItemHistory agar tampilan berjarak rapi
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_history, R.id.tvItemHistory, historyList);
            listViewHistory.setAdapter(adapter);
        }
    }

    private void hapusSemuaRiwayat() {
        if (getActivity() == null) return;

        // Bersihkan data riwayat di SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BuleFitPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("riwayat_bmi", "");
        editor.apply();

        Toast.makeText(getActivity(), "Semua riwayat berhasil dihapus", Toast.LENGTH_SHORT).show();

        // Refresh tampilan halaman
        muatDataRiwayat();
    }
}