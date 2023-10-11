package com.kakaotechcamp.step2.week1

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONObject


class MainActivity : AppCompatActivity(), ContactItemView.OnItemClickListener {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var addBtn: FloatingActionButton
    private lateinit var llContact: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerActivityResult()
        initViews()
    }

    private fun initViews() {
        llContact = findViewById(R.id.ll_contact)
        addBtn = findViewById<FloatingActionButton?>(R.id.fab_add).apply {
            setOnClickListener {
                val intent = Intent(context, AddContactActivity::class.java)
                activityResultLauncher.launch(intent)
            }
        }
    }

    private fun registerActivityResult() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.getStringExtra(CONTACT_DATA)?.let {
                        addContactItemView(contactJsonString = it)
                    }
                }
            }
    }

    private fun addContactItemView(contactJsonString: String) {
        ContactItemView(this).apply {
            setContactData(contactJsonString)
            setOnItemClickListener(this@MainActivity)
            llContact.addView(this)
        }
    }

    override fun onItemClickListener(contactData: JSONObject) {
        Intent(this, ContactDetailActivity::class.java)
            .apply {
                putExtra(CONTACT_DATA, contactData.toString())
                startActivity(this)
            }
    }
}