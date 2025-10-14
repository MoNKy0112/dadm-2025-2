package com.jomoncaleanoa.sqlitecrud.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.jomoncaleanoa.sqlitecrud.data.Company
import com.jomoncaleanoa.sqlitecrud.data.CompanyDao
import com.jomoncaleanoa.sqlitecrud.ui.screens.CompanyFormScreen
import com.jomoncaleanoa.sqlitecrud.ui.screens.CompanyListScreen

@Composable
fun AppNavigator(dao: CompanyDao) {
    val navController = rememberNavController()
    val gson = remember { Gson() }

    NavHost(navController = navController, startDestination = "company_list") {
        // 🏢 Lista de empresas
        composable("company_list") {
            CompanyListScreen(
                dao = dao,
                onAddCompany = { navController.navigate("add_company") },
                onEditCompany = { company ->
                    val companyJson = gson.toJson(company)
                    navController.navigate("edit_company/${companyJson}")
                }
            )
        }

        // ➕ Agregar empresa
        composable("add_company") {
            CompanyFormScreen(
                dao = dao,
                onSave = { navController.popBackStack() },
                companyToEdit = null
            )
        }

        // ✏️ Editar empresa
        composable(
            route = "edit_company/{company}",
            arguments = listOf(navArgument("company") { type = NavType.StringType })
        ) { backStackEntry ->
            val companyJson = backStackEntry.arguments?.getString("company")
            val company = gson.fromJson(companyJson, Company::class.java)
            CompanyFormScreen(
                dao = dao,
                onSave = { navController.popBackStack() },
                companyToEdit = company
            )
        }
    }
}
