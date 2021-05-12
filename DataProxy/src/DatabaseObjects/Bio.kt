package com.Table.Server.DatabaseObjects

import com.Table.Server.DatabaseObjects.Users.autoIncrement
import com.Table.Server.DatabaseObjects.Users.nullable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class Bio(val id: Int?=null, val age:Int?=null, val city:String?=null, val preference1:String?=null , val preference2:String?=null, val preference3:String?=null, val preference4:String?=null)

object Bios: Table() {
    val bio_id: Column<Int> = Bios.integer("bio_id").autoIncrement()
    val age: Column<Int?> = Bios.integer("age").nullable()
    val city: Column<String?> = Bios.varchar("city", 255).nullable()
    val preference1: Column<String?> = Bios.varchar("preference1", 255).nullable()
    val preference2: Column<String?> = Bios.varchar("preference2", 255).nullable()
    val preference3: Column<String?> = Bios.varchar("preference3", 255).nullable()
    val preference4: Column<String?> = Bios.varchar("preference4", 255).nullable()
    override val primaryKey = PrimaryKey(bio_id, name = "Pref_ID")

    fun toBio(row: ResultRow): Bio = Bio(
        id = row[Bios.bio_id],
        age = row[Bios.age],
        city = row[Bios.city],
        preference1 = row[Bios.preference1],
        preference2 = row[Bios.preference2],
        preference3 = row[Bios.preference3],
        preference4 = row[Bios.preference4],

    )

}