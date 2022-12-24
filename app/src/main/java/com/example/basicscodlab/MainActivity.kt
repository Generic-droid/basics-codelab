package com.example.basicscodlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodlab.ui.theme.BasicsCodlabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodlabTheme {
                MyApp(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    /**
     * [remember] is used to guard against recomposition,
     *  so the state is not reset.
     */

    var expanded by remember { mutableStateOf(false) }

    /**
     * Don't need to remember [extraPadding] against recomposition
     * because it's doing a simple calculation.
     */
    val extraPadding by animateDpAsState (
        if (expanded) 48.dp else 0.dp,
        animationSpec =
        // tween(1000, easing = CubicBezierEasing(0.4f, 0.0f, 0.8f, 0.8f))
        spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
        /*repeatable(
            iterations = 5,
            animation = tween(1000),
            repeatMode = RepeatMode.Restart
        )*/
    )

    val startColor = Color.Blue

    val endColor = Color.Green

    val backgroundColor by animateColorAsState(
        if (expanded) endColor else startColor
    )

    Surface(
        color = MaterialTheme.colorScheme.primary,
        //color = backgroundColor,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column( modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            )
            {
                Text(text = "Hello, ")
                Text(text = "$name!")
            }
            ElevatedButton(
                /*colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,
                    contentColor = Color.Red),*/
                onClick = { expanded = !expanded }
            ) {
                Text(text = if (expanded) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, name = "Text preview", widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodlabTheme {
        MyApp()
    }
}

@Composable
private fun MyApp( modifier: Modifier = Modifier ) {

    var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier) {
        if (shouldShowOnBoarding) {
            OnBoardingScreen( onContinueClicked = { shouldShowOnBoarding = false } )
        } else Greetings()
    }
}

@Composable
private fun OnBoardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to the Basics Codelab!")
        Button(
            modifier = modifier.padding(24.dp),
            onClick = onContinueClicked
        ) {
            Text(text = "Continue")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnBoardingPreview() {
    BasicsCodlabTheme {
        OnBoardingScreen(onContinueClicked = {}) // Do nothing onClick.
    }
}

@Composable
fun Greetings(modifier: Modifier = Modifier,
              names: List<String> = List(1000) {"$it"}
) {
    LazyColumn( modifier = modifier.padding(vertical = 4.dp) ) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingsPreview() {
    BasicsCodlabTheme {
        Greetings()
    }
}

@Preview
@Composable
fun MyAppPreview() {
    BasicsCodlabTheme {
        MyApp(modifier = Modifier.fillMaxSize())
    }
}