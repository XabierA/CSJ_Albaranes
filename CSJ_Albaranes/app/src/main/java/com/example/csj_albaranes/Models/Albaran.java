package com.example.csj_albaranes.Models;

import java.io.Serializable;
import java.util.Date;

public class Albaran implements Serializable {

    public String objectId;

    public String numAlbaran;

    public String proveedor;

    public String m3;

    public String tipoHormigon;

    public String otro;

    public String foto;

    public Boolean activo;

    public Albaran(){

    };
    public Albaran(String numAlbaran, String proveedor, String m3, String tipoHormigon, String otro, Boolean activo){
        this.numAlbaran = numAlbaran;
        this.proveedor = proveedor;
        this.m3 = m3;
        this.tipoHormigon = tipoHormigon;
        this.otro = otro;
        this.activo = activo;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getNumAlbaran() {
        return numAlbaran;
    }

    public void setNumAlbaran(String numAlbaran) {
        this.numAlbaran = numAlbaran;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getM3() {
        return m3;
    }

    public void setM3(String m3) {
        this.m3 = m3;
    }

    public String getTipoHormigon() {
        return tipoHormigon;
    }

    public void setTipoHormigon(String tipoHormigon) {
        this.tipoHormigon = tipoHormigon;
    }

    public String getOtro() {
        return otro;
    }

    public void setOtro(String otro) {
        this.otro = otro;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
