package com.dekola.fhrs.ui.authorities.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.databinding.AuthorityItemBinding

class AuthoritiesAdapter(private val viewAuthorities: (AuthoritiesPresentation?) -> Unit) :
    ListAdapter<AuthoritiesPresentation, AuthoritiesViewHolder>(AuthoritiesDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthoritiesViewHolder {
        val binding =
            AuthorityItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AuthoritiesViewHolder(binding, viewAuthorities)
    }

    override fun onBindViewHolder(holder: AuthoritiesViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }
}

class AuthoritiesDiffUtil : DiffUtil.ItemCallback<AuthoritiesPresentation>() {
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