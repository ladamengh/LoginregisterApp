package com.example.loginregister

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.loginregister.API.INodeJS
import com.example.loginregister.API.RetroFitClient
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog
import com.rengwuxian.materialedittext.MaterialEditText
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    // init API
    val retrofit = RetroFitClient.getInstance()
    var IMyService = retrofit.create(INodeJS::class.java)

    internal var disposables = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login_button.setOnClickListener {
            loginUser(edit_email.text.toString(), password.text.toString())
        }

        register_button.setOnClickListener {
            registerUser(edit_email.text.toString(), password.text.toString())
        }
    }

    override fun onStop() {
        disposables.clear() // do not send event after activity has been stopped
        super.onStop()
    }

    override fun onDestroy() {
        disposables.clear() // do not send event after activity has been destroyed
        super.onDestroy()
    }

    private fun loginUser(email: String, password: String) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        disposables.addAll(IMyService.loginUser(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { message ->
                if (message.contains("encrypted_password")) {
                    Toast.makeText(this, "Login success", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, UserProfileActivity::class.java))
                }
                else
                    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            })
    }

    private fun registerUser(email: String, password: String) {
        val enter_name_view = LayoutInflater.from(this)
            .inflate(R.layout.enter_name_layout, null)

        MaterialStyledDialog.Builder(this)
            .setTitle("Register")
            .setDescription("One more step")
            .setCustomView(enter_name_view)
            .setIcon(R.drawable.ic_user)
            .setNegativeText("Cancel")
            .onNegative { dialog, _ -> dialog.dismiss() }
            .setPositiveText("Register")
            .onPositive {
                    _, _ ->
                    val editName = enter_name_view.findViewById<View>(R.id.name) as MaterialEditText

                    disposables.addAll(IMyService.registerUser(email, editName.text.toString(), password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { message ->
                            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    })
            }
            .show()
    }
}
