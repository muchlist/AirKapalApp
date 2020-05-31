package com.muchlis.simak.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muchlis.simak.R
import com.muchlis.simak.adapter.WaterListRecyclerAdapter
import com.muchlis.simak.databinding.ActivityListWaterBinding
import com.muchlis.simak.datas.output.WaterResponse
import com.muchlis.simak.services.Api
import com.muchlis.simak.utils.invisible
import com.muchlis.simak.utils.visible
import com.muchlis.simak.viewmodels.WatersViewModel
import com.muchlis.simak.viewmodels.WatersViewModelFactory
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*

class WatersActivity : AppCompatActivity() {

    private lateinit var bd: ActivityListWaterBinding

    //viewModel
    private lateinit var viewModel: WatersViewModel
    private lateinit var viewModelFactory: WatersViewModelFactory

    //RV
    private lateinit var recyclerAdapter: WaterListRecyclerAdapter
    private val items: MutableList<WaterResponse> = mutableListOf()

    //JOB pending loading
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var pendingLoadingJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = DataBindingUtil.setContentView(this, R.layout.activity_list_water)

        initViewModel()
        initRecyclerView()
        observeViewModel()

        viewModel.reqListWaterItems()

        bd.btBack.setOnClickListener {
            onBackPressed()
        }

        bd.refreshWaters.setOnRefreshListener {
            viewModel.reqListWaterItems()
            bd.refreshWaters.isRefreshing = false
        }

    }

    private fun initRecyclerView() {
        bd.rvLstWater.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = WaterListRecyclerAdapter(this, items) {

            //TODO  ONCLICK

        }
        bd.rvLstWater.adapter = recyclerAdapter
        bd.rvLstWater.setHasFixedSize(true)
    }

    private fun initViewModel(){
        val application = requireNotNull(this).application
        val apiService = Api.retrofitService
        viewModelFactory = WatersViewModelFactory(apiService, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(WatersViewModel::class.java)
    }

    private fun observeViewModel() {
        viewModel.getWaterListData().observe(this, Observer {
            items.clear()
            items.addAll(it.waters)
            runLayoutAnimation(bd.rvLstWater)
            recyclerAdapter.notifyDataSetChanged()
        })

        viewModel.isLoading.observe(this, Observer {
            if (it){
                bd.pbWatersLoading.visible()
                bd.loadingInclude.progressShimmer.startShimmerAnimation()
            } else {
                pendingLoadingJob = uiScope.launch {
                    delay(300L)
                    bd.loadingInclude.progressShimmer.stopShimmerAnimation()
                    bd.pbWatersLoading.invisible()
                }
            }
        })

        viewModel.messageFailure.observe(this, Observer {
            if (it.isNotEmpty()) {
                Toasty.error(this, it, Toasty.LENGTH_LONG).show()
            }
        })
    }

    private fun runLayoutAnimation(recyclerView: RecyclerView?) {
        recyclerView?.scheduleLayoutAnimation()
        recyclerView?.invalidate()
    }

    override fun onDestroy() {
        pendingLoadingJob?.cancel()
        super.onDestroy()
    }
}
