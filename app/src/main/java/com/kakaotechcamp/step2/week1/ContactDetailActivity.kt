package com.kakaotechcamp.step2.week1

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class ContactDetailActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private var contactData: JSONObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        initViews()
        setContactData()
        updateViews()
    }

    private fun initViews() {
        tvName = findViewById(R.id.tv_name)
    }

    private fun setContactData() {
        contactData = JSONObject(intent.getStringExtra(CONTACT_DATA).orEmpty())
    }

    private fun updateViews() {
        if (contactData == null) return

        contactData?.apply {
            tvName.text = if (has(CONTACT_NAME)) getString(CONTACT_NAME) else ""
        }
    }
}