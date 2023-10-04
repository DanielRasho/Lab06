package uvg.edu.gt.lab06.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun DisplayCityView(navHost: NavHostController, imgName : String){
    Column( horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = Modifier.fillMaxSize()) {
        Text(text = imgName)
    }
}