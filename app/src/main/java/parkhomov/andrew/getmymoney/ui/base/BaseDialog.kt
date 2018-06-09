package parkhomov.andrew.getmymoney.ui.base

import android.annotation.TargetApi
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.constraint.ConstraintLayout
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.ViewGroup
import android.view.Window
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.di.component.ActivityComponent
import timber.log.Timber

abstract class BaseDialog : DialogFragment(), DialogMvpView {

    var baseActivity: BaseActivity? = null
        private set

    val activityComponent: ActivityComponent?
        get() = baseActivity?.activityComponent

    @TargetApi(23)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        onAttachToContext(context)
    }

    @Suppress("DEPRECATION", "OverridingDeprecatedMember")
    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            onAttachToContext(activity)
    }

    private fun onAttachToContext(context: Context) {
        if (context is BaseActivity) {
            try {
                this.baseActivity = context
            } catch (e: NullPointerException) {
                Timber.i(e.toString())
            }
        }
    }

    override fun showMessage(message: String?) {
        baseActivity?.showMessage(message)
    }

    override fun showMessage(@StringRes resId: Int) {
        baseActivity?.showMessage(resId)
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }


    protected abstract fun setUp(view: View)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the content
        val root = ConstraintLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)

        // creating the fullscreen dialog
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)

        if (dialog.window != null) {
            dialog.window.setBackgroundDrawableResource(R.drawable.selector_background_rounded_corners_white)
            dialog.window.setLayout(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
        } else {
            dismiss()
        }

        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp(view)
    }

    override fun show(fragmentManager: FragmentManager, tag: String) {
        val transaction = fragmentManager.beginTransaction()
        val prevFragment = fragmentManager.findFragmentByTag(tag)
        if (prevFragment != null)
            transaction.remove(prevFragment)
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    override fun dismissDialog(tag: String) {
        dismiss()
    }
}