package app.ma3.ui.components.routeDetailsScreen

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager

@Composable
fun MapboxMapComponent(
    modifier: Modifier = Modifier,
    startPoint: Point = Point.fromLngLat(36.8219, -1.2921), // Nairobi center
    zoom: Double = 12.0,
    originPoint: Point? = null,  // Origin marker
    destinationPoint: Point? = null  // Destination marker
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Create marker bitmaps
    val originMarkerBitmap = remember {
        createMarkerBitmap(color = 0xFF10B981.toInt(), size = 120) // Green
    }

    val destinationMarkerBitmap = remember {
        createMarkerBitmap(color = 0xFFF97316.toInt(), size = 120) // Orange
    }

    val mapView = remember {
        val initOptions = MapInitOptions(context)

        MapView(context, initOptions).apply {
            // Set initial camera position
            mapboxMap.setCamera(
                CameraOptions.Builder()
                    .center(startPoint)
                    .zoom(zoom)
                    .build()
            )

            // Load map style
            mapboxMap.loadStyleUri(Style.MAPBOX_STREETS)
        }
    }

    // Add markers when origin/destination change
    LaunchedEffect(originPoint, destinationPoint) {
        mapView.mapboxMap.getStyle { style ->
            // Clear existing annotations
            mapView.annotations.cleanup()

            // Add marker images to style
            style.addImage("origin-marker", originMarkerBitmap)
            style.addImage("destination-marker", destinationMarkerBitmap)

            val annotationApi = mapView.annotations
            val pointAnnotationManager = annotationApi.createPointAnnotationManager()

            // Add origin marker
            originPoint?.let { origin ->
                val pointAnnotationOptions = PointAnnotationOptions()
                    .withPoint(origin)
                    .withIconImage("origin-marker")
                    .withIconSize(1.0)

                pointAnnotationManager.create(pointAnnotationOptions)
            }

            // Add destination marker
            destinationPoint?.let { destination ->
                val pointAnnotationOptions = PointAnnotationOptions()
                    .withPoint(destination)
                    .withIconImage("destination-marker")
                    .withIconSize(1.0)

                pointAnnotationManager.create(pointAnnotationOptions)
            }

            // Adjust camera to show both markers
            if (originPoint != null && destinationPoint != null) {
                val bounds = listOf(originPoint, destinationPoint)
                val minLat = bounds.minOf { it.latitude() }
                val maxLat = bounds.maxOf { it.latitude() }
                val minLon = bounds.minOf { it.longitude() }
                val maxLon = bounds.maxOf { it.longitude() }

                val centerLat = (minLat + maxLat) / 2
                val centerLon = (minLon + maxLon) / 2

                mapView.mapboxMap.setCamera(
                    CameraOptions.Builder()
                        .center(Point.fromLngLat(centerLon, centerLat))
                        .zoom(11.0)
                        .build()
                )
            } else if (originPoint != null) {
                mapView.mapboxMap.setCamera(
                    CameraOptions.Builder()
                        .center(originPoint)
                        .zoom(13.0)
                        .build()
                )
            }
        }
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onDestroy()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { mapView },
    )

}

/**
 * Creates a custom pin-shaped marker bitmap
 */
private fun createMarkerBitmap(color: Int, size: Int): Bitmap {
    val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    val centerX = size / 2f
    val circleRadius = size / 3f
    val pointY = size * 0.9f

    // Draw the pin shadow
    paint.color = 0x40000000 // Semi-transparent black
    paint.style = Paint.Style.FILL
    canvas.drawCircle(centerX, pointY, circleRadius * 0.3f, paint)

    // Draw the pin point (triangle)
    paint.color = color
    paint.style = Paint.Style.FILL
    val path = android.graphics.Path()
    path.moveTo(centerX, pointY) // Bottom point
    path.lineTo(centerX - circleRadius * 0.4f, circleRadius * 2) // Left
    path.lineTo(centerX + circleRadius * 0.4f, circleRadius * 2) // Right
    path.close()
    canvas.drawPath(path, paint)

    // Draw the main circle
    canvas.drawCircle(centerX, circleRadius, circleRadius, paint)

    // Draw white inner circle
    paint.color = 0xFFFFFFFF.toInt()
    canvas.drawCircle(centerX, circleRadius, circleRadius * 0.6f, paint)

    // Draw colored center dot
    paint.color = color
    canvas.drawCircle(centerX, circleRadius, circleRadius * 0.3f, paint)

    return bitmap
}
