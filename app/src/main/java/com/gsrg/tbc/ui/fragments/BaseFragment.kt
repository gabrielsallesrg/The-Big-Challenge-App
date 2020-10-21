package com.gsrg.tbc.ui.fragments

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.gsrg.tbc.ui.MainActivity

abstract class BaseFragment : Fragment() {

    fun showLoading() = (requireActivity() as MainActivity).showLoading()
    fun hideLoading() = (requireActivity() as MainActivity).hideLoading()

    fun showMessage(view: View, message: String, longDuration: Boolean = false) {
        val duration = if (longDuration) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(view.context, message, duration).show()
    }
}