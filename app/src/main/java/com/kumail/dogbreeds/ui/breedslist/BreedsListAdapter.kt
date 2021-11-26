package com.kumail.dogbreeds.ui.breedslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kumail.dogbreeds.R
import com.kumail.dogbreeds.data.model.BreedItem
import com.kumail.dogbreeds.databinding.ItemBreedBinding
import com.kumail.dogbreeds.util.enableTopDivider
import com.kumail.dogbreeds.util.navigateTo
import com.kumail.dogbreeds.util.rotate
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by kumailhussain on 12/10/2021.
 */
@Singleton
class BreedsListAdapter @Inject constructor() :
    ListAdapter<BreedItem, BreedsListAdapter.ViewHolder>(BreedListDiffCallback),
    Filterable {

    private var breeds = emptyList<BreedItem>()
    private var filterBreeds = mutableListOf<BreedItem>()
    private var onItemClick: ((String) -> Unit)? = null
    private var expanded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_breed,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val breedItem = getItem(position)
        holder.bind(breedItem)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                filterBreeds = if (charSearch.isEmpty()) {
                    breeds.toMutableList()
                } else {
                    val resultList = mutableListOf<BreedItem>()
                    for (item in breeds) {
                        if (item.breed.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList.addAll(breeds.filter { it.breed == item.breed })
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterBreeds
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterBreeds = results?.values as MutableList<BreedItem>
                submitList(filterBreeds)
            }
        }
    }

    fun setBreedsList(items: List<BreedItem>) {
        breeds = items
        filterBreeds.clear()
        filterBreeds.addAll(breeds)
        submitList(breeds)
    }

    fun setOnItemClickListener(onClick: (String) -> Unit) {
        onItemClick = onClick
    }

    inner class ViewHolder(private val binding: ItemBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var subBreedsListAdapter: SubBreedsListAdapter

        init {
            setupSubBreeds(binding.rvSubBreeds)
        }

        fun bind(breedItem: BreedItem) {
            val breed = breedItem.breed
            val subBreedList = breedItem.subBreed
            binding.breed = breed

            if (subBreedList.isNotEmpty()) {
                expanded = false
                binding.ivDropDownArrow.isVisible = true
                binding.isExpanded = expanded
                subBreedsListAdapter.setBreed(breed)
                subBreedsListAdapter.submitList(subBreedList)
                binding.root.setOnClickListener {
                    if (!expanded) {
                        binding.ivDropDownArrow.rotate()
                        expanded = true
                        binding.isExpanded = expanded
                    } else {
                        binding.ivDropDownArrow.rotate()
                        expanded = false
                        binding.isExpanded = expanded
                    }
                }
            } else {
                binding.ivDropDownArrow.isVisible = false
                binding.isExpanded = false
                binding.root.setOnClickListener {
                    onItemClick?.let {
                        it(breed)
                    }
                }
            }
        }

        private fun setupSubBreeds(listView: RecyclerView) {
            subBreedsListAdapter = SubBreedsListAdapter { breed, subBreed ->
                navigateToSubBreedImages(breed, subBreed, binding.root)
            }
            listView.layoutManager = LinearLayoutManager(binding.root.context)
            listView.adapter = subBreedsListAdapter
            listView.enableTopDivider()
        }

        private fun navigateToSubBreedImages(breed: String, subBreed: String, view: View) {
            view.navigateTo(
                BreedsListFragmentDirections.actionBreedsListToBreedImages(
                    breed,
                    subBreed
                )
            )
        }
    }
}

object BreedListDiffCallback : DiffUtil.ItemCallback<BreedItem>() {
    override fun areItemsTheSame(oldItem: BreedItem, newItem: BreedItem): Boolean {
        return oldItem.breed == newItem.breed
    }

    override fun areContentsTheSame(oldItem: BreedItem, newItem: BreedItem): Boolean {
        return oldItem == newItem
    }
}