package com.enxaquecapp.app.ui.home

import android.os.Bundle
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
import com.enxaquecapp.app.ui.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private val loginViewModel: LoginViewModel by activityViewModels()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (requireActivity().toolbar ) {
            this?.visibility = View.VISIBLE
        }

        with(requireActivity().actionBar){
            this?.show()
        }
        with(requireActivity().fab) {
            this?.show()
        }

        val navController = findNavController()
        loginViewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> showWelcomeMessage()
                AuthenticationState.UNAUTHENTICATED -> navController.navigate(R.id.login_fragment)
            }
        })
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
