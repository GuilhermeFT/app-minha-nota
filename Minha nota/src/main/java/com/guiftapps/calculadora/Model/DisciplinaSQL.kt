package com.guiftapps.calculadora.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

import com.guiftapps.calculadora.Controller.Disciplina

class DisciplinaSQL(private val nContext: Context) {
    private val db: SQLiteDatabase? = Database(nContext).writableDatabase

    fun Cadastrar(dsc: Disciplina): Boolean {
        Database(nContext)
        var check = false
        try {
            val cv = ContentValues()
            cv.put("disciplina", dsc.disciplina)
            cv.put("dias", dsc.diaSemana)

            db!!.insert("disciplina", null, cv)
            check = true
        } catch (e: SQLException) {

        }

        return check
    }

    fun Atualizar(dsc: Disciplina): Boolean {
        var check = false
        try {
            val cv = ContentValues()
            cv.put("disciplina", dsc.disciplina)
            cv.put("dias", dsc.diaSemana)

            db!!.update("disciplina", cv, "id = " + dsc.id, null)
            check = true
        } catch (e: SQLException) {

        }

        return check
    }

    fun CriarTabela(): Boolean {
        var check = false
        try {
            db!!.execSQL("create table if not exists disciplina(" +
                    "id integer primary key AUTOINCREMENT," +
                    "disciplina text not null," +
                    "dias text," +
                    "local text)")
            check = true
            db.close()
        } catch (e: SQLException) {

        }

        return check
    }

    fun ifExists(name: String): Boolean {
        Database(nContext).readableDatabase
        var check = false
        var cursor: Cursor?
        try {
            cursor = db!!.rawQuery("select * from disciplina where disciplina = '$name';", null)

            if (!cursor!!.moveToNext()) {

                check = true
            }
            db.close()
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return check
    }

    fun PegarID(nome: String): Int {
        Database(nContext).readableDatabase
        var cursor: Cursor?
        var n = 0
        try {
            cursor = db!!.rawQuery("select * from disciplina where disciplina = '$nome';", null)
            if (cursor!!.moveToNext()) {
                n = cursor.getInt(0)
            }
            db.close()
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return n
    }

    fun Selecionar(): Cursor? {
        Database(nContext).readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db!!.rawQuery("select * from disciplina", null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return cursor
    }

    fun PegarNome(id: Int): String {
        Database(nContext).readableDatabase
        var cursor: Cursor?
        var nome = "Sem nome"
        try {
            cursor = db!!.rawQuery("select * from disciplina where id = $id", null)
            if (cursor!!.moveToNext()) {
                nome = cursor.getString(1)
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return nome
    }

    fun Deletar(id: Int): Boolean {

        return try {
            db!!.delete("disciplina", "id = $id", null)
            true
        } catch (e: SQLiteException) {
            e.printStackTrace()
            false
        }

    }
}
