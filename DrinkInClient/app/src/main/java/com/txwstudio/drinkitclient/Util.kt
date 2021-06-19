package com.txwstudio.drinkitclient

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

class Util {

    fun toast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun snackBarShort(view: View, resId: Int) {
        Snackbar.make(view, resId, Snackbar.LENGTH_SHORT).show()
    }

    fun snackBarLong(view: View, resId: Int) {
        Snackbar.make(view, resId, Snackbar.LENGTH_LONG).show()
    }


}