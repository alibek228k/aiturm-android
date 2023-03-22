package kz.devs.aiturm.ui.presentation.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kz.devs.aiturm.R
import kz.devs.aiturm.ui.presentation.authentication.AuthActivity

class HomeActivity : AppCompatActivity() {

    private var toolbar: MaterialToolbar? = null
    private var toolbarTitle: MaterialToolbar? = null
    private var materialTextView: MaterialTextView? = null


    companion object{
        fun newInstance(context: Context): Intent{
            return Intent(context, HomeActivity::class.java)
        }
    }

    private var signOutButton: MaterialButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        signOutButton = findViewById(R.id.signOutButton)
        materialTextView = findViewById(R.id.materialTextView)
        materialTextView?.text = if (Firebase.auth.currentUser?.displayName?.isBlank() == true) Firebase.auth.currentUser?.email else Firebase.auth.currentUser?.displayName

        signOutButton?.setOnClickListener{
            Firebase.auth.signOut()
            Toast.makeText(this, "Successfully signed out!", Toast.LENGTH_SHORT).show()
            startActivity(AuthActivity.newInstance(this))
        }
    }
}