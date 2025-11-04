package com.jomoncaleanoa.apidata.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jomoncaleanoa.apidata.data.MapInfo
import com.jomoncaleanoa.apidata.data.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MapViewModel : ViewModel() {
    var currentMap: MapInfo? = null
        private set
    var errorMessage: String? = null
        private set

    fun fetchMapById(id: Int, onResult: () -> Unit) {
        viewModelScope.launch {
            try {
                currentMap = RetrofitInstance.api.getMapById(id)
                println("Mapa obtenido: $currentMap")
                errorMessage = null
            } catch (e: HttpException) {
                errorMessage = "El mapa con ID $id no existe."
                currentMap = null
            } catch (e: IOException) {
                errorMessage = "Error de conexión. Verifica tu internet."
                currentMap = null
            } catch (e: Exception) {
                errorMessage = "Ocurrió un error inesperado."
                currentMap = null
            }
            onResult()
        }
    }
}
