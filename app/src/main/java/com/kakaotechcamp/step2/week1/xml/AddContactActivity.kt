package com.kakaotechcamp.step2.week1.xml

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.isVisible
import com.kakaotechcamp.step2.week1.CONTACT_BIRTHDAY
import com.kakaotechcamp.step2.week1.CONTACT_DATA
import com.kakaotechcamp.step2.week1.CONTACT_GENDER
import com.kakaotechcamp.step2.week1.CONTACT_MAIL
import com.kakaotechcamp.step2.week1.CONTACT_MEMO
import com.kakaotechcamp.step2.week1.CONTACT_NAME
import com.kakaotechcamp.step2.week1.CONTACT_PHONE_NUMBER
import com.kakaotechcamp.step2.week1.GENDER_FEMALE
import com.kakaotechcamp.step2.week1.GENDER_MALE
import com.kakaotechcamp.step2.week1.R
import com.kakaotechcamp.step2.week1.compose.DATE_FORMAT
import com.kakaotechcamp.step2.week1.compose.DEFAULT_BIRTHDAY
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

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
    private val dateFormat = SimpleDateFormat(DATE_FORMAT)

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            confirmToFinish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)
        initViews()

        onBackPressedDispatcher.addCallback(onBackPressedCallback)
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
                        R.id.rb_female -> GENDER_FEMALE
                        R.id.rb_male -> GENDER_MALE
                        else -> ""
                    }
                },
                CONTACT_MEMO to etMemo.text.toString(),
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
            val calendar = getBirthdayCalendar()
            getBirthdayCalendar().apply {
                updateDate(get(Calendar.YEAR), get(Calendar.MONTH), get(Calendar.DATE))
            }
            setOnDateSetListener { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                tvBirthday.text = dateFormat.format(calendar.time)
            }
            show()
        }
    }

    private fun getBirthdayCalendar(): Calendar {
        val birthdayText =
            if (tvBirthday.text.isNotEmpty()) tvBirthday.text.toString() else DEFAULT_BIRTHDAY
        val birthdayDate = dateFormat.parse(birthdayText) ?: Date()
        return Calendar.getInstance().apply { time = birthdayDate }
    }

    private fun checkDataValidation(): Boolean {
        etName.checkEmpty(R.string.warning_empty_name).also { isNullOrEmpty ->
            if (isNullOrEmpty) return false
        }
        etPhoneNumber.checkEmpty(R.string.warning_empty_phone_number).also { isNullOrEmpty ->
            if (isNullOrEmpty) return false
        }
        return true
    }

    private fun EditText.checkEmpty(@StringRes warningStringResId: Int): Boolean {
        return text.isNullOrEmpty().also { isNullOrEmpty ->
            if (isNullOrEmpty) {
                showToast(warningStringResId)
                (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).also {
                    requestFocus()
                    it.showSoftInput(this, 0)
                }
            }
        }
    }

    private fun showToast(@StringRes stringResId: Int) {
        toast.apply {
            setText(stringResId)
            show()
        }
    }

    private fun confirmToFinish() {
        if (etName.text.isNotEmpty() || etPhoneNumber.text.isNotEmpty() || etMail.text.isNotEmpty() || tvBirthday.text.isNotEmpty() || etMemo.text.isNotEmpty()) {
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