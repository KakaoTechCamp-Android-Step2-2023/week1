package com.kakaotechcamp.step2.week1.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.kakaotechcamp.step2.week1.CONTACT_DATA
import com.kakaotechcamp.step2.week1.CONTACT_NAME
import com.kakaotechcamp.step2.week1.R
import com.kakaotechcamp.step2.week1.compose.ui.theme.Typography
import com.kakaotechcamp.step2.week1.compose.ui.theme.Week1Theme
import org.json.JSONObject

class ComposeMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        setContent {
            Week1Theme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.default_padding)),
                ) {

                    var contactJsonString by rememberSaveable { mutableStateOf("") }

                    val result = rememberLauncherForActivityResult(
                        ActivityResultContracts.StartActivityForResult()
                    ) { result ->
                        if (result.resultCode == RESULT_OK) {
                            result.data?.getStringExtra(CONTACT_DATA)?.let {
                                contactJsonString = it
                            }
                        }
                    }
                    EmptyNotice(modifier = Modifier.align(Alignment.Center))
                    val scrollState = rememberScrollState()
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            .padding(dimensionResource(id = R.dimen.default_padding))
                    ) {
                        if (contactJsonString.isNotEmpty()) {
                            ContactItem(contactJsonString) {
                                Intent(baseContext, ComposeContactDetailActivity::class.java)
                                    .apply {
                                        putExtra(CONTACT_DATA, contactJsonString)
                                        startActivity(this)
                                    }
                            }
                        }
                    }
                    AddBtn(modifier = Modifier.align(Alignment.BottomEnd)) {
                        val intent = Intent(baseContext, ComposeAddContactActivity::class.java)
                        result.launch(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun AddBtn(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    FloatingActionButton(
        modifier = modifier,
        containerColor = colorResource(id = R.color.yellow),
        interactionSource = remember { MutableInteractionSource() },
        onClick = { onClick.invoke() },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_add_24),
            contentDescription = stringResource(
                id = R.string.add_contact
            )
        )
    }
}

@Composable
@Preview
fun AddBtnPreview() {
    AddBtn()
}

@Composable
fun EmptyNotice(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.empty_notice),
        style = Typography.bodyLarge
    )
}

@Preview(showBackground = true)
@Composable
fun EmptyNoticePreview() {
    Week1Theme {
        EmptyNotice()
    }
}

@Composable
fun ContactItem(contactJsonString: String, onClick: () -> Unit) {
    val contactJson = JSONObject(contactJsonString)
    if (!contactJson.has(CONTACT_NAME)) return
    val name = contactJson.getString(CONTACT_NAME)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke()
            },
    ) {
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.small_padding)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CircleShape
            Text(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                colorResource(id = R.color.yellow),
                                colorResource(id = R.color.orange)
                            )
                        ),
                        shape = CircleShape,
                    )
                    .size(dimensionResource(id = R.dimen.tv_short_name_size))
                    .padding(dimensionResource(id = R.dimen.default_padding)),
                text = name.first().toString(),
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Text(
                text = name,
                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.default_padding)),
                fontSize = 20.sp,
                color = colorResource(id = R.color.fgTertiary)
            )
        }
    }
}

@Preview
@Composable
fun PreviewContactItem() {
    ContactItem("안녕하세요") {}
}