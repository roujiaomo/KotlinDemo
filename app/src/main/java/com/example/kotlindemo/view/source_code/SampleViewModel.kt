package com.example.kotlindemo.view.source_code

/**
 * LiveData为抽象类
 */
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

class SampleViewModel : ViewModel() {

    private val _getData = MediatorLiveData<String>()
     val getData: LiveData<String> = _getData


}