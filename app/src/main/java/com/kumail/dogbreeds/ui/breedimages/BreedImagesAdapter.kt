package com.kumail.dogbreeds.ui.breedimages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kumail.dogbreeds.R
import com.kumail.dogbreeds.databinding.ItemBreedImageBinding
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by kumailhussain on 12/10/2021.
 */
@Singleton
class BreedImagesAdapter @Inject constructor() :
    RecyclerView.Adapter<BreedImagesAdapter.ViewHolder>() {

    private var breedImageUrls = emptyList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_breed_image, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(breedImageUrls[position])
    }

    override fun getItemCount(): Int {
        return breedImageUrls.size
    }

    fun setBreedImageUrls(items: List<String>) {
        breedImageUrls = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBreedImageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(imageUrl: String) {
            binding.breedImageUrl = imageUrl
        }
    }
}