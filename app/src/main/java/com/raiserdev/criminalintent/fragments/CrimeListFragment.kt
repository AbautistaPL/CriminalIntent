package com.raiserdev.criminalintent.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.raiserdev.criminalintent.databinding.FragmentCrimeListBinding
import com.raiserdev.criminalintent.fragments.adapter.CrimeListAdapter
import com.raiserdev.criminalintent.models.CrimeListViewModel
import kotlinx.coroutines.launch

private const val TAG = "CrimeListFragment"
class CrimeListFragment : Fragment() {
    private var _binding : FragmentCrimeListBinding ?= null
    private val binding
    get() = checkNotNull(_binding){
        "Cannot access binding because it is null. Is the view visible?"
    }

    private val crimeListViewModel : CrimeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrimeListBinding.inflate(layoutInflater,container,false)
        binding.crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                crimeListViewModel.crimes.collect{ crimes ->
                    binding.crimeRecyclerView.adapter = CrimeListAdapter(crimes) { crimeId ->
                        findNavController().navigate(
                            //R.id.show_crime_detail
                            CrimeListFragmentDirections.showCrimeDetail(crimeId)
                        )
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}