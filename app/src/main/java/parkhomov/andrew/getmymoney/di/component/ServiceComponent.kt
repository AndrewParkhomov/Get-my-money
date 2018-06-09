package parkhomov.andrew.getmymoney.di.component

import dagger.Subcomponent
import parkhomov.andrew.getmymoney.di.PerService
import parkhomov.andrew.getmymoney.di.module.ServiceModule

@PerService
@Subcomponent(modules = [(ServiceModule::class)])
interface ServiceComponent {


}
