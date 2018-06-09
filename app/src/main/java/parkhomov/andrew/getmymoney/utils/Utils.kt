package parkhomov.andrew.getmymoney.utils

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ScaleXSpan
import android.widget.TextView

object Utils {


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
