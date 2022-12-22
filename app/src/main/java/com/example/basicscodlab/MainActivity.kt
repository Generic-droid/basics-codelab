package com.example.basicscodlab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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

    val expanded = remember {
        mutableStateOf(false)
    }

    /**
     * Don't need to remember [extraPadding] against recomposition
     * because it's doing a simple calculation.
     */
    val extraPadding = if (expanded.value) 48.dp else 0.dp

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column( modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)
            )
            {
                Text(text = "Hello, ")
                Text(text = "$name!")
            }
            ElevatedButton(onClick = { expanded.value = !expanded.value }) {
                Text(text = if (expanded.value) "Show less" else "Show more")
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
private fun MyApp(
    modifier: Modifier = Modifier,
    name: List<String> = listOf("Word", "Compose")
) {
    Column( modifier = modifier.padding(vertical = 4.dp) ) {
        name.forEach { Greeting(it) }
    }
}