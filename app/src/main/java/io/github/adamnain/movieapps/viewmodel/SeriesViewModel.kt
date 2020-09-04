package io.github.adamnain.movieapps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeriesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is series Fragment"
    }
    val text: LiveData<String> = _text
}