package br.infnet.dr3_gabriel_justino_tp3

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.infnet.dr3_gabriel_justino_tp3.domain.MyFirestoreUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {
    val users = Firebase.firestore.collection("users")
    val currentFirestoreUser = MutableLiveData<MyFirestoreUser>()
    fun setFirestoreUser(mUser: FirebaseUser?){
        mUser?.let{user->
            val uid = user.uid
            if(uid != null){
                val task = users.document(uid).get()
                task.addOnSuccessListener { snapshot->
                    val snapshotUser = snapshot.toObject<MyFirestoreUser>()
                    snapshotUser?.let { currentFirestoreUser.postValue(it) }
                }

            }
        }
    }
    fun createUserOnFirestore(mUser: FirebaseUser?) {
        mUser?.let{
            viewModelScope.launch {

                val firestoreuser = MyFirestoreUser.fromFirebaseUser(mUser)
                firestoreuser.uidUser.let{uid ->
                    val task = users.document("$uid").set(firestoreuser)
                    task.addOnSuccessListener {
                        println(it)
                    }
                }
            }
        }
    }

    val isLoggedIn = MutableLiveData<Boolean>().apply { value=false }

}