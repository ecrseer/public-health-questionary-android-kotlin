package br.infnet.dr3_gabriel_justino_tp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import br.infnet.dr3_gabriel_justino_tp3.ui.main.MainFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var publicHealthQuestionaryApplication: PublicHealthQuestionaryApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        publicHealthQuestionaryApplication = application as PublicHealthQuestionaryApplication
        with(publicHealthQuestionaryApplication){
            mAuth = FirebaseAuth.getInstance()
        }

    }

    override fun onStart() {
        super.onStart()
        with(publicHealthQuestionaryApplication){
            mUser = mAuth?.currentUser
        }
        AlertDialog.Builder(this).setView(2)
    }
}