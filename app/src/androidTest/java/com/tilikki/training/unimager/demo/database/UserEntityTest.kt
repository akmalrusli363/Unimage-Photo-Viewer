package com.tilikki.training.unimager.demo.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class UserEntityTest {
    private lateinit var userDao: UserDao
    private lateinit var db: RoomDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RoomDB::class.java
        ).build()
        userDao = db.userDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetUserById_success() {
        val user = EntityTestDataSet.generateSampleUserData()
        userDao.insertUser(user.toDatabaseEntityUser())
        val retrievedUser = userDao.getUserById("owner")
        Assert.assertEquals(user, retrievedUser)
    }

    @Test
    fun insertAndGetUserByUsername_success() {
        val user = EntityTestDataSet.generateSampleUserData()
        userDao.insertUser(user.toDatabaseEntityUser())
        val retrievedUser = userDao.getUserByUsername("username")
        Assert.assertEquals(user, retrievedUser)
    }
}