package com.enxaquecapp.app.ui.login

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.enxaquecapp.app.R
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.shared.State
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: Fragment() {

    private val loginViewModel: LoginViewModel by activityViewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_login, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with (requireActivity().toolbar ) {
            this?.visibility = GONE
        }

        with(requireActivity().actionBar){
            this?.hide()
        }
        with(requireActivity().fab) {
            this?.hide()
        }

        login_submit.setOnClickListener {
            var email = login_email.editText!!.text.toString()
            var password = login_password.editText!!.text.toString()

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) ) {
                Log.i("LoginFragment", "autenticando")
                loginViewModel.authenticate(email, password)
            } else {
                Snackbar.make(view, "Ops! Preencha os dois campos", Snackbar.LENGTH_SHORT).show()
            }
        }

        login_register.setOnClickListener {
            findNavController().navigate(R.id.action_login_fragment_to_register_fragment)
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            loginViewModel.refuseAuthentication()
        }

        val navController = findNavController()
        State.authenticationState.observe(viewLifecycleOwner, Observer { state ->
            Log.i("LoginFragment", "observe: ${state}")
            when (state) {
                AuthenticationState.AUTHENTICATED -> navController.navigate(R.id.action_login_fragment_to_nav_home)
                AuthenticationState.INVALID_AUTHENTICATION -> showErrorMessage()
                else -> {
                    Log.i("LoginFragment", "observe else ${state}")
                }
            }
        })
    }

    fun showErrorMessage() {
        Snackbar.make(requireView(), "Ops! Erro ao autenticar", Snackbar.LENGTH_SHORT).show()
    }


}