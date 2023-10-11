package com.kakaotechcamp.step2.week1

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import org.json.JSONObject

class AddContactActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var etMail: EditText
    private lateinit var tvBirthday: TextView
    private lateinit var rgGender: RadioGroup
    private lateinit var etMemo: EditText
    private lateinit var tvCancel: TextView
    private lateinit var tvSave: TextView
    private lateinit var viewMore: View
    private lateinit var groupMore: Group
    private lateinit var toast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        initViews()
    }

    override fun onBackPressed() {
        confirmToFinish()
    }

    private fun initViews() {
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        etName = findViewById(R.id.et_name)
        etPhoneNumber = findViewById(R.id.et_phone_number)
        etMail = findViewById(R.id.et_mail)
        etMemo = findViewById(R.id.et_memo)

        rgGender = findViewById(R.id.rg_gender)

        groupMore = findViewById(R.id.group_more)

        viewMore = findViewById<View>(R.id.view_more).apply {
            setOnClickListener {
                groupMore.isVisible = true
                isVisible = false
            }
        }

        tvBirthday = findViewById<TextView>(R.id.tv_birthday).apply {
            setOnClickListener {
                showDatePickerDialog()
            }
        }

        tvCancel = findViewById<TextView>(R.id.tv_cancel).apply {
            setOnClickListener {
                confirmToFinish()
            }
        }

        tvSave = findViewById<TextView>(R.id.tv_save).apply {
            setOnClickListener {
                if (checkDataValidation()) {
                    sendContactData()
                    finish()
                }
            }
        }
    }

    private fun extractData(): JSONObject {
        return JSONObject(
            mapOf(
                CONTACT_NAME to etName.text.toString(),
                CONTACT_PHONE_NUMBER to etPhoneNumber.text.toString(),
                CONTACT_MAIL to etMail.text.toString(),
                CONTACT_BIRTHDAY to tvBirthday.text.toString(),
                CONTACT_GENDER to rgGender.run {
                    when (checkedRadioButtonId) {
                        R.id.rb_female -> "female"
                        R.id.rb_male -> "male"
                        else -> ""
                    }
                },
                CONTACT_MEMO to etPhoneNumber.text.toString(),
            )
        )
    }

    private fun sendContactData() {
        Intent(this, MainActivity::class.java).apply {
            putExtra(CONTACT_DATA, extractData().toString())
            setResult(RESULT_OK, this)
        }
    }

    private fun showDatePickerDialog() {
        DatePickerDialog(this).apply {
            updateDate(2000, 0, 1)
            setOnDateSetListener { _, year, month, dayOfMonth ->
                tvBirthday.text = "$year.${month.inc()}.$dayOfMonth"
            }
            show()
        }
    }

    private fun checkDataValidation(): Boolean {
        etName.apply {
            if (text.isNullOrEmpty()) {
                showToast(R.string.warning_empty_name)
                setSelection(0)
                return false
            }
        }
        etPhoneNumber.apply {
            if (text.isNullOrEmpty()) {
                showToast(R.string.warning_empty_phone_number)
                setSelection(0)
                return false
            }
        }
        return true
    }

    private fun showToast(@StringRes stringResId: Int) {
        toast.apply {
            setText(stringResId)
            show()
        }
    }

    private fun confirmToFinish() {
        if (etName.text.isNotEmpty()) {
            AlertDialog.Builder(this).apply {
                setMessage(R.string.warning_cancel)
                setPositiveButton(R.string.exit) { _, _ ->
                    finish()
                }
                setNegativeButton(R.string.write) { _, _ -> }
                show()
            }
        } else {
            finish()
        }
    }
}