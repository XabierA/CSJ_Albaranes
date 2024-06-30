package com.example.csj_albaranes.AWS;

import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;
import java.util.List;

public class UploadAmazonS3 extends AsyncTask {
    private String ACCESS_ID = "";
    private String SECRET_KEY = "";
    private String BUCKET_NAME = "";
    private AWSCredentials credentials;
    private AmazonS3 conn;

    private int numAlbaran;


    @Override
    protected Object doInBackground(Object[] objects) {

        credentials = new BasicAWSCredentials(ACCESS_ID, SECRET_KEY);
        conn = new AmazonS3Client(credentials);

        File archivo = (File) objects[0];

        conn.putObject(BUCKET_NAME, "albaran"+numAlbaran+".jpg", archivo);
        return null;
    }

    public void setName(int numAlbaran){
        this.numAlbaran = numAlbaran;
    }

    protected void onPostExecute(String feed) {

        return ;
    }


}
