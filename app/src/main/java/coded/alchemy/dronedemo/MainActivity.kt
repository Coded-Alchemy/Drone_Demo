package coded.alchemy.dronedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coded.alchemy.dronedemo.ui.app.DroneDemoApp
import coded.alchemy.dronedemo.ui.theme.DroneDemoTheme


class MainActivity : ComponentActivity() {
    private val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContent {
            DroneDemoTheme {
                DroneDemoApp()
            }
        }
    }
}
