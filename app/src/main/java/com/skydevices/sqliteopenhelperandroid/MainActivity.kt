package com.skydevices.sqliteopenhelperandroid

import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skydevices.sqliteopenhelperandroid.database.DatabaseHelper
import com.skydevices.sqliteopenhelperandroid.database.ProdutoDAO
import com.skydevices.sqliteopenhelperandroid.databinding.ActivityMainBinding
import com.skydevices.sqliteopenhelperandroid.model.Produto

class MainActivity : AppCompatActivity() {


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
        val item: Int = binding.spinnerLista.selectedItem.toString().toInt()
        val produtoDAO = ProdutoDAO(this)


        if (produtoDAO.remover(item)) {
            Toast.makeText(this, "Sucesso ao Remover item ", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Falha ao Remover item ", Toast.LENGTH_SHORT).show()
        }


    }

    private fun atualizar() {


        val titulo = binding.editProduto.text.toString()
        val item: Int = binding.spinnerLista.selectedItem.toString().toInt()
        // val posicao = binding.spinnerLista.selectedItemPosition
        val produtoDAO = ProdutoDAO(this)
        val produto = Produto(
            item, titulo, "descricao..."
        )

        if (produtoDAO.atualizar(produto)) {
            Toast.makeText(this, "Sucesso ao Atualizar item ", Toast.LENGTH_SHORT).show()

        } else {
            Toast.makeText(this, "Falha ao Atualizar item ", Toast.LENGTH_SHORT).show()
        }


    }

    @SuppressLint("Recycle")
    private fun listar() {

        var resultado = ""
        val produtoDAO = ProdutoDAO(this)
        val listaProdutos = produtoDAO.listar()

        if (listaProdutos.isNotEmpty()) {
            listaProdutos.forEach { produto ->
                Log.i("info_db", "${produto.nome} + ${produto.descricao} ")

                resultado += "${produto.nome} + ${produto.descricao}\n" // concatena os resultados para visualizar em um text


            }

            //spinner
            val adapter = ArrayAdapter(
                this,
                R.layout.simple_spinner_item,
                listaProdutos.map { produto -> "${produto.id_produto}" })
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)

            // Aplicar o adapter ao Spinner
            binding.spinnerLista.adapter = adapter
            binding.txtResultado.text = resultado
        }


    }

    private fun salvar() {
        val titulo = binding.editProduto.text.toString()
        val produtoDAO = ProdutoDAO(this)
        val produto = Produto(
            -1,
            titulo,
            "descricao.."
        )
        if (produtoDAO.salvar(produto)) {
            Toast.makeText(this, "Sucesso ao inserir item", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Falha ao inserir item ", Toast.LENGTH_SHORT).show()
        }
    }
}