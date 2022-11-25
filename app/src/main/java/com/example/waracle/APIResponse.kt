package com.example.waracle

import kotlin.collections.ArrayList

class ApiResponse(allCakes: ArrayList<Cakes>?, s: Throwable?) {
    var posts: List<Cakes>? = allCakes
    var error: Throwable? = s
}