package com.sonicth06.lessonstracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.sonicth06.lessonstracker.ui.navigation.LessonTrackerNavHost
import com.sonicth06.lessonstracker.ui.theme.LessonsTrackerTheme
import com.sonicth06.lessonstracker.ui.topics.TopicScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LessonsTrackerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LessonTrackerNavHost(navController = rememberNavController())
                }
            }
        }
    }
}