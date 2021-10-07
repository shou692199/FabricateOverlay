package tk.zwander.fabricateoverlaysample.ui.pages

import android.content.pm.ApplicationInfo
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import tk.zwander.fabricateoverlaysample.ui.elements.AppItem

@Composable
fun AppListPage(
    navController: NavController
) {
    var apps by remember { mutableStateOf(listOf<ApplicationInfo>()) }

    val context = LocalContext.current

    LaunchedEffect("app_launch") {
        apps = withContext(Dispatchers.IO) {
            context.packageManager.getInstalledApplications(0)
        }.filterNot { it.isResourceOverlay }
    }

    LazyColumn {
        items(apps.size) {
            AppItem(apps[it]) { info ->
                navController.currentBackStackEntry?.arguments?.putParcelable("appInfo", info)

                navController.navigate(
                    route = "list_overlays"
                )
            }
        }
    }
}