package com.msigurd.dilemmas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.msigurd.dilemmas.model.Category
import com.msigurd.dilemmas.model.Dilemma
import com.msigurd.dilemmas.ui.DilemmasApp
import com.msigurd.dilemmas.ui.theme.DilemmasTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()

        val categoriesList = Category.createCategoriesList(res = resources)
        val dilemmasList = Dilemma.createDilemmasList(res = resources)

        setContent {
            DilemmasTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    DilemmasApp(categoriesList, dilemmasList)
                }
            }
        }
    }
}
