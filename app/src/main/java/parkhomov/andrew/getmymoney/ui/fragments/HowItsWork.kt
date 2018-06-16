package parkhomov.andrew.getmymoney.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.how_its_work.*
import kotlinx.android.synthetic.main.toolbar.*
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.ui.base.BaseFragment
import javax.inject.Inject

class HowItsWork : BaseFragment(), HowItsWorkMvpView {

    @Inject
    lateinit var presenter: HowItsWorkMvpPresenter<HowItsWorkMvpView>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
                R.layout.how_its_work,
                container,
                false
        )
        setHasOptionsMenu(true)

        activityComponent?.inject(this)
        presenter.onAttach(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.getCheckboxState()
        baseActivity!!.supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        baseActivity!!.supportActionBar!!.setTitle(R.string.how_its_work_title)
        setCheckboxClickListener()
    }

    override fun setCheckboxState(isChecked: Boolean) {
        checkbox.isChecked = isChecked
    }

    private fun setCheckboxClickListener() {
        checkbox.setOnCheckedChangeListener { _, isChecked ->
            presenter.setIsCheckboxChecked(isChecked)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.action_delete).isVisible = false
        menu.findItem(R.id.action_share).isVisible = false
        menu.findItem(R.id.action_save).isVisible = false
        menu.findItem(R.id.action_restore).isVisible = false
        menu.findItem(R.id.action_show_fragment).isVisible = false
    }

    override fun onDestroyView() {
        baseActivity!!.supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        baseActivity!!.supportActionBar!!.setTitle(R.string.app_name)
        presenter.onDetach()
        super.onDestroyView()
    }

    companion object {
        val TAG: String = HowItsWork::class.java.simpleName
        val instance = HowItsWork()
    }
}
