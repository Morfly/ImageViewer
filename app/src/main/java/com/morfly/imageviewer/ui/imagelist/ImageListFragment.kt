package com.morfly.imageviewer.ui.imagelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.morfly.imageviewer.R
import com.morfly.imageviewer.domain.image.Image
import com.morfly.imageviewer.ui.lib.EndlessRecyclerViewScrollListener

class ImageListFragment : Fragment(), ImagesListContract.View, View.OnClickListener {

    private val provider = ImageListComponentProvider()

    private val imageListAdapter = provider.imageListAdapter
    private val presenter = provider.presenter

    private lateinit var etSearch: EditText
    private lateinit var btnSearch: ImageView
    private lateinit var progress: View
    private lateinit var textNoImages: View

    private lateinit var onScrollListener: EndlessRecyclerViewScrollListener

    @get:LayoutRes
    private val layoutRes
        get() = R.layout.fragment_images_list


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(layoutRes, container, false)

        progress = root.findViewById<View>(R.id.progress)
        etSearch = root.findViewById(R.id.et_search)
        btnSearch = root.findViewById<ImageView>(R.id.btn_search).apply {
            setOnClickListener(this@ImageListFragment)
        }
        textNoImages = root.findViewById<View>(R.id.text_no_images)

        root.findViewById<RecyclerView>(R.id.list_images)
            .apply {
                adapter = imageListAdapter
                val lm = GridLayoutManager(context, presenter.numberOfListColumns)
                layoutManager = lm

                onScrollListener = object : EndlessRecyclerViewScrollListener(lm) {
                    override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                        presenter.loadMoreImages(page)
                    }
                }
                addOnScrollListener(onScrollListener)
            }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.view = this
    }

    override fun onClick(v: View?) {
        when (v) {
            btnSearch -> {
                if (etSearch.text.isNotEmpty()) {
                    onScrollListener.resetState()
                    presenter.loadImages(etSearch.text.toString())
                }
            }
        }
    }


    override fun displayImages(images: List<Image>) {
        imageListAdapter.setImages(images)
    }

    override fun displayMoreImages(images: List<Image>) {
        imageListAdapter.addImages(images)
    }

    override fun hidePlaceholder() {
        textNoImages.visibility = GONE
    }

    override fun displayMessage(messageStringRes: Int) {
        Toast.makeText(context, messageStringRes, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progress.visibility = VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.view = null
    }


    companion object {

        fun newInstance() = ImageListFragment()
    }
}