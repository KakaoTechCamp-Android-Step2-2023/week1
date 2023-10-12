package com.kakaotechcamp.step2.week1

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import org.json.JSONObject

class ContactDetailActivity : AppCompatActivity() {

    private var contactData: JSONObject? = null
    private lateinit var tvName: TextView
    private lateinit var tvPhoneNumber: TextView
    private lateinit var tvMail: TextView
    private lateinit var groupMail: Group
    private lateinit var tvBirthday: TextView
    private lateinit var groupBirthday: Group
    private lateinit var tvGender: TextView
    private lateinit var groupGender: Group
    private lateinit var tvMemo: TextView
    private lateinit var groupMemo: Group
    private lateinit var ivProfile: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        initViews()
        setContactData()
        updateViews()
    }

    private fun initViews() {
        ivProfile = findViewById(R.id.iv_profile)
        tvName = findViewById(R.id.tv_name)
        tvPhoneNumber = findViewById(R.id.tv_phone_number)
        tvMail = findViewById(R.id.tv_mail)
        groupMail = findViewById(R.id.group_mail)
        tvBirthday = findViewById(R.id.tv_birthday)
        groupBirthday = findViewById(R.id.group_birthday)
        tvGender = findViewById(R.id.tv_gender)
        groupGender = findViewById(R.id.group_gender)
        tvMemo = findViewById(R.id.tv_memo)
        groupMemo = findViewById(R.id.group_memo)
    }

    private fun setContactData() {
        contactData = JSONObject(intent.getStringExtra(CONTACT_DATA).orEmpty())

        contactData?.apply {
            getString(CONTACT_NAME).orEmpty().let { tvName.text = it }
            getString(CONTACT_PHONE_NUMBER).orEmpty().let { tvPhoneNumber.text = it }
            getString(CONTACT_MAIL).orEmpty().let {
                groupMail.isVisible = it.isNotEmpty()
                tvMail.text = it
            }
            getString(CONTACT_BIRTHDAY).orEmpty().let {
                groupBirthday.isVisible = it.isNotEmpty()
                tvBirthday.text = it
            }
            getString(CONTACT_GENDER).orEmpty().let {
                groupGender.isVisible = it.isNotEmpty()
                when (it) {
                    GENDER_FEMALE -> {
                        ivProfile.setColorFilter(
                            ContextCompat.getColor(
                                baseContext,
                                R.color.red
                            )
                        )
                        tvGender.text = getString(R.string.female)
                    }

                    GENDER_MALE -> {
                        ivProfile.setColorFilter(
                            ContextCompat.getColor(
                                baseContext,
                                R.color.blue
                            )
                        )
                        tvGender.text = getString(R.string.mail)
                    }
                }
            }
            getString(CONTACT_MEMO).orEmpty().let {
                groupMemo.isVisible = it.isNotEmpty()
                tvMemo.text = it
            }
        }
    }

    private fun updateViews() {
        if (contactData == null) return

        contactData?.apply {
            tvName.text = if (has(CONTACT_NAME)) getString(CONTACT_NAME) else ""
        }
    }
}