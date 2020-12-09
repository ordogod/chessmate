package com.ordolabs.chessmate.model

data class StopwatchSettingsPresentation(
    val limitMinutes: Int,
    val limitSeconds: Int,
    val player1: String,
    val player2: String
)