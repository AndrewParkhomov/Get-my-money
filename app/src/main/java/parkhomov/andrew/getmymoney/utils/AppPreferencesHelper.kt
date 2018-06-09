package parkhomov.andrew.getmymoney.utils

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesHelper
@Inject
constructor(private val preferences: SharedPreferences) : PreferencesHelper {

    override fun putStringValue(key: String, value: String) =
            preferences.edit().putString(key, value).apply()

    override fun putIntValue(key: String, value: Int) =
            preferences.edit().putInt(key, value).apply()

    override fun putFloatValue(key: String, value: Float) =
            preferences.edit().putFloat(key, value).apply()

    override fun putMutableListString(key: String, value: MutableList<String>) =
            preferences.edit().putString(key, convertToJson(value)).apply()

    override fun putBooleanValue(key: String, value: Boolean) =
            preferences.edit().putBoolean(key, value).apply()

    override fun putMutableListInt(key: String, value: MutableList<Int>) =
            preferences.edit().putString(key, convertToJson(value)).apply()

    override fun getStringValue(key: String, default: String): String = getPrefsValue(key, default)
    override fun getIntValue(key: String, default: Int): Int = getPrefsValue(key, default)
    override fun getFloatValue(key: String, default: Float): Float = getPrefsValue(key, default)
    override fun getBooleanValue(key: String, default: Boolean): Boolean = getPrefsValue(key, default)
    override fun getMutableListString(key: String, default: String): MutableList<String> =
            convertFromJsonString(getPrefsValue(key, default))

    override fun getMutableListInt(key: String, default: String): MutableList<Int> =
            convertFromJsonInt(getPrefsValue(key, default))

    private fun <T> convertToJson(list: List<T>): String = Gson().toJson(list)
    private fun convertFromJsonInt(stringJson: String): MutableList<Int> =
            if (stringJson.isEmpty())
                mutableListOf()
            else
                Gson().fromJson(stringJson, object : TypeToken<MutableList<Int>>() {}.type)

    private fun convertFromJsonString(stringJson: String): MutableList<String> =
            if (stringJson.isEmpty())
                mutableListOf()
            else
                Gson().fromJson(stringJson, object : TypeToken<MutableList<String>>() {}.type)


    private inline fun <reified T> getPrefsValue(key: String, default: T): T =
            when (T::class) {
                Int::class -> preferences.getInt(key, default as Int) as T
                String::class -> preferences.getString(key, default as String) as T
                Boolean::class -> preferences.getBoolean(key, default as Boolean) as T
                Float::class -> preferences.getFloat(key, default as Float) as T
                else -> default
            }

}