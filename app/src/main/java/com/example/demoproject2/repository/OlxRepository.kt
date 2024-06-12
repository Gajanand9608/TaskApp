package com.example.demoproject2.repository

import com.example.demoproject2.interfaces.OlxAPI
import com.example.demoproject2.model.Response
import javax.inject.Inject

class OlxRepository @Inject constructor(private val OlxApi: OlxAPI) {

    // below parameters we should pass to this function as a paramters
    suspend fun getData(): retrofit2.Response<Response?> {
        return OlxApi.getData(
            category = 81,
            facetLimit = 1000,
            location = 5309207,
            locationFacetLimit = 20,
            platform = "web-desktop",
            relaxedFilters = true,
            user = "04677280112180715",
            lang = "en-IN"
        )
    }
}