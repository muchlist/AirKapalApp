package com.muchlis.simak.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.muchlis.simak.R
import com.muchlis.simak.adapter.HomeRecyclerAdapter
import com.muchlis.simak.databinding.ActivityHomeBinding
import com.muchlis.simak.datas.HomeItemData
import com.muchlis.simak.utils.App


class HomeActivity : AppCompatActivity() {

    private lateinit var bd: ActivityHomeBinding

    private lateinit var recyclerAdapter: HomeRecyclerAdapter
    private val items: MutableList<HomeItemData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = DataBindingUtil.setContentView(this, R.layout.activity_home)

        setToolBar()
        initRecyclerView()

    }

    private fun setToolBar() {
        bd.toolbar.title = App.prefs.userBranchSave
        setSupportActionBar(bd.toolbar)
    }

    private fun initRecyclerView() {

        items.apply {
            clear()
            add(
                HomeItemData(
                    index = 0,
                    title = "Pengisian Air Kapal",
                    subtitle = "Submenu untuk pengisian air kapal",
                    picture = R.drawable.icon_air_kapal
                )
            )
            add(
                HomeItemData(
                    index = 1,
                    title = "Konfirmasi",
                    subtitle = "Kegiatan air kapal yang memerlukan konfirmasi manager",
                    picture = R.drawable.icon_konfirmasi
                )
            )
            add(
                HomeItemData(
                    index = 2,
                    title = "Statistik",
                    subtitle = "Dasbor untuk keperluan monitoring data",
                    picture = R.drawable.icon_report
                )
            )
        }


        bd.recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = HomeRecyclerAdapter(this, items) {

            //ONCLICK
            when (it.index) {
                0 -> changeToListWaterActivity()
            }

        }
        bd.recyclerView.adapter = recyclerAdapter
        bd.recyclerView.setHasFixedSize(true)
    }

    private fun changeToListWaterActivity() {
        val intent = Intent(this, WatersActivity::class.java)
        startActivity(intent)
    }
}
