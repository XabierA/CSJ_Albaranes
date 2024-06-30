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

import com.example.csj_albaranes.AWS.UploadAmazonS3;
import com.example.csj_albaranes.Models.Albaran;
import com.example.csj_albaranes.Models.LoginResponse;
import com.example.csj_albaranes.Models.RegistrarFoto;
import com.example.csj_albaranes.Models.RegistrarFotoResponse;
import com.example.csj_albaranes.Models.response;
import com.example.csj_albaranes.R;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;

public class Datos_Fragment extends Fragment {
    private Context context;

    private Button anteriorBtn, finalizarBtn;

    private EditText etM3, etHormigon, etOtro;

    private String m3, hormigon, otro, numAlbaran, proveedor, token;

    private Integer numAlbaranes;

    private File foto;

    private ImageView remoteEyeBtn, zoomBtn;

    private RegistrarFoto registrarFoto2;

    private interface GetNumAlbaranes{
        @GET("GetNumAlbaranes")
        Call<Integer> getNumAlbaranes();
    }

    private interface PostRequestAlbaran{
        @POST("insertarAlbaran")
        Call<response> postAlbaran(@Body Albaran albaran);
    }

    public Datos_Fragment() {
        // Required empty public constructor
    }

    public static Datos_Fragment newInstance() {
        Datos_Fragment fragment = new Datos_Fragment();
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
        return inflater.inflate(R.layout.fragment_datos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        remoteEyeBtn = getView().findViewById(R.id.remoteeye_desc);
        zoomBtn = getView().findViewById(R.id.zoom_desc);
        etM3 = getView().findViewById(R.id.etM3);
        etHormigon = getView().findViewById(R.id.etHormigon);
        etOtro = getView().findViewById(R.id.etOtro);
        anteriorBtn = getView().findViewById(R.id.btnAnterior_Datos);
        finalizarBtn = getView().findViewById(R.id.btnSiguiente_Datos);
        token = getArguments().getString("token");
        registrarFoto2 = (RegistrarFoto) getArguments().getSerializable("registro");



        Retrofit retrofit = new Retrofit.Builder().baseUrl("URL de la api")
                .addConverterFactory(GsonConverterFactory.create()).build();

        GetNumAlbaranes requestNumAlbaran = retrofit.create(GetNumAlbaranes.class);
        Call<Integer> call = requestNumAlbaran.getNumAlbaranes();
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
                Toast.makeText(context, "no focuchiona", Toast.LENGTH_LONG).show();
            }
        });

        foto = (File)getArguments().getSerializable("foto");
        numAlbaran = getArguments().getString("numAlbaran");
        proveedor = getArguments().getString("proveedor");

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

        anteriorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStackImmediate();
            }
        });

        finalizarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m3 = etM3.getText().toString();
                hormigon = etHormigon.getText().toString();
                otro = etOtro.getText().toString();

                Albaran albaran = new Albaran();
                albaran.setNumAlbaran(numAlbaran);
                albaran.setProveedor(proveedor);
                albaran.setM3(m3);
                albaran.setTipoHormigon(hormigon);
                albaran.setOtro(otro);
                albaran.setFoto("albaran"+numAlbaranes+".jpg");
                albaran.setActivo(true);
                getArguments().getSerializable("registro");


                Datos_Fragment.PostRequestAlbaran postRequestAlbaran = retrofit.create(Datos_Fragment.PostRequestAlbaran.class);
                Call<response> call = postRequestAlbaran.postAlbaran(albaran);
                call.enqueue(new Callback<response>() {
                    @Override
                    public void onResponse(Call<response> call, Response<response> response) {
                        UploadAmazonS3 s3 = new UploadAmazonS3();
                        s3.setName(numAlbaranes);
                        s3.execute(foto);
                        Log.v("test","me cago en dios");
                        FragmentManager fm = getParentFragmentManager();
                        for (int i = fm.getBackStackEntryCount() - 1; i > 0; i--) {
                            if (!fm.getBackStackEntryAt(i).getName().equalsIgnoreCase("main")) {
                                fm.popBackStack();
                            }
                            else
                            {
                                break;
                            }
                        }
                        getParentFragmentManager().popBackStackImmediate();
                    }

                    @Override
                    public void onFailure(Call<response> call, Throwable t) {
                        Toast.makeText(context, "Error al guardar la incidencia", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}