package org.mrlem.placetime.common

import android.app.Activity
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText

fun EditText.setOnDoneEditingListener(callback: (text: String) -> Unit) {
    setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            callback(editableText.toString())
            (context as? Activity)?.hideSoftKeyboard(this)
        }
    }
    setOnEditorActionListener { _, actionId, event ->
        val validated = when {
            event == null -> {
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE,
                    EditorInfo.IME_ACTION_NEXT -> true
                    else -> false
                }
            }
            actionId == EditorInfo.IME_NULL -> event.action == KeyEvent.ACTION_DOWN
            else -> false
        }

        if (validated) {
            clearFocus()
        }
        validated
    }
}