package com.noobcode.fcat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.noobcode.fcat.ui.theme.FcatTheme
import com.noobcode.fcat.ui.theme.Gradient1
import com.noobcode.fcat.ui.theme.Gradient2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            navigationTimerStart()
            FcatTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                listOf(Gradient1, Gradient2),
                                Offset.Zero,
                                Offset.Infinite,
                                TileMode.Clamp
                            )
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(painter = painterResource(id = R.mipmap.cat_1), contentDescription = "Cat Logo", modifier = Modifier.fillMaxSize(0.25f))
                }
            }
        }
    }

    private fun navigationTimerStart() {
        CoroutineScope(Main).launch {
            delay(2000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        }
    }
}