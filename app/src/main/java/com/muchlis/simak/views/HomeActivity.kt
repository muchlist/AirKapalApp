package com.muchlis.simak.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.muchlis.simak.R
import com.muchlis.simak.adapter.HomeRecyclerAdapter
import com.muchlis.simak.databinding.ActivityHomeBinding
import com.muchlis.simak.datas.HomeItemData


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
        bd.toolbar.title = "Muchlis"
        setSupportActionBar(bd.toolbar)
    }

    private fun initRecyclerView() {

        items.apply {
            clear()
            add(
                HomeItemData(
                    title = "Pengisian Air Kapal",
                    subtitle = "Submenu untuk pengisian air kapal",
                    picture = R.drawable.icon_air_kapal
                )
            )
            add(
                HomeItemData(
                    title = "Konfirmasi",
                    subtitle = "Kegiatan air kapal yang memerlukan konfirmasi manager",
                    picture = R.drawable.icon_konfirmasi
                )
            )
            add(
                HomeItemData(
                    title = "Statistik",
                    subtitle = "Dasbor untuk keperluan monitoring data",
                    picture = R.drawable.icon_report
                )
            )
        }


        bd.recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerAdapter = HomeRecyclerAdapter(this, items) {

            //ONCLICK

        }
        bd.recyclerView.adapter = recyclerAdapter
        bd.recyclerView.setHasFixedSize(true)
    }
}
