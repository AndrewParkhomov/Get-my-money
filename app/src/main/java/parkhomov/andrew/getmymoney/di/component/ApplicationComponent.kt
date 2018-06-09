package parkhomov.andrew.getmymoney.di.component

import android.content.Context
import dagger.Component
import parkhomov.andrew.getmymoney.MyApp
import parkhomov.andrew.getmymoney.di.module.ActivityModule
import parkhomov.andrew.getmymoney.di.module.ApplicationModule
import parkhomov.andrew.getmymoney.di.module.ServiceModule
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    fun inject(app: MyApp)

    fun context(): Context

    fun plusActivityComponent(module: ActivityModule): ActivityComponent

    fun plusServiceComponent(module: ServiceModule): ServiceComponent

}