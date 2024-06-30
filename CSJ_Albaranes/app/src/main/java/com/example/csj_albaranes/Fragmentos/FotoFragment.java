package com.example.csj_albaranes.Fragmentos;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
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
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.csj_albaranes.Models.Albaran;
import com.example.csj_albaranes.Models.ItemHashesAndMetadata;
import com.example.csj_albaranes.Models.Login;
import com.example.csj_albaranes.Models.LoginResponse;
import com.example.csj_albaranes.Models.Metadata;
import com.example.csj_albaranes.Models.ProyectoCC;
import com.example.csj_albaranes.Models.ProyectoCCResponse;
import com.example.csj_albaranes.Models.RegistrarFoto;
import com.example.csj_albaranes.Models.response;
import com.example.csj_albaranes.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;


public class FotoFragment extends Fragment {
    private static int MICROPHONE_PERMISSION_CODE = 200;
    private Context context;

    private ImageView remoteEyeBtn, zoomBtn;

    private Button fotoBtn, siguienteBtn;

    private File foto;

    private String token;

    private RegistrarFoto registrarFoto;

    private Integer numAlbaranes;

    private interface GetNumAlbaranes{
        @GET("GetNumAlbaranes")
        Call<Integer> getNumAlbaranes();
    }

    public FotoFragment() {
        // Required empty public constructor
    }

    public static FotoFragment newInstance() {
        FotoFragment fragment = new FotoFragment();
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
        return inflater.inflate(R.layout.fragment_data_, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity().getApplicationContext();

        remoteEyeBtn = getView().findViewById(R.id.remoteeye_data);
        zoomBtn = getView().findViewById(R.id.zoom_data);
        fotoBtn = getView().findViewById(R.id.fotoBtn);
        //anteriorBtn = getView().findViewById(R.id.btnAnterior_Docs);
        siguienteBtn = getView().findViewById(R.id.btnSiguiente_Docs);

        if(ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.CAMERA}, 101);
        }else {

        }

        Retrofit retrofit2 = new Retrofit.Builder().baseUrl("URL de la api")
                .addConverterFactory(GsonConverterFactory.create()).build();

        FotoFragment.GetNumAlbaranes requestPaciente = retrofit2.create(FotoFragment.GetNumAlbaranes.class);
        Call<Integer> call = requestPaciente.getNumAlbaranes();
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Integer apiResponse = response.body();
                if(apiResponse != null){
                    if (apiResponse > 0){
                        numAlbaranes = apiResponse+1;
                    }else {
                        numAlbaranes = 0;
                    }
                }else {
                    numAlbaranes = 0;
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, "Problemas de conexion", Toast.LENGTH_LONG).show();
            }
        });

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

        fotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        siguienteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putSerializable("foto", foto);
                args.putSerializable("registro", registrarFoto);
                args.putString("token", token);
                CheckQRFragment checkQRFragment = new CheckQRFragment();
                checkQRFragment.setArguments(args);
                getParentFragmentManager().beginTransaction().replace(R.id.nav_container, checkQRFragment).addToBackStack("foto").commit();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_OK && resultCode == RESULT_OK) {

        }
        String filePath = foto.getPath();

        // Create a SHA-256 message digest
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        // Read the file and update the digest
        try (FileInputStream fis = new FileInputStream(filePath)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the hash value
        byte[] hashBytes = digest.digest();

        // Convert the hash bytes to a hexadecimal string
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            hexString.append(String.format("%02x", b));
        }

        String hash = hexString.toString();

        registrarFoto = new RegistrarFoto(new ItemHashesAndMetadata(hash, new Metadata("IMG", "Albaran"+numAlbaranes+".jpg")), "6625424b6a25fab563cc267e");

        Log.v("nombrefoto", "Albaran"+numAlbaranes+".jpg");
        Log.v("hash", hash);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //imagenes.add(photoFile);
                Uri photoURI = FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()),
                        context.getPackageName()+".provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 102);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "albaran"+numAlbaranes;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        foto = image;
        return image;
    }
}