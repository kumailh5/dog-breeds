package com.kumail.dogbreeds.ui.breedslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kumail.dogbreeds.R
import com.kumail.dogbreeds.databinding.ItemSubBreedBinding
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by kumailhussain on 12/10/2021.
 */
@Singleton
class SubBreedsListAdapter @Inject constructor() :
    RecyclerView.Adapter<SubBreedsListAdapter.ViewHolder>() {

    private lateinit var breed: String
    private var subBreeds = emptyList<String>()
    private var onItemClick: ((String, String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_sub_breed,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(subBreeds[position])
    }

    override fun getItemCount(): Int {
        return subBreeds.size
    }

    fun setBreed(breed: String) {
        this.breed = breed
    }

    fun setSubBreedsList(items: List<String>) {
        subBreeds = items
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(onClick: (String, String) -> Unit) {
        onItemClick = onClick
    }

    inner class ViewHolder(private val binding: ItemSubBreedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subBreed: String) {
            binding.subBreed = subBreed
            binding.root.setOnClickListener {
                onItemClick?.let {
                    it(breed, subBreed)
                }
            }
        }
    }
}