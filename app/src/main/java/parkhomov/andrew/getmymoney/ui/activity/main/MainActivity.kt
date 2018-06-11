package parkhomov.andrew.getmymoney.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.commit451.modalbottomsheetdialogfragment.ModalBottomSheetDialogFragment
import com.commit451.modalbottomsheetdialogfragment.Option
import kotlinx.android.synthetic.main.main_activity.*
import parkhomov.andrew.getmymoney.BuildConfig
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.ui.base.BaseActivity
import parkhomov.andrew.getmymoney.ui.base.BaseViewHolder
import parkhomov.andrew.getmymoney.ui.fragments.dialog.AddPerson
import parkhomov.andrew.getmymoney.utils.RecyclerDivider
import java.util.*
import javax.inject.Inject


fun Context.mainActivityIntent(): Intent =
        Intent(this, MainActivity::class.java)

class MainActivity : BaseActivity(),
        ModalBottomSheetDialogFragment.Listener,
        AddPerson.OnSavePressed,
        MainActivityMvpView {

    @Inject
    lateinit var presenter: MainActivityMvpPresenter<MainActivityMvpView>
    @Inject
    lateinit var recyclerDivider: RecyclerDivider
    @Inject
    lateinit var adapter: PersonItemsAdapter

    private lateinit var builder: ModalBottomSheetDialogFragment.Builder
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

        builder = ModalBottomSheetDialogFragment.Builder()
        builder.add(R.menu.fab_actions)
        initListeners()
        initializeAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_action, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_add_field -> onAddFieldClicked()
            R.id.action_delete -> onDeleteFieldClicked()
            R.id.action_calculate -> onCalculateButtonClicked()
            R.id.action_share -> shareResultClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initListeners() {
        add_field.setOnClickListener { onFabClicked() }
    }

    private fun initializeAdapter() {
        adapter.personList = mutableListOf(
                PersonItem("name", 100f, 0f, black),
                PersonItem("namesdsdsdsdsdasdsad 2", 120f, 0f, black),
                PersonItem("namadadsdsdadadae 3", 85f, 0f, black),
                PersonItem("name 4", 20f, 0f, black),
                PersonItem("name 4", 20f, 0f, black),
                PersonItem("name 4", 20f, 0f, black),
                PersonItem("name 4", 20f, 0f, black),
                PersonItem("name 4", 20f, 0f, black),
                PersonItem("name 4", 20f, 0f, black),
                PersonItem("name 4", 20f, 0f, black),
                PersonItem("name 4", 20f, 0f, black),
                PersonItem("name 4", 20f, 0f, black),
                PersonItem("name 4", 20f, 0f, black)

        )
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(recyclerDivider)
        recycler_view.adapter = adapter

        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && add_field.visibility == View.VISIBLE) {
                    add_field.hide()
                } else if (dy < 0 && add_field.visibility != View.VISIBLE) {
                    add_field.show()
                }
            }
        })

        handleTotalAmountVisibility()
    }

    private fun handleTotalAmountVisibility() {
        val isVisible = adapter.personList.count() != 0
        top_image_view.visibility = if (isVisible) View.VISIBLE else View.GONE
        total_amount.visibility = if (isVisible) View.VISIBLE else View.GONE
        amount_for_person.visibility = if (isVisible) View.VISIBLE else View.GONE
        divider.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun onFabClicked() {
        builder.show(supportFragmentManager, this::class.java.simpleName)
    }

    override fun onModalOptionSelected(tag: String?, option: Option) {
        when (option.id) {
            R.id.action_add_field -> onAddFieldClicked()
            R.id.action_delete -> onDeleteFieldClicked()
            R.id.action_calculate -> onCalculateButtonClicked()
            R.id.action_share -> shareResultClicked()
        }
    }

    private fun onAddFieldClicked() {
        AddPerson().show(supportFragmentManager)
    }

    private fun onDeleteFieldClicked() {
        AlertDialog.Builder(this)
                .setMessage(getString(R.string.delete_all_question))
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    adapter.isCalculated = false
                    adapter.personList.clear()
                    adapter.notifyDataSetChanged()
                    clearTotalTextView()
                    handleTotalAmountVisibility()
                }.create().show()
    }

    private fun clearTotalTextView() {
        total_amount.text = ""
        amount_for_person.text = ""
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
                    "%s%s%.02f",
                    getString(R.string.total_amount),
                    "\n",
                    totalAmount
            )
            amount_for_person.text = String.format(
                    Locale.US,
                    "%s%s%.02f",
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
        if (total_amount.text != "" && amount_for_person.text != "") {
            val linkInPlayStore = "https://play.google.com/store/apps/details?id=" +
                    BuildConfig.APPLICATION_ID
            val headerText = getString(R.string.calculated_by)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/html"
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
                "%s %.02f%s",
                getString(R.string.total_amount), totalAmount, "\n"
        ))
        stringBuilder.append(String.format(
                Locale.US,
                "%s %.02f%s",
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
        if (total_amount.visibility == View.GONE) handleTotalAmountVisibility()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    internal data class PersonItem(
            val name: String,
            val value: Float,
            var targetValue: Float,
            var targetValueColor: Int,
            val viewType: Int = BaseViewHolder.VIEW_TYPE_PERSON
    )

}
