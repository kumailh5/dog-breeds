package com.kumail.dogbreeds.ui.breedslist

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kumail.dogbreeds.R
import com.kumail.dogbreeds.databinding.FragmentBreedsListBinding
import com.kumail.dogbreeds.util.navigateTo
import com.kumail.dogbreeds.util.setToolbarTitle
import com.kumail.dogbreeds.util.showErrorDialog
import com.kumail.dogbreeds.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by kumailhussain on 12/10/2021.
 */
@AndroidEntryPoint
class BreedsListFragment : Fragment() {

    @Inject
    lateinit var breedsListAdapter: BreedsListAdapter

    private val mainViewModel: MainViewModel by viewModels()

    private var _binding: FragmentBreedsListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBreedsListBinding.inflate(layoutInflater)

        this.setToolbarTitle(getString(R.string.app_name))
        setupBreedsList(binding.rvBreeds)
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_toolbar, menu)

        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.queryHint = getString(R.string.search)
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                breedsListAdapter.filter.filter(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setupObservers() {
        mainViewModel.errorMessage.observe(viewLifecycleOwner, { errorRes ->
            showErrorDialog(requireContext(), errorRes)
        })

        mainViewModel.breedsList.observe(viewLifecycleOwner, {
            breedsListAdapter.setBreedsList(it)
        })
    }

    private fun setupBreedsList(listView: RecyclerView) {
        breedsListAdapter.setOnItemClickListener { breed -> navigateToBreedImages(breed) }
        listView.layoutManager = LinearLayoutManager(requireContext())
        listView.adapter = breedsListAdapter
        listView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    private fun navigateToBreedImages(breed: String) {
        this.navigateTo(BreedsListFragmentDirections.actionBreedsListToBreedImages(breed, null))
    }
}