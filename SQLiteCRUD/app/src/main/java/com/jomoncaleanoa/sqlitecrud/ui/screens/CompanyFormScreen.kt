package com.jomoncaleanoa.sqlitecrud.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jomoncaleanoa.sqlitecrud.data.Company
import com.jomoncaleanoa.sqlitecrud.data.CompanyClassification
import com.jomoncaleanoa.sqlitecrud.data.CompanyDao

@Composable
fun CompanyFormScreen(
    dao: CompanyDao,
    onSave: () -> Unit,
    companyToEdit: Company? = null
) {
    var name by remember { mutableStateOf(TextFieldValue(companyToEdit?.name ?: "")) }
    var website by remember { mutableStateOf(TextFieldValue(companyToEdit?.website ?: "")) }
    var phone by remember { mutableStateOf(TextFieldValue(companyToEdit?.phone ?: "")) }
    var email by remember { mutableStateOf(TextFieldValue(companyToEdit?.email ?: "")) }
    var services by remember { mutableStateOf(TextFieldValue(companyToEdit?.services ?: "")) }

    var selectedClassifications by remember {
        mutableStateOf(companyToEdit?.classification ?: emptyList())
    }

    // ✅ Estado para controlar si los campos están completos
    val isFormValid by derivedStateOf {
        name.text.isNotBlank() &&
                website.text.isNotBlank() &&
                phone.text.isNotBlank() &&
                email.text.isNotBlank() &&
                services.text.isNotBlank() &&
                selectedClassifications.isNotEmpty()
    }

    fun toggleClassification(option: CompanyClassification) {
        selectedClassifications = if (selectedClassifications.contains(option)) {
            selectedClassifications - option
        } else {
            selectedClassifications + option
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = if (companyToEdit == null) "Agregar Empresa" else "Editar Empresa",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre de la empresa *") },
            modifier = Modifier.fillMaxWidth(),
            isError = name.text.isBlank()
        )

        OutlinedTextField(
            value = website,
            onValueChange = { website = it },
            label = { Text("Página web *") },
            modifier = Modifier.fillMaxWidth(),
            isError = website.text.isBlank()
        )

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Teléfono *") },
            modifier = Modifier.fillMaxWidth(),
            isError = phone.text.isBlank()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico *") },
            modifier = Modifier.fillMaxWidth(),
            isError = email.text.isBlank()
        )

        OutlinedTextField(
            value = services,
            onValueChange = { services = it },
            label = { Text("Productos y servicios *") },
            modifier = Modifier.fillMaxWidth(),
            isError = services.text.isBlank()
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ){
            Text(
                "Clasificación de la empresa *",
                style = MaterialTheme.typography.titleMedium
            )

            Column {
                CompanyClassification.values().forEach { classification ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = selectedClassifications.contains(classification),
                                onValueChange = { toggleClassification(classification) }
                            )
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedClassifications.contains(classification),
                            onCheckedChange = { toggleClassification(classification) }
                        )
                        Text(text = classification.displayName)
                    }
                }
            }
        }

        if (selectedClassifications.isEmpty()) {
            Text(
                text = "Selecciona al menos una clasificación",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Botón deshabilitado si el formulario no es válido
        Button(
            onClick = {
                val company = Company(
                    id = companyToEdit?.id ?: 0,
                    name = name.text.trim(),
                    website = website.text.trim(),
                    phone = phone.text.trim(),
                    email = email.text.trim(),
                    services = services.text.trim(),
                    classification = selectedClassifications
                )

                if (companyToEdit == null) {
                    dao.insert(company)
                } else {
                    dao.update(company)
                }

                onSave()
            },
            enabled = isFormValid,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text(if (companyToEdit == null) "Guardar" else "Actualizar")
        }
    }
}
