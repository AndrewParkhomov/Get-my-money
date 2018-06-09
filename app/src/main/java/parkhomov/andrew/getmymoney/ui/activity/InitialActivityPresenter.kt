package parkhomov.andrew.getmymoney.ui.activity

import parkhomov.andrew.getmymoney.ui.base.BasePresenter
import javax.inject.Inject

class InitialActivityPresenter<V : InitialActivityMvpView>
@Inject
constructor(

) : BasePresenter<V>(), InitialActivityMvpPresenter<V>