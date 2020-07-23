package com.guiftapps.calculadora.Model

import android.content.Context
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class Database(private val nContext: Context?) : SQLiteOpenHelper(nContext, nome, null, versao) {

    override fun onCreate(db: SQLiteDatabase) {

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        try {
            db.execSQL("alter table materia add column status text")
        } catch (e: SQLException) {
            Log.d("DB", "UPDATE")
        }

        try {
            db.execSQL("create table if not exists disciplina(" +
                    "id integer primary key AUTOINCREMENT," +
                    "disciplina text not null," +
                    "dias text)")
        } catch (e: SQLException) {
            Log.d("DB", "UPDATE")
        }

        try {
            db.execSQL("create table if not exists notas(" +
                    "id integer primary key AUTOINCREMENT," +
                    "id_disciplina integer references disciplina (id)," +
                    "bimestre text," +
                    "nota1 decimal," +
                    "nota2 decimal," +
                    "nota3 decimal," +
                    "nota4 decimal)")
        } catch (e: SQLException) {
            Log.d("DB", "UPDATE")
        }

        try {
            db.execSQL("update notas set nota1 = -1 where nota1 = 0")
            db.execSQL("update notas set nota2 = -1 where nota2 = 0")
            db.execSQL("update notas set nota3 = -1 where nota3 = 0")
            db.execSQL("update notas set nota4 = -1 where nota4 = 0")
        } catch (e: SQLException) {
            Log.d("DB", "UPDATE")
        }

        try {
            db.execSQL("alter table disciplina add column local text")
        } catch (e: SQLException) {
            Log.d("DB", "UPDATE")
        }

    }


    companion object {
        private val nome = "BaseDados"
        private val versao = 3
        private val url = "/data/user/0/com.guiftapps.calculadora/databases/BaseDados"
    }
}
