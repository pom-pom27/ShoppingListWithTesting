package com.example.shoopinglistwithtesting.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shoopinglistwithtesting.R
import com.example.shoopinglistwithtesting.databinding.FragmentAddShoppingItemBinding
import com.example.shoopinglistwithtesting.ui.viewmodels.ShoppingViewModel

class AddShoppingFragment : Fragment(R.layout.fragment_add_shopping_item) {

    val viewModel: ShoppingViewModel by activityViewModels()

    private var _binding: FragmentAddShoppingItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddShoppingItemBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivShoppingImage.setOnClickListener {
            findNavController().navigate(R.id.action_addShoppingFragment_to_imagePickFragment)
        }

        val onBackPressCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.setCurImage("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressCallback
        )

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
