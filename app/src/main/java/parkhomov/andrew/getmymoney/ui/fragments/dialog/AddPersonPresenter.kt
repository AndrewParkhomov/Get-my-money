package parkhomov.andrew.getmymoney.ui.fragments.dialog

import parkhomov.andrew.getmymoney.ui.base.BasePresenter
import javax.inject.Inject

class AddPersonPresenter<V : AddPersonMvpView>
@Inject
constructor() : BasePresenter<V>(),
        AddPersonMvpPresenter<V>
