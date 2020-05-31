package com.enxaquecapp.app.ui.register

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.enxaquecapp.app.R
import com.enxaquecapp.app.extensions.validate
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.ui.login.LoginViewModel
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*

class RegisterFragment: Fragment() {


    private val registerViewModel: RegisterViewModel by activityViewModels()

    private var validPasswords: Boolean = false
    private var validEmail: Boolean = false

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

        with (requireActivity().toolbar ) {
            this?.visibility = View.GONE
        }
        with(requireActivity().actionBar){
            this?.hide()
        }
        with(requireActivity().fab) {
            this?.hide()
        }

        setValidators()
        setSubmitListener()
    }

    private fun setValidators() {
        validEmail = register_email.editText!!.validate("E-mail inválido") {
            it.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(it).matches()
        }

        validPasswords = register_password_repeat.editText!!.validate("As duas senhas precisam ser iguais") {
            it.compareTo(register_password.editText!!.text.toString()) == 0
        }
    }


    private fun setSubmitListener() {
        register_submit.setOnClickListener {
            submit()
        }
    }

    private fun submit() {
        Log.i("RegisterFragment", "submitting")
        val birth = getBirthDate(register_birth.editText!!.text.toString())
        val age = getAge(birth)
        val gender = getGender()

        val user = User(
            name = register_name.editText!!.text.toString(),
            email = register_email.editText!!.text.toString(),
            birth = birth,
            age = age,
            gender = gender
        )

        registerViewModel.register(user)

        findNavController().navigate(R.id.action_register_fragment_to_nav_home)

    }

    /** TODO: Cast string to date **/
    private fun getBirthDate(birthString: String): Date {
        return Date()
    }

    /** TODO: Generate age from date **/
    private fun getAge(birth: Date): Int {
        return 21
    }

    private fun getGender(): Char {
        return when(register_gender.checkedRadioButtonId) {
            register_gender_male.id -> 'M'
            register_gender_female.id -> 'F'
            else -> '?'
        }
    }

}