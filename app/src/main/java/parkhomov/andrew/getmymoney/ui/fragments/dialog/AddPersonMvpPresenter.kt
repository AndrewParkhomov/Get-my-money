package parkhomov.andrew.getmymoney.ui.fragments.dialog


import parkhomov.andrew.getmymoney.di.PerActivity
import parkhomov.andrew.getmymoney.ui.base.MvpPresenter

@PerActivity
interface AddPersonMvpPresenter<V : AddPersonMvpView> : MvpPresenter<V>
