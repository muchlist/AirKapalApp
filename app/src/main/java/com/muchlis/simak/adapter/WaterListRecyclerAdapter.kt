package com.muchlis.simak.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muchlis.simak.R
import com.muchlis.simak.datas.output.WaterResponse
import com.muchlis.simak.utils.toDate
import com.muchlis.simak.utils.toStringDateForView
import kotlinx.android.synthetic.main.item_list_water.view.*

class WaterListRecyclerAdapter(
    private val context: Context?,
    private val itemWaters: List<WaterResponse>,
    private val itemClick: (WaterResponse) -> Unit
) : RecyclerView.Adapter<WaterListRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_list_water, parent, false)
        return ViewHolder(
            view,
            itemClick
        )
    }

    override fun getItemCount(): Int = itemWaters.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemWaters[position])
    }


    class ViewHolder(view: View, val itemClick: (WaterResponse) -> Unit) :
        RecyclerView.ViewHolder(view) {
        fun bindItem(items: WaterResponse) {

            itemView.apply {
                tv_list_date.text = items.createdAt.toDate().toStringDateForView()
                tv_list_job.text = items.jobNumber
                tv_list_ship_name.text = items.vessel.vesselName
                tv_list_tonase_order.text = items.volume.tonaseOrdered.toString()
                //tv_list_status.text = items.volume.

                itemView.setOnClickListener { itemClick(items) }
            }
        }
    }
}