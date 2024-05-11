package com.htrack.hnotes.ui.theme

import androidx.activity.SystemBarStyle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.htrack.hnotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenCore(
    title: String = "",
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable () -> Unit = {},
    screen: @Composable (paddingValues: PaddingValues) -> Unit
) {
    HnotesTheme {
        SystemBarStyle
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        title = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleLarge
                            )
                        },

                        navigationIcon = navigationIcon,
                        actions = { actions() }
                    )
                }) { values ->
                screen(values)
            }
        }
    }
}

@Composable
fun BackNavigationIcon(backNavigationAction: () -> Unit) {
    IconButton(onClick = backNavigationAction) {
        Icon(
            painter = painterResource(R.drawable.baseline_arrow_back_24),
            contentDescription = "Go back"
        )
    }
}

@Composable
fun HHorizontalDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(top = 4.dp),
        color = MaterialTheme.colorScheme.onPrimary,
        thickness = 0.1.dp
    )
}

@Composable
fun HTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    maxLines: Int = Int.MAX_VALUE,
    textStyle: TextStyle = TextStyle.Default,
    textStyleHint: TextStyle = TextStyle.Default,
    onValueChange: (String?) -> Unit
) {
    TextField(
        modifier = modifier,
        maxLines = maxLines,
        value = text,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint, style = textStyleHint) },
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            disabledTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        textStyle = textStyle
    )
}

@Composable
fun HButton(text: String, click: () -> Unit) {
    Button(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        onClick = click
    ) { Text(text = text, fontSize = 16.sp, maxLines = 1) }
}

@Composable
fun AlertDialog(
    dialogText: String = "",
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
) {
    AlertDialog(
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss", color = MaterialTheme.colorScheme.onPrimary)
            }
        }
    )
}
