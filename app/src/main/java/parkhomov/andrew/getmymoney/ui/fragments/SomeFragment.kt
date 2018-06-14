package parkhomov.andrew.getmymoney.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.ui.base.BaseFragment
import javax.inject.Inject

class SomeFragment : BaseFragment(), SomeFragmentMvpView {

    @Inject
    lateinit var presenter: SomeFragmentMvpPresenter<SomeFragmentMvpView>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
                R.layout.some_fragment,
                container,
                false
        )

        activityComponent?.inject(this)
        presenter.onAttach(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        header.setBigSpacing(getString(R.string.terms_and_conditions_header))
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }
//    mBinding.listCategoryContainer.animate().alpha(1.0f).translationY(0).setInterpolator(new DecelerateInterpolator(1)).start();

    companion object {
        val TAG: String = SomeFragment::class.java.simpleName
        fun getInstance(termsAndConditionsText: String) = SomeFragment().apply {
            arguments = Bundle(1).apply {
                putString(TAG, termsAndConditionsText)
            }
        }
    }
}
