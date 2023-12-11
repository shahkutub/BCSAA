package com.bcsaa.model

class FeedbackListResponse : ArrayList<FeedbackListResponseItem>()

data class FeedbackListResponseItem(
    val created_at: String,
    val description: String,
    val id: Int,
    val reply: Any,
    val status: Int,
    val subject: String,
    val updated_at: String,
    val user_id: Int,
    val usertype: String
)