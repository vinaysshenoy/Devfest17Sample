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

            val radius: Float = when {
                bounds.width() > bounds.height() -> bounds.height() / 2F
                else -> bounds.width() / 2F
            }

            path.addCircle(bounds.centerX(), bounds.centerY(), radius, Path.Direction.CW)

            return path
        }
    },
    SQUARE {
        override fun path(bounds: RectF): Path {
            val path = Path()

            val delta: Float = when {
                bounds.height() > bounds.width() -> bounds.width() / 2F
                else -> bounds.height() / 2F
            }

            path.addRect(bounds.centerX() - delta, bounds.centerY() - delta, bounds.centerX() + delta, bounds.centerY() + delta, Path.Direction.CW)
            return path
        }
    },
    ROUNDED_SQUARE {
        override fun path(bounds: RectF): Path {
            val path = Path()

            val delta: Float = when {
                bounds.height() > bounds.width() -> bounds.width() / 2F
                else -> bounds.height() / 2F
            }

            val cornerRadius = delta * 0.25F
            path.addRoundRect(bounds.centerX() - delta, bounds.centerY() - delta, bounds.centerX() + delta, bounds.centerY() + delta, cornerRadius, cornerRadius, Path.Direction.CW)
            return path
        }
    },
    TRIANGLE {
        override fun path(bounds: RectF): Path {
            val path = Path()

            path.moveTo(bounds.centerX(), bounds.top)
            path.lineTo(bounds.right, bounds.bottom)
            path.lineTo(bounds.left, bounds.bottom)
            path.lineTo(bounds.centerX(), bounds.top)

            return path
        }
    };

    abstract fun path(bounds: RectF): Path
}