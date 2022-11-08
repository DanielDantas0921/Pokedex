package com.example.pokmomapp.view

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.pokmomapp.BuildConfig
import com.example.pokmomapp.R

class AboutFragment: Fragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tv_version = view.findViewById<TextView>(R.id.tv_version_about)



        tv_version.text = getString(R.string.about_version, BuildConfig.VERSION_NAME )
    }



}