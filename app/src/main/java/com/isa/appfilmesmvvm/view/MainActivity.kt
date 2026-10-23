package com.isa.appfilmesmvvm.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.isa.appfilmesmvvm.databinding.ActivityMainBinding
import com.isa.appfilmesmvvm.viewmodel.FavoritosViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: FavoritosViewModel by viewModels()
    private lateinit var adapter: FavoritosAdapter
    private var idEmEdicao: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = FavoritosAdapter(emptyList()) { favorito ->
            idEmEdicao = favorito.id
            binding.campoNome.setText(favorito.nome)
            binding.campoNota.setText(favorito.nota.toString())
            binding.campoLinkFoto.setText(favorito.linkFoto)
            binding.botaoGravar.text = "Atualizar Favorito"
        }

        binding.listaFavoritos.layoutManager = LinearLayoutManager(this)
        binding.listaFavoritos.adapter = adapter

        viewModel.filmesFavoritos.observe(this) { dados ->
            adapter.recarregar(dados)
        }

        binding.botaoGravar.setOnClickListener {
            val nome = binding.campoNome.text.toString().trim()
            val notaStr = binding.campoNota.text.toString().trim().replace(",", ".")
            val linkFoto = binding.campoLinkFoto.text.toString().trim()

            if (nome.isEmpty() || notaStr.isEmpty()) {
                Toast.makeText(this, "Digite o nome e a nota do filme", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val nota = notaStr.toDoubleOrNull()
            if (nota == null) {
                Toast.makeText(this, "Valor de nota inválido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val idAtual = idEmEdicao
            if (idAtual == null) {
                viewModel.inserirFilme(nome, nota, linkFoto)
            } else {
                viewModel.modificarFilme(idAtual, nome, nota, linkFoto)
                idEmEdicao = null
                binding.botaoGravar.text = "Salvar Favorito"
            }

            binding.campoNome.text.clear()
            binding.campoNota.text.clear()
            binding.campoLinkFoto.text.clear()
        }
    }
}
