package uz.gita.chontak.views

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import uz.gita.chontak.R

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.statusBarColor = Color.parseColor("#001A63")
        Handler(Looper.getMainLooper()).postDelayed({
            val i = Intent(this,ChooseActivity::class.java)
            startActivity(i)
            finish()
        },1500)

    }
}