package com.example.data.model

import com.google.gson.annotations.SerializedName


data class ListCharacter(

    @SerializedName("info")
    var info: Info? = Info(),
    @SerializedName("results")
    var results: ArrayList<Results> = arrayListOf()

)