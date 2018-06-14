package parkhomov.andrew.getmymoney.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ScaleXSpan
import android.widget.TextView
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup





object Utils {

    /**
     * This method converts dp unit to equivalent pixels, depending on device density.
     *
     * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent px equivalent to dp depending on device density
     */
    fun convertDpToPixel(dp: Float, context: Context): Float {
        val metrics = context.resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    /**
     * This method converts device specific pixels to density independent pixels.
     *
     * @param px A value in px (pixels) unit. Which we need to convert into db
     * @param context Context to get resources and device specific display metrics
     * @return A float value to represent dp equivalent to px value
     */
    fun convertPixelsToDp(px: Float, context: Context): Float {
        val metrics = context.resources.displayMetrics
        return px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun setMargins(v: View, left: Int, top: Int, right: Int, bottom: Int) {
        if (v.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = v.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(left, top, right, bottom)
            v.requestLayout()
        }
    }

    fun TextView.setSmallSpacing(text: String) {
        this.text = spacing(text, 0.4f)
    }

    fun TextView.setBigSpacing(text: String) {
        this.text = spacing(text, 0.8f)
    }

    private fun spacing(src: CharSequence?, kerning: Float): Spannable? {
        if (src == null) return null
        val srcLength = src.length
        if (srcLength < 2) return src as? Spannable ?: SpannableString(src)
        val builder = src as? SpannableStringBuilder ?: SpannableStringBuilder(src)
        for (i in src.length - 1 downTo 1) {
            builder.insert(i, "\u00A0")
            builder.setSpan(ScaleXSpan(kerning), i, i + 1,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return builder
    }
}
