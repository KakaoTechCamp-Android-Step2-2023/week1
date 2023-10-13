package com.kakaotechcamp.step2.week1.compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kakaotechcamp.step2.week1.CONTACT_DATA
import com.kakaotechcamp.step2.week1.R
import com.kakaotechcamp.step2.week1.compose.ui.theme.Typography
import com.kakaotechcamp.step2.week1.compose.ui.theme.Week1Theme

class ComposeMainActivity : ComponentActivity() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerActivityResult()
        setContent {
            Week1Theme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(id = R.dimen.default_padding)),
                ) {
                    EmptyNotice(modifier = Modifier.align(Alignment.Center))
                    AddBtn(modifier = Modifier.align(Alignment.BottomEnd)) {
                        val intent = Intent(baseContext, ComposeAddContactActivity::class.java)
                        activityResultLauncher.launch(intent)
                    }
                }
            }
        }
    }

    private fun registerActivityResult() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                if (result.resultCode == RESULT_OK) {
                    result.data?.getStringExtra(CONTACT_DATA)?.let {
                        //AddContactItemView(contactJsonString = it)
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