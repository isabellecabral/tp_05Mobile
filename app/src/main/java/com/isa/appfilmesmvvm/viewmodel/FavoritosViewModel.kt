package com.isa.appfilmesmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isa.appfilmesmvvm.model.FilmeFavorito

class FavoritosViewModel : ViewModel() {

    private val repositorio = mutableListOf(
        FilmeFavorito(
            1,
            "A Viagem de Chihiro",
            9.7,
            "https://upload.wikimedia.org/wikipedia/pt/3/3b/Spirited_Away_poster.JPG"
        ),
        FilmeFavorito(
            2,
            "Parasita",
            9.4,
            "https://upload.wikimedia.org/wikipedia/pt/b/be/Parasite_poster.jpg"
        ),
        FilmeFavorito(
            3,
            "La La Land",
            9.0,
            "https://upload.wikimedia.org/wikipedia/pt/c/c0/La_La_Land_%28filme%29.png"
        )
    )

    private val _filmesFavoritos = MutableLiveData<List<FilmeFavorito>>(repositorio.toList())
    val filmesFavoritos: LiveData<List<FilmeFavorito>> = _filmesFavoritos

    private var contadorId = 4

    fun inserirFilme(nome: String, nota: Double, linkFoto: String) {
        val novo = FilmeFavorito(contadorId++, nome, nota, linkFoto)
        repositorio.add(novo)
        _filmesFavoritos.value = repositorio.toList()
    }

    fun modificarFilme(id: Int, nome: String, nota: Double, linkFoto: String) {
        val indice = repositorio.indexOfFirst { it.id == id }
        if (indice != -1) {
            repositorio[indice] = FilmeFavorito(id, nome, nota, linkFoto)
            _filmesFavoritos.value = repositorio.toList()
        }
    }
}
