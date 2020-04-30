package com.thoughworks.bookrecommendation.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.thoughworks.bookrecommendation.R
import com.thoughworks.bookrecommendation.databinding.CatalogItemBinding
import com.thoughworks.bookrecommendation.model.data.Catalog

class CatalogAdaptor : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val catalogList = mutableListOf<Catalog>()

    private lateinit var clickListener: OnItemClickListener

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val viewHolder = CatalogViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.catalog_item,
                parent,
                false
            )
        )

        viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                clickListener.onItemClick(v, v.tag)
            }

        })

        return viewHolder
    }

    override fun getItemCount(): Int {
        return catalogList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CatalogViewHolder).bind(catalogList[position])
        holder.itemView.tag = catalogList[position].id
    }

    fun updateData(newData: List<Catalog>?) {
        newData?.let {
            catalogList.clear()
            catalogList.addAll(it)
            notifyDataSetChanged()
        }
    }

    inner class CatalogViewHolder(val dataBinding: CatalogItemBinding) :
        RecyclerView.ViewHolder(dataBinding.root) {
        fun bind(catalog: Catalog) {
            dataBinding.catalog = catalog
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.clickListener = onItemClickListener
    }

}