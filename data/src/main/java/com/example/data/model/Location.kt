package com.example.data.model

import com.google.gson.annotations.SerializedName


data class Location (

  @SerializedName("name" ) var name : String? = null,
  @SerializedName("url"  ) var url  : String? = null

)