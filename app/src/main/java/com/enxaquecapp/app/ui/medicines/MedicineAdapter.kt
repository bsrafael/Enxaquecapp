package com.enxaquecapp.app.ui.medicines

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxaquecapp.app.R
import com.enxaquecapp.app.model.Medicine
import kotlinx.android.synthetic.main.item_medicine.view.*

class MedicineAdapter(
    private val dataset: List<Medicine>,
    val callback: OnMedClick
) :
    RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    class MedicineViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface OnMedClick {
        fun onClick(index: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MedicineAdapter.MedicineViewHolder {

        val medItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)

        return MedicineViewHolder(medItem)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        val med = dataset[position]
        Log.i("Adapter", "bind: $med")

        holder.view.apply{
            item_medicine_name.text = med.name
            item_medicine_frequency.text = med.hourInterval.displayValue
            item_medicine_description.text = med.description
            item_medicine_doses.text = "Doses tomadas: ${med.consumedDoses}/${med.totalDoses}"
        }

        holder.view.setOnClickListener {
            callback.onClick(position)
        }


    }

    override fun getItemCount() = dataset.size
}
