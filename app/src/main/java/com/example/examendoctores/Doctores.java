package com.example.examendoctores;


public class Doctores {

    private String mCodigoHospital;
    private int mIdenDoctor;
    private String mApellido;
    private String mEspecialidad;
    private int mSalario;


    public Doctores() {
    }

    public Doctores(String mCodigoHospital, int mIdenDoctor, String mApellido, String mEspecialidad, int mSalario) {
        this.mCodigoHospital = mCodigoHospital;
        this.mIdenDoctor = mIdenDoctor;
        this.mApellido = mApellido;
        this.mEspecialidad = mEspecialidad;
        this.mSalario = mSalario;
    }

    public String getCodigoHospital() {
        return mCodigoHospital;
    }

    public void setCodigoHospital(String mCodigoHospital) {
        this.mCodigoHospital = mCodigoHospital;
    }

    public int getIdenDoctor() {
        return mIdenDoctor;
    }

    public void setIdenDoctor(int mIdenDoctor) {
        this.mIdenDoctor = mIdenDoctor;
    }

    public String getApellido() {
        return mApellido;
    }

    public void setApellido(String mApellido) {
        this.mApellido = mApellido;
    }

    public String getEspecialidad() {
        return mEspecialidad;
    }

    public void setEspecialidad(String mEspecialidad) {
        this.mEspecialidad = mEspecialidad;
    }

    public int getSalario() {
        return mSalario;
    }

    public void setSalario(int mSalario) {
        this.mSalario = mSalario;
    }

    @Override
    public String toString() {
        return  mApellido;
    }
}
