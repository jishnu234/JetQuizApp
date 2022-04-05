package com.example.triviaquiz.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.triviaquiz.model.QuestionItem
import com.example.triviaquiz.screens.TriviaViewModel
import com.example.triviaquiz.util.AppColors


@Composable
fun Questions(
    question: QuestionItem,
    viewModel: TriviaViewModel,
    questionIndex: MutableState<Int>,
    nextQuestion: (Int) -> Unit
) {

    val pathEffect = PathEffect.dashPathEffect(
        intervals = floatArrayOf(10f, 10f),
        phase = 0f
    )

    val choicesState = remember(question) {
        question.choices.toMutableList()
    }
    val correctAnswerState = remember(question) {
        mutableStateOf<Boolean?>(null)
    }
    val answerState = remember(question) {
        mutableStateOf<Int?>(null)
    }
    val updateAnswer: (Int) -> Unit = remember(question) {
        {
            answerState.value = it
            correctAnswerState.value = (choicesState[it] == question.answer)
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppColors.mDarkPurple
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
//            if (questionIndex.value > 5) showProgress(progress = questionIndex.value)
            viewModel.data.value.data?.toMutableList()?.let {
                QuestionLayout(
                    questionNo = questionIndex.value + 1,
                    outOff = it.size
                )
            }
            DrawDottedLine(pathEffect)
            Column(modifier = Modifier) {
                Text(
                    text = question.question,
                    modifier = Modifier
                        .align(alignment = Alignment.Start)
                        .fillMaxHeight(0.3f),
                    fontSize = 17.sp,
                    color = AppColors.mOffWhite,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 22.sp
                )

                //choices
                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .border(
                                width = 4.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        AppColors.mOffDarkPurple,
                                        AppColors.mOffDarkPurple
                                    )
                                ),
                                shape = RoundedCornerShape(12.dp),
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStartPercent = 50,
                                    topEndPercent = 50,
                                    bottomEndPercent = 50,
                                    bottomStartPercent = 50
                                )
                            )
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        RadioButton(
                            selected = (answerState.value == index),
//                            enabled = radioState,
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 16.dp),
                            colors = RadioButtonDefaults.colors(

                                if (correctAnswerState.value == true &&
                                    answerState.value == index
                                ) Color.Green.copy(alpha = 0.2f)
                                else Color.Red.copy(alpha = 0.2f)
                            )
                        )//end rb
                        val annotatedString = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontSize = 17.sp,
                                    color = if (correctAnswerState.value == true &&
                                        answerState.value == index
                                    ) Color.Green.copy(alpha = 0.5f)
                                    else if (correctAnswerState.value == false &&
                                        answerState.value == index
                                    ) Color.Red.copy(alpha = 0.5f)
                                    else AppColors.mOffWhite
                                )
                            ) {
                                append(answerText)
                            }
                        }
                        Text(text = annotatedString)
                    }//end of row
                }// forEach loop


            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier
                        .padding(12.dp)
                        .width(LocalConfiguration.current.screenWidthDp.dp / 2),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = AppColors.mBlue
                    ),
                    shape = RoundedCornerShape(percent = 25),
                    onClick = {
                        nextQuestion(questionIndex.value)
                    },
                ) {
                    Text(
                        modifier = Modifier
                            .padding(
                                top = 4.dp,
                                start = 12.dp,
                                end = 16.dp,
                                bottom = 4.dp
                            ),
                        fontWeight = FontWeight.SemiBold,
                        text = "Next"
                    )
                }
            }// end Button //end Button
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun QuestionLayout(questionNo: Int, outOff: Int) {
    Text(text = buildAnnotatedString {
        withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGray,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            ) {
                append("Question $questionNo/")
                withStyle(
                    style = SpanStyle(
                        color = AppColors.mLightGray,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append("$outOff")
                }
            }
        }
    })
}

@Composable
fun DrawDottedLine(pathEffect: PathEffect) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .height(1.dp)
    ) {
        drawLine(
            color = AppColors.mLightGray,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}


@Preview(showBackground = true)
@Composable
fun showProgress(progress: Int = 12) {

    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFFF95075),
            Color(0xFFBE6BE5)
        )
    )

    val progressFactor by remember(progress) {
        mutableStateOf(progress * 0.005)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .height(45.dp)
            .border(
                2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(AppColors.mLightPurple, AppColors.mLightPurple)
                ),
                shape = RoundedCornerShape(34.dp),
            )
            .clip(RoundedCornerShape(50)),

    ) {

        Button(
            modifier = Modifier
                .fillMaxWidth(progressFactor.toFloat())
                .padding(vertical = 4.dp)
                .background(brush = gradient),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent
            ),
            enabled = false,
            elevation = null,
            contentPadding = PaddingValues(1.dp),
            onClick = { /*TODO*/ }) {

        }

    }
}