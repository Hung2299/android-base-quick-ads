package com.harison.core.app.ui.main.ui.home

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.harison.core.app.R
import com.harison.core.app.databinding.FragmentHomeBinding
import com.harison.core.app.domain.ItemSound
import com.harison.core.app.platform.BaseFragment
import com.harison.core.app.ui.main.MainViewModel
import com.harison.core.app.ui.main.ui.home.recyclerview.SoundAdapter
import com.harison.core.app.utils.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val shareViewModel: MainViewModel by activityViewModels()
    private lateinit var adapter: SoundAdapter
    private lateinit var mediaPlayer: MediaPlayer

    override val layoutId: Int
        get() = R.layout.fragment_home

    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun setupUI() {
        super.setupUI()
        mediaPlayer = MediaPlayer()
        binding.apply {
            rcvSound.adapter = adapter
        }
    }

    override fun setupData() {
        super.setupData()
        adapter = SoundAdapter()
        shareViewModel.data.observe(viewLifecycleOwner) { data ->
            val dataSound = arrayListOf<ItemSound>()
            data.forEach {
                dataSound.add(
                    ItemSound(
                        id = it.id,
                        soundUrl = it.sound_url,
                        name = it.name,
                        isHot = it.isHot?.isNotEmpty() ?: false
                    )
                )
            }
            adapter.updateItems(dataSound)
        }
    }

    @Suppress("DEPRECATION")
    override fun setupListener() {
        adapter.onClickItem = { item ->
            Timber.tag("----").d("Click Item")
            playSound(item.soundUrl)
        }
        binding.apply {
            bottomNav.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.settingFragment -> {
                        findNavController().navigate(R.id.settingFragment)
                        return@setOnNavigationItemSelectedListener true
                    }
                }
                return@setOnNavigationItemSelectedListener false
            }
        }
    }
    override fun onResume() {
        super.onResume()
        binding.bottomNav.menu.getItem(0).isChecked = true
    }

    private fun playSound(url: String) {
        mediaPlayer.reset()
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA).build()
        )
        try {
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepare()
            mediaPlayer.start()
            requireContext {
                Toast.makeText(it, "Audio started playing..", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Timber.e("----Play media error: ${e.printStackTrace()}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
