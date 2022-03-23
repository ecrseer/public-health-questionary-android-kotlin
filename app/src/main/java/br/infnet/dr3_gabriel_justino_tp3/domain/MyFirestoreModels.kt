package br.infnet.dr3_gabriel_justino_tp3.domain

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class DistrictSynthData(
    val district:String?=null,
    @DocumentId
    val docId:String?=null,
    var total:Long?=null,
    val answersTotal:MutableList<Int>?=null,
    val positiveAnswers:MutableList<Int>?=null
){
    private fun incrementIntegerOnList(list: MutableList<Int>?,index:Int){
        list?.get(index)?.let { answerByIndex->
            list.set(index,answerByIndex+1)
        }
    }
    fun setSynthsizedDataByStringAnswers(answersA:String): MutableList<Int> {
        val answersAverages = mutableListOf<Int>();
        for((index,character) in answersA.toCharArray().withIndex()){
            try {
                val newTotal = this.total?.plus(1)
                newTotal?.let { this.total=it }

                val currentAnswer =  character.digitToInt()
                val isPositiveAnswer = currentAnswer==9
                val isNegativeAnswer = currentAnswer==1

                if(isPositiveAnswer){
                    incrementIntegerOnList(this.answersTotal,index)
                    incrementIntegerOnList(this.positiveAnswers,index)
                }
                if(isNegativeAnswer){
                    incrementIntegerOnList(this.answersTotal,index)
                }
            }catch (e:Exception){}
        }

        return answersAverages;
    }
}
object DistrictSynthDataRepository{

    val districtSyntData = Firebase.firestore
        .collection("synthesizedData")

}

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