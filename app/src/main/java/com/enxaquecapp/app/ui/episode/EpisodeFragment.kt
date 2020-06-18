package com.enxaquecapp.app.ui.episode

import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Build
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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.enxaquecapp.app.R
import com.enxaquecapp.app.api.models.input.EpisodeInputModel

import com.enxaquecapp.app.model.Episode
import com.enxaquecapp.app.model.Cause
import com.enxaquecapp.app.model.Location
import com.enxaquecapp.app.model.Relief
import com.enxaquecapp.app.shared.StringUtils
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder

import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_episode.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*
import kotlin.math.roundToInt


class EpisodeFragment : Fragment() {
    val viewModel: EpisodeViewModel by activityViewModels()

    private lateinit var startDateBuilder: MaterialDatePicker.Builder<Long>
    private lateinit var startDatePicker: MaterialDatePicker<Long>

    private lateinit var startTimePicker: TimePickerDialog
    private lateinit var endTimePicker: TimePickerDialog

    private lateinit var endDateBuilder: MaterialDatePicker.Builder<Long>
    private lateinit var endDatePicker: MaterialDatePicker<Long>


    private val locations: MutableList<Location> = mutableListOf()
    private val causes: MutableList<Cause> = mutableListOf()
    private val reliefs: MutableList<Relief> = mutableListOf()

    private var error: String = ""

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

        viewModel.causes.observe(viewLifecycleOwner, Observer {
            causes.clear()
            causes.addAll(it)
            addCausesOptions()

        })

        viewModel.locations.observe(viewLifecycleOwner, Observer {
            locations.clear()
            locations.addAll(it)
            addLocationsOptions()
        })

        viewModel.reliefs.observe(viewLifecycleOwner, Observer {
            reliefs.clear()
            reliefs.addAll(it)
            addReliefsOptions()
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {
            error = it
            MaterialAlertDialogBuilder(context)
                .setTitle("Ops!")
                .setMessage(error)
                .setPositiveButton("Ok") { dialog, which ->
                    findNavController().popBackStack()
                }
            .show()
        })

        viewModel.loadOptions()

        createStepValueSeekbar(0, 10, 1)
        addButtonListeners()

        buildDialogs()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun buildDialogs() {

        buildDateDialogs()
        buildTimeDialogs()
        val fields = listOf(episode_start_date, episode_start_time, episode_end_date, episode_end_time)

        fields.forEach { field ->
            field.editText!!.apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    focusable = View.NOT_FOCUSABLE
                }
                isFocusableInTouchMode = false
                isClickable = false
                isLongClickable = false
                isCursorVisible = false
            }
        }

        episode_start_date.setOnClickListener {
            Log.i("EpisodeFrag", "episode_start_date click")
            if (!startDatePicker.isVisible) {
                startDatePicker.show(childFragmentManager, "Start Date Picker")
            }
        }
        episode_start_time.setOnClickListener {
            Log.i("EpisodeFrag", "episode_start_time click")
            if (!startTimePicker.isShowing) {
                startTimePicker.show()
            }
        }
        episode_end_date.setOnClickListener {
            Log.i("EpisodeFrag", "episode_end_date click")
            if (!endDatePicker.isVisible) {
                endDatePicker.show(childFragmentManager, "End Date Picker")
            }
        }
        episode_end_time.setOnClickListener {
            Log.i("EpisodeFrag", "episode_end_time click")
            if (!endTimePicker.isShowing) {
                endTimePicker.show()
            }
        }


    }

    private fun buildDateDialogs() {
        startDateBuilder = MaterialDatePicker.Builder.datePicker()
        startDateBuilder.setTitleText("Data de início")
        startDatePicker = startDateBuilder.build()
        startDatePicker.addOnPositiveButtonClickListener {
            episode_start_date.editText!!.setText( StringUtils.dateToString(it) )
        }

        endDateBuilder = MaterialDatePicker.Builder.datePicker()
        endDateBuilder.setTitleText("Data de início")
        endDatePicker = endDateBuilder.build()
        endDatePicker.addOnPositiveButtonClickListener {
            episode_end_date.editText!!.setText( StringUtils.dateToString(it) )
        }
    }

    private fun buildTimeDialogs() {
        val c: Calendar = Calendar.getInstance();
        val mHour = c.get(Calendar.HOUR_OF_DAY);
        val mMinute = c.get(Calendar.MINUTE);

        startTimePicker = TimePickerDialog(context,
            OnTimeSetListener { view, hourOfDay, minute ->
                val t = "${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
                episode_start_time.editText!!.setText( t )
            }
            , mHour, mMinute, true)

        endTimePicker = TimePickerDialog(context,
            OnTimeSetListener { view, hourOfDay, minute ->
                val t = "${String.format("%02d", hourOfDay)}:${String.format("%02d", minute)}"
                episode_end_time.editText!!.setText( t )
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


    private fun dateOrNull(field: String): Date?{
        return if (field.isNotEmpty()) {
            StringUtils.strToDate(field)
        } else {
            null
        }
    }

    private fun buildCase() {

        val concatStart: String = "${episode_start_date.editText!!.text.toString()} ${episode_start_time.editText!!.text.toString()}"
        val start = SimpleDateFormat("dd/MM/yyyy HH:mm").parse(concatStart)!!

        val end = if (episode_end_date.editText!!.text.toString().isNotEmpty() and episode_end_time.editText!!.text.toString().isNotEmpty()) {
            val concatEnd: String = "${episode_end_date.editText!!.text.toString()} ${episode_end_time.editText!!.text.toString()}"
            SimpleDateFormat("dd/MM/yyyy HH:mm").parse(concatStart)
        } else null

        val im = EpisodeInputModel(
            start = start,
            end = end,
            intensity = episode_intensity_seekbar.progress,
            releafWorked = episode_relief_helped.isChecked,
            localId = locations.firstOrNull { it.description.compareTo(episode_causes_place.editText!!.text.toString()) == 0 }?.id,
            causeId = causes.firstOrNull { it.description.compareTo(episode_causes_triggers.editText!!.text.toString()) == 0 }?.id,
            reliefId = reliefs.firstOrNull { it.description.compareTo(episode_relief_action.editText!!.text.toString()) == 0 }?.id
        )

        viewModel.create(im)
    }



    private fun addButtonListeners() {
        episode_btn_cancel.setOnClickListener {
            findNavController().popBackStack()
        }

        episode_btn_remindme.setOnClickListener {
            findNavController().popBackStack()
            Toast.makeText(context, "TODO: Notification", Toast.LENGTH_LONG).show()
        }

        episode_btn_confirm.setOnClickListener {
            findNavController().popBackStack()
            buildCase()
            Toast.makeText(context, "TODO: New episode", Toast.LENGTH_LONG).show()
        }
    }

    private fun addLocationsOptions() {
        val items = mutableListOf<String>()
        locations.forEach {  items.add(it.description) }

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_list_item,
            items)
        (episode_causes_place.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun addCausesOptions() {
        val items = mutableListOf<String>()
        causes.forEach {  items.add(it.description) }

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_list_item,
            items)
        (episode_causes_triggers.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun addReliefsOptions() {
        val items = mutableListOf<String>()
        reliefs.forEach {  items.add(it.description) }

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.dropdown_list_item,
            items)
        (episode_relief_action.editText as? AutoCompleteTextView)?.setAdapter(adapter)
    }


}