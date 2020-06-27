package com.enxaquecapp.app.ui.episodesList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.enxaquecapp.app.R
import com.enxaquecapp.app.model.Episode
import com.enxaquecapp.app.ui.episode.EpisodeFragmentDirections
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_episodes_list.*


class EpisodesListFragment: Fragment() {


    val viewModel: EpisodesListViewModel by activityViewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager


    private var episodes: MutableList<Episode> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_episodes_list, container, false)

        viewModel.update()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        viewModel.episodes.observe(viewLifecycleOwner, Observer {
            episodes.clear()
            episodes.addAll(it)
            viewAdapter.notifyDataSetChanged()
            progress.visibility = View.GONE
        })
    }

    private fun setupRecyclerView() {
        viewManager = LinearLayoutManager(context)
        viewAdapter = EpisodesListAdapter(episodes, object : EpisodesListAdapter.OnEpClick {
            override fun onClick(index: Int) {
                val action = EpisodeFragmentDirections.actionGlobalEpisodeFragment(episodes[index])
                findNavController().navigate(action)
            }
        })

        recyclerView = episodes_rv.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }

    }
}