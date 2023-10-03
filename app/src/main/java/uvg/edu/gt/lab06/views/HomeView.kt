package uvg.edu.gt.lab06.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import uvg.edu.gt.lab06.models.City

@Composable
fun HomeView(navController: NavController){
    var cities = remember { mutableStateListOf<City>() }
    Column( horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        Text(text = "Select an Urban City",
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            CoroutineScope(Dispatchers.Default).launch {

                withContext(Dispatchers.Main){

                }
            }
        }) {
            Text(text = "Load cities")
        }

    }

suspend fun citiesList() : ArrayList<City>{
    var responseBody : String = "{}"
    val cities : ArrayList<City> = ArrayList();

    val client = HttpClient(CIO) {
        install(Logging) {
            logger = Logger.DEFAULT
            filter { request ->
                request.url.host.contains("ktor.io")
            }
        }
    }
    val response : HttpResponse = client.request("https://api.teleport.org/api/urban_areas/"){
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

    val data : JSONArray = JSONObject(responseBody)
        .getJSONObject("_links")
        .getJSONArray("ua:item")
    println(data.toString())

    for (index in 0..data.length() - 1){
        var href = data.getJSONObject(index).getString("href")
        var name = data.getJSONObject(index).getString("name")
        cities.add(City(href, name))
    }

    return cities
}
