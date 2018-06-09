package parkhomov.andrew.getmymoney

import android.content.Context
import parkhomov.andrew.getmymoney.di.component.ActivityComponent
import parkhomov.andrew.getmymoney.di.component.ApplicationComponent
import parkhomov.andrew.getmymoney.di.component.DaggerApplicationComponent
import parkhomov.andrew.getmymoney.di.component.ServiceComponent
import parkhomov.andrew.getmymoney.di.module.ActivityModule
import parkhomov.andrew.getmymoney.di.module.ApplicationModule
import parkhomov.andrew.getmymoney.di.module.ServiceModule

class ComponentsManager(private val context: Context) {


    private var appComponent: ApplicationComponent? = null
    private var serviceComponent: ServiceComponent? = null
    private var activityComponent: ActivityComponent? = null


    // We don't have to think about multithreading here, because we get our components
    // only from Activity/Component classes on main thread
    fun getAppComponent(): ApplicationComponent {
        if (appComponent == null) {
            appComponent = DaggerApplicationComponent.builder()
                    .applicationModule(ApplicationModule(context.applicationContext))
                    .build()
        }
        return appComponent!!
    }

    fun getServiceComponent(): ServiceComponent {
        if (serviceComponent == null) {
            serviceComponent = appComponent!!.plusServiceComponent(ServiceModule())
        }
        return serviceComponent!!
    }

    fun getActivityComponent(): ActivityComponent {
        if (activityComponent == null) {
            activityComponent = appComponent!!.plusActivityComponent(ActivityModule(context))
        }
        return activityComponent!!
    }
}