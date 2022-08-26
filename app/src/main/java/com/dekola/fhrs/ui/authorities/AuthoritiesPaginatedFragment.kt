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
import com.dekola.fhrs.ui.authorities.adapter.AuthoritiesPagingDataAdapter
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@AndroidEntryPoint
class AuthoritiesPaginatedFragment : Fragment() {

    private val authoritiesViewModel: AuthoritiesViewModel by viewModels()

    private var _binding: FragmentAuthoritiesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val authoritiesPagingDataAdapter by lazy { AuthoritiesPagingDataAdapter(::viewAuthorities) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View/**/ {
        _binding = FragmentAuthoritiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authoritiesPagingDataAdapter.addLoadStateListener { loadState ->
            authoritiesViewModel.manageLoadStates(loadState)
        }

        setViews()
        setObservers()
    }

    private fun setObservers() {
        with(authoritiesViewModel) {
            getAuthoritiesPagination()

            authoritiesPaginationLiveData.observe(viewLifecycleOwner) { movieResponse ->
//                binding.progressBar.visibility = View.GONE
                authoritiesPagingDataAdapter.submitData(lifecycle, movieResponse)
            }

            loadLiveData.observe(viewLifecycleOwner) { load ->
                binding.progressBar.visibility = if (load) View.VISIBLE else View.GONE
            }

            toastLiveData.observe(viewLifecycleOwner) { toastWrapper ->
                showToast(getString(toastWrapper.message))
            }

            showError.observe(viewLifecycleOwner) { errorMessage ->
                showToast(errorMessage)
            }
        }
    }

    private fun setViews() {
        with(binding) {
            authoritiesRecycleView.adapter = authoritiesPagingDataAdapter
        }
    }

    private fun viewAuthorities(authoritiesPresentation: AuthoritiesPresentation?) {

    }

    private fun showToast(errorMessage: String?) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}