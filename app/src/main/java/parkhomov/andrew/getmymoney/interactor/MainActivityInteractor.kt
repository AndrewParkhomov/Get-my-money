package parkhomov.andrew.getmymoney.interactor

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import parkhomov.andrew.getmymoney.ui.activity.main.MainActivity
import parkhomov.andrew.getmymoney.utils.PreferencesHelper
import parkhomov.andrew.getmymoney.utils.isCheckboxCheckedKey
import parkhomov.andrew.getmymoney.utils.personListKey

class MainActivityInteractor(
        private val preferencesHelper: PreferencesHelper
) {

    var observable: CheckboxObservable? = null

    fun saveList(list: String) = preferencesHelper.putStringValue(personListKey, list)


    fun getSavedList(): String = preferencesHelper.getStringValue(personListKey)

    fun restoreList(stringJson: String): MutableList<MainActivity.PersonItem> =
            if (stringJson.isEmpty())
                mutableListOf()
            else
                Gson().fromJson(
                        stringJson,
                        object : TypeToken<MutableList<MainActivity.PersonItem>>() {}.type
                )

    fun <T> convertToJson(list: List<T>): String = Gson().toJson(list)

    fun setIsCheckboxChecked(isChecked: Boolean){
        observable?.checkboxStateChanged(isChecked)
        preferencesHelper.putBooleanValue(isCheckboxCheckedKey, isChecked)
    }


    fun getCheckboxState(): Boolean = preferencesHelper.getBooleanValue(isCheckboxCheckedKey, true)

}