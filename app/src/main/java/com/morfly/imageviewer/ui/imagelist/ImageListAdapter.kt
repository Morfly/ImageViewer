package com.morfly.imageviewer.ui.imagelist

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.morfly.imageviewer.R
import com.morfly.imageviewer.domain.image.Image
import com.morfly.imageviewer.imageloader.ImageLoader
import com.morfly.imageviewer.imageloader.ImageLoadingPolicy

class ImageListAdapter(private val imageLoader: ImageLoader) :
    RecyclerView.Adapter<ImageListAdapter.ImageViewHolder>() {

    private val items = mutableListOf<Image>()

    var listener: OnImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
            .let(::ImageViewHolder)


    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.setImage(items[position])
    }

    override fun getItemCount(): Int = items.size


    fun addImages(images: List<Image>) {
        items.addAll(images)

        val startPositionToUpdate = items.size - images.size - 1
        notifyItemRangeChanged(startPositionToUpdate, images.size)
    }

    fun setImages(images: List<Image>) {
        items.clear()
        items.addAll(images)
        notifyDataSetChanged()

    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageView = itemView.findViewById<ImageView>(R.id.image)

        init {
            imageView.setOnClickListener {
                val image = items[adapterPosition]
                listener?.invoke(image.url, image.id)
            }
        }

        fun setImage(image: Image) {
            imageLoader
                .load(image.thumbUrl)
//                .loadingPolicy(ImageLoadingPolicy.NO_MEMORY_CACHE)
                .into(imageView)
        }
    }

    companion object {

        private val LOG_TAG = ImageListAdapter::class.java.simpleName
    }
}

typealias OnImageClickListener = (url: String, name: String) -> Unit