package com.example.shoopinglistwithtesting.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.shoopinglistwithtesting.R
import com.example.shoopinglistwithtesting.databinding.FragmentImagePickBinding
import com.example.shoopinglistwithtesting.ui.viewmodels.ShoppingViewModel

class ImagePickFragment : Fragment(R.layout.fragment_image_pick) {

    private val viewModel: ShoppingViewModel by activityViewModels()

    private var _binding: FragmentImagePickBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagePickBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //        subscribeToObserver()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
