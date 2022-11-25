package com.example.waracle

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class MyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MyRepo = MyRepo()

    fun loadData(): MutableLiveData<ApiResponse?> {
        return repository.callAPI()
    }

    fun filterData(cakesList: ArrayList<Cakes>): ArrayList<Cakes> {
        var list: ArrayList<Cakes> = cakesList.distinctBy { it.title } as ArrayList<Cakes>
        list.sortBy { it.title }
        return list
    }

}