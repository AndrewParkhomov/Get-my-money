package parkhomov.andrew.getmymoney.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.commit451.modalbottomsheetdialogfragment.ModalBottomSheetDialogFragment
import com.commit451.modalbottomsheetdialogfragment.Option
import kotlinx.android.synthetic.main.initial.*
import parkhomov.andrew.getmymoney.BuildConfig
import parkhomov.andrew.getmymoney.R
import parkhomov.andrew.getmymoney.ui.base.BaseActivity
import parkhomov.andrew.getmymoney.ui.fragments.dialog.AddPerson
import parkhomov.andrew.getmymoney.utils.RecyclerDivider
import java.util.*
import javax.inject.Inject

fun Context.mainActivityIntent(): Intent =
        Intent(this, InitialActivity::class.java)

class InitialActivity : BaseActivity(),
        ModalBottomSheetDialogFragment.Listener,
        AddPerson.OnSavePressed,
        InitialActivityMvpView {

    @Inject
    lateinit var presenter: InitialActivityMvpPresenter<InitialActivityMvpView>
    @Inject
    lateinit var recyclerDivider: RecyclerDivider
    @Inject
    lateinit var adapter: InitialAdapter

    private lateinit var builder: ModalBottomSheetDialogFragment.Builder
    private var black: Int = 0
    private var green: Int = 0
    private var red: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.initial)
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
                PersonItem("name 2", 120f, 0f, black),
                PersonItem("name 3", 85f, 0f, black),
                PersonItem("name 4", 20f, 0f, black)

        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(recyclerDivider)
        recyclerView.adapter = adapter
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
                .setMessage("Do you really want to delete all items?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    adapter.isCalculated = false
                    adapter.personList.clear()
                    adapter.notifyDataSetChanged()
                    clearTotalTextView()
                }.create().show()
    }

    private fun clearTotalTextView() {
        total_amount.text = ""
        amount_for_person.text = ""
    }

    private fun onCalculateButtonClicked() {
        var totalAmount = 0f
        if (adapter.personList.count() > 1) {
            adapter.personList.forEach {
                totalAmount += it.value
            }
            val amountForPerson = totalAmount / adapter.personList.count()
            total_amount.text = String.format(
                    Locale.US,
                    "%s%s%.02f",
                    "Total amount:",
                    "\n",
                    totalAmount
            )
            amount_for_person.text = String.format(
                    Locale.US,
                    "%s%s%.02f",
                    "Amount for person",
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
            showMessage("You need 2 persons for calculation")

    }

    private fun shareResultClicked() {
        if (total_amount.text != "" && amount_for_person.text != "") {
            val linkInPlayStore = "https://play.google.com/store/apps/details?id=" +
                    BuildConfig.APPLICATION_ID
            val headerText = "Calculated by 'Get my money' app"

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/html"
                putExtra(Intent.EXTRA_EMAIL, "")
                putExtra(Intent.EXTRA_SUBJECT, "Get my money result")
                putExtra(Intent.EXTRA_TEXT, headerText + "\n" + linkInPlayStore + "\n\n" + createTextForSharing())
            }
            startActivity(Intent.createChooser(intent, getString(R.string.share_result)))
        } else {
            showMessage("You should press 'Calculate' so that you can share results")
        }
    }

    private fun createTextForSharing(): String {
        val stringBuilder = StringBuilder()
        return "hello world"
    }

    override fun newPersonData(name: String, value: Float) {
        hideKeyboard()
        adapter.personList.add(PersonItem(name, value, 0f, black))
        adapter.isCalculated = false
        adapter.notifyDataSetChanged()
        clearTotalTextView()
    }

    override fun onDestroy() {
        presenter.onDetach()
        super.onDestroy()
    }

    internal data class PersonItem(
            val name: String,
            val value: Float,
            var targetValue: Float,
            var targetValueColor: Int
    )

}
