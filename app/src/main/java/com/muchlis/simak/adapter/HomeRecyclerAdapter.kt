package com.muchlis.simak.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.muchlis.simak.R
import com.muchlis.simak.datas.HomeItemData
import kotlinx.android.synthetic.main.item_home.view.*

class HomeRecyclerAdapter(
    private val context: Context?,
    private val itemPrint: List<HomeItemData>,
    private val itemClick: (HomeItemData) -> Unit
) : RecyclerView.Adapter<HomeRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_home, parent, false)
        return ViewHolder(
            view,
            itemClick
        )
    }

    override fun getItemCount(): Int = itemPrint.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(itemPrint[position])
    }


    class ViewHolder(view: View, val itemClick: (HomeItemData) -> Unit) :
        RecyclerView.ViewHolder(view) {
        fun bindItem(items: HomeItemData) {

            itemView.apply {
                tv_title_item_home.text = items.title
                tv_subtitle_item_home.text = items.subtitle
                iv_circle_item_home.setImageResource(items.picture)

                //onClick
                //itemView.setOnClickListener { itemClick(items) }
            }
        }
    }
}