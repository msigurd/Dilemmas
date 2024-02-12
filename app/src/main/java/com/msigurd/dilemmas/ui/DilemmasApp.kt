package com.msigurd.dilemmas.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.msigurd.dilemmas.R
import com.msigurd.dilemmas.model.Category
import com.msigurd.dilemmas.ui.screens.GameScreen
import com.msigurd.dilemmas.ui.screens.SelectionScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.msigurd.dilemmas.model.Dilemma

enum class ScreenType(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Game(title = R.string.game)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DilemmasAppBar(
    currentScreen: ScreenType,
    currentCategoryName: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = if (currentScreen == ScreenType.Game) {
                    currentCategoryName
                } else {
                    stringResource(currentScreen.title)
                }
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun DilemmasApp(
    categories: List<Category>,
    dilemmas: List<Dilemma>,
    viewModel: DilemmasViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = ScreenType.valueOf(
        backStackEntry?.destination?.route ?: ScreenType.Start.name
    )

    viewModel.setAllDilemmas(dilemmas)
    val currentCategoryName = viewModel.currentCategory.name

    Scaffold(
        topBar = {
            DilemmasAppBar(
                currentScreen = currentScreen,
                currentCategoryName = currentCategoryName,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenType.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = ScreenType.Start.name) {
                SelectionScreen(
                    categories,
                    viewModel,
                    navigateToGameScreen = {
                        navController.navigate(ScreenType.Game.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            composable(route = ScreenType.Game.name) {
                GameScreen(viewModel)
            }
        }
    }
}
