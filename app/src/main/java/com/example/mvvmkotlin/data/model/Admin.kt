package com.example.mvvmkotlin.data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class Admin (
    @PrimaryKey
    @Required
    var key: Int ?= 0,
    var username: String?= null,
    var password: String?= null
) : RealmObject() {}