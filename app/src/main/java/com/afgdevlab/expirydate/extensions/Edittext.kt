package com.afgdevlab.expirydate.extensions

import android.os.Build
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.widget.EditText

/**
 * this method is for text of edittext
 *
 * editTextEmail.isEmpty() -> true/false
 */
fun EditText.isEmpty(): Boolean {
    if (this.isNull()) return true
    if (this.text.trim().isEmpty()) return true

    return false
}

/**
 * this method is for text of edittext
 *
 * editTextEmail.isEmpty() -> true/false
 */
fun EditText.isNotEmpty(): Boolean {
    if (this.isNull()) return false
    if (this.text.isNullOrEmpty()) return false

    return true
}

/**
 * this method is for text of edittext
 *
 * this method is triggered after text is changed
 */
inline fun EditText?.afterTextChanged(crossinline afterTextChanged: (Editable?) -> Unit) {
    this?.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable)
        }
    })
}

/**
 * this method is for text of edittext
 *
 * this method is triggered when text is changed
 */
inline fun EditText?.onTextChanged(crossinline onTextChanged: (CharSequence?, Int, Int, Int) -> Unit) {
    this?.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            onTextChanged.invoke(p0, p1, p2, p3)
        }

        override fun afterTextChanged(editable: Editable?) = Unit
    })
}


/**
 * this method is for text of edittext
 *
 * this method is triggered before text is changed
 */
inline fun EditText?.beforeTextChanged(crossinline beforeTextChanged: (CharSequence?, Int, Int, Int) -> Unit) {
    this?.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            beforeTextChanged.invoke(p0, p1, p2, p3)
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun afterTextChanged(editable: Editable?) = Unit
    })
}


/**
 * this method applies filter on edittext only letters with turkish alphabet
 *
 */

fun EditText?.filterInputOnlyLetters() {
    this?.filters = arrayOf<InputFilter>(
        object : InputFilter {
            override fun filter(
                cs: CharSequence, start: Int,
                end: Int, spanned: Spanned?, dStart: Int, dEnd: Int
            ): CharSequence? {
                if (cs == "") { // for backspace
                    return cs
                }
                if (cs.toString().matches(Regex("[a-zA-ZüÜğĞÇçİöÖşŞ ]+"))) {
                    return cs
                }
                return ""
            }
        }
    )
}

fun EditText.limitLength(maxLength: Int) {
    filters = arrayOf(InputFilter.LengthFilter(maxLength))
}

fun EditText?.length(length: Int) {
    this?.filters = this?.filters?.plus(InputFilter.LengthFilter(length))
}


fun EditText.setAppearance(res: Int) {
    if (Build.VERSION.SDK_INT < 23) {
        setTextAppearance(context, res)
    } else {
        setTextAppearance(res)
    }
}
