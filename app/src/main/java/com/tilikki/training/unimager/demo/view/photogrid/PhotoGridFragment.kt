package com.tilikki.training.unimager.demo.view.photogrid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tilikki.training.unimager.demo.databinding.FragmentPhotoGridBinding
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.view.main.PhotoRecyclerViewAdapter
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PhotoGridFragment : DaggerFragment() {
    @Inject
    lateinit var viewModel: PhotoGridViewModel

    private lateinit var binding: FragmentPhotoGridBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val photoList = it.getParcelableArray(PHOTO_LIST)?.map { parcel ->
                parcel as Photo
            }
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

        viewModel.photos.observe(viewLifecycleOwner, {
            (binding.rvPhotosGrid.adapter as PhotoRecyclerViewAdapter).submitList(it)
        })
        return binding.root
    }

    companion object {
        const val PHOTO_LIST = "PHOTO_LIST"

        @JvmStatic
        fun newInstance(photoList: List<Photo>?): PhotoGridFragment {
            return PhotoGridFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray(PHOTO_LIST, photoList?.toTypedArray())
                }
            }
        }
    }
}