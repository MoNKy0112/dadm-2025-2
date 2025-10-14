package com.jomoncaleanoa.sqlitecrud.data

import android.content.ContentValues
import android.content.Context


class CompanyDao(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    // Convierte la lista de CompanyClassification a una cadena separada por comas
    private fun classificationsToString(classifications: List<CompanyClassification>): String {
        return classifications.joinToString(",") { it.displayName }
    }

    fun insert(company: Company): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", company.name)
            put("website", company.website)
            put("phone", company.phone)
            put("email", company.email)
            put("services", company.services)
            put("classification", classificationsToString(company.classification))
        }
        val id = db.insert("companies", null, values)
        db.close()
        return id
    }

    fun getAll(nameFilter: String = "", classifications: List<CompanyClassification> = emptyList()): List<Company> {
        val db = dbHelper.readableDatabase
        val whereClauses = mutableListOf<String>()
        val args = mutableListOf<String>()

        if (nameFilter.isNotEmpty()) {
            whereClauses.add("name LIKE ?")
            args.add("%$nameFilter%")
        }

        if (classifications.isNotEmpty()) {
            classifications.forEach {
                whereClauses.add("classification LIKE ?")
                args.add("%${it.displayName}%")
            }
        }

        val where = if (whereClauses.isNotEmpty()) whereClauses.joinToString(" AND ") else null
        val cursor = db.query("companies", null, where, args.toTypedArray(), null, null, "name ASC")

        val companies = mutableListOf<Company>()
        if (cursor.moveToFirst()) {
            do {
                val classificationStr = cursor.getString(cursor.getColumnIndexOrThrow("classification"))
                val classificationList = classificationStr.split(",").mapNotNull {
                    CompanyClassification.fromDisplayName(it.trim())
                }

                companies.add(
                    Company(
                        id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        name = cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        website = cursor.getString(cursor.getColumnIndexOrThrow("website")),
                        phone = cursor.getString(cursor.getColumnIndexOrThrow("phone")),
                        email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        services = cursor.getString(cursor.getColumnIndexOrThrow("services")),
                        classification = classificationList
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return companies
    }


    fun update(company: Company): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", company.name)
            put("website", company.website)
            put("phone", company.phone)
            put("email", company.email)
            put("services", company.services)
            put("classification", classificationsToString(company.classification))
        }
        val rows = db.update("companies", values, "id=?", arrayOf(company.id.toString()))
        db.close()
        return rows
    }

    fun delete(id: Int): Int {
        val db = dbHelper.writableDatabase
        val rows = db.delete("companies", "id=?", arrayOf(id.toString()))
        db.close()
        return rows
    }
}
