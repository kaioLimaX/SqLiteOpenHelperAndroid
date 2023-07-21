package com.skydevices.sqliteopenhelperandroid.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context): SQLiteOpenHelper(// databaseHelper herda de SqLiteOpenHelper

    context, "loja.db", null, 2//vc tem que passar o conteudo, Nome do banco de dados, CursosFactory e a versao do banco

) {
    companion object{
        const val TABELA_PRODUTOS = "produtos"
        const val ID_PRODUTO = "id_produto"
        const val TITULO = "titulo"
        const val DESCRICAO = "descricao"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE IF NOT EXISTS $TABELA_PRODUTOS(" + // linha de comando responsavel para executar no execSQL
                "$ID_PRODUTO INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "$TITULO VARCHAR(100)," +
                "$DESCRICAO text" +
                ");"
        try { // try é um comando necessario para tratamento de erros de retorno de funções
            db?.execSQL(sql) // comando para executar linha de codigo mysql
            Log.i("info_db", "Sucesso ao criar Tabela ")
        }catch (e : Exception){ // catch captura a excessao(erro) caso o comando execSQL retornar
            e.printStackTrace()
            Log.i("info_db", "Erro ao criar Tabela ")
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.i("info_db", "nova versão inserida ")
    }

}