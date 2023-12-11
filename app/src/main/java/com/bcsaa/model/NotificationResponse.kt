package com.bcsaa.model

class NotificationResponse : ArrayList<NotificationResponseItem>()

data class NotificationResponseItem(
    val created_at: String,
    val data: Data,
    val id: String,
    val notifiable_id: Int,
    val notifiable_type: String,
    val read_at: Any,
    val type: String,
    val updated_at: String
)

data class Data(
    val message: String,
    val redirect_to: String,
    val user_id: Int,
    val user_name: String
)