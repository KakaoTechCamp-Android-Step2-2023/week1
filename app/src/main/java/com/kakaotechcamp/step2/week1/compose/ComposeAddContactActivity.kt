@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.kakaotechcamp.step2.week1.compose

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import com.kakaotechcamp.step2.week1.compose.ui.theme.Typography
import com.kakaotechcamp.step2.week1.compose.ui.theme.Week1Theme
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class ComposeAddContactActivity : ComponentActivity() {
    private val inputs = mutableMapOf(
        CONTACT_NAME to "",
        CONTACT_PHONE_NUMBER to "",
        CONTACT_MAIL to "",
        CONTACT_BIRTHDAY to "",
        CONTACT_GENDER to "",
        CONTACT_MEMO to "",
    )
    private val dateFormat = SimpleDateFormat(DATE_FORMAT)
    private lateinit var toast: Toast

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            confirmToFinish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    private fun initViews() {
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)

        setContent {
            Week1Theme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.default_padding)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val scrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .verticalScroll(scrollState),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.profile_size)),
                            painter = painterResource(id = R.drawable.baseline_face_24),
                            contentDescription = stringResource(id = R.string.profile_image),
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.default_padding)))
                        ContactTextField(
                            hint = stringResource(id = R.string.name)
                        ) { textValue ->
                            inputs[CONTACT_NAME] = textValue
                        }
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_padding)))
                        ContactTextField(
                            hint = stringResource(id = R.string.phone_number),
                            keyboardType = KeyboardType.Phone
                        ) { textValue ->
                            inputs[CONTACT_PHONE_NUMBER] = textValue
                        }
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_padding)))
                        ContactTextField(
                            hint = stringResource(id = R.string.mail),
                            keyboardType = KeyboardType.Email
                        ) { textValue ->
                            inputs[CONTACT_MAIL] = textValue
                        }
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_padding)))

                        var visible by remember { mutableStateOf(false) }

                        if (!visible) {
                            Text(
                                text = stringResource(id = R.string.more),
                                modifier = Modifier.clickable {
                                    visible = true
                                },
                            )
                        }

                        AnimatedVisibility(visible = visible) {
                            Column {
                                Birthday {
                                    showDatePickerDialog()
                                }
                                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_padding)))
                                GenderRadioGroup()
                                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.small_padding)))
                                ContactTextField(
                                    hint = stringResource(id = R.string.memo),
                                    isSingleLine = false
                                ) { textValue ->
                                    inputs[CONTACT_MEMO] = textValue
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(dimensionResource(id = R.dimen.btn_height)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clickable { confirmToFinish() },
                                text = stringResource(id = R.string.cancel),
                                style = Typography.titleLarge,
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .clickable {
                                        if (checkDataValidation()) {
                                            sendContactData()
                                            finish()
                                        }
                                    },
                                text = stringResource(id = R.string.save),
                                style = Typography.titleLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    private fun extractData(): JSONObject {
        return JSONObject(
            mapOf(
                CONTACT_NAME to inputs.getOrDefault(CONTACT_NAME, ""),
                CONTACT_PHONE_NUMBER to inputs.getOrDefault(CONTACT_PHONE_NUMBER, ""),
                CONTACT_MAIL to inputs.getOrDefault(CONTACT_MAIL, ""),
                CONTACT_BIRTHDAY to inputs.getOrDefault(CONTACT_BIRTHDAY, ""),
                CONTACT_GENDER to inputs.getOrDefault(CONTACT_GENDER, ""),
                CONTACT_MEMO to inputs.getOrDefault(CONTACT_MEMO, ""),
            )
        )
    }

    private fun sendContactData() {
        Intent(this, ComposeMainActivity::class.java).apply {
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
                inputs[CONTACT_BIRTHDAY] = dateFormat.format(calendar.time)
            }
            show()
        }
    }

    private fun getBirthdayCalendar(): Calendar {
        val birthdayText = inputs.getOrDefault(CONTACT_NAME, DEFAULT_BIRTHDAY)
        val birthdayDate = dateFormat.parse(birthdayText) ?: Date()
        return Calendar.getInstance().apply { time = birthdayDate }
    }

    private fun checkDataValidation(): Boolean {
        inputs.getOrDefault(CONTACT_NAME, "").checkEmpty(R.string.warning_empty_name)
            .also { isNullOrEmpty ->
                if (isNullOrEmpty) return false
            }
        inputs.getOrDefault(CONTACT_PHONE_NUMBER, "")
            .checkEmpty(R.string.warning_empty_phone_number)
            .also { isNullOrEmpty ->
                if (isNullOrEmpty) return false
            }
        return true
    }

    private fun String.checkEmpty(@StringRes warningStringResId: Int): Boolean {
        return isEmpty().also { isEmpty ->
            if (isEmpty) {
                showToast(warningStringResId)
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
        if (inputs.values.any { it.isNotEmpty() }) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactTextField(
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isSingleLine: Boolean = true,
    textChanged: (String) -> Unit
) {
    var textState by rememberSaveable {
        mutableStateOf("")
    }
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = dimensionResource(id = R.dimen.et_height)),
        value = textState,
        textStyle = Typography.bodyLarge,
        placeholder = {
            Text(
                text = hint,
                style = Typography.bodyLarge,
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        onValueChange = { textValue ->
            textState = textValue
            textChanged(textValue)
        },
        singleLine = isSingleLine,
        colors = TextFieldDefaults.textFieldColors(
            textColor = colorResource(id = R.color.fgPrimary),
            placeholderColor = colorResource(id = R.color.fgTertiary),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.default_radius))
    )
}

@Composable
@Preview
fun PreviewContactTextField() {
    ContactTextField(hint = "hello") {}
}

@Composable
fun Birthday(onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.et_height))
            .background(
                color = colorResource(id = R.color.bgSecondary),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.default_radius))
            )
            .padding(dimensionResource(id = R.dimen.default_padding))
            .clickable {
                onClick?.invoke()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.birthday),
            style = Typography.bodyLarge.copy(color = colorResource(id = R.color.fgTertiary))
        )
    }
}

@Preview
@Composable
fun PreviewBirthday() {
    Birthday()
}

@Composable
fun GenderRadioGroup() {
    val selectedValue = remember { mutableStateOf("") }

    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val onChangeState: (String) -> Unit = { selectedValue.value = it }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(id = R.dimen.et_height))
            .background(
                color = colorResource(id = R.color.bgSecondary),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.default_radius))
            )
            .padding(dimensionResource(id = R.dimen.default_padding)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(id = R.string.gender),
            style = Typography.bodyLarge.copy(color = colorResource(id = R.color.fgTertiary))
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .selectable(
                    selected = isSelectedItem(GENDER_FEMALE),
                    onClick = { onChangeState(GENDER_FEMALE) },
                    role = Role.RadioButton
                )
        ) {
            RadioButton(
                selected = isSelectedItem(GENDER_FEMALE),
                onClick = null
            )
            Text(
                text = stringResource(id = R.string.female),
            )
        }
        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.large_padding)))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .selectable(
                    selected = isSelectedItem(GENDER_MALE),
                    onClick = { onChangeState(GENDER_MALE) },
                    role = Role.RadioButton
                )
        ) {
            RadioButton(
                selected = isSelectedItem(GENDER_MALE),
                onClick = null
            )
            Text(
                text = stringResource(id = R.string.male),
            )
        }
    }
}

@Preview
@Composable
fun PreviewGenderRadioGroup() {
    GenderRadioGroup()
}
