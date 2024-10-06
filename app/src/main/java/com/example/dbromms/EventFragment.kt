package com.example.dbromms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dbromms.databinding.FragmentEventBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment





class EventFragment : BottomSheetDialogFragment() {
    //binding
    lateinit var binding: FragmentEventBinding
    lateinit var eventsViewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        //acts as the join between the viewmodel and the view
        eventsViewModel = ViewModelProvider(activity).get(EventsViewModel::class.java)
        //binding to db
        eventsViewModel.eventInsertionStatus.observe(viewLifecycleOwner){
                isSuccess ->
            if (isSuccess){
                Toast.makeText(requireContext(), "Event saved to room db", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "failed to save event ", Toast.LENGTH_SHORT).show()
            }
        }

        binding.saveButton.setOnClickListener{
            saveAction()
        }
    }

    private fun saveAction(){

//        eventsViewModel.name.value = binding.name.text.toString()
//        eventsViewModel.desc.value = binding.name.text.toString()


        //fetch data from ui -->room
        val evtName =  binding.name.text.toString()
        val evtDesc = binding.desc.text.toString()
        //checks
        if(evtName.isNotEmpty() && evtDesc.isNotEmpty()){
            val event = Events(name = evtName, description = evtDesc)
            eventsViewModel.insert(event)
            //set the empty string
            binding.name.setText("")
            binding.desc.setText("")
            dismiss()

        }else{
            Toast.makeText(requireContext(), "Please fill in all fields to continue", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventBinding.inflate(inflater,container,false)
        return binding.root
    }
}
