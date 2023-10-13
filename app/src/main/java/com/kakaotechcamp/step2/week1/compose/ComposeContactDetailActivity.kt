package com.kakaotechcamp.step2.week1.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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
            val scrollState = rememberScrollState()
            Week1Theme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.default_padding))
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    JSONObject(intent.getStringExtra(CONTACT_DATA).orEmpty()).apply {
                        val genderValues: Pair<String, Color> =
                            when (getString(CONTACT_GENDER).orEmpty()) {
                                GENDER_FEMALE -> Pair(
                                    stringResource(id = R.string.female),
                                    colorResource(id = R.color.red)
                                )

                                GENDER_MALE -> Pair(
                                    stringResource(id = R.string.male),
                                    colorResource(id = R.color.blue)
                                )

                                else -> Pair("", Color.Black)
                            }

                        Image(
                            modifier = Modifier.size(dimensionResource(id = R.dimen.profile_size)),
                            painter = painterResource(id = R.drawable.baseline_face_24),
                            contentDescription = stringResource(id = R.string.profile_image),
                            colorFilter = ColorFilter.tint(genderValues.second)
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.default_padding)))
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
                            stringResource(id = R.string.gender),
                            genderValues.first
                        )
                        TitleWithContent(
                            title = stringResource(id = R.string.memo),
                            content = getString(CONTACT_MEMO).orEmpty(),
                            isMultipleLine = true
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
        Column() {

            Row(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.default_padding))
                    .height(
                        dimensionResource(id = R.dimen.et_height)
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    modifier = Modifier.weight(0.3f),
                    style = Typography.titleLarge.copy(fontSize = 20.sp)
                )
                Text(
                    text = content,
                    modifier = Modifier.weight(0.7f),
                    maxLines = if (isMultipleLine) Int.MAX_VALUE else 1,
                    overflow = if (isMultipleLine) TextOverflow.Clip else TextOverflow.Ellipsis,
                    style = Typography.bodyLarge.copy(color = colorResource(id = R.color.fgTertiary)),
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.default_padding)))
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