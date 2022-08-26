package com.dekola.fhrs.ui.authorities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.databinding.AuthorityItemBinding

class AuthoritiesPagingDataAdapter(private val onItemClicked: (AuthoritiesPresentation?) -> Unit) :
    PagingDataAdapter<AuthoritiesPresentation, AuthoritiesViewHolder>(MovieListDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthoritiesViewHolder {
        val binding = AuthorityItemBinding.inflate(LayoutInflater.from(parent.context))
        return AuthoritiesViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: AuthoritiesViewHolder, position: Int) {
        getItem(position)?.let { holder.bindItem(it) }
    }

    object MovieListDiff : DiffUtil.ItemCallback<AuthoritiesPresentation>() {
        override fun areItemsTheSame(
            oldItem: AuthoritiesPresentation,
            newItem: AuthoritiesPresentation,
        ): Boolean {
            return false
        }

        override fun areContentsTheSame(
            oldItem: AuthoritiesPresentation,
            newItem: AuthoritiesPresentation,
        ): Boolean {
            return false
        }
    }
}
