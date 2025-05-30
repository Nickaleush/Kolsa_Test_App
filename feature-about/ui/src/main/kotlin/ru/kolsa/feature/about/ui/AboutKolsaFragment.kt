package ru.kolsa.feature.about.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kolsa.core.ui.video.VideoView
import ru.kolsa.core.ui.video.VideoViewState
import ru.kolsa.core.ui.video.orientationManager
import ru.kolsa.feature.about.ui.databinding.AboutKolsaScreenBinding

class AboutKolsaFragment : Fragment(R.layout.about_kolsa_screen) {

    companion object {
        const val VIDEO_URL =
            "https://pouch.jumpshare.com/preview/hOLcngrxiuBYAAnsR_kMBpY2duGlNn7pwgfAkJeyReZDeYmKpAkLw2X9Y3kDpQX9NudP8UWqMJjbvw8kjBnm9VqnjmcMYyqt44QQh6LZOZW-Kn6REwu6oy1eGjtfgkF3cQRkeod9rTqTzYO3geL9y26yjbN-I2pg_cnoHs_AmgI.mp4"
        const val TITLE_TEXT = "Мы - \n Kolsanovafit!"
        const val DESCRIPTION_TEXT =
            "150,000+ участниц из 70 стран уже достигли крутых результатов с нами: до -5 кг в весе и до -10 см в объёмах всего за 14 дней. \n Да, это РЕАЛЬНО"
    }

    private val binding by viewBinding(AboutKolsaScreenBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orientationManager.setFragmentRef(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setViewTreeLifecycleOwner(viewLifecycleOwner)

        binding.videoView.setDelegate(object : VideoView.Delegate {
            @SuppressLint("SourceLockedOrientationActivity")
            override fun onFullScreenClick(state: VideoViewState) {
                if (state.isFullscreen) {
                    orientationManager.setLandscape()
                } else {
                    orientationManager.setPortrait()
                }
            }
        })
        binding.toolbar.setNavigationOnClickListener {
            onBackClick()
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            onBackClick()
        }
        setUpUI()
    }

    private fun setUpUI() {
        binding.title.text = TITLE_TEXT
        binding.description.text = DESCRIPTION_TEXT
        binding.videoView.configure(
            VIDEO_URL,
            !orientationManager.isPortrait
        )
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.videoView.configure(
            VIDEO_URL,
            !orientationManager.isPortrait
        )
    }

    private fun onBackClick() {
        if (!binding.videoView.onBackPressed()) {
            findNavController().popBackStack()
        } else {
            orientationManager.setPortrait()
        }
    }
}