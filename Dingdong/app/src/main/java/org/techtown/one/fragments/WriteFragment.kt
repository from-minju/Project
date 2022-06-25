package org.techtown.one.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
//import org.techtown.mysololife.DataBinderMapperImpl
import org.techtown.one.R
import org.techtown.one.databinding.FragmentWriteBinding

class WriteFragment : Fragment() {

    private lateinit var binding : FragmentWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_write, container, false)

        binding.homeTap.setOnClickListener{
            findNavController().navigate(R.id.action_writeFragment_to_homeFragment)
        }

        binding.listTap.setOnClickListener{
            findNavController().navigate(R.id.action_writeFragment_to_listFragment)
        }

        binding.chatTap.setOnClickListener{
            findNavController().navigate(R.id.action_writeFragment_to_chatFragment)
        }

        binding.userTap.setOnClickListener{
            findNavController().navigate(R.id.action_writeFragment_to_userFragment)
        }
        return binding.root
    }

}