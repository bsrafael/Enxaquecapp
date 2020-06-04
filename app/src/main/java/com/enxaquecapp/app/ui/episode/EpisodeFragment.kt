package com.enxaquecapp.app.ui.episode

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.enxaquecapp.app.R
import com.enxaquecapp.app.model.Case
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_episode.*
import java.util.*

class EpisodeFragment : Fragment() {
    val viewModel: EpisodeViewModel by activityViewModels()
    lateinit var case: Case

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_episode, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with(requireActivity().fab) {
            this?.hide()
        }

        addButtonListeners()
        addPlaceOptions()
        addTriggersOptions()

        super.onViewCreated(view, savedInstanceState)
    }


    private fun buildCase(): Case {
        return Case(
        )
    }

    private fun addButtonListeners() {
        episode_btn_cancel.setOnClickListener {
            findNavController().popBackStack()
        }

        episode_btn_remindme.setOnClickListener {
            findNavController().popBackStack()
            Toast.makeText(context, "TODO: Notification", Toast.LENGTH_LONG)
        }

        episode_btn_remindme.setOnClickListener {
            findNavController().popBackStack()
            buildCase()
            Toast.makeText(context, "TODO: New episode", Toast.LENGTH_LONG)
        }
    }

    private fun addPlaceOptions() {
        val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_list_item,
            items)
        (episode_causes_place.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun addTriggersOptions() {
        val items = listOf("Sample", "List", "Of", "Triggers")
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_list_item,
            items)
        (episode_causes_triggers.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }


}