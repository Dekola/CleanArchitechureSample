package com.dekola.fhrs.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dekola.fhrs.R
import com.dekola.fhrs.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAuthorities.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AuthoritiesFragment)
        }

        binding.buttonPaginatedAuthorities.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AuthoritiesPaginatedFragment)
        }

        binding.buttonFlowAuthorities.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_AuthoritiesFlowFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}