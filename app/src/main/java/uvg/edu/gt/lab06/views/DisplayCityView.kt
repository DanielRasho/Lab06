package uvg.edu.gt.lab06.views

import android.content.res.Resources.Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import org.json.JSONArray
import org.json.JSONObject
import uvg.edu.gt.lab06.R
import uvg.edu.gt.lab06.models.City

@Composable
fun DisplayCityView(navHost: NavHostController, cityName : String){
    var (imageUrl, setImage) = remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        val fetchedImageUrl : String = fetchImage(cityName)
        setImage(fetchedImageUrl)
    }
    Column( horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = Modifier.fillMaxSize()
            .padding(16.dp)) {
        Text(text = cityName,
            style = MaterialTheme.typography.displayMedium)
        Spacer(modifier = Modifier.height(32.dp))
        AsyncImage(model = imageUrl,
            contentDescription = "A city image",
            placeholder = painterResource(R.drawable.placehold)
        )
    }
}

suspend fun fetchImage(cityName : String) : String {
    var imgUrl : String = ""
    var responseBody : String = ""

    val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            filter { request ->
                request.url.host.contains("ktor.io")
            }
        }
    }
    println("https://api.teleport.org/api/urban_areas/slug:${cityName.lowercase()}/images/")
    val response : HttpResponse = client.request(
        "https://api.teleport.org/api/urban_areas/slug:${cityName.lowercase()}/images/"){
        method = HttpMethod.Get
        headers {
            append(HttpHeaders.Accept, "application/vnd.teleport.v1+json")
        }

    }

    if (response.status.value in 200..299){
        responseBody = response.body()
        println(responseBody)
    }
    else {
        println("Error:${response.status.value}, something when wrong.")
    }

    imgUrl = JSONObject(responseBody)
        .getJSONArray("photos")
        .getJSONObject(0)
        .getJSONObject("image")
        .getString("mobile")
    println(imgUrl)

    return imgUrl
}