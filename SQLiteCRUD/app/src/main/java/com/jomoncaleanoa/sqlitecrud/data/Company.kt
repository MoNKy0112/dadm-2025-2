package com.jomoncaleanoa.sqlitecrud.data

data class Company(
    var id: Int = 0,
    var name: String,
    var website: String,
    var phone: String,
    var email: String,
    var services: String,
    var classification: List<CompanyClassification>
)

enum class CompanyClassification(val displayName: String) {
    CONSULTORIA("Consultoría"),
    DESARROLLO_A_LA_MEDIDA("Desarrollo a la medida"),
    FABRICA_DE_SOFTWARE("Fábrica de software");

    companion object {
        fun fromDisplayName(name: String): CompanyClassification? {
            return values().find { it.displayName == name }
        }
    }
}