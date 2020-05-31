package com.muchlis.simak.views

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muchlis.simak.R
import com.muchlis.simak.adapter.VesselListAdapter
import com.muchlis.simak.databinding.ActivityWatersAddBinding
import com.muchlis.simak.datas.input.WaterPostRequest
import com.muchlis.simak.datas.output.VesselListDataResponse
import com.muchlis.simak.services.Api
import com.muchlis.simak.utils.*
import com.muchlis.simak.utils.Singleton.Companion.isWatersNeedUpdate
import com.muchlis.simak.viewmodels.WatersAddViewModel
import com.muchlis.simak.viewmodels.WatersAddViewModelFactory
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class WatersAddActivity : AppCompatActivity() {

    private lateinit var bd: ActivityWatersAddBinding
    private lateinit var viewModel: WatersAddViewModel
    private lateinit var viewModelFactory: WatersAddViewModelFactory

    private lateinit var vesselDialog: Dialog

    //JOB search cancleable on Dialog
    val uiScope = CoroutineScope(Dispatchers.Main)
    private var textChangeCountJob: Job? = null

    //recyclerView on Dialog
    private lateinit var recyclerAdapter: VesselListAdapter
    private val dataResponse: MutableList<VesselListDataResponse.Vessel> = mutableListOf()

    //Data For Insert with no visual
    private lateinit var vesselId: String
    private lateinit var agent: String

    //Data with Visual spiner
    private lateinit var meterSelected: String

    //Date Value
    private lateinit var dateTimeNowCalander: Calendar
    private lateinit var dateTimeNow: Date

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = DataBindingUtil.setContentView(this, R.layout.activity_waters_add)

        initViewModel()
        observeViewModel()

        vesselDialog = Dialog(this)

        spinerMeterInit()

        bd.tvInsertVessel.setOnClickListener {
            showVesselDialog()
        }

        //DATE DAN TIMEPICKER HANDLER
        dateTimeNowCalander = Calendar.getInstance()
        dateTimeNow = dateTimeNowCalander.time

        bd.tvInsertDate.text = dateTimeNow.toStringJustDate()
        bd.tvInsertTime.text = dateTimeNow.toStringJustTime()

        bd.tvInsertDate.setOnClickListener {
            showDatePicker()
        }

        bd.tvInsertTime.setOnClickListener {
            showTimePicker()
        }
        //DATE DAN TIMEPICKER HANDLER END

        bd.btBack.setOnClickListener {
            onBackPressed()
        }

        bd.btInsertSave.setOnClickListener {
            addWaters()
        }

        bd.btOk.setOnClickListener {
            addWaters()
        }

    }

    private fun addWaters() {
        val vesselName = bd.tvInsertVessel.text.toString()
        val tonase = bd.etInsertTonase.text.toString().toInt()
        val intDom = bd.tvInsertInternational.text.toString()
        val jobNumber = bd.etInsertJobOrder.text.toString().toUpperCase()

        viewModel.insertWaterToServer(
            WaterPostRequest(
                agent = agent,
                createdAt = dateTimeNow.toStringInputDate(),
                intDom = intDom,
                jobNumber = jobNumber,
                locate = meterSelected,
                tonaseOrdered = tonase,
                vesselId = vesselId,
                vesselName = vesselName
            )
        )

    }

    private fun initViewModel() {
        val application = requireNotNull(this).application
        val apiService = Api.retrofitService
        viewModelFactory = WatersAddViewModelFactory(apiService, application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(WatersAddViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.messageSuccess.observe(this, androidx.lifecycle.Observer {
            if (it.isNotEmpty()) {
                Toasty.success(this, it, Toasty.LENGTH_SHORT).show()
            }
        })
        viewModel.messageFailure.observe(this, androidx.lifecycle.Observer {
            if (it.isNotEmpty()) {
                Toasty.error(this, it, Toasty.LENGTH_LONG).show()
            }
        })

        viewModel.isLoading.observe(this, androidx.lifecycle.Observer {
            if (it) {
                bd.pbInsertContainer.visible()
            } else {
                bd.pbInsertContainer.invisible()
            }
        })

        viewModel.isInsertSuccessfull.observe(this, androidx.lifecycle.Observer {
            if (it) {
                isWatersNeedUpdate = true
                finish()
            }
        })
    }

    private fun spinerMeterInit() {
        //Meter SPINER
        val meterDropdownOption = arrayOf("METER_1")
        bd.spInsertMeter.adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, meterDropdownOption)
        bd.spInsertMeter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                meterSelected = meterDropdownOption[position]
            }
        }
    }

    private fun showVesselDialog() {
        vesselDialog.setContentView(R.layout.dialog_vessel)

        //ADD NEW SHIP BUTTON
        val btAddVessel: Button = vesselDialog.findViewById(R.id.bt_vessel_dialog_add)
        btAddVessel.setOnClickListener {
            //TODO intentToInsertVesselActivity()
        }

        //SEARCHVIEW
        val etSearchVessel: SearchView = vesselDialog.findViewById(R.id.et_vessel_dialog_searchbar)
        etSearchVessel.setIconifiedByDefault(false)
        etSearchVessel.requestFocus()
        etSearchVessel.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                textChangeCountJob?.cancel()

                //TODO Change length to 2
                //if (newText?.length ?: 0 > 2) {
                    textChangeCountJob = uiScope.launch {
                        delay(800L)
                        viewModel.getVessel(
                            newText!!.toUpperCase(Locale.ROOT)
                        )
                    }
                //}
                return false
            }

        })

        //RECYCLERVIEW
        val recyclerVessel: RecyclerView = vesselDialog.findViewById(R.id.rv_vessel_dialog)
        recyclerVessel.layoutManager = LinearLayoutManager(this)
        recyclerAdapter =
            VesselListAdapter(this, dataResponse) {
                bd.tvInsertVessel.text = it.shipName
                bd.tvInsertInternational.text =
                    if (it.isInternational) "INTERNASIONAL" else "DOMESTIK"

                vesselId = it.id
                agent = it.agent

                vesselDialog.dismiss()
            }
        recyclerVessel.adapter = recyclerAdapter
        recyclerVessel.setHasFixedSize(true)


        //OBSERVER
        viewModel.getVesselData().observe(this, androidx.lifecycle.Observer {
            dataResponse.clear()
            dataResponse.addAll(it.vessels)
            recyclerAdapter.notifyDataSetChanged()
        })

        val pbVeselDialog: ProgressBar = vesselDialog.findViewById(R.id.pb_vessel_dialog)
        viewModel.isVesselLoading.observe(this, androidx.lifecycle.Observer {
            if (it) {
                pbVeselDialog.visible()
            } else {
                pbVeselDialog.invisible()
            }
        })


        vesselDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        vesselDialog.show()
    }

    private fun showDatePicker() {
        val format = SimpleDateFormat("dd-MMM", Locale.US)
        val datePicker = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                //EXEKUSI DISINI JIKA TANGGAL DIGANTI
                dateTimeNowCalander.set(Calendar.YEAR, year)
                dateTimeNowCalander.set(Calendar.MONTH, month)
                dateTimeNowCalander.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val date = format.format(dateTimeNowCalander.time)
                bd.tvInsertDate.text = date
                dateTimeNow = dateTimeNowCalander.time
            },
            dateTimeNowCalander.get(Calendar.YEAR),
            dateTimeNowCalander.get(Calendar.MONTH),
            dateTimeNowCalander.get(
                Calendar.DAY_OF_MONTH
            )
        )
        datePicker.show()
    }

    private fun showTimePicker() {
        val format = SimpleDateFormat("HH:mm", Locale.US)
        val timePicker = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                //EXEKUSI DISINI JIKA WAKTU DIGANTI
                dateTimeNowCalander.set(Calendar.HOUR_OF_DAY, hourOfDay)
                dateTimeNowCalander.set(Calendar.MINUTE, minute)
                val time = format.format(dateTimeNowCalander.time)
                bd.tvInsertTime.text = time
                dateTimeNow = dateTimeNowCalander.time
            },
            dateTimeNowCalander.get(Calendar.HOUR_OF_DAY),
            dateTimeNowCalander.get(Calendar.MINUTE),
            true
        )
        timePicker.show()
    }
}