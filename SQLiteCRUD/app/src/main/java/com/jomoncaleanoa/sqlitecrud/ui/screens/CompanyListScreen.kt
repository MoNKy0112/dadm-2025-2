package com.jomoncaleanoa.sqlitecrud.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.jomoncaleanoa.sqlitecrud.data.Company
import com.jomoncaleanoa.sqlitecrud.data.CompanyClassification
import com.jomoncaleanoa.sqlitecrud.data.CompanyDao
import com.jomoncaleanoa.sqlitecrud.ui.components.CompanyCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListScreen(
    dao: CompanyDao,
    onAddCompany: () -> Unit,
    onEditCompany: (Company) -> Unit
) {
    var filterName by remember { mutableStateOf(TextFieldValue("")) }
    var selectedClassifications by remember { mutableStateOf<List<CompanyClassification>>(emptyList()) }
    var companies by remember { mutableStateOf(dao.getAll()) }
    var expanded by remember { mutableStateOf(false) }

    fun reload() {
        companies = dao.getAll(filterName.text, selectedClassifications)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Directorio de Empresas") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCompany) {
                Text("+")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = filterName,
                onValueChange = {
                    filterName = it
                    reload()
                },
                label = { Text("Filtrar por nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Dropdown de selección múltiple
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = if (selectedClassifications.isEmpty())
                        "Seleccionar clasificación"
                    else
                        selectedClassifications.joinToString { it.displayName },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Filtrar por clasificación") },
                    trailingIcon = {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.ArrowDropDown,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .clickable { expanded = !expanded }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    CompanyClassification.values().forEach { classification ->
                        DropdownMenuItem(
                            text = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Checkbox(
                                        checked = selectedClassifications.contains(classification),
                                        onCheckedChange = {
                                            if (selectedClassifications.contains(classification)) {
                                                selectedClassifications =
                                                    selectedClassifications - classification
                                            } else {
                                                selectedClassifications =
                                                    selectedClassifications + classification
                                            }
                                            reload()
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(classification.displayName)
                                }
                            },
                            onClick = {
                                if (selectedClassifications.contains(classification)) {
                                    selectedClassifications =
                                        selectedClassifications - classification
                                } else {
                                    selectedClassifications =
                                        selectedClassifications + classification
                                }
                                reload()
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(companies) { company ->
                    CompanyCard(
                        company = company,
                        onEdit = { onEditCompany(company) },
                        onDelete = {
                            dao.delete(company.id)
                            reload()
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(70.dp)) // espacio para el FAB
        }
    }
}
