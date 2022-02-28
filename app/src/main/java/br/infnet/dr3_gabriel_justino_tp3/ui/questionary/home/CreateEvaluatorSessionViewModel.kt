package br.infnet.dr3_gabriel_justino_tp3.ui.questionary.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class CreateEvaluatorSessionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun setText(txt:String){
        _text.postValue(txt)
    }
}