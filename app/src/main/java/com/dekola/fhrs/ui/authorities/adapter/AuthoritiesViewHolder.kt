package com.dekola.fhrs.ui.authorities.adapter

import androidx.recyclerview.widget.RecyclerView
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.databinding.AuthorityItemBinding

class AuthoritiesViewHolder(
    private val binding: AuthorityItemBinding,
    private val viewAuthorities: (AuthoritiesPresentation?) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun bindItem(authoritiesPresentation: AuthoritiesPresentation?) {
        binding.authorityNameTextView.text = authoritiesPresentation?.name
        viewAuthorities.invoke(authoritiesPresentation)
    }

}