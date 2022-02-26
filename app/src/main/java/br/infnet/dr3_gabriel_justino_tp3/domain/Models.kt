package br.infnet.dr3_gabriel_justino_tp3.domain

import androidx.annotation.WorkerThread
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity
data class EvaluatorSession(
    @PrimaryKey(autoGenerate = true)
    val idEvaluator:Long? = null,
    val companyName:String,
    val district:String,
    val answers:MutableList<String>
)
interface EvaluatorSessionDAO{
    @Query("SELECT * FROM evaluatorsession")
    fun getAll():List<EvaluatorSession>
}
class EvaluatorRepository(private val evaluatorSessionDAO: EvaluatorSessionDAO){
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllSessions (): List<EvaluatorSession> {
        return evaluatorSessionDAO.getAll()

    }

}