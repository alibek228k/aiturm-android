package kz.devs.aiturm.ui.presentation.authentication.registration.step2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar
import kz.devs.aiturm.R

class ProfileFillOutActivity : AppCompatActivity() {

    companion object{
        fun newInstance(context: Context): Intent {
            return Intent(context, ProfileFillOutActivity::class.java)
        }
    }

    private var toolbar: MaterialToolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_fill_out)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.setBackgroundColor(resources.getColor(R.color.purple_700, null))

        setSupportActionBar(toolbar)
    }
}