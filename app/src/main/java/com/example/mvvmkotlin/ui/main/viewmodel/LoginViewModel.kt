package com.example.mvvmkotlin.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvmkotlin.data.model.Admin
import io.realm.Realm
import io.realm.kotlin.where
import kotlin.random.Random

class LoginViewModel : ViewModel() {

    // Store data to realm db
    fun realmStoreData(username: String, password: String, realm: Realm) {
        val admin = Admin()
        admin.key = Random.nextInt(0, 10000)
        admin.username = username
        admin.password = password
        realm.beginTransaction()
        realm.executeTransactionAsync { processRealm ->
            processRealm.copyToRealmOrUpdate(admin)
        }
        realm.commitTransaction()
    }

    // realm db validate login account
    fun realmValidateCheckingUser(username: String, password: String, realm: Realm): Int {
        val admin = realm.where<Admin>().equalTo("username", username).equalTo("password", password).findAll()
        return admin.size
    }

    // Check if the object is exist
    fun realmValidateUserExist(username: String, realm: Realm): Int{
        val admin = realm.where<Admin>().equalTo("username", username).findAll()
        return admin.size
    }

}