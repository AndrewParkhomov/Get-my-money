package parkhomov.andrew.getmymoney

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.support.v7.app.AppCompatDelegate
import com.crashlytics.android.Crashlytics
import com.squareup.leakcanary.LeakCanary
import io.fabric.sdk.android.Fabric
import parkhomov.andrew.getmymoney.utils.Utils
import timber.log.Timber
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.util.*
import javax.inject.Inject


class MyApp : Application() {

    @Inject
    lateinit var calligraphyConfig: CalligraphyConfig

    override fun onCreate() {
        super.onCreate()
        initComponentManager()
        initAppComponent()
        setLanguage()
        appContext = applicationContext
        initCustomFonts()

        if (BuildConfig.DEBUG) {
            initLogger()
            initLeakCanary()
        } else
            initFabric()
    }

    private fun initComponentManager() {
        componentsManager = ComponentsManager(this)
    }

    private fun initAppComponent() {
        componentsManager.getAppComponent().inject(this)
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }

    private fun initLogger() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String =
                    super.createStackElementTag(element) + " line " + element.lineNumber
        })
    }

    private fun initCustomFonts() = CalligraphyConfig.initDefault(calligraphyConfig)
    private fun initFabric() = Fabric.with(this, Crashlytics())

    private fun setLanguage() {
        val currentLanguage = "" // your lang

        val country = when (currentLanguage) {
            "uk" -> "uk_UA"
            "ru" -> "RU"
            else -> "en_US"
        }

        var config = resources.configuration
        val sysLocale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            config.locales.get(0) else config.locale

        if (currentLanguage != "" && sysLocale.language != currentLanguage) {
            val locale = Locale(currentLanguage, country)
            config = Configuration()
            Locale.setDefault(locale)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
                config.setLocale(locale) else config.locale = locale

            createConfigurationContext(config)
        }
    }

    companion object {
        lateinit var componentsManager: ComponentsManager
        lateinit var appContext: Context

        // for pre lollipop
        // allow to use drawableLeft/Right on filter and Map button
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}