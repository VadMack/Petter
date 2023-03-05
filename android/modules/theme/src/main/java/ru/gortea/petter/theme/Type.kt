package ru.gortea.petter.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val fonts = FontFamily(
    Font(R.font.font_nunito_bold, weight = FontWeight.Bold),
    Font(R.font.font_nunito_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.font_nunito_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.font_nunito_light, weight = FontWeight.Light),
    Font(R.font.font_nunito_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.font_nunito_medium, weight = FontWeight.Medium),
    Font(R.font.font_nunito_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.font_nunito_regular, weight = FontWeight.Normal),
    Font(R.font.font_nunito_semi_bold, weight = FontWeight.SemiBold),
    Font(
        R.font.font_nunito_semi_bold_italic,
        weight = FontWeight.SemiBold,
        style = FontStyle.Italic
    ),
)

// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        fontSize = 36.sp
    ),
    h2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        fontSize = 30.sp
    ),
    h3 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    h4 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp
    ),
    body2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp
    ),
    subtitle1 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 14.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 14.sp
    ),
    caption = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 12.sp
    ),
    button = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )
)

val Typography.appHeader: TextStyle
    get() = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Italic,
        fontSize = 48.sp,
        color = Primary600
    )

val Typography.body3: TextStyle
    get() = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Light,
        fontStyle = FontStyle.Italic,
        fontSize = 16.sp,
        lineHeight = 20.sp
    )

val Typography.button2: TextStyle
    get() = TextStyle(
        fontFamily = fonts,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 18.sp
    )
