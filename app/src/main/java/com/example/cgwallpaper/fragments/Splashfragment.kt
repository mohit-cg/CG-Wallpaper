package com.example.cgwallpaper.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cgwallpaper.R
import com.example.cgwallpaper.databinding.FragmentSplashfragmentBinding


class Splashfragment : Fragment() {
   private lateinit var binding: FragmentSplashfragmentBinding

   override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {

      binding = FragmentSplashfragmentBinding.inflate(inflater)
      return binding.root
   }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      Handler(Looper.getMainLooper()).postDelayed({

                findNavController().navigate(R.id.action_splashfragment_to_homeFragment22)
      },3000
      )
   }

}