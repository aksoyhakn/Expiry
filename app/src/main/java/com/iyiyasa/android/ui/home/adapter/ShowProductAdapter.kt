package com.iyiyasa.android.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.iyiyasa.android.R
import com.iyiyasa.android.data.persistence.entity.Data
import com.iyiyasa.android.databinding.ItemProductBinding
import com.iyiyasa.android.extensions.isNotNull
import com.iyiyasa.android.extensions.lastDateControl
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
        ) { item,position ->
            listener.clickProduct(item,position)
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

    fun deletePosition(position:Int){
        listener.clickProductDelete(products.get(position))
        products.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateProduct(position: Int,product: Data?) {
        product.let {
            notifyItemChanged(position,product)
            notifyDataSetChanged()
        }
    }


    class ShowProductViewHolder(
        var binding: ItemProductBinding,
        val onClick: (Data,Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        var isLastData = false
            set(value) {
                binding.isLastData = value
                field = value
            }

        var isOpenProduct = false
            set(value) {
                binding.isOpenProduct = value
                field = value
            }


        fun bindData(product: Data, position: Int, lastData: Boolean) {
            isLastData = lastData
            isOpenProduct=product.isOpenProduct ?: false
            
            if(product.isOpenProduct == true){
                binding.ivIcon.setBackgroundResource(R.drawable.ic_date4)
            }else{
                binding.ivIcon.lastDateControl(product.productDateControl!!)
            }
            binding.item = product

            binding.llProduct.setOnClickListener {
                if(product.isOpenProduct != true){
                    onClick(product,position)
                }
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

                view.adapter =
                    ShowProductAdapter(product as ArrayList<Data>, listener)
                val swipeHandler = object : SwipeToDeleteCallback(view.context) {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        val adapter = view.adapter as ShowProductAdapter
                        adapter.deletePosition(viewHolder.adapterPosition)
                    }
                }
                val itemTouchHelper = ItemTouchHelper(swipeHandler)
                itemTouchHelper.attachToRecyclerView(view)
            }


        }
    }

    interface ListenerShowProductData {
        fun clickProduct(item: Data,position:Int)
        fun clickProductDelete(item: Data)
    }
}