package com.example.waracle

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.waracle.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), Adapter.OnNoteList {

    private lateinit var binding: ActivityMainBinding

    private var adapter: Adapter? = null
    private var recyclerView: RecyclerView? = null
    private var modelRecyclerArrayList: ArrayList<Cakes>? = null
    private var myViewModel: MyViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        modelRecyclerArrayList = ArrayList()
        recyclerView = binding.recyclerView
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]
        initRecycler()

        binding.fab.setOnClickListener {
            updateRecyclerView()

        }

        updateRecyclerView()

    }

    private fun updateRecyclerView() {
        myViewModel?.loadData()?.observe(this,
            Observer<ApiResponse?> { apiResponse ->
                if (apiResponse == null) {
                    // handle error here
                    return@Observer
                }
                if (apiResponse.error == null) {
                    val list = myViewModel?.filterData(apiResponse.posts as ArrayList<Cakes>)
                    modelRecyclerArrayList = list
                    adapter?.updateList(modelRecyclerArrayList)
                } else {
                    // call failed.
                    adapter?.updateList(null)
                    val e = apiResponse.error
                    val errorMessage = if(e?.message?.contains("Unable to resolve") == true){ "No network available. Please try again"} else { e?.message }
                    Toast.makeText(this@MainActivity, errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }

    private fun initRecycler() {
        adapter = Adapter(this, this, modelRecyclerArrayList, this)
        recyclerView!!.layoutManager =
            LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = adapter
    }

    override fun OnnoteClick(userClass: Cakes?) {
        val snack = binding.fab.let { Snackbar.make(it,userClass?.desc.toString(),Snackbar.LENGTH_INDEFINITE) }
        snack.setAction("DISMISS") {
            // executed when DISMISS is clicked
            println("Snack bar Set Action - OnClick.")
        }
        snack.show()
    }

}