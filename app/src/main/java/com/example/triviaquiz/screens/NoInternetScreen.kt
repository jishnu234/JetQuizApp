package com.example.triviaquiz.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaquiz.R


@Composable
fun NoInternetAvailable() {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.DarkGray
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {

            Image(
                modifier = Modifier
                    .fillMaxHeight(0.5f),
                painter = painterResource(id = R.drawable.sleeping_owl),
                contentDescription = "sleeping owl"
            )

            Column(modifier = Modifier
                .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ){
                Text(text = buildAnnotatedString {
                    withStyle(style = ParagraphStyle(
                        textIndent = TextIndent.None,
                        lineHeight = 24.sp
                    )
                    ){

                        withStyle(style = SpanStyle(
                            color = Color.Black,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Light,
                            fontSize = 20.sp
                        )
                        ){
                            append("    Check your")
                            withStyle(style = SpanStyle(
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 27.sp,
                                color = Color.Black
                            )
                            ){
                                append("\nConnection...!")
                            }
                        }
                    }
                })
                Button(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    onClick = {
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 12.dp),
                        text = "Try Again")
                }
            }
        }
    }
}