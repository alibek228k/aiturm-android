package kz.devs.aiturm.ui.validation

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText

abstract class EditTextValidator(
    private var editText: TextInputEditText?
): TextWatcher {

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        editText?.text?.toString()?.let { validatedText(it) }
    }
    abstract fun validatedText(text: String)
}