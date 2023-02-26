package kz.devs.aiturm.ui.presentation.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar
import kz.devs.aiturm.R

class HomeActivity : AppCompatActivity() {

    private var toolbar: MaterialToolbar? = null

    private var toolbarTitle: MaterialToolbar? = null

    companion object{
        fun newInstance(context: Context): Intent{
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setBackgroundColor(resources.getColor(R.color.purple_700))

        setSupportActionBar(toolbar)
    }
}