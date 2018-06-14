package parkhomov.andrew.getmymoney.ui.activity.main

import parkhomov.andrew.getmymoney.di.PerActivity
import parkhomov.andrew.getmymoney.ui.base.MvpPresenter

@PerActivity
interface MainActivityMvpPresenter<V : MainActivityMvpView> : MvpPresenter<V> {

    fun saveList(personList: MutableList<MainActivity.PersonItem>)

    fun onRestoreButtonClicked()

    fun onSaveButtonClicked()

}
