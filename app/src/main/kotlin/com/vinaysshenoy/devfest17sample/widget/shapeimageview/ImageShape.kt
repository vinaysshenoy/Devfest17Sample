package com.vinaysshenoy.devfest17sample.widget.shapeimageview

import android.graphics.Path
import android.graphics.RectF

/**
 * Created by vinaysshenoy on 03/09/17.
 */
enum class ImageShape {
    CIRCLE {
        override fun path(bounds: RectF): Path {
            val path = Path()

            val radius: Float = if (bounds.width() > bounds.height()) {
                bounds.height() / 2F
            } else {
                bounds.width() / 2F
            }

            path.addCircle(bounds.centerX(), bounds.centerY(), radius, Path.Direction.CW)

            return path
        }
    };

    abstract fun path(bounds: RectF): Path
}