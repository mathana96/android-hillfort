/**
 * Adapted from: http://devdeeds.com/android-create-splash-screen-kotlin/
 */
package org.mathana.hillfort.views
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import org.mathana.hillfort.R
import org.mathana.hillfort.views.login.LoginView

class SplashView : AppCompatActivity() {
  lateinit var mDelayHandler : Handler
  private val SPLASH_DELAY: Long = 2000 //3 seconds

  internal val mRunnable: Runnable = Runnable {
    if (!isFinishing) {

      val intent = Intent(applicationContext, LoginView::class.java)
      startActivity(intent)
      setResult(AppCompatActivity.RESULT_OK)
      finish()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_splash)

    //Initialize the Handler
    mDelayHandler = Handler()

    //Navigate with delay
    mDelayHandler.postDelayed(mRunnable, SPLASH_DELAY)

  }

  public override fun onDestroy() {

    if (mDelayHandler != null) {
      mDelayHandler.removeCallbacks(mRunnable)
    }

    super.onDestroy()
  }

}