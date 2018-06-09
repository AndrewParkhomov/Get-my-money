package parkhomov.andrew.getmymoney.ui.fragments

import parkhomov.andrew.getmymoney.ui.base.BasePresenter
import javax.inject.Inject

class SomeFragmentPresenter<V : SomeFragmentMvpView>
@Inject
constructor() : BasePresenter<V>(),
        SomeFragmentMvpPresenter<V>
