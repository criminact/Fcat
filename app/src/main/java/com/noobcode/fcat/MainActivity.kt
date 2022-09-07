package com.noobcode.fcat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noobcode.fcat.ui.theme.FcatTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val vm = AdviceViewModel()
        setContent {
            FcatTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    SetupFcat(vm, Modifier.align(Alignment.Center).padding(24.dp, 0.dp))
                }
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(onClick = {
                        MakeAPICall(vm)
                    }, modifier = Modifier.padding(0.dp, 32.dp)) {
                        Text(
                            text = "Give me an Advice",
                            fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
                            fontSize = 14.sp,
                            color = colorResource(R.color.text_color)
                        )
                    }
                }
            }
        }
    }

    private fun MakeAPICall(vm: AdviceViewModel){
        CoroutineScope(IO).launch {
            vm.fetchAdvice()
        }
    }

}

@Composable
fun SetupFcat(vm: AdviceViewModel, modifier: Modifier) {
    val state = vm.advice.collectAsState()

    when(state.value.state){
        AdviceViewModel.State.Success -> {
            Text(
                modifier = modifier,
                text = state.value.advice.slip.advice,
                fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
                fontSize = 24.sp
            )
        }

        AdviceViewModel.State.Loading -> {
            Text(
                modifier = modifier,
                text = "Loading",
                fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
                fontSize = 24.sp
            )
        }

        AdviceViewModel.State.Failed -> {
            Text(
                modifier = modifier,
                text = "Failed",
                fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
                fontSize = 24.sp
            )
        }
    }
}