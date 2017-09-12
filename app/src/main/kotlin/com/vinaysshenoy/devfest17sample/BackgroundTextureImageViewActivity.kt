package com.vinaysshenoy.devfest17sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_backgroundtexture_imageview.*

/**
 * Created by vinaysshenoy on 03/09/17.
 */
class BackgroundTextureImageViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_backgroundtexture_imageview)
        cbv_1.text = "Android is awesome! \nI write awesome apps."
        cbv_2.text = "Hey! This grows to fit the content very nicely!\n\nI absolutely love custom drawing!\n\n\n\n:)"
    }
}