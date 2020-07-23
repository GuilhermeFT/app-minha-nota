package com.guiftapps.calculadora.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

import com.guiftapps.calculadora.Controller.Materia

class MateriaSQL(nContext: Context) {
    private val db: SQLiteDatabase? = Database(nContext).writableDatabase

    fun CriarTabela(): Boolean {
        val SQL = "create table if not exists materia (" +
                "id integer primary key AUTOINCREMENT," +
                "nome text not null, " +
                "bim1 decimal," +
                "bim2 decimal," +
                "bim3 decimal," +
                "nfinal decimal," +
                "status text)"
        return try {
            db!!.execSQL(SQL)
            true
        } catch (e: SQLiteException) {
            e.printStackTrace()
            false
        } finally {
            db!!.close()
        }
    }

    fun Cadastrar(mat: Materia): Boolean {
        try {
            val cv = ContentValues()
            cv.put("nome", mat.nomeMateria)
            cv.put("bim1", mat.bim1)
            cv.put("bim2", mat.bim2)
            cv.put("bim3", mat.bim3)
            cv.put("nfinal", mat.getnFinal())
            cv.put("status", mat.status)
            db!!.insert("materia", null, cv)
            return true
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return false
        } finally {
            db!!.close()
        }
    }

    fun Atualizar(mat: Materia): Boolean {
        try {
            val cv = ContentValues()
            cv.put("nome", mat.nomeMateria)
            cv.put("bim1", mat.bim1)
            cv.put("bim2", mat.bim2)
            cv.put("bim3", mat.bim3)
            cv.put("nfinal", mat.getnFinal())
            cv.put("status", mat.status)
            db!!.update("materia", cv, "id = " + mat.id, null)
            return true
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return false
        } finally {
            db!!.close()
        }
    }

    fun Listar(): Cursor? {
        var cursor: Cursor? = null
        try {
            cursor = db!!.rawQuery("select * from materia;", null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return cursor
    }

    fun Selecionar(nome: String): Boolean {
        var cursor: Cursor?
        try {
            cursor = db!!.rawQuery("select * from materia where nome = '$nome';", null)

            if (!cursor!!.moveToNext()) {
                return true
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return false
    }

    fun PegarID(nome: String): Int {
        var cursor: Cursor?
        var n = 0
        try {
            cursor = db!!.rawQuery("select * from materia where nome = '$nome';", null)
            if (cursor!!.moveToNext()) {
                n = cursor.getInt(0)
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return n
    }

    fun Deletar(nome: String): Boolean {
        return try {
            db!!.delete("materia", "nome = '$nome'", null)
            true
        } catch (e: SQLiteException) {
            e.printStackTrace()
            false
        }

    }

    fun GetId(): Int {

        var id = 0
        var cursor: Cursor?
        try {
            cursor = db!!.rawQuery("select id from materia;", null)

            if (cursor!!.moveToLast()) {
                id = cursor.getInt(0)
            }
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return id
    }

}

