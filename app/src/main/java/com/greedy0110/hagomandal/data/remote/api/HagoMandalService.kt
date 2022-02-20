package com.greedy0110.hagomandal.data.remote.api

import com.greedy0110.hagomandal.data.remote.GoalDto
import com.greedy0110.hagomandal.data.remote.JobOptionListDto
import com.greedy0110.hagomandal.data.remote.MandalartIdDto
import com.greedy0110.hagomandal.data.remote.MessageDto
import com.greedy0110.hagomandal.data.remote.UserInfoDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface HagoMandalService {
    @POST("/v0/users")
    fun postUserId(@Query("tracking_id") userId: String): Response<UserInfoDto>

    @POST("/v0/user_info")
    fun postUserInfo(@Body userInfoDto: UserInfoDto): Response<MessageDto>

    @POST("/v0/purpose")
    fun postMandalart(): Response<MandalartIdDto>

    @POST("/v0/purpose/{purpose_id}/node")
    fun postGoal(@Path("purpose_id") mandalartId: String, @Body goalDto: GoalDto): Response<GoalDto>

    @PUT("/v0/purpose/{purpose_id}/node/{node_id}")
    fun putGoal(@Path("purpose_id") mandalartId: String, @Path("node_id") goalId: String, @Body goalDto: GoalDto): Response<MessageDto>

    @GET("/v0/purpose/hints")
    fun getGoalRecommendations(@Query("hints_for") goalCategory: Int): Response<MessageDto>

    @GET("/v0/user_info/hints")
    fun getJobOptions(@Query("hints_for") jobCategory: String): Response<JobOptionListDto>
}
