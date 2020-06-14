package com.example.roomdemo.adepter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.R
import com.example.roomdemo.databinding.ListItemBinding
import com.example.roomdemo.db.Subscriber

class MyRecycleview(val clickListener: (Subscriber) -> Unit) :
    RecyclerView.Adapter<MyViewHolder>() {
    lateinit var subscriberlist: ArrayList<Subscriber>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subscriberlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(subscriberlist[position], clickListener)
    }

    fun setList(subscriber: List<Subscriber>) {
        subscriberlist.clear()
        subscriberlist.addAll(subscriber)
    }
}

class MyViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun binding(subscriber: Subscriber, clickListener: (Subscriber) -> Unit) {
        binding.emailTextView.text = subscriber.email
        binding.nameTextView.text = subscriber.name
        binding.listItemLayout.setOnClickListener {
            clickListener(subscriber)
        }
    }


}