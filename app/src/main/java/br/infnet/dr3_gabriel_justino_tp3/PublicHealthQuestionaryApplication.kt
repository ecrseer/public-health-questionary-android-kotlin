package br.infnet.dr3_gabriel_justino_tp3

import android.app.Application
import br.infnet.dr3_gabriel_justino_tp3.domain.EvaluatorRepository
import br.infnet.dr3_gabriel_justino_tp3.services.AppDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PublicHealthQuestionaryApplication: Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    var mAuth: FirebaseAuth?= null
    var mUser: FirebaseUser? = null

    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getInstance(this,applicationScope) }
    val repository by lazy { EvaluatorRepository(database.getEvaluatorSessionDAO()) }
}