package parkhomov.andrew.getmymoney.ui.activity

import android.os.Bundle
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.ui.base.BaseActivity
import javax.inject.Inject


class InitialActivity : BaseActivity(),
        InitialActivityMvpView {

    @Inject
    lateinit var presenter: InitialActivityMvpPresenter<InitialActivityMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent?.inject(this)
        presenter.onAttach(this)

    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }


}
