package com.enxaquecapp.app.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.enxaquecapp.app.R
import com.enxaquecapp.app.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment: Fragment() {


    private val loginViewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_register, container, false)

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()


        with(requireActivity().actionBar){
            this?.hide()
        }
        with(requireActivity().fab) {
            this?.hide()
        }


        register_submit.setOnClickListener {
            loginViewModel.authenticate("mock@mail.com", "mockpasswords")
            navController.navigate(R.id.action_register_fragment_to_nav_home)
        }

    }

}