package app.ma3.ui.components.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val NoRouteIcon: ImageVector
    get() {
        if (_noRouteIcon != null) {
            return _noRouteIcon!!
        }
        _noRouteIcon = ImageVector.Builder(
            name = "NoRoute",
            defaultWidth = 93.dp,
            defaultHeight = 92.dp,
            viewportWidth = 93f,
            viewportHeight = 92f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFFE74C3C)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 4f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(88.9556f, 65.8f)
                curveTo(90.2443f, 62.7572f, 90.7591f, 59.4425f, 90.4538f, 56.1522f)
                curveTo(90.1485f, 52.8619f, 89.0327f, 49.6985f, 87.2061f, 46.9448f)
                curveTo(85.3795f, 44.191f, 82.8991f, 41.9328f, 79.9865f, 40.3719f)
                curveTo(77.074f, 38.811f, 73.82f, 37.9961f, 70.5156f, 38f)
                horizontalLineTo(65.4756f)
                curveTo(63.8428f, 31.5699f, 60.2519f, 25.8063f, 55.1997f, 21.5066f)
                curveTo(50.1476f, 17.2069f, 43.884f, 14.5837f, 37.2756f, 14f)
                moveTo(18.5156f, 18f)
                curveTo(12.2679f, 21.4546f, 7.35483f, 26.8991f, 4.55794f, 33.4676f)
                curveTo(1.76106f, 40.0361f, 1.241f, 47.3513f, 3.0805f, 54.2494f)
                curveTo(4.91999f, 61.1475f, 9.01339f, 67.2323f, 14.7095f, 71.536f)
                curveTo(20.4057f, 75.8397f, 27.3774f, 78.115f, 34.5156f, 78f)
                horizontalLineTo(70.5156f)
                curveTo(72.8347f, 77.9973f, 75.1356f, 77.5912f, 77.3156f, 76.8f)
                moveTo(2.51559f, 2f)
                lineTo(90.5156f, 90f)
            }
        }.build()
        return _noRouteIcon!!
    }

private var _noRouteIcon: ImageVector? = null

