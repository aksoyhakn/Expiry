package com.iyiyasa.android.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.iyiyasa.android.R
import com.iyiyasa.android.data.persistence.entity.Data
import com.iyiyasa.android.databinding.ItemProductBinding
import com.iyiyasa.android.extensions.isNotNull
import com.iyiyasa.android.extensions.notNull


class ShowProductAdapter(
    val products: ArrayList<Data>,
    val listener: ListenerShowProductData
) : RecyclerView.Adapter<ShowProductAdapter.ShowProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowProductViewHolder {
        return ShowProductViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product,
                parent,
                false
            )
        ) { item ->
            listener.clickProduct(item)
        }
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ShowProductViewHolder, position: Int) {
        holder.bindData(products[position], position, (products.size - 1 == position))
    }

    fun addProduct(product: Data?) {
        product.let {
            var previousSize = this.products.size
            this.products.add(it!!)
            notifyItemRangeInserted(previousSize, products.size)
            notifyDataSetChanged()
        }
    }


    class ShowProductViewHolder(
        var binding: ItemProductBinding,
        val onClick: (Data) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var isLastData = false
            set(value) {
                binding.isLastData = value
                field = value
            }

        fun bindData(product: Data, position: Int, lastData: Boolean) {
            isLastData = lastData
            binding.item = product

            binding.ivEndCon.setOnClickListener {
                onClick(product)
            }

            binding.executePendingBindings()
        }
    }

    companion object {

        @JvmStatic
        @BindingAdapter(value = ["bind:setShowProductData", "bind:setShowProductDataListener"])
        fun setShowProductData(
            view: RecyclerView,
            product: ArrayList<Data>?,
            listener: ListenerShowProductData
        ) {
            if (product.isNotNull()) {
                view.adapter =
                    ShowProductAdapter((product as ArrayList<Data>), listener)
            }
        }
    }

    interface ListenerShowProductData {
        fun clickProduct(item: Data)
    }
}