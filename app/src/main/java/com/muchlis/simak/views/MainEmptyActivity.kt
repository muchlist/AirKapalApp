package com.muchlis.simak.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.muchlis.simak.utils.App

class MainEmptyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Jika sharedpreference untuk token kosong maka akan diarahkan ke login activity
        * Jika isi akan diarahkan ke dashboard activity*/
        val activityIntent: Intent = if (App.prefs.authTokenSave.isEmpty()) {
            Intent(this, LoginActivity::class.java)
        } else {
            Intent(this, HomeActivity::class.java)
        }

        startActivity(activityIntent)
        finish()

    }
}
