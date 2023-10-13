package com.kakaotechcamp.step2.week1.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
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

class ComposeContactDetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Week1Theme {
                // A surface container using the 'background' color from the theme
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {

                    JSONObject(intent.getStringExtra(CONTACT_DATA).orEmpty()).apply {
                        TitleWithContent(
                            stringResource(id = R.string.name),
                            getString(CONTACT_NAME).orEmpty()
                        )
                        TitleWithContent(
                            stringResource(id = R.string.phone_number),
                            getString(CONTACT_PHONE_NUMBER).orEmpty()
                        )
                        TitleWithContent(
                            stringResource(id = R.string.mail),
                            getString(CONTACT_MAIL).orEmpty()
                        )
                        TitleWithContent(
                            stringResource(id = R.string.birthday),
                            getString(CONTACT_BIRTHDAY).orEmpty()
                        )
                        TitleWithContent(
                            stringResource(id = R.string.memo),
                            getString(CONTACT_MEMO).orEmpty()
                        )
                        TitleWithContent(
                            stringResource(id = R.string.gender),
                            when (getString(CONTACT_GENDER).orEmpty()) {
                                GENDER_FEMALE -> stringResource(id = R.string.female)
                                GENDER_MALE -> stringResource(id = R.string.female)
                                else -> ""
                            }
                        )

                    }
                }
            }
        }
    }
}


@Composable
fun TitleWithContent(title: String, content: String, isMultipleLine: Boolean = false) {
    if (content.isNotEmpty()) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                modifier = Modifier.weight(0.3f),
                style = Typography.titleSmall
            )
            Text(
                text = content,
                modifier = Modifier.weight(0.7f),
                maxLines = if (isMultipleLine) Int.MAX_VALUE else 1,
                overflow = if (isMultipleLine) TextOverflow.Clip else TextOverflow.Ellipsis,
                style = Typography.titleSmall
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TitleWithContentPreview() {
    TitleWithContent(title = "제목", content = "내용")
}

@Preview
@Composable
fun TitleWithContentPreviewWithoutContent() {
    TitleWithContent(title = "제목", content = "")
}