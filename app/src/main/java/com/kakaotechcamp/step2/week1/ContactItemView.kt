package com.kakaotechcamp.step2.week1

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONObject

class ContactItemView : LinearLayout {
    init {
        initViews()
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    private var contactData: JSONObject? = null
    private lateinit var tvShortName: TextView
    private lateinit var tvName: TextView
    private var itemClickListener: OnItemClickListener? = null

    private fun initViews() {
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE).apply {
            addView(inflate(context, R.layout.item_contact, null))
        }
        tvShortName = findViewById(R.id.tv_short_name)
        tvName = findViewById(R.id.tv_name)
        findViewById<LinearLayout>(R.id.ll_contact_item).setOnClickListener {
            itemClickListener?.onItemClickListener(contactData ?: JSONObject())
        }
    }

    fun setContactData(contactJsonString: String) {
        contactData = JSONObject(contactJsonString)
        updateName()
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        itemClickListener = onItemClickListener
    }

    private fun updateName() {
        contactData?.getString(CONTACT_NAME)?.let { name ->
            tvShortName.text = name.first().toString()
            tvName.text = name
        }
    }

    interface OnItemClickListener {
        fun onItemClickListener(contactData: JSONObject)
    }
}