package jp.co.cyberagent.android.gpuimage.util

import android.graphics.Bitmap
import jp.co.cyberagent.android.gpuimage.GPUImage.ResponseListener
import jp.co.cyberagent.android.gpuimage.GPUImageRenderer
import jp.co.cyberagent.android.gpuimage.PixelBuffer
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter

/**
 * Gets the images for multiple filters on a image. This can be used to
 * quickly get thumbnail images for filters. <br></br>
 * Whenever a new Bitmap is ready, the listener will be called with the
 * bitmap. The order of the calls to the listener will be the same as the
 * filter order.
 *
 * @param filters  the filters which will be applied on the bitmap
 * @param listener the listener on which the results will be notified
 */
fun Bitmap.getBitmapForMultipleFilters(
    filters: List<GPUImageFilter>, listener: ResponseListener<Bitmap?>
) {
    if (filters.isEmpty()) {
        return
    }
    val renderer = GPUImageRenderer(filters[0])
    renderer.setImageBitmap(this, false)
    val buffer = PixelBuffer(width, height)
    buffer.setRenderer(renderer)

    for (filter in filters) {
        renderer.setFilter(filter)
        listener.response(buffer.bitmap)
        filter.destroy()
    }
    renderer.deleteImage()
    buffer.destroy()
}