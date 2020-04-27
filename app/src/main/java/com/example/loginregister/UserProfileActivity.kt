package com.example.loginregister

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregister.API.INodeJS
import com.example.loginregister.API.RetroFitClient
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_user_profile.*


class UserProfileActivity: AppCompatActivity() {

    // init API
    val retrofit = RetroFitClient.getInstance()
    var IMyService = retrofit.create(INodeJS::class.java)

    internal var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        search_for_users_button.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
            finish()
        }
    }
}