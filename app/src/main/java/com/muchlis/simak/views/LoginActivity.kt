package com.muchlis.simak.views

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.muchlis.simak.R
import com.muchlis.simak.databinding.ActivityLoginBinding
import com.muchlis.simak.datas.input.LoginDataRequest
import com.muchlis.simak.services.Api
import com.muchlis.simak.utils.*
import com.muchlis.simak.viewmodels.LoginViewModel
import com.muchlis.simak.viewmodels.LoginViewModelFactory
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.*
import kotlin.system.exitProcess

class LoginActivity : AppCompatActivity() {

    private lateinit var bd: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewModelFactory: LoginViewModelFactory

    private lateinit var serverSelectorDialog: Dialog

    //JOB search cancleable on Dialog
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private var killAppJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bd = DataBindingUtil.setContentView(this, R.layout.activity_login)

        //makeStatusBarTransparent()

        initViewModel()
        observeViewModel()

        bd.btLoginLogin.setOnClickListener {
            val username = bd.etLoginUsername.text.toString().trim()
            val password = bd.etLoginPassword.text.toString()

            if (username.isEmpty()) {
                bd.etLoginUsername.error = "Username tidak boleh kosong"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                bd.etLoginPassword.error = "Password tidak boleh kosong!"
                return@setOnClickListener
            }

            val loginInput = LoginDataRequest(username = username, password = password)
            viewModel.postLogin(loginInput)
        }

        bd.ivLoginChangeServer.setOnClickListener {
            showSelectServerDialog()
        }

    }

    private fun initViewModel() {
        val application = requireNotNull(this).application
        val apiService = Api.retrofitService
        viewModelFactory = LoginViewModelFactory(apiService, application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    private fun observeViewModel() {
        //LOGININTENT
        viewModel.isLoginSuccess.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        })

        //LOADING
        viewModel.isLoading.observe(this, Observer {
            if (it) {
                bd.pbLoginLogin.visible()
                bd.btLoginLogin.invisible()
            } else {
                bd.pbLoginLogin.invisible()
                bd.btLoginLogin.visible()
            }
        })

        //ERROR
        viewModel.isError.observe(this, Observer {
            if (it.isNotEmpty()) {
                Toasty.error(this, it, Toasty.LENGTH_LONG).show()
            }
        })
    }

    private fun showSelectServerDialog() {
        serverSelectorDialog = Dialog(this)
        serverSelectorDialog.setContentView(R.layout.dialog_change_server)

        //TEXTVIEW DIALOG
        val tvLocal: TextView = serverSelectorDialog.findViewById(R.id.tv_dialog_server_local)
        val tvInternet: TextView = serverSelectorDialog.findViewById(R.id.tv_dialog_server_internet)

        tvLocal.setOnClickListener {
            if (App.prefs.baseUrl != LOCAL_SERVER) {
                App.prefs.baseUrl = LOCAL_SERVER
                Toasty.info(
                    this,
                    "Beralih ke server lokal pelindo, silahkan buka ulang aplikasi",
                    Toasty.LENGTH_LONG
                ).show()
                killAppJob = uiScope.launch {
                    delay(3000L)
                    killApp()
                }
            } else {
                Toasty.info(this, "Saat ini sedang menggunakan Server Lokal", Toasty.LENGTH_LONG)
                    .show()
            }
        }

        tvInternet.setOnClickListener {
            if (App.prefs.baseUrl != INTERNET_SERVER) {
                App.prefs.baseUrl = INTERNET_SERVER
                Toasty.info(
                    this,
                    "Beralih ke server internet, silahkan buka ulang aplikasi",
                    Toasty.LENGTH_LONG
                ).show()
                killAppJob = uiScope.launch {
                    delay(3000L)
                    killApp()
                }
            } else {
                Toasty.info(this, "Saat ini sedang menggunakan Server Internet", Toasty.LENGTH_LONG)
                    .show()
            }
        }

        serverSelectorDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        serverSelectorDialog.show()
    }

    private fun killApp() {
        this.finishAffinity()
        exitProcess(0)
    }

}