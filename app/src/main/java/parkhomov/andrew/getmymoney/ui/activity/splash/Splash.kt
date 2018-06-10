package parkhomov.andrew.getmymoney.ui.activity.splash

import android.os.Bundle
import parkhomov.andrew.getmymoney.ui.activity.main.mainActivityIntent
import parkhomov.andrew.getmymoney.ui.base.BaseActivity

class Splash : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(mainActivityIntent())
        finish()
    }

}