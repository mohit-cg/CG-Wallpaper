package com.example.cgwallpaper.fragments

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.cgwallpaper.R
import com.example.cgwallpaper.databinding.FragmentGoogleSignInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class GoogleSignInFragment : Fragment() {

       private lateinit var googleSignInOptions: GoogleSignInOptions
       private lateinit var googleSignInClient: GoogleSignInClient
       private val requestCode = 4
       private lateinit var binding: FragmentGoogleSignInBinding
       private lateinit var auth: FirebaseAuth

       override fun onCreateView(
              inflater: LayoutInflater,
              container: ViewGroup?,
              savedInstanceState: Bundle?
       ): View? {
              binding = FragmentGoogleSignInBinding.inflate(inflater)
              return binding.root
       }

       override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
              auth = FirebaseAuth.getInstance()
              if (auth.currentUser!=null){
                     findNavController().navigate(R.id.action_googleSignInFragment_to_splashfragment)
              }
              googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.web_client_id))
                     .requestEmail().build()


              googleSignInClient = GoogleSignIn.getClient(requireContext(), googleSignInOptions)

              binding.cardView.setOnClickListener{
                     binding.pb.visibility = View.VISIBLE
                     signIn()
              }


       }

       private fun signIn() {
              val intent = googleSignInClient.signInIntent
              startActivityForResult(intent, requestCode)
       }

       override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
              super.onActivityResult(requestCode, resultCode, data)
              if (requestCode==this.requestCode) {
                     val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                     handleSignInResult(task)
              }
       }

       private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
              try {
                  val account = task.getResult(ApiException::class.java)
                     firebaseAuthWithGoogle(account)
              }
              catch (e: ApiException) {
                     Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
              }
       }

       private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
              val credential = GoogleAuthProvider.getCredential(account.idToken, null)
              auth.signInWithCredential(credential).addOnCompleteListener(requireActivity()) { task->
                     if (task.isSuccessful) {
                        binding.pb.visibility = View.INVISIBLE
                            findNavController().navigate(R.id.action_googleSignInFragment_to_splashfragment)
                     }
              }
       }

}