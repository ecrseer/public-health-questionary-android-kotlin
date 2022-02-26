package br.infnet.dr3_gabriel_justino_tp3.domain

import androidx.annotation.WorkerThread
import androidx.room.*

@Entity
data class EvaluatorSession(
    @PrimaryKey(autoGenerate = true)
    val idEvaluator:Long? = null,
    val companyName:String,
    val district:String,
    val answers: String
)

@Dao
interface EvaluatorSessionDAO{
    @Insert
    fun insert(evaluatorSession: EvaluatorSession):Long
}

class EvaluatorRepository(private val evaluatorSessionDAO: EvaluatorSessionDAO){


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addNewSession (evaluatorSession: EvaluatorSession):Long{
        return evaluatorSessionDAO.insert(evaluatorSession)

    }

}