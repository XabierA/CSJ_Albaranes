package com.example.csj_albaranes.Fragmentos;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.csj_albaranes.R;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CheckQRFragment extends Fragment {
    private static int MICROPHONE_PERMISSION_CODE = 200;
    private Context context;
    private ImageView remoteEyeBtn, zoomBtn;

    private Button anteriorBtn, siguienteBtn;

    private ImageButton QRBtn;

    public CheckQRFragment() {
        // Required empty public constructor
    }

    public static CheckQRFragment newInstance() {
        CheckQRFragment fragment = new CheckQRFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkqr, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity().getApplicationContext();

        remoteEyeBtn = getView().findViewById(R.id.remoteeye_data);
        zoomBtn = getView().findViewById(R.id.zoom_data);
        anteriorBtn = getView().findViewById(R.id.btnAnterior_Docs);
        siguienteBtn = getView().findViewById(R.id.btnSiguiente_Docs);
        QRBtn = getView().findViewById(R.id.qrBtn);

        if(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA}, 101);
        }else {

        }

        zoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("us.zoom.videomeetings");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });
        remoteEyeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getActivity().getPackageManager().getLaunchIntentForPackage("com.wideum.remoteeye");
                if (launchIntent != null) {
                    startActivity(launchIntent);//null pointer check in case package name was not found
                }
            }
        });

        QRBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRFragment f = new QRFragment();
                Bundle args = new Bundle();
                args.putSerializable("foto", getArguments().getSerializable("foto"));
                args.putSerializable("registro", getArguments().getSerializable("registro"));
                args.putString("token", getArguments().getString("token"));
                f.setArguments(args);
                getParentFragmentManager().beginTransaction().replace(R.id.nav_container, f).addToBackStack("foto").commit();
            }
        });

        anteriorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStackImmediate();
            }
        });

        siguienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("foto", getArguments().getSerializable("foto"));
                args.putSerializable("registro", getArguments().getSerializable("registro"));
                args.putString("token", getArguments().getString("token"));
                Albaran_Proveedor_Fragment albaranProveedorFragment = new Albaran_Proveedor_Fragment();
                albaranProveedorFragment.setArguments(args);
                getParentFragmentManager().beginTransaction().replace(R.id.nav_container, albaranProveedorFragment).addToBackStack("skipQR").commit();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    }

    private void getMicrophonePermission(){
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(), new String[]
                    {Manifest.permission.RECORD_AUDIO}, MICROPHONE_PERMISSION_CODE);
        }
    }
}