package com.example.myapplication

import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.text.Spanned
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import android.text.Editable
import android.text.TextWatcher

class MainActivity : AppCompatActivity() {
    private lateinit var loginSwitchView: LoginSwitchView
    private lateinit var etPhoneNumber: EditText
    private lateinit var btnGetVerificationCode: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginSwitchView = findViewById(R.id.loginSwitchView)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        val tvCountryCode: TextView = findViewById(R.id.tvCountryCode)
        btnGetVerificationCode = findViewById(R.id.btnGetVerificationCode)

        loginSwitchView.onLoginTypeChanged = { loginType ->
            when (loginType) {
                LoginSwitchView.LoginType.PHONE -> {
                    tvCountryCode.visibility = View.VISIBLE
                    etPhoneNumber.hint = "无需注册，请输入手机号验证登录"
                    etPhoneNumber.inputType = InputType.TYPE_CLASS_PHONE
                    etPhoneNumber.filters = arrayOf(InputFilter.LengthFilter(11))
                }
                LoginSwitchView.LoginType.EMAIL -> {
                    tvCountryCode.visibility = View.GONE
                    etPhoneNumber.hint = "无需注册，请输入完整邮箱验证登录"
                    etPhoneNumber.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    etPhoneNumber.filters = arrayOf(InputFilter.LengthFilter(256))
                }
            }
            etPhoneNumber.text.clear()
            updateVerificationButtonState()
        }

        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                updateVerificationButtonState()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        btnGetVerificationCode.setOnClickListener {
            // 处理获取验证码按点击
        }

        // 初始状态下禁用按钮
        updateVerificationButtonState()
    }

    private fun updateVerificationButtonState() {
        val input = etPhoneNumber.text.toString()
        btnGetVerificationCode.isEnabled = when (loginSwitchView.currentLoginType) {
            LoginSwitchView.LoginType.PHONE -> input.length == 11 // 假设手机号为11位
            LoginSwitchView.LoginType.EMAIL -> android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()
        }
    }
}