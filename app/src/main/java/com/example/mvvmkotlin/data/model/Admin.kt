package com.example.mvvmkotlin.data.model

import io.realm.RealmObject

open class Admin (
    var username: String?= null,
    var password: String?= null
) : RealmObject() {}