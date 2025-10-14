package com.jomoncaleanoa.sqlitecrud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jomoncaleanoa.sqlitecrud.data.CompanyDao
import com.jomoncaleanoa.sqlitecrud.ui.navigation.AppNavigator
import com.jomoncaleanoa.sqlitecrud.ui.theme.SQLiteCRUDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = CompanyDao(this)

        setContent {
            SQLiteCRUDTheme {
                AppNavigator(dao = dao)
            }
        }
    }
}
