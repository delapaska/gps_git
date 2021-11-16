package com.example.gps_git.database

import androidx.lifecycle.LiveData

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<user>> = userDao.readAllData()
    suspend fun addUser(user: user) {
        userDao.addUser((user))
    }
}