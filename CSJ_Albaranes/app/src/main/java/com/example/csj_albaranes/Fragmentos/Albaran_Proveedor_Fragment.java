package com.example.csj_albaranes.Fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.csj_albaranes.R;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;


public class Albaran_Proveedor_Fragment extends Fragment {
    private Context context;

    private EditText etNumAlbaran, etProveedor;

    private ImageView zoomBtn, remoteEyeBtn;

    private Button anteriorBtn, siguienteBtn;

    private File foto;

    public Albaran_Proveedor_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etNumAlbaran = getActivity().findViewById(R.id.etNumAlbaran);
        etProveedor = getActivity().findViewById(R.id.etProveedor);
        anteriorBtn = getActivity().findViewById(R.id.btnAnterior_Data1);
        siguienteBtn = getActivity().findViewById(R.id.btnSiguiente_Data1);
        zoomBtn = getActivity().findViewById(R.id.zoom_login);
        remoteEyeBtn = getActivity().findViewById(R.id.remoteeye_login);

        etNumAlbaran.clearFocus();
        etProveedor.clearFocus();

        foto = (File) getArguments().getSerializable("foto");

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

        siguienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("numAlbaran", etNumAlbaran.getText().toString());
                args.putString("proveedor", etProveedor.getText().toString());
                args.putSerializable("foto", foto);
                args.putSerializable("registro", getArguments().getSerializable("registro"));
                args.putString("token", getArguments().getString("token"));
                Datos_Fragment datosFragment = new Datos_Fragment();
                datosFragment.setArguments(args);
                getParentFragmentManager().beginTransaction().replace(R.id.nav_container, datosFragment).addToBackStack("data1").commit();
            }
        });

        anteriorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStackImmediate();
            }
        });
    }
}