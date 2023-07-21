package com.skydevices.sqliteopenhelperandroid

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydevices.sqliteopenhelperandroid.database.DatabaseHelper
import com.skydevices.sqliteopenhelperandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    data class Produto(val id_produto: Int,val nome: String, val descricao: String)

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val bancoDados by lazy { // carregamento preguiçoso do banco de dados  para a activity
        DatabaseHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding) {

            btnSalvar.setOnClickListener {
                salvar()
            }

            btnListar.setOnClickListener {
                listar()
            }

            btnAtualizar.setOnClickListener {
                atualizar()

            }

            btnRemover.setOnClickListener {
                remover()
            }

        }


    }



    private fun remover() {
        val item = binding.spinnerLista.selectedItem
        val posicao = binding.spinnerLista.selectedItemPosition

        val sql =
            "DELETE FROM ${DatabaseHelper.TABELA_PRODUTOS} WHERE ${DatabaseHelper.ID_PRODUTO} = '${item}'" // COMANDO UPDATE para atualizar itens

        try {
            bancoDados.writableDatabase.execSQL(sql)//whitableDatabase é utilizado para comandos de INSERT,UPDATE,DELETE

            Log.i("info_db", "Sucesso ao Remover Item Tabela ") // log do sistema
            Toast.makeText(this, "Sucesso ao Remover Tabela", Toast.LENGTH_SHORT)
                .show() // toast para o usuario
        } catch (e: Exception) {
            Log.i("info_db", "Falha ao Remover Tabela ")
            Toast.makeText(this, "Falha ao Remover Tabela", Toast.LENGTH_SHORT).show()
        }
    }

    private fun atualizar() {
        val titulo = binding.editProduto.text.toString()
        val item = binding.spinnerLista.selectedItem
        val posicao = binding.spinnerLista.selectedItemPosition
        Toast.makeText(this, "item $item + posicao ${posicao + 1}", Toast.LENGTH_SHORT).show()
        val sql =
            "UPDATE ${DatabaseHelper.TABELA_PRODUTOS} SET ${DatabaseHelper.TITULO} = '$titulo' WHERE ${DatabaseHelper.ID_PRODUTO} = '${item}'" // COMANDO UPDATE para atualizar itens

        try {
            bancoDados.writableDatabase.execSQL(sql)//whitableDatabase é utilizado para comandos de INSERT,UPDATE,DELETE

            Log.i("info_db", "Sucesso ao Atualizar item da Tabela ") // log do sistema
            Toast.makeText(this, "Sucesso ao Atualizar item da Tabela", Toast.LENGTH_SHORT)
                .show() // toast para o usuario
        } catch (e: Exception) {
            Log.i("info_db", "Falha ao Atualizar item da Tabela ")
            Toast.makeText(this, "Falha ao Atualizar item Tabela", Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("Recycle")
    private fun listar() {
        val listaProdutos = mutableListOf<Produto>()

        val sql =
            "SELECT * FROM ${DatabaseHelper.TABELA_PRODUTOS}" // linha de comando SQL para selecionar todos os itens da tabela Produtos

        val cursor = bancoDados.readableDatabase. // usado para selecionar(ler) dados
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

            resultado += "id: $idProduto titulo: $titulo\n" // contatena os resultados para visualizar em um text
            listaProdutos.add(Produto(idProduto, titulo, descricao))

        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listaProdutos.map { produto -> "${produto.id_produto}" })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Aplicar o adapter ao Spinner
        binding.spinnerLista.adapter = adapter
        binding.txtResultado.text = resultado
    }

    private fun salvar() {
        val titulo = binding.editProduto.text.toString()
        val sql = "INSERT INTO produtos VALUES( null, '$titulo','descricao..' )"

        try {
            bancoDados.writableDatabase.execSQL(sql)//whitableDatabase é utilizado para comandos de INSERT,UPDATE,DELETE

            Log.i("info_db", "Sucesso ao Inserir Tabela ") // log do sistema
            Toast.makeText(this, "Sucesso ao Inserir Tabela", Toast.LENGTH_SHORT)
                .show() // toast para o usuario
        } catch (e: Exception) {
            Log.i("info_db", "Falha ao criar Tabela ")
            Toast.makeText(this, "Falha ao Inserir Tabela", Toast.LENGTH_SHORT).show()
        }
    }
}