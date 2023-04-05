package kz.devs.aiturm.ui.presentation.authentication.registration

import android.content.Context
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
import kz.devs.aiturm.ui.presentation.home.HomeActivity
import kz.devs.aiturm.ui.validation.EditTextValidator

class RegistrationFragment : Fragment(R.layout.fragment_registration) {

    private var signIn: MaterialTextView? = null
    private var signUpButton: MaterialButton? = null
    private var emailInputLayout: TextInputLayout? = null
    private var passwordInputLayout: TextInputLayout? = null

    private val viewModel: AuthViewModel by activityViewModels()

    companion object {
        private val TAG = RegistrationFragment::class.java.simpleName

        fun newInstance(context: Context): RegistrationFragment{
            val registrationFragment = RegistrationFragment()
            registrationFragment.arguments = Bundle()
            return registrationFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signIn = view.findViewById(R.id.signIn)
        signUpButton = view.findViewById(R.id.signUpButton)
        emailInputLayout = view.findViewById(R.id.emailInputLayout)
        passwordInputLayout = view.findViewById(R.id.passwordInputLayout)

        setupEmailTextField()
        setupPasswordTextField()
        setupSignUpButton()
        setupBackToSignInButton()

        observeEmailErrorMessage()
        observePasswordErrorMessage()
        observeSignUpButton()
        observeRegistrationResult()
    }

    private fun setupEmailTextField(){
        emailInputLayout?.editText?.addTextChangedListener(object : EditTextValidator(
            emailInputLayout?.editText as TextInputEditText
        ){
            override fun validatedText(text: String) {
                viewModel.onRegisterEmailTextChanged(text)
            }
        })
    }

    private fun setupPasswordTextField(){
        passwordInputLayout?.editText?.addTextChangedListener(object : EditTextValidator(
            passwordInputLayout?.editText as TextInputEditText
        ){
            override fun validatedText(text: String) {
                viewModel.onRegistrationPasswordTextChanged(text)
            }
        })
    }

    private fun setupSignUpButton(){
        signUpButton?.setOnClickListener{
            if (!passwordInputLayout?.editText?.text.isNullOrBlank() && !emailInputLayout?.editText?.text.isNullOrBlank()){
                viewModel.onSignUpClicked(
                    email = emailInputLayout?.editText?.text.toString(),
                    password = passwordInputLayout?.editText?.text.toString()
                )
            }
        }
    }

    private fun setupBackToSignInButton(){
        signIn?.setOnClickListener{
            findNavController().popBackStack()
        }
    }

    private fun observeEmailErrorMessage(){
        viewModel.getRegistrationEmailError().observe(viewLifecycleOwner){ validator ->
            emailInputLayout?.error = validator.errorMessage
        }
    }

    private fun observePasswordErrorMessage(){
        viewModel.getRegistrationPasswordError().observe(viewLifecycleOwner){ validator ->
            passwordInputLayout?.error = validator.errorMessage
        }
    }

    private fun observeSignUpButton(){
        viewModel.getRegisterButtonAvailability().observe(viewLifecycleOwner){
            signUpButton?.isEnabled = it
        }
    }

    private fun observeRegistrationResult(){
        viewModel.getRegistrationResult().observe(viewLifecycleOwner){
            if (it) {
                Toast.makeText(requireContext(), "Successfully signed up!", Toast.LENGTH_SHORT).show()
                startActivity(HomeActivity.newInstance(requireContext()))
            } else {
                Toast.makeText(requireContext(), "Failed to sign in, try again", Toast.LENGTH_SHORT).show()
            }
        }
    }
}