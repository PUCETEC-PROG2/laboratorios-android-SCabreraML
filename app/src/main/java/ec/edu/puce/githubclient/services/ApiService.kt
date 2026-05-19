package ec.edu.puce.githubclient.services

import ec.edu.puce.githubclient.models.Repository
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("/user/repos")
    suspend fun getRepositories(
        @Query( value =  "affiliation") affiliation: String = "owner",
        @Query( value = "sort") sort: String = "created",
        @Query( value = "direction") direction: String = "desc",
        @Query( value = "per_page") perPage: Int = 100
    ): List<Repository>

}