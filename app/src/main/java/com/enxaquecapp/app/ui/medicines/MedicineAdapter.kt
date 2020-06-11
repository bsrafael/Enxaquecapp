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

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MedicineViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: Medicine) {

        }
    }

    interface OnMedClick {
        fun onClick(index: Int)
    }


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MedicineAdapter.MedicineViewHolder {


        // create a new view
        val medItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)
        // set the view's size, margins, paddings and layout parameters

        return MedicineViewHolder(medItem)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        val med = dataset[position]
        Log.i("Adapter", "bind: ${med.toString()}")

        holder.view.item_medicine_name.text = med.name
        holder.view.item_medicine_frequency.text = med.hourInterval

        holder.view.setOnClickListener {
            callback.onClick(position)
        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataset.size
}
