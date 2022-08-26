package com.dekola.fhrs.data

import com.dekola.fhrs.data.model.AuthoritiesEntity
import com.dekola.fhrs.data.model.AuthoritiesPresentation

fun AuthoritiesResponseItem.toPresentation() = AuthoritiesPresentation(
    name = name
)

fun AuthoritiesEntity.toPresentation() = AuthoritiesPresentation(
    name = name
)
fun AuthoritiesPresentation.toEntity() = AuthoritiesEntity(
    name = name
)