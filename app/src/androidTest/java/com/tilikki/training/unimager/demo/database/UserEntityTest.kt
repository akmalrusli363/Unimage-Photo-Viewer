package com.tilikki.training.unimager.demo.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.tilikki.training.unimager.demo.mocks.TestDataConstants
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class UserEntityTest {
    private companion object {
        const val DEMO_USER_ID = "owner"
        const val DEMO_USERNAME = "username"
    }

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
        val user = generateSampleUserData()
        userDao.insertUser(user)
        val retrievedUser = userDao.getUserById("owner")
        Assert.assertEquals(user, retrievedUser)
    }

    @Test
    fun insertAndGetUserByUsername_success() {
        val user = generateSampleUserData()
        userDao.insertUser(user)
        val retrievedUser = userDao.getUserByUsername("username")
        Assert.assertEquals(user, retrievedUser)
    }

    private fun generateSampleUserData(): EntityUser {
        return EntityUser(
            id = DEMO_USER_ID,
            username = DEMO_USERNAME,
            name = DEMO_USERNAME,
            htmlUrl = TestDataConstants.WEB_URL + DEMO_USERNAME,
            apiUrl = TestDataConstants.API_URL + DEMO_USERNAME,
            apiPhotosUrl = TestDataConstants.API_URL + DEMO_USERNAME + "/apiPhotos",
            profileImageUrl = TestDataConstants.API_URL + DEMO_USERNAME + "/avatar",
            totalPhotos = 10,
            following = 10,
            followers = 10
        )
    }
}