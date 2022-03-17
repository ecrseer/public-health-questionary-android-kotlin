package br.infnet.dr3_gabriel_justino_tp3

import android.app.Instrumentation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry

import org.junit.Before




@RunWith(AndroidJUnit4::class)
@LargeTest
class Ttetst {


    private val ACTIVITY_TO_BE_TESTED = "br.infnet.dr3_gabriel_justino_tp3.ui.MainActivity"

    @Before
    fun setup() {
        var activityClass: Class<*>? = null
        activityClass = try {
            Class.forName(ACTIVITY_TO_BE_TESTED)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            return
        }
        val instrumentation: Instrumentation = InstrumentationRegistry.getInstrumentation()
        instrumentation.setInTouchMode(true)
        val targetPackage: String = instrumentation.getTargetContext().getPackageName()
        val startIntent = Intent(Intent.ACTION_MAIN)
        activityClass?.name?.let{
            startIntent.setClassName(targetPackage, it)
            startIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            instrumentation.startActivitySync(startIntent)
            instrumentation.waitForIdleSync()

        }
    }
    @Test
    fun first(){
        onView(ViewMatchers.withText("alimentos")).check(
            ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    fun loginOnAccount(){
        //onView(ViewMatchers.withId(R.))
        val entrarBtn = onView(ViewMatchers.withText("entrar"))
        val result = entrarBtn.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        entrarBtn.check(ViewAssertions.matches(ViewMatchers.isClickable()))
        if(result.equals(true)){

        }
        onView(ViewMatchers.withText("entrar")).perform(ViewActions.click())

        onView(ViewMatchers.withId(R.id.editTextTextEmailAddress)).perform(ViewActions.typeText("ga@bris.com"))
        onView(ViewMatchers.withId(R.id.editTextTextPassword)).perform(ViewActions.typeText("123456"))
        onView(ViewMatchers.withText("login")).perform(ViewActions.click())
        val d=0

    }
}