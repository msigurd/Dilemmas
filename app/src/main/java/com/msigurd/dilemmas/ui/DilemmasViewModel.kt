package com.msigurd.dilemmas.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.msigurd.dilemmas.model.Category
import com.msigurd.dilemmas.model.Dilemma
import com.msigurd.dilemmas.model.DilemmasUiState
import com.msigurd.dilemmas.ui.theme.complimentaryColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class DilemmasViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(DilemmasUiState())
    val uiState: StateFlow<DilemmasUiState> = _uiState.asStateFlow()

    var currentCategory = Category()
        private set
    private var allDilemmas = listOf<Dilemma>()
    private var selectedDilemmas = allDilemmas
    private var dilemmasIterator = selectedDilemmas.listIterator()

    fun setAllDilemmas(dilemmas: List<Dilemma>) {
        allDilemmas = dilemmas
    }

    fun startGame(selectedCategory: Category) {
        currentCategory = selectedCategory
        selectedDilemmas = filterDilemmasByCategory()
        dilemmasIterator = selectedDilemmas.listIterator()
        nextDilemma()
    }

    fun nextDilemma() {
        if (!dilemmasIterator.hasNext()) {
            dilemmasIterator = selectedDilemmas.listIterator()
        }

        val nextDilemma = dilemmasIterator.next()
        val (newColor1, newColor2) = generateRandomComplementaryColors()

        updateDilemmasState(
            currentDilemma = nextDilemma,
            color1 = newColor1,
            color2 = newColor2,
        )
    }

    private fun updateDilemmasState(
        currentDilemma: Dilemma,
        color1: Color,
        color2: Color
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                currentDilemma = currentDilemma,
                color1 = color1,
                color2 = color2
            )
        }
    }

    private fun generateRandomComplementaryColors(): Pair<Color, Color> {
        val (color1, color2) = complimentaryColors.random()

        return if (Random.nextBoolean())
            Pair(color1, color2)
        else
            Pair(color2, color1)
    }

    private fun filterDilemmasByCategory(): List<Dilemma> {
        if (currentCategory.id == 0) return allDilemmas

        val filteredDilemmas = mutableListOf<Dilemma>()

        for (dilemma in allDilemmas) {
            val categoriesArray = dilemma.categories
            if (categoriesArray != null) {
                for (i in 0 until categoriesArray.length()) {
                    if (categoriesArray.optInt(i) == currentCategory.id) {
                        filteredDilemmas.add(dilemma)
                        break
                    }
                }
            }
        }

        return filteredDilemmas
    }

}
