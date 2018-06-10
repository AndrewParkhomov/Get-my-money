package parkhomov.andrew.getmymoney.di.component

import dagger.Subcomponent
import parkhomov.andrew.getmymoney.di.PerActivity
import parkhomov.andrew.getmymoney.di.module.ActivityModule
import parkhomov.andrew.getmymoney.ui.activity.main.MainActivity
import parkhomov.andrew.getmymoney.ui.base.BaseActivity
import parkhomov.andrew.getmymoney.ui.base.BaseFragment
import parkhomov.andrew.getmymoney.ui.fragments.SomeFragment
import parkhomov.andrew.getmymoney.ui.fragments.dialog.AddPerson

@PerActivity
@Subcomponent(modules = [(ActivityModule::class)])
interface ActivityComponent {

    fun inject(activity: BaseActivity)

    fun inject(fragment: BaseFragment)

    fun inject(activity: MainActivity)

    fun inject(fragment: SomeFragment)

    fun inject(dialog: AddPerson)

}