package br.infnet.dr3_gabriel_justino_tp3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivityViewModel: ViewModel() {
    val isLoggedIn = MutableLiveData<Boolean>().apply { value=false }

}