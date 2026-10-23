package com.isa.appfilmesmvvm.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.isa.appfilmesmvvm.R
import com.isa.appfilmesmvvm.databinding.ItemFilmeBinding
import com.isa.appfilmesmvvm.model.FilmeFavorito

class FavoritosAdapter(
    private var lista: List<FilmeFavorito>,
    private val acaoEditar: (FilmeFavorito) -> Unit
) : RecyclerView.Adapter<FavoritosAdapter.FavoritoViewHolder>() {

    class FavoritoViewHolder(val binding: ItemFilmeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritoViewHolder {
        val binding = ItemFilmeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritoViewHolder, position: Int) {
        val filme = lista[position]
        holder.binding.lblNomeFilme.text = filme.nome
        holder.binding.lblNotaFilme.text = "★ Nota: ${filme.nota}"

        holder.binding.imgPoster.load(filme.linkFoto) {
            crossfade(true)
            placeholder(R.mipmap.ic_launcher)
            error(R.mipmap.ic_launcher)
        }

        holder.binding.botaoEditarItem.setOnClickListener {
            acaoEditar(filme)
        }
    }

    override fun getItemCount(): Int = lista.size

    fun recarregar(novaLista: List<FilmeFavorito>) {
        lista = novaLista
        notifyDataSetChanged()
    }
}
