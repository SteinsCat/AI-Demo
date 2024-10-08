package com.example.myapplication

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class LoginSwitchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var phoneLoginText: TextView
    private lateinit var emailLoginText: TextView

    var onLoginTypeChanged: ((LoginType) -> Unit)? = null

    enum class LoginType {
        PHONE, EMAIL
    }

    var currentLoginType: LoginType = LoginType.PHONE
        private set

    init {
        orientation = HORIZONTAL
        LayoutInflater.from(context).inflate(R.layout.view_login_switch, this, true)
        
        phoneLoginText = findViewById(R.id.phoneLoginText)
        emailLoginText = findViewById(R.id.emailLoginText)

        setupClickListeners()
        updateSelection(LoginType.PHONE)
    }

    private fun setupClickListeners() {
        phoneLoginText.setOnClickListener {
            updateSelection(LoginType.PHONE)
            onLoginTypeChanged?.invoke(LoginType.PHONE)
        }
        emailLoginText.setOnClickListener {
            updateSelection(LoginType.EMAIL)
            onLoginTypeChanged?.invoke(LoginType.EMAIL)
        }
    }

    private fun updateSelection(loginType: LoginType) {
        currentLoginType = loginType
        val selectedColor = ContextCompat.getColor(context, R.color.selected_text_color)
        val unselectedColor = ContextCompat.getColor(context, R.color.unselected_text_color)
        val selectedBackground = ContextCompat.getDrawable(context, R.drawable.login_switch_item_background)

        when (loginType) {
            LoginType.PHONE -> {
                phoneLoginText.setTextColor(selectedColor)
                phoneLoginText.background = selectedBackground
                emailLoginText.setTextColor(unselectedColor)
                emailLoginText.background = null
            }
            LoginType.EMAIL -> {
                emailLoginText.setTextColor(selectedColor)
                emailLoginText.background = selectedBackground
                phoneLoginText.setTextColor(unselectedColor)
                phoneLoginText.background = null
            }
        }
    }
}
