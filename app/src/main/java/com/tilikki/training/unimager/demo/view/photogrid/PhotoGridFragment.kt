package com.tilikki.training.unimager.demo.view.photogrid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tilikki.training.unimager.demo.databinding.FragmentPhotoGridBinding
import com.tilikki.training.unimager.demo.model.PageMetadata
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.view.photogrid.scroll.FetchableEndlessScrollRecyclerListener
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PhotoGridFragment : DaggerFragment() {
    @Inject
    lateinit var viewModel: PhotoGridViewModel

    private lateinit var binding: FragmentPhotoGridBinding
    private lateinit var pageMetadata: PageMetadata
    private var nestedScroll: Boolean = false
    private var scrollListener: FetchableEndlessScrollRecyclerListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val photoList = it.getParcelableArray(PHOTO_LIST)?.map { parcel ->
                parcel as Photo
            }
            pageMetadata = it.getParcelable(PAGE_METADATA) ?: defaultPageMetadata
            nestedScroll = it.getBoolean(NESTED_SCROLL)
            viewModel.postPhotos(photoList)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotoGridBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.rvPhotosGrid.adapter = PhotoRecyclerViewAdapter()
        binding.rvPhotosGrid.layoutManager = PhotoRecyclerViewAdapter.getPhotoGridLayoutManager()
        binding.rvPhotosGrid.setHasFixedSize(true)

        if (!nestedScroll) {
            scrollListener = FetchableEndlessScrollRecyclerListener(
                binding.rvPhotosGrid.layoutManager as StaggeredGridLayoutManager, pageMetadata
            ).also {
                binding.rvPhotosGrid.addOnScrollListener(it)
            }
        }

        viewModel.photos.observe(viewLifecycleOwner, {
            (binding.rvPhotosGrid.adapter as PhotoRecyclerViewAdapter).submitList(it)
        })
        return binding.root
    }

    fun setPhotoList(photoList: List<Photo>?) {
        viewModel.postPhotos(photoList)
        scrollListener?.resetState()
    }

    companion object {
        const val PHOTO_LIST = "PHOTO_LIST"
        const val PAGE_METADATA = "PAGE_METADATA"
        const val NESTED_SCROLL = "NESTED_SCROLL"
        private val defaultPageMetadata = PageMetadata(0)

        @JvmStatic
        fun newInstance(
            photoList: List<Photo>?,
            pageMetadata: PageMetadata? = defaultPageMetadata,
            nestedScroll: Boolean = false
        ): PhotoGridFragment {
            return PhotoGridFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray(PHOTO_LIST, photoList?.toTypedArray())
                    putParcelable(PAGE_METADATA, pageMetadata ?: defaultPageMetadata)
                    putBoolean(NESTED_SCROLL, nestedScroll)
                }
            }
        }
    }
}
