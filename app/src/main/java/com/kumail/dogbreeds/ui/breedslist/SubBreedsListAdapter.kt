package com.kumail.dogbreeds.ui.breedslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kumail.dogbreeds.R
import com.kumail.dogbreeds.databinding.ItemSubBreedBinding

/**
 * Created by kumailhussain on 12/10/2021.
 */
class SubBreedsListAdapter (private val onClick: (String, String) -> Unit) :
    ListAdapter<String, SubBreedsListAdapter.ViewHolder>(SubBreedListDiffCallback) {

    private lateinit var breed: String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_sub_breed,
                parent,
                false
            ), onClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subBreed = getItem(position)
        holder.bind(subBreed)
    }

    fun setBreed(breed: String) {
        this.breed = breed
    }

    inner class ViewHolder(
        private val binding: ItemSubBreedBinding,
        val onClick: (String, String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private var currentSubBreed: String? = null

        init {
            binding.root.setOnClickListener {
                currentSubBreed?.let { subBreed -> onClick(breed, subBreed) }
            }
        }

        fun bind(subBreed: String) {
            currentSubBreed = subBreed
            binding.subBreed = subBreed
        }
    }
}

object SubBreedListDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}