package uvg.edu.gt.lab06

sealed class Screen (val route: String){
    object HomeView : Screen("HomeView")
    object DisplayCityView : Screen("DisplayCityView")
}