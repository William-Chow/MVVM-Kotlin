# MVVM-Kotlin
Example of MVVM with Kotlin

Realm - Database in App
https://github.com/realm/realm-kotlin

Timber - Log
https://github.com/JakeWharton/timber

Retrofit
https://github.com/square/retrofit

MVVM + Kotlin App

Advanced Features in Future 
- Firebase Login
- Firebase Google Map
- App Redesign for Home Page




Realm Control App


        // Check Database Value All the database record, uncomment to view Admin Database
        val admins = realm.where<Admin>().findAll()
        for (admin in admins) {
            Timber.i("Result :: %s + %s + %s", admin.username, admin.password, admin.key)
        }
        
  
  
        // added to delete the db once the activity is created. Only use this if required (Clear the Database)
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
        
        
        
        
        
        
        
 Login Page
        
 ![Screenshot_1614414397](https://user-images.githubusercontent.com/8773222/109382002-aaffd100-7918-11eb-84d2-afccf88f676d.png)

