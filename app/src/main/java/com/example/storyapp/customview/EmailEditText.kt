package com.example.storyapp.customview

import android.content.Context
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import com.example.storyapp.R.string
import androidx.appcompat.widget.AppCompatEditText
import com.example.storyapp.isEmailValid

class EmailEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString()
                when {
                    email.isBlank() -> error = context.getString(string.empty_email)
                    !email.isEmailValid() -> error = context.getString(string.invalid_email)
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                // do nothing
            }
        })
    }
}