package com.vinaysshenoy.devfest17sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vinaysshenoy.devfest17sample.widget.shapeimageview.ImageShape
import kotlinx.android.synthetic.main.activity_shape_imageview.*

/**
 * Created by vinaysshenoy on 03/09/17.
 */
class ShapeImageViewActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shape_imageview)

        iv_shapeImageViewCircle.setImageShape(ImageShape.CIRCLE)
        iv_shapeImageViewRoundedSquare.setImageShape(ImageShape.ROUNDED_SQUARE)
        iv_shapeImageViewSquare.setImageShape(ImageShape.SQUARE)
        iv_shapeImageViewTriangle.setImageShape(ImageShape.TRIANGLE)
    }
}