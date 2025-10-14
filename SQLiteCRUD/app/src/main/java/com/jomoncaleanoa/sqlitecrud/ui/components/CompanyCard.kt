package com.jomoncaleanoa.sqlitecrud.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jomoncaleanoa.sqlitecrud.data.Company

@Composable
fun CompanyCard(
    company: Company,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = company.name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "Clasificación:\n\t->${
                    company.classification.joinToString("\n\t->") { it.displayName }
                }"
            )
            Text(text = "Teléfono: ${company.phone}")
            Text(text = "Email: ${company.email}")
            Text(text = "Servicios: ${company.services}")
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEdit) { Text("Editar") }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}
