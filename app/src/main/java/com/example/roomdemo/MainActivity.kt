package com.example.roomdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomdemo.adepter.MyRecycleview
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberDatabase
import com.example.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = SubscriberDatabase.getInstance(application).subscriberDAO
        val repository = SubscriberRepository(dao)
        val factory = SubscriberFactory(repository)
        subscriberViewModel = ViewModelProvider(this, factory).get(SubscriberViewModel::class.java)
        binding.myViewModel = subscriberViewModel
        binding.lifecycleOwner = this
        initRecyclerView()

    }

    private fun initRecyclerView() {
        binding.subscriberRecyclerView.layoutManager = LinearLayoutManager(this)
        displaySubscribersList()
    }

    fun displaySubscribersList() {
        subscriberViewModel.subscriber.observe(this, Observer {
            Log.i("list", it.toString())
            binding.subscriberRecyclerView.adapter =
                MyRecycleview(it, { selectItem: Subscriber -> listItemClick(selectItem) })
        })
    }

    fun listItemClick(subscriber: Subscriber) {
        subscriberViewModel.initUpdateOrDelete(subscriber)
    }

}