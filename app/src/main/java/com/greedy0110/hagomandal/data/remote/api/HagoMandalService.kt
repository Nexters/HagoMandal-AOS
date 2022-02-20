package com.greedy0110.hagomandal.data.remote.api

import com.greedy0110.hagomandal.data.remote.GoalDto
import com.greedy0110.hagomandal.data.remote.HintListDto
import com.greedy0110.hagomandal.data.remote.JobOptionListDto
import com.greedy0110.hagomandal.data.remote.MandalartIdDto
import com.greedy0110.hagomandal.data.remote.MessageDto
import com.greedy0110.hagomandal.data.remote.UserInfoDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HagoMandalService {
    @POST("/v0/users")
    suspend fun postUserId(@Query("tracking_id") userId: String): UserInfoDto

    @POST("/v0/user_info")
    suspend fun postUserInfo(@Body userInfoDto: UserInfoDto): MessageDto

    @POST("/v0/purpose")
    suspend fun postMandalart(): MandalartIdDto

    @POST("/v0/purpose/{purpose_id}/level")
    suspend fun postGoal(@Path("purpose_id") mandalartId: String, @Body goalDto: GoalDto): GoalDto

    @PUT("/v0/purpose/{purpose_id}/level/{level_id}")
    suspend fun putGoal(@Path("purpose_id") mandalartId: String, @Path("level_id") goalId: String, @Body goalDto: GoalDto): MessageDto

    @GET("/v0/purpose/hints")
    suspend fun getGoalRecommendations(@Query("hints_for") goalCategory: Int): HintListDto

    @GET("/v0/user_info/hints")
    fun getJobOptions(@Query("hints_for") jobCategory: String): JobOptionListDto
}
