package com.seda.filmsearchapp.data.model

data class FilmResult(
    val Response: String,
    val Search: List<Search>,
    val totalResults: String
)