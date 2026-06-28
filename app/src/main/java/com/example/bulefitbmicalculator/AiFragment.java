package com.example.bulefitbmicalculator;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Handler; // Ditambahkan untuk efek delay balas chat
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import java.util.ArrayList;

public class AiFragment extends Fragment {

    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private ArrayList<MessageModel> messageList;
    private EditText inputMessage;
    private ImageView btnSend;

    public AiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ai, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewChat);
        inputMessage = view.findViewById(R.id.inputMessage);
        btnSend = view.findViewById(R.id.btnSend);

        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(messageList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatAdapter);

        // Pesan sambutan awal dari AI
        if (messageList.isEmpty()) {
            messageList.add(new MessageModel("Halo! Ada yang bisa saya bantu terkait BuleFit?", false));
            chatAdapter.notifyDataSetChanged();
        }

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = inputMessage.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    // 1. Kirim pesan dari User ke RecyclerView
                    messageList.add(new MessageModel(messageText, true));
                    chatAdapter.notifyItemInserted(messageList.size() - 1);
                    recyclerView.scrollToPosition(messageList.size() - 1);

                    inputMessage.setText("");

                    // 2. Jalankan fungsi balasan otomatis dari AI
                    simulasiBalasanAi();
                }
            }
        });

        return view;
    }

    // Fungsi baru untuk memicu jawaban otomatis dari AI
    private void simulasiBalasanAi() {
        // Kita beri sedikit delay 1 detik (1000 milidetik) agar terasa natural seperti sedang mengetik
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String responAi = "Termasuk kategori normal, berat badan Anda ideal. Pertahankan pola makan sehat dan rutin berolahraga ya!";

                // Tambahkan pesan AI ke dalam list (false artinya dikirim oleh AI/bukan user)
                messageList.add(new MessageModel(responAi, false));

                // Beritahu adapter bahwa ada data baru masuk
                chatAdapter.notifyItemInserted(messageList.size() - 1);

                // Gulir otomatis chat ke baris paling bawah
                recyclerView.scrollToPosition(messageList.size() - 1);
            }
        }, 1000);
    }
}