package com.skydevices.sqliteopenhelperandroid.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.skydevices.sqliteopenhelperandroid.model.Produto

class ProdutoDAO(context: Context) : iProdutoDAO {

    private val escrita = DatabaseHelper(context).writableDatabase
    private val leitura = DatabaseHelper(context).readableDatabase

    override fun salvar(produto: Produto): Boolean {
        // val sql = "INSERT INTO produtos VALUES( null, '$[${produto.nome},'descricao..' )"

        val valores = ContentValues()
        valores.put(DatabaseHelper.TITULO, produto.nome)
        valores.put(DatabaseHelper.DESCRICAO, produto.descricao)

        try {
            //escrita.execSQL(sql)//whitableDatabase é utilizado para comandos de INSERT,UPDATE,DELETE
            escrita.insert(
                DatabaseHelper.TABELA_PRODUTOS,
                null,
                valores
            )

            Log.i("info_db", "Sucesso ao Inserir item na Tabela ") // log do sistema

        } catch (e: Exception) {
            Log.i("info_db", "Falha ao inserir item na Tabela ")

            return false
        }
        return true
    }

    override fun atualizar(produto: Produto): Boolean {
        /*val titulo = binding.editProduto.text.toString()
        val item = binding.spinnerLista.selectedItem
        val posicao = binding.spinnerLista.selectedItemPosition

        val sql =
            "UPDATE ${DatabaseHelper.TABELA_PRODUTOS} SET ${DatabaseHelper.TITULO} = '$titulo' WHERE ${DatabaseHelper.ID_PRODUTO} = '${item}'" // COMANDO UPDATE para atualizar itens
*/

        val valores = ContentValues()
        valores.put("${DatabaseHelper.TITULO}", produto.nome)
        valores.put("${DatabaseHelper.DESCRICAO}", produto.descricao)
        val args = arrayOf(produto.id_produto.toString())
        try {
            //bancoDados.writableDatabase.execSQL(sql)//whitableDatabase é utilizado para comandos de INSERT,UPDATE,DELETE
            escrita.update(
                DatabaseHelper.TABELA_PRODUTOS,
                valores,
                "id_produto = ?",
                args
            )

            Log.i("info_db", "Sucesso ao Atualizar item da Tabela ") // log do sistema

        } catch (e: Exception) {
            Log.i("info_db", "Falha ao Atualizar item da Tabela ")

            return false
        }
        return true
    }

    override fun remover(idProduto: Int): Boolean {
/*
        val item = binding.spinnerLista.selectedItem
        val posicao = binding.spinnerLista.selectedItemPosition

        val sql =
            "DELETE FROM ${DatabaseHelper.TABELA_PRODUTOS} WHERE ${DatabaseHelper.ID_PRODUTO} = '${item}'" // COMANDO UPDATE para atualizar itens
*/
        val args = arrayOf(idProduto.toString())

        try {
            escrita.delete(
                DatabaseHelper.TABELA_PRODUTOS,
                "id_produto = ?",
                args

            )


            Log.i("info_db", "Sucesso ao Remover Item Tabela ") // log do sistema

        } catch (e: Exception) {
            Log.i("info_db", "Falha ao Remover Tabela ")

            return false
        }
        return true
    }

    override fun listar(): List<Produto> {
        val listaProdutos = mutableListOf<Produto>()

        val sql =
            "SELECT * FROM ${DatabaseHelper.TABELA_PRODUTOS}" // linha de comando SQL para selecionar todos os itens da tabela Produtos
        @SuppressLint("Recycle")
        val cursor = leitura. // usado para selecionar(ler) dados
        rawQuery(sql, null)

        val indiceId = cursor.getColumnIndex(DatabaseHelper.ID_PRODUTO)
        val indiceTitulo = cursor.getColumnIndex(DatabaseHelper.TITULO)
        val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.DESCRICAO)
        var resultado = ""

        while (cursor.moveToNext()) {

            val idProduto =
                cursor.getInt(indiceId) // recupera o indice na coluna determinada, no caso coluna 1 no tipo INT
            val titulo =
                cursor.getString(indiceTitulo) // recupera o indice na coluna determinada, no caso coluna 2 no tipo String
            val descricao = cursor.getString(indiceDescricao)

            Log.i("info_db", "id: $idProduto titulo: $titulo ")
            listaProdutos.add(
                Produto(idProduto, titulo, descricao)
            )

            resultado += "id: $idProduto titulo: $titulo\n" // contatena os resultados para visualizar em um text


        }

        return listaProdutos

    }


}