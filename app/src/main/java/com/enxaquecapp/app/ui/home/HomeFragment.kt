package com.enxaquecapp.app.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.enxaquecapp.app.R
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.shared.State
import com.enxaquecapp.app.ui.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val homeViewModel: HomeViewModel by activityViewModels<HomeViewModel>()


        with (requireActivity().toolbar ) {
            this?.visibility = View.VISIBLE
        }

        with(requireActivity().actionBar){
            this?.show()
        }
        with(requireActivity().fab) {
            this?.show()
        }

        homeViewModel.update()

        State.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            Log.i("HomeFragment", "observe ${authenticationState}")
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> showWelcomeMessage()
                AuthenticationState.UNAUTHENTICATED -> findNavController().navigate(R.id.login_fragment)
            }
        })

        homeViewModel.greetingText.observe(viewLifecycleOwner, Observer {
            home_greeting_text.text = it
        })

        homeViewModel.casesText.observe(viewLifecycleOwner, Observer {
            home_cases_text.text = it
        })

        super.onViewCreated(view, savedInstanceState)
    }

    private fun showWelcomeMessage() {
        Snackbar
            .make(
                requireView(),
                "Boas vindas!",
                Snackbar.LENGTH_SHORT)
            .show()
    }

}
