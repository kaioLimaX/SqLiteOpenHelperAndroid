package com.skydevices.sqliteopenhelperandroid.database

import android.content.Context
import com.skydevices.sqliteopenhelperandroid.model.Produto

interface iProdutoDAO {

    fun salvar (produto: Produto): Boolean
    fun atualizar (produto: Produto): Boolean
    fun remover (idProduto : Int): Boolean
    fun listar(): List<Produto>
}