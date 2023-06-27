package org.android.go.sopt.presentation.main.music

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.android.go.sopt.R
import org.android.go.sopt.data.model.ResGetMusicListDto
import org.android.go.sopt.databinding.FragmentMusicBinding
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.presentation.main.home.HomeAdapter
import org.android.go.sopt.util.BindingFragment
import org.android.go.sopt.util.showToast

class MusicFragment : BindingFragment<FragmentMusicBinding>(R.layout.fragment_music) {
    private val mainVm: MainViewModel by activityViewModels()
    private val musicVm: MusicViewModel by viewModels()

    private lateinit var rv: RecyclerView
    private lateinit var musicAdapter: MusicAdapter

    private val pickMedia =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) musicVm.setSelImg(uri)
            else requireContext().showToast("Please select photo.")
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = musicVm
        displayScreen()
        registerClickEvents()
        registerObservers()
    }

    private fun displayScreen() {
        mainVm.setDialogFlag(true)
        musicVm.getMusicList(requireContext(), mainVm)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv = binding.rv
        musicAdapter = MusicAdapter()
        rv.adapter = musicAdapter
    }

    private fun registerClickEvents() {
        binding.btnSelectPhoto.setOnClickListener {
            pickMedia.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
            )
        }

        binding.btnUpload.setOnClickListener {
            if (musicVm.title.value.isNullOrEmpty() || musicVm.singer.value.isNullOrEmpty())
                requireContext().showToast("Please fill in the empty blanks.")
            else {
                mainVm.setDialogFlag(true)
                musicVm.uploadMusic(requireContext(), mainVm)
            }
        }
    }

    private fun registerObservers() {
        musicVm.selImg.observe(viewLifecycleOwner) {
            with(binding.ivAlbum) {
                if (it == null) setImageResource(R.color.mint_10)
                else load(it)
            }
        }

        musicVm.musicList.observe(viewLifecycleOwner) {
            Log.e("ABC", "[MusicFrag-observer] you got the list!")
            musicAdapter.submitList(it.reversed().toMutableList())
        }

        musicAdapter.registerAdapterDataObserver(object: RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                Log.e("ABC", "onChanged()")
                binding.rv.scrollToPosition(0)
            }
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                Log.e("ABC", "onItemRangeInserted()")
                binding.rv.scrollToPosition(0)
            }
        })
    }
}