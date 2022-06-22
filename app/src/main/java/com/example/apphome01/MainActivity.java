package com.example.apphome01;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUrl;
    private Button btnDownload;
    private ProgressBar pgbProgress;
    private ImageView imgDown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUrl = findViewById(R.id.edtUrl);
        btnDownload = findViewById(R.id.btnDownload);
        pgbProgress = findViewById(R.id.pgbProgress);
        imgDown = findViewById(R.id.imgDown);

        btnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        btnDownload.setEnabled(false);
        pgbProgress.setVisibility(View.VISIBLE);
        uploadImg();
    }

    private void uploadImg() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(5000);
                try {
                    String url = edtUrl.getText().toString();
                    /* Donload da imagem */
                    InputStream in = new URL(url).openStream();
                    /* Converte a InputStream do Java para Bitmap */
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    in.close();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /* Pega a imagem convertida pelo Bitmap */
                            imgDown.setImageBitmap(bitmap);
                            /* Deixa progres off */
                            pgbProgress.setVisibility(View.INVISIBLE);
                            /* Apresenta a imagem */
                            imgDown.setVisibility(View.VISIBLE);
                            /* Botão fica off */
                            btnDownload.setEnabled(true);
                        }
                    });
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        });
    }
}