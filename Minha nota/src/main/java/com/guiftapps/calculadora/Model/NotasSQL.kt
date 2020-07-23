package com.guiftapps.calculadora.Model

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException

import com.guiftapps.calculadora.Controller.Notas

class NotasSQL(nContext: Context) {
    private val db: SQLiteDatabase = Database(nContext).writableDatabase

    fun CriarTabela(): Boolean {

        val SQL = "create table if not exists notas(" +
                "id integer primary key AUTOINCREMENT," +
                "id_disciplina integer references disciplina (id)," +
                "bimestre text," +
                "nota1 decimal," +
                "nota2 decimal," +
                "nota3 decimal," +
                "nota4 decimal)"
        try {
            db.execSQL(SQL)
            return true
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return false
        } finally {
            db.close()
        }
    }

    fun Deletar(id: Int): Boolean {

        return try {
            db.delete("notas", "id = $id", null)
            true
        } catch (e: SQLiteException) {
            e.printStackTrace()
            false
        }

    }

    fun Selecionar(): Cursor? {
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("select * from notas", null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return cursor
    }

    fun Selecionar(id: Int): Cursor? {
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery("select * from notas where id_disciplina = $id;", null)
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return cursor
    }

    fun Cadastrar(notas: Notas): Boolean {

        try {
            val cv = ContentValues()
            cv.put("id_disciplina", notas.id_disciplina)
            cv.put("bimestre", notas.bimestre)
            cv.put("nota1", notas.nota1)
            cv.put("nota2", notas.nota2)
            cv.put("nota3", notas.nota3)
            cv.put("nota4", notas.nota4)
            db.insert("notas", null, cv)
            return true
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return false
        } finally {
            db.close()
        }
    }

    fun Atualizar(notas: Notas): Boolean {

        try {
            val cv = ContentValues()
            cv.put("id_disciplina", notas.id_disciplina)
            cv.put("bimestre", notas.bimestre)
            cv.put("nota1", notas.nota1)
            cv.put("nota2", notas.nota2)
            cv.put("nota3", notas.nota3)
            cv.put("nota4", notas.nota4)
            db.update("notas", cv, "id = " + notas.id, null)
            return true
        } catch (e: SQLiteException) {
            e.printStackTrace()
            return false
        } finally {
            db.close()
        }
    }

    fun PegarID(id: Int): Int {
        var cursor: Cursor? = null
        var n = 0
        try {
            cursor = db.rawQuery("select * from notas where id_disciplina = $id;", null)
            if (cursor!!.moveToNext()) {
                n = cursor.getInt(0)
            }
            db.close()
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return n
    }

    fun Verificar(n: Notas): Boolean {
        var cursor: Cursor?
        var check = false
        try {
            cursor = db.rawQuery("select * from notas where id_disciplina = " + n.id_disciplina + ";", null)
            if (cursor!!.moveToNext()) {
                when (n.bimestre) {
                    "1º Bimestre" -> if (cursor.getDouble(3) == 0.0 || cursor.getDouble(3) == -1.0) {
                        check = true
                    }
                    "2º Bimestre" -> if (cursor.getDouble(4) == 0.0 || cursor.getDouble(4) == -1.0) {
                        check = true
                    }
                    "3º Bimestre" -> if (cursor.getDouble(5) == 0.0 || cursor.getDouble(5) == -1.0) {
                        check = true
                    }
                    "4º Bimestre" -> if (cursor.getDouble(6) == 0.0 || cursor.getDouble(6) == -1.0) {
                        check = true
                    }
                }
            } else {
                check = true
            }
            db.close()
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return check
    }

    fun Verificar(nome: Int): Boolean {
        var cursor: Cursor? = null
        var check = false
        try {
            cursor = db.rawQuery("select * from notas where id_disciplina = $nome", null)
            if (!cursor!!.moveToNext()) {
                check = true
            }
            db.close()
        } catch (e: SQLiteException) {
            e.printStackTrace()
        }

        return check
    }
}
