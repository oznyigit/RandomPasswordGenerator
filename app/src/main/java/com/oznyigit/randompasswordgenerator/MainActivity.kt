package com.oznyigit.randompasswordgenerator

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.oznyigit.randompasswordgenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    private fun generateButtonClickedCallback()
    {
        val editTextLength = mBinding.etNumber.text.toString()
        val radioButtonLetters = mBinding.rbLetters
        val radioButtonDigits = mBinding.rbDigits
        val radioButtonCombined = mBinding.rbLettersDigits

        if (editTextLength.isEmpty()) {
            Toast.makeText(this, R.string.warning_length, Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val length = editTextLength.toInt()

            val resultText = when {
                radioButtonLetters.isChecked -> PasswordGenerator().generate(length, LETTERS)
                radioButtonDigits.isChecked -> PasswordGenerator().generate(length, DIGITS)
                radioButtonCombined.isChecked -> PasswordGenerator().generate(length, LD_COMBINED)
                else -> ""
            }

            if (!radioButtonLetters.isChecked && !radioButtonDigits.isChecked && !radioButtonCombined.isChecked) {
                Toast.makeText(this, R.string.warning_config, Toast.LENGTH_SHORT).show()
                return
            }

            mBinding.tvResult.text = resultText

        } catch (ex: NumberFormatException) {
            Toast.makeText(this, "Number Format Exception: ${ex.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun initGenerateButton()
    {
        mBinding.btnGenerate.setOnClickListener { generateButtonClickedCallback() }
    }

    private fun copyPasswordButtonClickedCallback()
    {
        val password = mBinding.tvResult.text.toString()
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("password", password)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(this, "Password Copied: $password", Toast.LENGTH_SHORT).show()
    }

    private fun initCopyPasswordButton()
    {
        mBinding.btnCopyPassword.setOnClickListener { copyPasswordButtonClickedCallback() }
    }

    private fun exitButtonClickedCallback() = finish()

    private fun initExitButton()
    {
        mBinding.btnExit.setOnClickListener { exitButtonClickedCallback() }
    }

    private fun initViews()
    {
        initGenerateButton()
        initCopyPasswordButton()
        initExitButton()
    }

    private fun initBinding()
    {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
    }

    private fun initialize()
    {
        initBinding()
        initViews()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
    }

}

