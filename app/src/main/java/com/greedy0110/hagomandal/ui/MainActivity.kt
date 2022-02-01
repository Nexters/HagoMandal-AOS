package com.greedy0110.hagomandal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.greedy0110.hagomandal.ui.onboarding.OnBoardingApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO: Activity 는 하나의 Scenario 를 의미해야한다.
            OnBoardingApp()
        }
    }
}
