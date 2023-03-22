package kz.devs.aiturm.ui.presentation.authentication.login

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import kz.devs.aiturm.R
import kz.devs.aiturm.ui.presentation.authentication.AuthViewModel
import kz.devs.aiturm.ui.presentation.authentication.registration.RegistrationFragment
import kz.devs.aiturm.ui.presentation.home.HomeActivity
import kz.devs.aiturm.ui.validation.EditTextValidator

class LoginFragment: Fragment(R.layout.fragment_login) {

    private var signIn: MaterialTextView? = null
    private var loginButton: MaterialButton? = null
    private var emailInputLayout: TextInputLayout? = null
    private var passwordInputLayout: TextInputLayout? = null

    private val viewModel: AuthViewModel by activityViewModels()

    companion object{
        private val TAG = LoginFragment::class.java

        fun newInstance(): LoginFragment{
            val loginFragment = LoginFragment()
            loginFragment.arguments = Bundle()
            return  loginFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signIn = view.findViewById(R.id.signUp)
        loginButton = view.findViewById(R.id.loginButton)
        emailInputLayout = view.findViewById(R.id.emailInputLayout)
        passwordInputLayout = view.findViewById(R.id.passwordInputLayout)

        setupEmailValidator()
        setupPasswordValidator()
        setupLoginButton()

        observePasswordErrorMessage()
        observeEmailErrorMessage()
        observeLoginButtonAvailability()
        observeLoginResult()

        signIn?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun setupEmailValidator() {
        emailInputLayout?.editText?.addTextChangedListener(object : EditTextValidator(
            emailInputLayout?.editText as TextInputEditText
        ) {
            override fun validate(editText: TextInputEditText, text: String) {

            }

            override fun validatedText(text: String) {
                viewModel.onEmailTextChanged(text)
            }
        })
    }

    private fun setupPasswordValidator() {
        passwordInputLayout?.editText?.addTextChangedListener(object : EditTextValidator(
            passwordInputLayout?.editText as TextInputEditText
        ) {
            override fun validate(editText: TextInputEditText, text: String) {

            }

            override fun validatedText(text: String) {
                viewModel.onPasswordTextChanged(text)
            }
        })
    }

    private fun setupLoginButton() {
        loginButton?.isEnabled = false
        loginButton?.setOnClickListener {
            if (!passwordInputLayout?.editText?.text.isNullOrBlank() && !emailInputLayout?.editText?.text.isNullOrBlank()){
                viewModel.onSignInButtonClicked(
                    email = emailInputLayout?.editText?.text.toString(),
                    password = passwordInputLayout?.editText?.text.toString()
                )
            }
        }
    }

    private fun observeEmailErrorMessage(){
        viewModel.getEmailError().observe(viewLifecycleOwner){ validator ->
            emailInputLayout?.error = validator.errorMessage
        }
    }

    private fun observePasswordErrorMessage(){
        viewModel.getPasswordError().observe(viewLifecycleOwner){ validator ->
            passwordInputLayout?.error = validator.errorMessage
        }
    }

    private fun observeLoginButtonAvailability(){
        viewModel.getLoginButtonAvailability().observe(viewLifecycleOwner){
            loginButton?.isEnabled = it
        }
    }

    private fun observeLoginResult(){
        viewModel.getLoginResult().observe(viewLifecycleOwner){
            if (it) {
                Toast.makeText(requireContext(), "Successfully signed in!", Toast.LENGTH_SHORT).show()
                startActivity(HomeActivity.newInstance(requireContext()))
            } else {
                Toast.makeText(requireContext(), "Failed to sign in, try again", Toast.LENGTH_SHORT).show()
            }
        }
    }

}