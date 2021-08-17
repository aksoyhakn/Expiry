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


class ShowProductAdapter(
    val shopLinks: ArrayList<Data>,
    val listener: ListenerShowProductData
) : RecyclerView.Adapter<ShowProductAdapter.ShoppingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingViewHolder {
        return ShoppingViewHolder(
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

    override fun getItemCount(): Int = shopLinks.size

    override fun onBindViewHolder(holder: ShoppingViewHolder, position: Int) {
        holder.bindData(shopLinks[position], position)
    }


    class ShoppingViewHolder(
        var binding: ItemProductBinding,
        val onClick: (Data) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(product: Data, position: Int) {
            binding.item=product
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