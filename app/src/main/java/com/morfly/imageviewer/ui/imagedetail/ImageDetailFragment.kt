package com.morfly.imageviewer.ui.imagedetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.morfly.imageviewer.R
import com.morfly.imageviewer.imageloader.ImageLoader
import com.morfly.imageviewer.imageloader.ImageMemoryCacheManagerDefault

class ImageDetailFragment : Fragment() {

    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private var imageLoader: ImageLoader = ImageLoader(cacheManager = ImageMemoryCacheManagerDefault())
    private val url by lazy { arguments?.getString(ARG_URL)!! }
    private val name by lazy { arguments?.getString(ARG_NAME)!! }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_image_detail, container, false)

        imageView = view.findViewById(R.id.image)
        titleView = view.findViewById(R.id.title)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageLoader.load(url).into(imageView)
        titleView.text = name
    }

    companion object {

        private const val ARG_URL = "ARG_URL"
        private const val ARG_NAME = "ARG_NAME"

        fun newInstance(url: String, name: String) = ImageDetailFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_URL, url)
                putString(ARG_NAME, name)
            }
        }
    }
}