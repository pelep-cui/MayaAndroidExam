package com.rpc.mayaandroidexam.ui

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rpc.mayaandroidexam.ui.theme.MayaAppTheme

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        enableEdgeToEdge()
        setContent {
            MayaAppTheme {
                App()
            }
        }
    }
}