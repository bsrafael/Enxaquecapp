package com.enxaquecapp.app.ui.medicines

import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enxaquecapp.app.R
import com.enxaquecapp.app.api.models.input.MedicationInputModel
import com.enxaquecapp.app.extensions.validate
import com.enxaquecapp.app.model.Medicine
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_medicines.*
import java.text.SimpleDateFormat
import java.util.*


class MedicinesFragment: Fragment() {


    val viewModel: MedicinesViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    private lateinit var builder: MaterialDatePicker.Builder<Long>
    private lateinit var picker: MaterialDatePicker<Long>

    private var medicines: MutableList<Medicine> = mutableListOf()

    private var loadedMedicine: UUID? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_medicines, container, false)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideFab()
        setupRecyclerView()
        addListeners()
        buildDateDialog()

        viewModel.medicines.observe(viewLifecycleOwner, Observer {
            medicines.clear()
            medicines.addAll(it)
            viewAdapter.notifyDataSetChanged()
            progress.visibility = View.GONE
            medicines_list_title.visibility = if (medicines.isEmpty()) View.GONE else View.VISIBLE
        })
    }

    private fun setupRecyclerView() {
        viewManager = LinearLayoutManager(context)
        viewAdapter = MedicineAdapter(medicines, object : MedicineAdapter.OnMedClick {
            override fun onClick(index: Int) {
                loadMedicine(medicines[index])
            }
        })


        recyclerView = medicines_rv.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }

    }

    private fun addListeners() {

        medicine_start_date.editText!!.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                focusable = View.NOT_FOCUSABLE
            }
            isFocusableInTouchMode = false
            isClickable = false
            isLongClickable = false
            isCursorVisible = false
        }

        medicine_start_date.setOnClickListener {
            if (!picker.isVisible) {
                picker.show(childFragmentManager, "Medicines Date Picker")
                Snackbar.make(requireView(), "Abrindo calendário", Snackbar.LENGTH_SHORT).show()
            }
        }

        medicine_btn_confirm.setOnClickListener {
            if ( validateFields() )
                submitMedicine()
            else
                Snackbar.make(requireView(), "Preencha todos os campos!", Snackbar.LENGTH_SHORT).show()
        }

        medicine_btn_discard.setOnClickListener {
            loadedMedicine?.let {id ->
                MaterialAlertDialogBuilder(context)
                    .setTitle("Atenção!")
                    .setMessage("Tem certeza que quer excluir esse medicamento?")
                    .setNeutralButton("Não, cancela") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("Tenho") {dialog, which ->
                        // TODO: not working
                        viewModel.remove(id)
                        progress.visibility = View.VISIBLE
                    }
                    .show()
            }

        }
    }


    private fun hideFab() {
        with(requireActivity().fab) {
            this?.hide()
        }
    }

    private fun validateFields(): Boolean {
        return (
            medicine_field_name.editText!!.validate("Qual o nome do medicamento?") { it.isNotEmpty() }
            &&
            medicine_start_date.editText!!.validate("Qual o primeiro dia do tratamento?") { it.isNotEmpty() }
            &&
            medicine_interval.editText!!.validate("De quanto em quanto tempo você toma?") { it.isNotEmpty() }
        )

    }

    /**
     * TODO: A data está vindo um dia antes
     * **/
    private fun getStartDate(dateString: String): Date {
        return SimpleDateFormat("dd/MM/yyyy").parse(dateString)
    }
    private fun getStartDateString(dateLong: Long): String {
        return SimpleDateFormat("dd/MM/yyyy").format(Date(dateLong))
    }

    private fun getStartDateString(date: Date): String {
        return SimpleDateFormat("dd/MM/yyyy").format(date)
    }

    private fun submitMedicine() {
        progress.visibility = View.VISIBLE
        viewModel.add(
            MedicationInputModel(
                name = medicine_field_name.editText!!.text.toString(),
                description = "no description",
                start = getStartDate(medicine_start_date.editText!!.text.toString()),
                hourInterval = medicine_interval.editText!!.text.toString(),
                totalDoses = 0 // TODO(Rafael) actually use this
            )
        )
        clearMedicine()
    }

    private fun clearMedicine() {
        medicine_field_name.editText!!.apply {
            error = null
            setText(" ")
        }

        medicine_start_date.editText!!.apply {
            error = null
            setText(" ")
        }

        medicine_interval.editText!!.apply {
            error = null
            setText(" ")
        }

    }

    private fun buildDateDialog() {
        builder = MaterialDatePicker.Builder.datePicker()
        builder.setTitleText("Data de início")
        picker = builder.build()
        picker.addOnPositiveButtonClickListener {
            medicine_start_date.editText!!.setText( getStartDateString(it) )
        }
        Log.i("MedicinesFrag", "dialog builded")
    }

    private fun loadMedicine(med: Medicine) {
        medicine_field_name.editText!!.setText(med.name)
        medicine_start_date.editText!!.setText(getStartDateString(med.start))
        medicine_interval.editText!!.setText(med.hourInterval)
        loadedMedicine = med.id

    }


}