package com.dekola.fhrs

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.dekola.fhrs.data.local.db.AuthoritiesDao
import com.dekola.fhrs.data.local.db.AuthoritiesDatabase
import com.dekola.fhrs.data.model.AuthoritiesEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthoritiesDaoTest {
    private lateinit var authoritiesDao: AuthoritiesDao
    private lateinit var authoritiesDatabase: AuthoritiesDatabase

    @Before
    fun createDb() {
        val application = ApplicationProvider.getApplicationContext<Application>()

        authoritiesDatabase = Room.inMemoryDatabaseBuilder(
            application, AuthoritiesDatabase::class.java).build()
        authoritiesDao = authoritiesDatabase.authoritiesDao()
    }

    @Test
    fun insertAndRetrieveAuthorities() = runBlocking {
        val testName = "testName"
        val authoritiesEntity = createAuthoritiesEntity(testName)

        authoritiesDao.saveAuthorities(listOf(authoritiesEntity))
        val savedAuthorities = authoritiesDao.getAllAuthorities()

        Assert.assertEquals(testName, savedAuthorities[0].name)
    }


    @After
    fun closeDb() {
        authoritiesDatabase.close()
    }

    private fun createAuthoritiesEntity(name: String) = AuthoritiesEntity(
        name = name
    )
}