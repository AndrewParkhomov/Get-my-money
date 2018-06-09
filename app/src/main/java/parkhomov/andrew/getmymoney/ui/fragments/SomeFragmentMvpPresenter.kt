package parkhomov.andrew.getmymoney.ui.fragments

import parkhomov.andrew.getmymoney.di.PerActivity
import parkhomov.andrew.getmymoney.ui.base.MvpPresenter

@PerActivity
interface SomeFragmentMvpPresenter<V : SomeFragmentMvpView> : MvpPresenter<V>
