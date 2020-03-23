package com.example.kotlindemo.base

import androidx.lifecycle.ViewModel

class BaseViewModel : ViewModel() {
    val multiStatusLiveData = LoadStatusLiveData()
}