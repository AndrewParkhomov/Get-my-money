package parkhomov.andrew.getmymoney.ui.activity.main

import parkhomov.andrew.getmymoney.ui.base.MvpView

interface MainActivityMvpView : MvpView {

    fun createRestoreListDialog(list: MutableList<MainActivity.PersonItem>)

    fun createSaveListDialog(stringId: Int)

    fun initializeAdapter(isChecked: Boolean)

    fun checkboxStateChanged(isChecked: Boolean)


}
