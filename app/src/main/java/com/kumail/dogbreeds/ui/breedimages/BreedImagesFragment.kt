package com.kumail.dogbreeds.ui.breedimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kumail.dogbreeds.R
import com.kumail.dogbreeds.databinding.FragmentBreedImagesBinding
import com.kumail.dogbreeds.util.setToolbarTitle
import com.kumail.dogbreeds.util.showErrorDialog
import com.kumail.dogbreeds.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by kumailhussain on 12/10/2021.
 */
@AndroidEntryPoint
class BreedImagesFragment : Fragment() {

    @Inject
    lateinit var breedImagesAdapter: BreedImagesAdapter

    private val args: BreedImagesFragmentArgs by navArgs()
    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentBreedImagesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_breed_images, container, false)

        setupDataBinding()
        setupToolbar()
        setupBreedImages(binding.rvBreedImages)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupDataBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = mainViewModel
    }

    private fun setupToolbar() {
        if (args.subBreed == null) {
            this.setToolbarTitle(args.breed)
        } else {
            this.setToolbarTitle(String.format("%s %s", args.subBreed, args.breed))
        }
    }

    private fun setupObservers() {
        mainViewModel.errorMessage.observe(viewLifecycleOwner, { errorRes ->
            showErrorDialog(requireContext(), errorRes)
        })

        if (args.subBreed == null) {
            mainViewModel.getBreedRandomImages(args.breed)
            mainViewModel.breedImageUrls.observe(viewLifecycleOwner, {
                breedImagesAdapter.setBreedImageUrls(it)
            })
        } else {
            mainViewModel.getSubBreedRandomImages(args.breed, args.subBreed!!)
            mainViewModel.breedImageUrls.observe(viewLifecycleOwner, {
                breedImagesAdapter.setBreedImageUrls(it)
            })
        }
    }

    private fun setupBreedImages(listView: RecyclerView) {
        listView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        listView.adapter = breedImagesAdapter
    }
}