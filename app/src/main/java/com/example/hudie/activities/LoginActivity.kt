package com.example.hudie.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hudie.R
import com.example.hudie.api.RetrofitClient
import com.example.hudie.models.TokenResponse
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.sign


/**
 * A Login Form Example in Kotlin Android
 */

class LoginActivity : AppCompatActivity() {

    private var googleLoginCode = 400;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.statusBarColor = getColor(R.color.royalblue);
        window.navigationBarColor = getColor(R.color.royalblue);
        supportActionBar?.hide();

        // get reference to all views
        val etUsername = findViewById<EditText>(R.id.et_user_name)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnGoToRegister = findViewById<Button>(R.id.goto_register)
        val btnSubmit = findViewById<Button>(R.id.btn_submit)
        val gglLogin = findViewById<SignInButton>(R.id.googleSignIn)
        gglLogin.setSize(SignInButton.SIZE_STANDARD)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestProfile()
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        val device = Build.DEVICE

        // set on-click listener
        btnSubmit.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            if(username.isEmpty()) {
                etUsername.error = "Username required"
                etUsername.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()) {
                etPassword.error = "Password required"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            Log.i("Log In", "Button Pressed!")

            RetrofitClient.instance.login(
                username, password, device
            ).enqueue(object : Callback<TokenResponse> {
                override fun onResponse(
                    call: Call<TokenResponse>,
                    response: Response<TokenResponse>
                ) {
                    Log.i("Log In #1", response.body().toString())

                    val token = response.body()?.token.toString();
                    val username = response.body()?.username.toString()
                    val id: String = response.body()?.user_id.toString()
                    val role: String = response.body()?.admin.toString()

                    var setting = getSharedPreferences("Hudie", 0);
                    var editor = setting.edit();

                    editor.putString("token", token)
                    editor.putString("username", username)
                    editor.putString("user_id", id)
                    editor.putString("role", role)
                    editor.commit()

                    val intent = Intent(this@LoginActivity, MainActivity::class.java);
                    startActivity(intent);
                    finish();
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
            })

        }
        btnGoToRegister.setOnClickListener{
            val intent : Intent = Intent(this, Register::class.java);
            startActivity(intent);
            finish();
        }

        gglLogin.setOnClickListener{
            mGoogleSignInClient.signOut()

            val signInIntent: Intent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, googleLoginCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == googleLoginCode) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account:GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            val tokenID = account?.idToken
            val googleEmail = account?.email
            val userID = account?.id
            val userFullName = account?.givenName + " " + account?.familyName

            Log.i("LOGIN", "GOOGLE LOGIN:" + tokenID.toString())

            RetrofitClient.instance.googleAuth(
                token_ID = tokenID.toString(),
                email = googleEmail.toString(),
                google_ID = userID.toString(),
                full_name = userFullName,
                device = Build.DEVICE
            ).enqueue(object : Callback<TokenResponse> {
                override fun onResponse(
                    call: Call<TokenResponse>,
                    response: Response<TokenResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.i("LOGIN", "Google Log In berhasil: " + response.body()?.user_id)
                        Toast.makeText(applicationContext, "Google Login berhasil", Toast.LENGTH_LONG).show()

                        var setting = getSharedPreferences("Hudie", 0);
                        var editor = setting.edit();

                        editor.putString("token", response.body()?.token)
                        editor.putString("username", response.body()?.username)
                        editor.putString("user_id", response.body()?.user_id)
                        editor.putString("role", response.body()?.admin.toString())

                        editor.commit()

                        val intent = Intent(this@LoginActivity, Home::class.java);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.i("LOGIN", "Google Log In gagal: CODE" + response.code())
                        Log.i("LOGIN", "Google Log In gagal: " + response.errorBody().toString())
                        Toast.makeText(applicationContext, "Google Log In gagal: CODE" + response.code(), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                    Log.i("LOGIN", "API Call Failure: " + t.message)
                    Toast.makeText(
                        applicationContext,
                        "Error: " + t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }

            })
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Log In", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(
                applicationContext,
                "signInResult:failed code=" + e.statusCode,
                Toast.LENGTH_LONG
            ).show()
        }
    }

}

