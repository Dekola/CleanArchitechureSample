package com.dekola.fhrs.ui.authorities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dekola.fhrs.data.model.AuthoritiesPresentation
import com.dekola.fhrs.databinding.FragmentAuthoritiesBinding
import com.dekola.fhrs.ui.authorities.adapter.AuthoritiesAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@AndroidEntryPoint
class AuthoritiesFragment : Fragment() {

    private val authoritiesViewModel: AuthoritiesViewModel by viewModels()

    private var _binding: FragmentAuthoritiesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val authoritiesAdapter by lazy { AuthoritiesAdapter(::viewAuthorities) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAuthoritiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setObservers() {
        with(authoritiesViewModel) {
            getAuthorities()
            authoritiesResult.observe(viewLifecycleOwner) { authoritiesResult ->
                authoritiesResult.success?.let { authorities ->
                    authoritiesAdapter.submitList(authorities)
                } ?: kotlin.run {
                    showToast(authoritiesResult.errorMessage)
                }
            }

            loadLiveData.observe(viewLifecycleOwner) { load ->
                binding.progressBar.visibility = if (load) View.VISIBLE else View.GONE
            }
        }
    }

    private fun setViews() {
        with(binding) {
            authoritiesRecycleView.adapter = authoritiesAdapter
        }
    }

    private fun viewAuthorities(authoritiesPresentation: AuthoritiesPresentation?){

    }

    private fun showToast(errorMessage: String?) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}