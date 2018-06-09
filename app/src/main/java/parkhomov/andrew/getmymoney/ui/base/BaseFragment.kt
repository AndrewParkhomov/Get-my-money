package parkhomov.andrew.getmymoney.ui.base

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import parkhomov.andrew.getmymoney.di.component.ActivityComponent
import timber.log.Timber


abstract class BaseFragment : Fragment(),
        MvpView {

    var baseActivity: BaseActivity? = null
        private set

    private var photoImageUri: Uri? = null
    protected var isReadyToUpload: Boolean = true

    val activityComponent: ActivityComponent?
        get() = baseActivity?.activityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

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
                activityComponent?.inject(this)
            } catch (e: NullPointerException) {
                Timber.i(e.toString())
            }
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    override fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }

    override fun showMessage(message: String?) {
        baseActivity?.showMessage(message)
    }

    override fun showMessage(@StringRes resId: Int) {
        baseActivity?.showMessage(resId)
    }

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
        if (resultCode != Activity.RESULT_OK) return

    }
}
