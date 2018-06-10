package parkhomov.andrew.getmymoney.ui.base

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import parkhomov.andrew.getmymoney.MyApp
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.di.component.ActivityComponent
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity : AppCompatActivity(),
        MvpView {

    var activityComponent: ActivityComponent? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = MyApp.componentsManager.getActivityComponent()
        activityComponent?.inject(this)
    }

    override fun attachBaseContext(newBase: Context) =
            super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsActivity(permissions: Array<String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permissions, requestCode)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsFragment(
            fragment: BaseFragment,
            permissions: Array<String>,
            requestCode: Int
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            fragment.requestPermissions(permissions, requestCode)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(this.currentFocus?.windowToken, 0)
    }

    override fun showMessage(message: String?) {
        Snackbar.make(
                findViewById(R.id.mainContainer),
                message ?: getString(R.string.unknown_error),
                Snackbar.LENGTH_LONG
        ).show()
    }

    override fun showMessage(@StringRes resId: Int) {
        showMessage(getString(resId))
    }

}
