package com.harrizontal.mindfi.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.*
import com.google.android.gms.common.SignInButton
import com.harrizontal.mindfi.R
import com.harrizontal.mindfi.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    val RC_SIGN_IN: Int = 1;
    private lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onStart() {
        super.onStart()
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(this,R.layout.activity_splash)

        configureGoogleSignIn()

        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
        signInButton.setOnClickListener {
            signIn()
        }


    }

    private fun configureGoogleSignIn(){
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this,mGoogleSignInOptions)
    }
    private fun signIn(){
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            val result: GoogleSignInResult? = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result!!.isSuccess){
                val account: GoogleSignInAccount = result.signInAccount!!
                updateUI(account)
            }
        }
    }

    private fun updateUI(account: GoogleSignInAccount?){
        if (account != null){
            finish()
            startActivity(Intent(this,MainActivity::class.java))
            Toast.makeText(this,"Welcome "+account.displayName,Toast.LENGTH_LONG).show()
        }
    }
}
