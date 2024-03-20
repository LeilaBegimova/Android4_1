package com.example.android4_1.data.remote.apiservises.models


import com.google.gson.annotations.SerializedName

data class SmallWebp(
    @SerializedName("height")
    val height: Int,
    @SerializedName("width")
    val width: Int
)