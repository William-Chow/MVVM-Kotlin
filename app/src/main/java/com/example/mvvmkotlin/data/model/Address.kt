package com.example.mvvmkotlin.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Address(
    @SerializedName("street")
    val street: String = "",
    @SerializedName("suite")
    val suite: String = "",
    @SerializedName("city")
    val city: String = "",
    @SerializedName("zipcode")
    val zipCode: String = "",
    @SerializedName("geo")
    val geo: Geo = Geo()
) : Serializable