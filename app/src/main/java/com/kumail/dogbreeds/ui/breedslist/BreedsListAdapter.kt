package com.kumail.dogbreeds.ui.breedslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kumail.dogbreeds.R
import com.kumail.dogbreeds.databinding.ItemBreedBinding
import com.kumail.dogbreeds.util.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by kumailhussain on 12/10/2021.
 */
@Singleton
class BreedsListAdapter @Inject constructor() :
    RecyclerView.Adapter<BreedsListAdapter.ViewHolder>(), Filterable {

    private var breeds = emptyMap<String, List<String>>()
    private var filterBreeds = mutableMapOf<String, List<String>>()
    private var onItemClick: ((String) -> Unit)? = null
    private var expanded = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_breed, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filterBreeds.entries.elementAt(position)
        holder.bind(item.key, item.value)
    }

    override fun getItemCount(): Int {
        return filterBreeds.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    filterBreeds = breeds.toMutableMap()
                } else {
                    val resultList = mutableMapOf<String, List<String>>()
                    for (row in breeds) {
                        if (row.key.lowercase(Locale.ROOT)
                                .contains(charSearch.lowercase(Locale.ROOT))
                        ) {
                            resultList[row.key] = row.value
                        }
                    }
                    filterBreeds = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filterBreeds
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filterBreeds = results?.values as MutableMap<String, List<String>>
                notifyDataSetChanged()
            }
        }
    }

    fun setBreedsList(items: Map<String, List<String>>) {
        breeds = items
        filterBreeds.clear()
        filterBreeds.putAll(breeds)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onClick: (String) -> Unit) {
        onItemClick = onClick
    }

    inner class ViewHolder(private val binding: ItemBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @Inject
        lateinit var subBreedsListAdapter: SubBreedsListAdapter

        init {
            setupSubBreeds(binding.rvSubBreeds)
        }

        fun bind(breed: String, subBreedList: List<String>) {
            binding.breed = breed

            if (subBreedList.isNotEmpty()) {
                expanded = false
                binding.ivDropDownArrow.isVisible = true
                binding.isExpanded = expanded
                subBreedsListAdapter.setBreed(breed)
                subBreedsListAdapter.setSubBreedsList(subBreedList)
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
            subBreedsListAdapter = SubBreedsListAdapter()
            subBreedsListAdapter.setOnItemClickListener { breed, subBreed ->
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