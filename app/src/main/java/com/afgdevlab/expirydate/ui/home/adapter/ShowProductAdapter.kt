package com.afgdevlab.expirydate.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.databinding.ItemProductBinding
import com.afgdevlab.expirydate.extensions.isNotNull
import com.afgdevlab.expirydate.extensions.lastDateControl


class ShowProductAdapter(
    var products: ArrayList<Data>,
    val listener: ListenerShowProductData
) : RecyclerView.Adapter<ShowProductAdapter.ShowProductViewHolder>(),Filterable {

    var productFiltered: ArrayList<Data> = products

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
                onClick(product, position)
            }

            binding.executePendingBindings()
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""

                if (charString.isEmpty()) {
                    products =productFiltered
                } else {
                    val filteredList = ArrayList<Data>()
                    products
                        .filter {
                            (it.productName.contains(constraint!!))
                        }
                        .forEach { filteredList.add(it) }

                    products = filteredList

                }
                return FilterResults().apply { values = products }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                products = if (results?.values == null)
                    productFiltered
                else
                    (results.values as ArrayList<Data>)

                listener.clickProductSearchSize(products.size)
                notifyDataSetChanged()
            }
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
        fun clickProduct(item: Data,position:Int)
        fun clickProductSearchSize(item: Int)
    }
}