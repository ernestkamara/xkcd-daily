package io.kamara.xkcd.daily.repository.api

import androidx.lifecycle.LiveData
import io.kamara.xkcd.daily.data.Comic
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ComicsService {
    /**
     * @GET declares an HTTP GET request
     * @Path("comic") annotation on the userId parameter marks it as a
     * replacement for the {comic} placeholder in the @GET path
     */
    @Headers("Content-Type:application/json; charset=UTF-8")
    @GET("/{comic_id}/info.0.json") //https://xkcd.com/614/info.0.json
    fun getComic(@Path("comic_id") comic_id: String): LiveData<ApiResponse<Comic>>
}