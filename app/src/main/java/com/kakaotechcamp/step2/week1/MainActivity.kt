package com.kakaotechcamp.step2.week1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var addBtn: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.getStringExtra(CONTACT_DATA)?.let {
                        Log.e("aschae", it)
                    }
                }
            }
    }

    private fun initViews() {
        addBtn = findViewById<FloatingActionButton?>(R.id.fab_add).apply {
            setOnClickListener {
                val intent = Intent(context, AddContactActivity::class.java)
                activityResultLauncher.launch(intent)
            }
        }
    }
}