package br.infnet.dr3_gabriel_justino_tp3.domain

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentId
data class DistrictSynthData(
    val district:String?=null,
    @DocumentId
    val docId:String?=null,
    val total:Long?=null,
    val answersAverages:List<Int>?=null
)

data class MyFirestoreUser (
    @DocumentId
    val uidUser:String?=null,
    val name:String?=null,
    val email:String?=null,
        ){
    companion object{
        fun fromFirebaseUser(mUser:FirebaseUser): MyFirestoreUser {
            return MyFirestoreUser(mUser.uid,mUser.displayName,mUser.email)
        }

    }
}