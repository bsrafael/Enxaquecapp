package com.enxaquecapp.app.ui.episodesList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.enxaquecapp.app.R
import com.enxaquecapp.app.model.Episode
import com.enxaquecapp.app.model.Medicine
import com.enxaquecapp.app.shared.StringUtils
import kotlinx.android.synthetic.main.item_episode.view.*
import kotlinx.android.synthetic.main.item_medicine.view.*

class EpisodesListAdapter(
    private val dataset: List<Episode>,
    val callback: OnEpClick
) :
    RecyclerView.Adapter<EpisodesListAdapter.EpisodeItemViewHolder>() {

    class EpisodeItemViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    interface OnEpClick {
        fun onClick(index: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): EpisodesListAdapter.EpisodeItemViewHolder {

        val medItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_medicine, parent, false)

        return EpisodeItemViewHolder(medItem)
    }

    override fun onBindViewHolder(holder: EpisodeItemViewHolder, position: Int) {
        val ep = dataset[position]
        Log.i("Adapter", "bind: $ep")


        holder.view.apply{
            item_episode_start_date.text = StringUtils.dateToString(ep.start)
            item_episode_duration.text = "TODO"
            item_episode_intensity.text = ep.intensity.toString()
            item_episode_intensity_icon.setImageDrawable( resources.getDrawable(ep.getIcon(), null) )
        }

        holder.view.setOnClickListener {
            callback.onClick(position)
        }


    }

    override fun getItemCount() = dataset.size
}
