package parkhomov.andrew.getmymoney.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.main_activity.*
import parkhomov.andrew.getmymoney.BuildConfig
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.ui.base.BaseActivity
import parkhomov.andrew.getmymoney.ui.base.BaseViewHolder
import parkhomov.andrew.getmymoney.ui.fragments.HowItsWork
import parkhomov.andrew.getmymoney.ui.fragments.dialog.AddPerson
import parkhomov.andrew.getmymoney.utils.Utils
import parkhomov.andrew.getmymoney.utils.subnavigation.common.BackButtonListener
import parkhomov.andrew.getmymoney.utils.ui.QuickReturnFooterBehavior
import parkhomov.andrew.getmymoney.utils.ui.RecyclerDivider
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.Command
import java.util.*
import javax.inject.Inject


fun Context.mainActivityIntent(): Intent =
        Intent(this, MainActivity::class.java)

class MainActivity : BaseActivity(),
        AddPerson.OnSavePressed,
        Toolbar.OnMenuItemClickListener,
        MainActivityMvpView {

    @Inject
    lateinit var presenter: MainActivityMvpPresenter<MainActivityMvpView>
    @Inject
    lateinit var recyclerDivider: RecyclerDivider
    @Inject
    lateinit var adapter: PersonItemsAdapter
    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = object : SupportFragmentNavigator(supportFragmentManager, R.id.main_container) {
        override fun createFragment(screenKey: String, data: Any): Fragment {
            return when (screenKey) {
                HowItsWork.TAG -> HowItsWork.instance
                else -> throw RuntimeException("No screen key provided")
            }
        }

        override fun showSystemMessage(message: String) {
            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
        }

        override fun exit() {
            finish()
        }

        override fun applyCommands(commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }
    }

    private var totalAmount = 0f
    private var amountForPerson = 0f
    private var black: Int = 0
    private var green: Int = 0
    private var red: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        black = ContextCompat.getColor(this, R.color.black)
        green = ContextCompat.getColor(this, R.color.green)
        red = ContextCompat.getColor(this, R.color.red)

        activityComponent?.inject(this)
        presenter.onAttach(this)
        presenter.getCheckboxState()

        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        initListeners()
        clearTotalTextView()
        handleTotalAmountVisibility()
        setRecyclerViewMargin(0f)
    }

    override fun onResumeFragments() {
        navigatorHolder.setNavigator(navigator)
        super.onResumeFragments()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> onDeleteFieldClicked()
            R.id.action_share -> shareResultClicked()
            R.id.action_save -> onSaveClicked()
            R.id.action_restore -> onRestoreClicked()
            R.id.action_show_fragment -> onShowHowItsWorkFragmentClicked()
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initializeAdapter(isChecked: Boolean) {
        //        adapter.personList = mutableListOf(
//                PersonItem("name", 100f, 0f, black),
//                PersonItem("namesdsdsdsdsdasdsad 2", 120f, 0f, black),
//                PersonItem("namadadsdsdadadae 3", 85f, 0f, black),
//                PersonItem("name 4", 20f, 0f, black),
//                PersonItem("name 4", 20f, 0f, black),
//                PersonItem("name 4", 20f, 0f, black),
//                PersonItem("name 4", 20f, 0f, black),
//                PersonItem("name 4", 20f, 0f, black),
//                PersonItem("name 4", 20f, 0f, black),
//                PersonItem("name 4", 20f, 0f, black),
//                PersonItem("name 4", 20f, 0f, black),
//                PersonItem("name 4", 20f, 0f, black),
//                PersonItem("name 4", 20f, 0f, black)
//
//        )
        adapter.onHowItsWorkClickListener = {
            onShowHowItsWorkFragmentClicked()
        }
        adapter.isShowLinkToHowItsWorkFragment = isChecked
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(recyclerDivider)
        recycler_view.adapter = adapter
    }

    override fun checkboxStateChanged(isChecked: Boolean) {
        adapter.isShowLinkToHowItsWorkFragment = isChecked
        adapter.notifyDataSetChanged()
    }

    private fun onShowHowItsWorkFragmentClicked() {
        presenter.openHowItsWorkFragment()
    }

    private fun onRestoreClicked() {
//        throw RuntimeException("Test first crash")
        presenter.onRestoreButtonClicked()
    }

    private fun onSaveClicked() {
        presenter.onSaveButtonClicked()
    }

    override fun createSaveListDialog(stringId: Int) {
        if (adapter.personList.count() != 0) {
            AlertDialog.Builder(this)
                    .setMessage(getString(stringId))
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        presenter.saveList(adapter.personList)
                    }.create().show()
        } else {
            showMessage(getString(R.string.save_list_is_empty))
        }
    }

    override fun createRestoreListDialog(list: MutableList<PersonItem>) {
        val stringId = if (adapter.personList.count() == 0)
            R.string.restore_list_empty_current_list_message
        else
            R.string.restore_list_not_empty_current_list_message
        AlertDialog.Builder(this)
                .setMessage(getString(stringId))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    adapter.isCalculated = false
                    adapter.personList = list
                    adapter.notifyDataSetChanged()
                    handleTotalAmountVisibility()
                    clearTotalTextView()
                    setRecyclerViewMargin(160f)
                }.create().show()
    }

    private fun initListeners() {
        button_add.setOnClickListener { onAddButtonClicked() }
        button_calculate.setOnClickListener { onCalculateButtonClicked() }
    }

    override fun onMenuItemClick(p0: MenuItem): Boolean {
        showMessage("CLICKED")
        return false
    }

    private fun handleTotalAmountVisibility() {
        val isVisible = adapter.personList.count() != 0
        top_image_view.visibility = if (isVisible) View.VISIBLE else View.GONE
        total_amount.visibility = if (isVisible) View.VISIBLE else View.GONE
        amount_for_person.visibility = if (isVisible) View.VISIBLE else View.GONE
        divider.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun setRecyclerViewMargin(topMargin: Float) {
        recycler_view.isNestedScrollingEnabled = topMargin != 0f
        Utils.setMargins(recycler_view,
                0,
                Utils.convertDpToPixel(topMargin, this).toInt(),
                0,
                0
        )
    }

    private fun onAddButtonClicked() {
        AddPerson().show(supportFragmentManager)
    }

    private fun onDeleteFieldClicked() {
        if (adapter.personList.count() != 0) {
            AlertDialog.Builder(this)
                    .setMessage(getString(R.string.delete_all_question))
                    .setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        adapter.isCalculated = false
                        adapter.personList.clear()
                        adapter.notifyDataSetChanged()
                        clearTotalTextView()
                        handleTotalAmountVisibility()
                        QuickReturnFooterBehavior(this, null).show(button_container)
                        setRecyclerViewMargin(0f)
                    }.create().show()
        } else {
            showMessage(getString(R.string.delete_list_is_empty))
        }
    }

    private fun clearTotalTextView() {
        total_amount.text = String.format(
                Locale.US,
                "%s:%s",
                getString(R.string.total_amount),
                "\n"
        )
        amount_for_person.text = String.format(
                Locale.US,
                "%s:%s",
                getString(R.string.amount_for_person),
                "\n"
        )
    }

    private fun onCalculateButtonClicked() {
        totalAmount = 0f
        if (adapter.personList.count() > 1) {
            adapter.personList.forEach {
                totalAmount += it.value
            }
            amountForPerson = totalAmount / adapter.personList.count()
            total_amount.text = String.format(
                    Locale.US,
                    "%s:%s%.02f",
                    getString(R.string.total_amount),
                    "\n",
                    totalAmount
            )
            amount_for_person.text = String.format(
                    Locale.US,
                    "%s:%s%.02f",
                    getString(R.string.amount_for_person),
                    "\n",
                    amountForPerson
            )
            adapter.personList.forEach {
                it.targetValue = it.value - amountForPerson
                if (it.targetValue > 0) {
                    it.targetValueColor = green
                } else if (it.targetValue < 0) {
                    it.targetValueColor = red
                }
            }
            adapter.isCalculated = true
            adapter.notifyDataSetChanged()
        } else
            showMessage(getString(R.string.dont_enough_persons_for_calculation))

    }

    private fun shareResultClicked() {
        if (adapter.isCalculated) {
            val linkInPlayStore = "https://play.google.com/store/apps/details?id=" +
                    BuildConfig.APPLICATION_ID
            val headerText = getString(R.string.calculated_by)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_EMAIL, "")
                putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_result_subject))
                putExtra(Intent.EXTRA_TEXT, headerText + "\n" + linkInPlayStore + "\n\n" +
                        createTextForSharing())
            }
            startActivity(Intent.createChooser(intent, getString(R.string.share_result)))
        } else {
            showMessage(getString(R.string.share_result_error))
        }
    }

    private fun createTextForSharing(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(String.format(
                Locale.US,
                "%s: %.02f%s",
                getString(R.string.total_amount), totalAmount, "\n"
        ))
        stringBuilder.append(String.format(
                Locale.US,
                "%s: %.02f%s",
                getString(R.string.amount_for_person), amountForPerson, "\n"
        ))
        adapter.personList.forEach { person ->
            when {
                person.targetValue > 0 -> {
                    stringBuilder.append(String.format(
                            Locale.US,
                            "%s %s %.02f%s",
                            person.name, getString(R.string.person_pay_more), person.targetValue, "\n"
                    ))
                }
                person.targetValue < 0 -> {
                    stringBuilder.append(String.format(
                            Locale.US,
                            "%s %s %.02f%s",
                            person.name, getString(R.string.person_must_pay), person.targetValue, "\n"
                    ))
                }
                else -> {
                    stringBuilder.append(String.format(
                            Locale.US,
                            "%s %s%s",
                            person.name, getString(R.string.person_nothing_should_give), "\n"
                    ))
                }
            }

        }
        return stringBuilder.toString()
    }

    override fun newPersonData(name: String, value: Float) {
        hideKeyboard()
        adapter.personList.add(PersonItem(name, value, 0f, black))
        adapter.isCalculated = false
        adapter.notifyDataSetChanged()
        clearTotalTextView()
        if (total_amount.visibility == View.GONE) {
            handleTotalAmountVisibility()
            setRecyclerViewMargin(160f)
        }
    }

    override fun onBackPressed() {
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        supportActionBar!!.setTitle(R.string.app_name)
        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)
        if (fragment != null
                && fragment is BackButtonListener
                && (fragment as BackButtonListener).onBackPressed()) {
            return
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    data class PersonItem(
            val name: String,
            val value: Float,
            var targetValue: Float,
            var targetValueColor: Int,
            val viewType: Int = BaseViewHolder.VIEW_TYPE_PERSON
    )

}
