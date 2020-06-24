package com.enxaquecapp.app.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.enxaquecapp.app.R
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.model.Episode
import com.enxaquecapp.app.shared.State
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_episode.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()

    private var intensity: Float = -1f

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        State.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            Log.i("HomeFragment", "observe $authenticationState")
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> buildHome()
                AuthenticationState.UNAUTHENTICATED -> findNavController().navigate(R.id.login_fragment)
            }
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun showWelcomeMessage() {
        if (!homeViewModel.welcomeShown) {
            Snackbar
                .make(
                    requireView(),
                    "Boas vindas!",
                    Snackbar.LENGTH_SHORT)
                .show()

            homeViewModel.welcomeShown = true
        }
    }

    private fun showHiddenUI() {
        with (requireActivity().toolbar ) {
            this?.visibility = View.VISIBLE
        }

        with(requireActivity().actionBar){
            this?.show()
        }
        with(requireActivity().fab) {
            this?.show()
        }
    }

    private fun setupObservers() {

        homeViewModel.greetingText.observe(viewLifecycleOwner, Observer {
            home_greeting_text.text = it
        })

        homeViewModel.casesText.observe(viewLifecycleOwner, Observer {
            home_cases_text.text = it
        })

        homeViewModel.error.observe(viewLifecycleOwner, Observer {
            Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
        })

        homeViewModel.meanIntensity.observe(viewLifecycleOwner, Observer {
            intensity = it
            if (intensity > 0) {
                home_intensity_seekbar.progress = (intensity*10).roundToInt()
                home_intensity_icon.setImageDrawable( resources.getDrawable(Episode.selectIcon(intensity.roundToInt()), null) )
                home_intensity_title.text = "Intensidade: ${intensity}"
            }

        })
    }

    private fun createStepValueSeekbar() {
        home_intensity_seekbar.max = 100
        home_intensity_seekbar.progress = 0
        home_intensity_seekbar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
            }
        })
    }


    private fun buildHome() {
        showHiddenUI()
        homeViewModel.update()
        showWelcomeMessage()
        setupObservers()
        createStepValueSeekbar()

    }

}
