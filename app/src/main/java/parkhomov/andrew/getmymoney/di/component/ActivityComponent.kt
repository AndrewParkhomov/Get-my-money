package parkhomov.andrew.getmymoney.di.component

import dagger.Subcomponent
import parkhomov.andrew.getmymoney.di.PerActivity
import parkhomov.andrew.getmymoney.di.module.ActivityModule
import parkhomov.andrew.getmymoney.ui.activity.InitialActivity
import parkhomov.andrew.getmymoney.ui.base.BaseActivity
import parkhomov.andrew.getmymoney.ui.base.BaseFragment

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(activity: BaseActivity)

    fun inject(fragment: BaseFragment)

    fun inject(activity: InitialActivity)


}