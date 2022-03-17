package br.infnet.dr3_gabriel_justino_tp3.domain

import androidx.annotation.WorkerThread
import androidx.room.*
import br.infnet.dr3_gabriel_justino_tp3.services.CriptoString
import kotlinx.coroutines.flow.Flow

@Entity
data class EvaluatorSession(
    @PrimaryKey(autoGenerate = true)
    val idEvaluator:Long? = null,
    val companyName: CriptoString,
    val district:CriptoString,
    val answers: String,
    val firebaseEmail:String,
    val timestamp: Long? = null
)

@Dao
interface EvaluatorSessionDAO{
    @Query("SELECT * FROM evaluatorsession")
    fun getAll():Flow<List<EvaluatorSession>>

    @Insert
    fun insert(evaluatorSession: EvaluatorSession):Long

    @Update
    fun update(evaluatorSession: EvaluatorSession)
}

class EvaluatorRepository(private val evaluatorSessionDAO: EvaluatorSessionDAO){
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
     fun getAllSessions (): Flow<List<EvaluatorSession>> {
        return evaluatorSessionDAO.getAll()

    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun addNewSession (evaluatorSession: EvaluatorSession):Long{
        return evaluatorSessionDAO.insert(evaluatorSession)

    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun editSession (evaluatorSession: EvaluatorSession){
        return evaluatorSessionDAO.update(evaluatorSession)

    }

}