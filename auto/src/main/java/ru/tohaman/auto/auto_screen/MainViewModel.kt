package ru.tohaman.auto.auto_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class MainViewModel() : ViewModel() {
    val carData = MutableLiveData<String>()


}