package com.example.shoopinglistwithtesting.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoopinglistwithtesting.R
import com.example.shoopinglistwithtesting.databinding.FragmentShoppingBinding
import com.example.shoopinglistwithtesting.ui.viewmodels.ShoppingViewModel

class ShoppingListFragment : Fragment(R.layout.fragment_shopping) {

    private val viewModel: ShoppingViewModel by activityViewModels()

    private var _binding: FragmentShoppingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.fabAddShoppingItem.setOnClickListener {
            findNavController().navigate(R.id.action_shoppingListFragment_to_addShoppingFragment)
        }

        //        subscribeToObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
