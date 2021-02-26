package com.example.mvvmkotlin.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("username")
    val username: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("address")
    val address: Address = Address(),
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("website")
    val website: String = "",
    @SerializedName("company")
    val company: Company = Company()
) : Serializable