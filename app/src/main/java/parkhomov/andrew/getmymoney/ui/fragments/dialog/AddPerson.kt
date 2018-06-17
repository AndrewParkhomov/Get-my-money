package parkhomov.andrew.getmymoney.ui.fragments.dialog

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.dialog_add_person.*
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.ui.base.BaseDialog
import javax.inject.Inject


class AddPerson : BaseDialog(),
        AddPersonMvpView {

    interface OnSavePressed {
        fun newPersonData(name: String, value: Float)
    }

    private var callback: OnSavePressed? = null

    @Inject
    lateinit var presenter: AddPersonPresenter<AddPersonMvpView>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
                R.layout.dialog_add_person,
                container,
                false
        )

        activityComponent?.inject(this)
        presenter.onAttach(this)

        try {
            callback = baseActivity as OnSavePressed
        } catch (e: Exception) {
            throw ClassCastException("Calling Fragment must implement OnAddFriendListener")
        }

        return view
    }

    override fun setUp(view: View) {
        name.requestFocus()
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)

        savePerson.setOnClickListener {
            onAddPersonButtonClicked()
        }
        value.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onAddPersonButtonClicked()
            }
            false
        }
    }

    private fun onAddPersonButtonClicked() {
        val personName = name.text.toString()
        val personValue = value.text.toString()
        if (personName.isEmpty()) {
            showMessage(getString(R.string.name_required))
            return
        }
        if (personValue.isEmpty()) {
            showMessage(getString(R.string.value_required))
            return
        }
        callback?.newPersonData(
                personName,
                personValue.toFloat()
        )
        super.dismissDialog(TAG)
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, TAG)

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    companion object {
        val TAG: String = AddPerson::class.java.simpleName
    }
}
