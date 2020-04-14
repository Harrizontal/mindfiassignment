package com.harrizontal.mindfi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.SignInButton
import com.harrizontal.mindfi.R
import com.harrizontal.mindfi.databinding.ActivityMainBinding
import com.harrizontal.mindfi.model.User

class MainActivity : AppCompatActivity() {

    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onStart() {
        super.onStart()
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,mGoogleSignInOptions)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val account = GoogleSignIn.getLastSignedInAccount(this)!!
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.user = User(account.displayName!!,account.photoUrl.toString())
        binding.buttonLogout.setOnClickListener {
             mGoogleSignInClient.signOut().addOnCompleteListener {
                if (it.isSuccessful){
                    Log.e("MainActivity","Account logout")
                    finish()
                    startActivity(Intent(this,SplashActivity::class.java))
                }else{
                    Log.e("MainActivity","Fail to logout")
                }
            }
        }

    }
}