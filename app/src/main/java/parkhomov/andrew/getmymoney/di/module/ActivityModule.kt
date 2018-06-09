package parkhomov.andrew.getmymoney.di.module

import android.content.Context
import android.support.v4.content.ContextCompat
import dagger.Module
import dagger.Provides
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.di.ActivityContext
import parkhomov.andrew.getmymoney.di.PerActivity
import parkhomov.andrew.getmymoney.ui.activity.InitialActivityMvpPresenter
import parkhomov.andrew.getmymoney.ui.activity.InitialActivityMvpView
import parkhomov.andrew.getmymoney.ui.activity.InitialActivityPresenter
import parkhomov.andrew.getmymoney.ui.fragments.SomeFragmentMvpPresenter
import parkhomov.andrew.getmymoney.ui.fragments.SomeFragmentMvpView
import parkhomov.andrew.getmymoney.ui.fragments.SomeFragmentPresenter
import parkhomov.andrew.getmymoney.utils.RecyclerDivider

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
    @PerActivity
    internal fun provideInitialActivityPresenter(
            presenter: InitialActivityPresenter<InitialActivityMvpView>
    ): InitialActivityMvpPresenter<InitialActivityMvpView> = presenter

    @Provides
    internal fun provideTermsAndConditionsPresenter(
            presenter: SomeFragmentPresenter<SomeFragmentMvpView>
    ): SomeFragmentMvpPresenter<SomeFragmentMvpView> = presenter

}

