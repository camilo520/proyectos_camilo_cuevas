package com.example.proyecto_final.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class asistenciaViewModelPR extends ViewModel {

    private MutableLiveData<String> mText;

    public asistenciaViewModelPR() {
        mText = new MutableLiveData<>();
        mText.setValue("Asistencia profesor");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
