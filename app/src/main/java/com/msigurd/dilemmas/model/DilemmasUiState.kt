package com.msigurd.dilemmas.model

import androidx.compose.ui.graphics.Color

data class DilemmasUiState (
    val currentDilemma: Dilemma = Dilemma(),
    val color1: Color = Color.Black,
    val color2: Color = Color.White
)
