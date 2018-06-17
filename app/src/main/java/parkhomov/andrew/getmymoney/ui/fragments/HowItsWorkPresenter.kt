package parkhomov.andrew.getmymoney.ui.fragments

import parkhomov.andrew.getmymoney.interactor.MainActivityInteractor
import parkhomov.andrew.getmymoney.ui.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class HowItsWorkPresenter<V : HowItsWorkMvpView>
@Inject
constructor(
        private val interactor: MainActivityInteractor,
        private val router: Router
) : BasePresenter<V>(),
        HowItsWorkMvpPresenter<V>{

    override fun setIsCheckboxChecked(isChecked: Boolean) {
        interactor.setIsCheckboxChecked(isChecked)
    }

    override fun getCheckboxState() {
        mvpView?.setCheckboxState(interactor.getCheckboxState())
    }

    override fun closeFragment() {
        router.exit()
    }
}
