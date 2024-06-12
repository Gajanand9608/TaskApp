package com.example.demoproject2.interfaces

import com.example.demoproject2.model.Response
import dagger.Provides
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


// this code will be used when retrofit instance is successfully created
interface OlxAPI {

    @GET("api/relevance/v4/search")
    suspend fun getData(
        @Query("category") category: Int,
        @Query("facet_limit") facetLimit: Int,
        @Query("location") location: Int,
        @Query("location_facet_limit") locationFacetLimit: Int,
        @Query("platform") platform: String,
        @Query("relaxedFilters") relaxedFilters: Boolean,
        @Query("user") user: String,
        @Query("lang") lang: String
    ): retrofit2.Response<Response?>
}