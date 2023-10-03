package uvg.edu.gt.lab06

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uvg.edu.gt.lab06.ui.theme.Lab06Theme
import uvg.edu.gt.lab06.views.DisplayCityView
import uvg.edu.gt.lab06.views.HomeView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab06Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Screen.HomeView.route){
                        composable(route = Screen.HomeView.route){
                            HomeView(navController)
                        }
                        composable(
                            route = Screen.DisplayCityView.route + "/{imgName}/{imgURL}",
                            arguments = listOf(
                                navArgument("imgName") { type = NavType.StringType },
                                navArgument("imgURL") { type = NavType.StringType })
                        ){ backStackEntry ->
                            val imgName = backStackEntry.arguments?.getString("imgName")
                            val imgURL = backStackEntry.arguments?.getString("imgURL")
                            requireNotNull(imgName) { Log.e("Error","imgName must NOT be null")}
                            requireNotNull(imgURL) { Log.e("Error","imgURL must NOT be null")}
                            DisplayCityView(navController, imgName, imgURL)
                        }
                    }
                }
            }
        }
    }
}