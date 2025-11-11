package app.ma3.ui.components.routeDetailsScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun MapboxMapComponent(
    modifier: Modifier = Modifier,
    startPoint: Point = Point.fromLngLat(36.8219, -1.2921), // Nairobi center
    zoom: Double = 12.0,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

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
