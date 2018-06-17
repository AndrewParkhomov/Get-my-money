package parkhomov.andrew.getmymoney.ui.fragments

import parkhomov.andrew.getmymoney.di.PerActivity
import parkhomov.andrew.getmymoney.ui.base.MvpPresenter

@PerActivity
interface HowItsWorkMvpPresenter<V : HowItsWorkMvpView> : MvpPresenter<V> {

    fun setIsCheckboxChecked(isChecked: Boolean)

    fun getCheckboxState()

    fun closeFragment()
}
