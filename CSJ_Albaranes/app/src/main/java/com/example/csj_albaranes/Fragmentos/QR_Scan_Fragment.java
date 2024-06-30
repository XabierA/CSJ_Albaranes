package com.example.csj_albaranes.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.csj_albaranes.R;

public class QR_Scan_Fragment extends Fragment {
    private Context context;

    private Button fotoBtn;

    private ImageView zoomBtn, remoteEyeBtn;

    private ImageButton QRBtn;

    public QR_Scan_Fragment() {
        // Required empty public constructor
    }

    public static QR_Scan_Fragment newInstance() {
        QR_Scan_Fragment fragment = new QR_Scan_Fragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        this.context = context;
        //control = (IControlFragmentos) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qr_scan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QRBtn = getActivity().findViewById(R.id.btnQR);
        fotoBtn = getActivity().findViewById(R.id.btnFoto);
        zoomBtn = getActivity().findViewById(R.id.zoom_Proyecto);
        remoteEyeBtn = getActivity().findViewById(R.id.remoteeye_proyecto);

        zoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("us.zoom.videomeetings");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            }
        });
        remoteEyeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.wideum.remoteeye");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            }
        });

        QRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRFragment f = new QRFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_container, f).addToBackStack("proyecto").commit();
            }
        });

        fotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FotoFragment fotoFragment = new FotoFragment();
                getParentFragmentManager().beginTransaction().replace(R.id.nav_container, fotoFragment).addToBackStack("foto").commit();
            }
        });
    }
}