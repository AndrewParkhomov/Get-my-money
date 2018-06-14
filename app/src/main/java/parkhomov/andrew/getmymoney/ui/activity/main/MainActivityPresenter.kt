package parkhomov.andrew.getmymoney.ui.activity.main

import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.interactor.MainActivityInteractor
import parkhomov.andrew.getmymoney.ui.base.BasePresenter
import javax.inject.Inject

class MainActivityPresenter<V : MainActivityMvpView>
@Inject
constructor(
        private val interactor: MainActivityInteractor
) : BasePresenter<V>(), MainActivityMvpPresenter<V> {

    override fun saveList(personList: MutableList<MainActivity.PersonItem>) {
        interactor.saveList(interactor.convertToJson(personList))
    }

    override fun onRestoreButtonClicked() {
        val list = interactor.restoreList(interactor.getSavedList())
        if (list.count() != 0)
            mvpView?.createRestoreListDialog(list)
        else
            mvpView?.showMessage(R.string.saved_list_is_empty)
    }

    override fun onSaveButtonClicked() {
        val stringId = if (interactor.restoreList(interactor.getSavedList()).count() == 0)
            R.string.save_list_message_without_warning
        else
            R.string.save_list_message_with_warning
        mvpView?.createSaveListDialog(stringId)
    }
}