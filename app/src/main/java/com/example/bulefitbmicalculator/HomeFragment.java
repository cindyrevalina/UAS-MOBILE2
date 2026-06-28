package com.example.bulefitbmicalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private EditText etTinggi, etBerat;
    private Button btnHitung;
    private LinearLayout layoutHasil;
    private TextView tvSkorBMI, tvStatusBMI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Menghubungkan layout XML fragment_home ke Java
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        etTinggi = view.findViewById(R.id.etTinggi);
        etBerat = view.findViewById(R.id.etBerat);
        btnHitung = view.findViewById(R.id.btnHitung);
        layoutHasil = view.findViewById(R.id.layoutHasil);
        tvSkorBMI = view.findViewById(R.id.tvSkorBMI);
        tvStatusBMI = view.findViewById(R.id.tvStatusBMI);

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitungSkorBMI();
            }
        });

        return view;
    }

    private void hitungSkorBMI() {
        String tinggiStr = etTinggi.getText().toString().trim();
        String beratStr = etBerat.getText().toString().trim();

        if (tinggiStr.isEmpty() || beratStr.isEmpty()) {
            Toast.makeText(getActivity(), "Tinggi dan Berat badan wajib diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        float tinggiCm = Float.parseFloat(tinggiStr);
        float beratKg = Float.parseFloat(beratStr);

        if (tinggiCm <= 0 || beratKg <= 0) {
            Toast.makeText(getActivity(), "Input nilai harus lebih dari 0!", Toast.LENGTH_SHORT).show();
            return;
        }

        float tinggiMeter = tinggiCm / 100;
        float bmi = beratKg / (tinggiMeter * tinggiMeter);

        layoutHasil.setVisibility(View.VISIBLE);
        String skorBmiStr = String.format("%.1f", bmi);
        tvSkorBMI.setText(skorBmiStr);

        String status;
        int warnaTeks;

        if (bmi < 18.5) {
            status = "Kurus (Kurang Berat Badan)";
            warnaTeks = 0xFF3B82F6;
        } else if (bmi >= 18.5 && bmi < 25) {
            status = "Normal (Ideal)";
            warnaTeks = 0xFF10B981;
        } else if (bmi >= 25 && bmi < 30) {
            status = "Gemuk (Kelebihan Berat)";
            warnaTeks = 0xFFF59E0B;
        } else {
            status = "Obesitas";
            warnaTeks = 0xFFEF4444;
        }

        tvStatusBMI.setText(status);
        tvStatusBMI.setTextColor(warnaTeks);
        tvSkorBMI.setTextColor(warnaTeks);

        // Menyimpan hasil perhitungan ke data lokal HP
        simpanKeRiwayat(skorBmiStr, status, tinggiStr, beratStr);
    }

    private void simpanKeRiwayat(String skor, String status, String tinggi, String berat) {
        if (getActivity() == null) return;

        // Mengambil tanggal dan jam saat tombol hitung ditekan
        String tanggal = new SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(new Date());

        // Menggabungkan data dengan pembatas tanda "|"
        String dataBaru = tanggal + "|" + skor + "|" + status + "|" + tinggi + " cm|" + berat + " kg";

        // Inisialisasi SharedPreferences dan Editor yang sudah diperbaiki
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("BuleFitPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Mengambil string riwayat lama jika sudah pernah menyimpan sebelumnya
        String riwayatLama = sharedPreferences.getString("riwayat_bmi", "");
        String riwayatUpdate;

        if (riwayatLama.isEmpty()) {
            riwayatUpdate = dataBaru;
        } else {
            // Menumpuk data terbaru agar selalu berada di posisi paling atas list
            riwayatUpdate = dataBaru + "\n" + riwayatLama;
        }

        // Menyimpan string gabungan yang baru ke dalam memori lokal
        editor.putString("riwayat_bmi", riwayatUpdate);
        editor.apply();

        Toast.makeText(getActivity(), "Hasil disimpan ke riwayat", Toast.LENGTH_SHORT).show();
    }
}