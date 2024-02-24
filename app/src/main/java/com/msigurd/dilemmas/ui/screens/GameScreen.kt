package com.msigurd.dilemmas.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.msigurd.dilemmas.ui.DilemmasViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.msigurd.dilemmas.R

@Composable
fun GameScreen(
    viewModel: DilemmasViewModel,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    if (isPortrait)
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 10.dp)
        ) {
            Content(viewModel, true)
        }
    else
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 10.dp)
        ) {
            Content(viewModel, false)
        }
}

@Composable
fun Content(
    viewModel: DilemmasViewModel,
    isPortrait: Boolean,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val dilemma = uiState.currentDilemma

    fun handleCardClick() {
        viewModel.nextDilemma()
    }

    Column(
        modifier = if (isPortrait)
            modifier.fillMaxHeight(0.44f)
        else
            modifier.fillMaxWidth(0.44f)
    ) {
        DilemmaCard(
            handleClick = { handleCardClick() },
            option = dilemma.optionOne,
            shape = RoundedCornerShape(0, 5, 5, 0),
            backgroundColor = uiState.color1,
            modifier = modifier.padding(end = 10.dp)
        )
    }
    Column(
        modifier = if (isPortrait)
            modifier
                .fillMaxHeight(0.21f)
                .fillMaxWidth()
                .wrapContentSize()
        else
            modifier
                .fillMaxWidth(0.22f)
                .fillMaxHeight()
                .wrapContentSize()
    ) {
        Text(stringResource(R.string.game_or))
    }
    Column{
        DilemmaCard(
            handleClick = { handleCardClick() },
            option = dilemma.optionTwo,
            shape = RoundedCornerShape(5, 0, 0, 5),
            backgroundColor = uiState.color2,
            modifier = modifier.padding(start = 10.dp)
        )
    }
}

@Composable
fun DilemmaCard(
    handleClick: () -> Unit,
    option: String,
    shape: RoundedCornerShape,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = shape,
        modifier = modifier
            .fillMaxWidth()
            .clickable(
                true,
                onClick = {
                    handleClick()
                }
            )
    ) {
        Column(
            modifier = Modifier.background(backgroundColor)
        ) {
            Text(
                text = option,
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
                    .padding(10.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}
