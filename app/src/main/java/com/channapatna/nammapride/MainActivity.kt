package com.channapatna.nammapride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.channapatna.nammapride.BuildConfig
import com.channapatna.nammapride.data.FallbackChannapatnaRepository
import com.channapatna.nammapride.data.FirebaseChannapatnaRepository
import com.channapatna.nammapride.data.GeminiStoryGenerator
import com.channapatna.nammapride.ui.ChannapatnaApp
import com.channapatna.nammapride.ui.ChannapatnaViewModel
import com.channapatna.nammapride.ui.ChannapatnaViewModelFactory
import com.channapatna.nammapride.ui.theme.AppBackground
import com.channapatna.nammapride.ui.theme.ChannapatnaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChannapatnaTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AppBackground)
                ) {
                    val viewModel: ChannapatnaViewModel = viewModel(
                        factory = ChannapatnaViewModelFactory(
                            FallbackChannapatnaRepository(
                                FirebaseChannapatnaRepository(
                                    storyGenerator = GeminiStoryGenerator(BuildConfig.GEMINI_API_KEY)
                                )
                            )
                        )
                    )
                    ChannapatnaApp(viewModel)
                }
            }
        }
    }
}
