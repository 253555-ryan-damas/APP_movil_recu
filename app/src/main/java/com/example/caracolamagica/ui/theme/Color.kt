package com.example.caracolamagica.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.caracolamagica.CaracolaViewModel
import com.example.caracolamagica.PantallaPrincipal


val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFF2BC9AE)
val Pink80 = Color(0xFC9BFFF0)

val Purple40 = Color(0xDE87FFFF)
val PurpleGrey40 = Color(0xFF28C0B5)
val Pink40 = Color(0xFF2E88B4)



@Preview(showBackground = true)
@Composable
fun PantallaPrincipalPreview() {
    MaterialTheme {
        PantallaPrincipal(viewModel = CaracolaViewModel())
    }
}