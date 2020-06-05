package com.enxaquecapp.app.ui.register

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.enxaquecapp.app.R
import com.enxaquecapp.app.enums.AuthenticationState
import com.enxaquecapp.app.extensions.validate
import com.enxaquecapp.app.model.User
import com.enxaquecapp.app.shared.State
import com.enxaquecapp.app.ui.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

class RegisterFragment: Fragment() {


    private val registerViewModel: RegisterViewModel by activityViewModels()
    val args: RegisterFragmentArgs by navArgs()


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

        hideFab()

        if (args.comingFromLogin)
            hideToolbar()
        else
            populateFields()

        register_title.text = args.title

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



    private fun hideToolbar() {
        with (requireActivity().toolbar ) {
            this?.visibility = View.GONE
        }
        with(requireActivity().actionBar){
            this?.hide()
        }
    }

    private fun hideFab() {
        with(requireActivity().fab) {
            this?.hide()
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
        val gender = getGender()

        val user = User(
            name = register_name.editText!!.text.toString(),
            email = register_email.editText!!.text.toString(),
            birthDate = birth,
            gender = gender,
            age = 0
        )

        if (args.comingFromLogin) {
            registerViewModel.register(user, register_password.editText!!.text.toString())
            State.authenticationState.observe(viewLifecycleOwner, Observer {state ->
                when (state) {
                    AuthenticationState.AUTHENTICATED -> findNavController().navigate(R.id.action_register_fragment_to_nav_home)
                    AuthenticationState.INVALID_AUTHENTICATION -> showErrorMessage()
                    else -> {
                        Log.i("RegisterFragment", "observe else ${state}")
                    }
                }

            })
        }
        else {
            Toast.makeText(context, "TODO: update", Toast.LENGTH_LONG).show()
            registerViewModel.update(user, register_password.editText!!.text.toString())
            findNavController().navigate(R.id.action_register_fragment_to_nav_home)
        }


    }

    private fun getBirthDate(birthString: String): Date {
        return SimpleDateFormat("dd/MM/yyyy").parse(birthString)
    }

    private fun getGender(): String {
        return when(register_gender.checkedRadioButtonId) {
            register_gender_male.id -> "Masculino"
            register_gender_female.id -> "Feminino"
            else -> "Não Informado"
        }
    }

    private fun populateFields() {
        registerViewModel.user?.let { user ->

            register_name.editText!!.setText(user.name)
            register_email.editText!!.setText(user.email)
            register_birth.editText!!.setText( SimpleDateFormat("dd/MM/yyyy").format(user.birthDate) )
            if (user.gender.compareTo( getString(R.string.male) ) == 0) {
                register_gender.check(R.id.register_gender_male)
            } else if (user.gender.compareTo( getString(R.string.female) ) == 0) {
                register_gender.check(R.id.register_gender_female)
            }

        }

    }

    fun showErrorMessage() {
        Snackbar.make(requireView(), "Ops! Erro ao criar conta", Snackbar.LENGTH_SHORT).show()
    }

}