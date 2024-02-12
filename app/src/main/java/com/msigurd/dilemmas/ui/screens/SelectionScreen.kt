package com.msigurd.dilemmas.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessibilityNew
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.msigurd.dilemmas.model.Category
import com.msigurd.dilemmas.ui.DilemmasViewModel

@Composable
fun SelectionScreen(
    categories: List<Category>,
    viewModel: DilemmasViewModel,
    navigateToGameScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val sortedCategories = categories.sortedBy { it.name }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 5.dp, vertical = 10.dp)
    ) {
        LazyColumn(modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                CategoryBox(
                    Category(0, "All"),
                    viewModel,
                    navigateToGameScreen
                )
            }
            items(sortedCategories) { category ->
                CategoryBox(
                    category, viewModel, navigateToGameScreen
                )
            }
        }
    }
}

@Composable
fun CategoryBox(
    category: Category,
    viewModel: DilemmasViewModel,
    navigateToGameScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                true,
                onClick = {
                    navigateToGameScreen()
                    viewModel.startGame(category)
                }
            )
    ) {
        Column(
            modifier = modifier
                .padding(15.dp)
        ) {
            Row{
                Icon(
                    imageVector = getCategoryImage(category.name),
                    contentDescription = category.name,
                    modifier = modifier
                        .padding(end = 20.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = category.name,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

fun getCategoryImage(categoryName: String): ImageVector {
    return when (categoryName) {
        "All" -> Icons.Filled.Star
        "Food" -> Icons.Filled.Fastfood
        "Technology" -> Icons.Filled.Computer
        "Body" -> Icons.Filled.AccessibilityNew
        else -> Icons.Filled.Android
    }
}
