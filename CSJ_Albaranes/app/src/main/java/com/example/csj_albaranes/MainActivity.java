package com.example.csj_albaranes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.csj_albaranes.Fragmentos.FotoFragment;
import com.example.csj_albaranes.Fragmentos.QR_Scan_Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.nav_container, FotoFragment.newInstance()).commit();
    }
}