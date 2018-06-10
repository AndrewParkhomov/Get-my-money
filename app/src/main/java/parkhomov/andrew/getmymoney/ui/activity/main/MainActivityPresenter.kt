package parkhomov.andrew.getmymoney.ui.activity.main

import parkhomov.andrew.getmymoney.ui.base.BasePresenter
import javax.inject.Inject

class MainActivityPresenter<V : MainActivityMvpView>
@Inject
constructor(

) : BasePresenter<V>(), MainActivityMvpPresenter<V>