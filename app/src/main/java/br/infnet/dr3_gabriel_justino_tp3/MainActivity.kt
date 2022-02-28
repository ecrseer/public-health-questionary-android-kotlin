package br.infnet.dr3_gabriel_justino_tp3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.infnet.dr3_gabriel_justino_tp3.ui.login.LoginFragment
import br.infnet.dr3_gabriel_justino_tp3.ui.questionary.QuestionaryFragment
import com.google.firebase.auth.FirebaseAuth


open class MainActivity : AppCompatActivity() {

    private val activityViewModel:MainActivityViewModel by viewModels()
    private lateinit var publicHealthQuestionaryApplication: PublicHealthQuestionaryApplication

    private fun goToPage(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        if (savedInstanceState == null) {
            goToPage(LoginFragment.newInstance())
        }
        publicHealthQuestionaryApplication = application as PublicHealthQuestionaryApplication
        with(publicHealthQuestionaryApplication){
            mAuth = FirebaseAuth.getInstance()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.dots_menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout_btn -> {
                with(publicHealthQuestionaryApplication) {
                    mAuth?.signOut()
                    mUser = null
                    activityViewModel.isLoggedIn.postValue(false)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        activityViewModel.isLoggedIn.observe(this, Observer {loggedIn->
            if(loggedIn) {
                goToPage(QuestionaryFragment())
            }else{
                goToPage(LoginFragment.newInstance())
            }
        })
        with(publicHealthQuestionaryApplication){
            mUser = mAuth?.currentUser
            mUser?.let{
                activityViewModel.isLoggedIn.postValue(true)
            }
        }
    }
}