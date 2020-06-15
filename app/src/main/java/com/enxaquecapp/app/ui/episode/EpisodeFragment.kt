package com.enxaquecapp.app.ui.episode

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.enxaquecapp.app.R
import com.enxaquecapp.app.model.Case
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_episode.*
import kotlinx.android.synthetic.main.fragment_medicines.*
import java.util.*
import kotlin.math.roundToInt


class EpisodeFragment : Fragment() {
    val viewModel: EpisodeViewModel by activityViewModels()
    lateinit var case: Case


    private lateinit var startDateBuilder: MaterialDatePicker.Builder<Long>
    private lateinit var startDatePicker: MaterialDatePicker<Long>

    private lateinit var startTimePicker: TimePickerDialog
    private lateinit var endTimePicker: TimePickerDialog

    private lateinit var endDateBuilder: MaterialDatePicker.Builder<Long>
    private lateinit var endDatePicker: MaterialDatePicker<Long>



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

        createStepValueSeekbar(0, 10, 1)
        addButtonListeners()
        addPlaceOptions()
        addTriggersOptions()

//        buildDialogs()

        super.onViewCreated(view, savedInstanceState)
    }


    private fun buildDateDialogs() {
        startDateBuilder = MaterialDatePicker.Builder.datePicker()
        startDateBuilder.setTitleText("Data de início")
        startDatePicker = startDateBuilder.build()
        startDatePicker.addOnPositiveButtonClickListener {
            episode_start_date.editText!!.setText( "$it" )
        }


        endDateBuilder = MaterialDatePicker.Builder.datePicker()
        endDateBuilder.setTitleText("Data de início")
        endDatePicker = endDateBuilder.build()
        endDatePicker.addOnPositiveButtonClickListener {
            episode_end_date.editText!!.setText( "$it" )
        }
    }

    private fun buildTimeDialog() {

        val c: Calendar = Calendar.getInstance();
        val mHour = c.get(Calendar.HOUR_OF_DAY);
        val mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog(context,
            OnTimeSetListener { view, hourOfDay, minute ->
                Log.i("f","$hourOfDay:$minute")
            }
            , mHour, mMinute, true)
    }


    private fun createStepValueSeekbar(
        min: Int,
        max: Int,
        step: Int,
        currentValue: Int = 0
    ) {
        episode_intensity_seekbar.max = 10
        episode_intensity_seekbar.progress = calculateProgress(currentValue, min, max, step)
        episode_intensity_seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                val value =
                    (progress * (max - min) / 10.toFloat()).roundToInt()
            }
        })
    }

    private fun calculateProgress(value: Int, MIN: Int, MAX: Int, STEP: Int): Int {
        return (100 * (value - MIN)) / (MAX - MIN);
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