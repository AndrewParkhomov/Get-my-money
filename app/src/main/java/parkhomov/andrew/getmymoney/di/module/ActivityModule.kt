package parkhomov.andrew.getmymoney.di.module

import android.content.Context
import android.support.v4.content.ContextCompat
import dagger.Module
import dagger.Provides
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.di.ActivityContext
import parkhomov.andrew.getmymoney.di.PerActivity
import parkhomov.andrew.getmymoney.ui.activity.main.MainActivityMvpPresenter
import parkhomov.andrew.getmymoney.ui.activity.main.MainActivityMvpView
import parkhomov.andrew.getmymoney.ui.activity.main.MainActivityPresenter
import parkhomov.andrew.getmymoney.ui.activity.main.PersonItemsAdapter
import parkhomov.andrew.getmymoney.ui.fragments.HowItsWorkMvpPresenter
import parkhomov.andrew.getmymoney.ui.fragments.HowItsWorkMvpView
import parkhomov.andrew.getmymoney.ui.fragments.HowItsWorkPresenter
import parkhomov.andrew.getmymoney.ui.fragments.dialog.AddPersonMvpPresenter
import parkhomov.andrew.getmymoney.ui.fragments.dialog.AddPersonMvpView
import parkhomov.andrew.getmymoney.ui.fragments.dialog.AddPersonPresenter
import parkhomov.andrew.getmymoney.utils.ui.RecyclerDivider

@Suppress("unused")
@Module
class ActivityModule(private val context: Context) {

    @Provides
    @ActivityContext
    internal fun provideContext(): Context = context

    @Provides
    internal fun provideRecyclerDivider(@ActivityContext context: Context): RecyclerDivider =
            RecyclerDivider(1, ContextCompat.getColor(context, R.color.gainsboro))

    @Provides
    internal fun provideAddDocumentAdapter(): PersonItemsAdapter = PersonItemsAdapter()

    @Provides
    @PerActivity
    internal fun provideInitialActivityPresenter(
            presenter: MainActivityPresenter<MainActivityMvpView>
    ): MainActivityMvpPresenter<MainActivityMvpView> = presenter

    @Provides
    internal fun provideTermsAndConditionsPresenter(
            presenter: HowItsWorkPresenter<HowItsWorkMvpView>
    ): HowItsWorkMvpPresenter<HowItsWorkMvpView> = presenter

    @Provides
    internal fun provideAddPersonDialogPresenter(
            presenter: AddPersonPresenter<AddPersonMvpView>
    ): AddPersonMvpPresenter<AddPersonMvpView> = presenter


}

