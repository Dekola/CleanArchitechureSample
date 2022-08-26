package com.dekola.fhrs.data.local.dataSource

import com.dekola.fhrs.data.model.AuthoritiesPresentation

interface IAuthoritiesLocalDataSource {
    suspend fun saveAuthorities(data: List<AuthoritiesPresentation?>?)
    suspend fun fetchSavedAuthorities(): List<AuthoritiesPresentation?>
}