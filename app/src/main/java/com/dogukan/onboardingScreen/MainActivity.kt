package com.dogukan.onboardingScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.dogukan.onboardingScreen.data.OnBoardingData
import com.dogukan.onboardingScreen.ui.theme.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState

class MainActivity : ComponentActivity() {
    private val items = ArrayList<OnBoardingData>()

    @ExperimentalPagerApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(this, R.color.grey)
        setContent {
            DrinkWaterReminderTheme() {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    items.add(OnBoardingData(
                        R.drawable.a,
                        "Hi, I'm Hamza",
                        " I'm currently developing mobile applications with Android Studio."
                    ))
                    items.add(OnBoardingData(
                        R.drawable.a,
                        "Career",
                        "I want to continue my software career in\n" +
                                "an environment where I can take myself\n" +
                                "to the next level in the field of mobile\n" +
                                "development.\n"
                    ))



                    val pagerState = rememberPagerState(
                        pageCount = items.size,
                        initialOffScreenLimit = 2,
                        infiniteLoop = false,
                        initialPager = 0
                    )
                    onBoardingPager(item = items, pagerState = pagerState, modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Grey900)
                    )
                }
            }
        }
    }
    @ExperimentalPagerApi
    @Composable
    fun onBoardingPager(
        item : List<OnBoardingData>,
        pagerState : PagerState,
        modifier: Modifier = Modifier
    ){
        Box(modifier = modifier){
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalPager(state = pagerState) {page->
                    Column(modifier = Modifier
                        .padding(top = 60.dp)
                        .fillMaxWidth() , horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painter = painterResource(id = item[page].image), contentDescription = item[page].title,
                            modifier = Modifier
                                .height(250.dp)
                                .fillMaxWidth()

                        )
                        Text(text = item[page].title,
                            modifier = Modifier.padding(top = 50.dp),
                            color = androidx.compose.ui.graphics.Color.White,
                            style = Typography.body1,
                            fontSize = 30.sp,
                            fontFamily = FontFamily.Default
                        )
                        Text(text = item[page].description,
                            modifier = Modifier.padding(top = 30.dp, start = 20.dp, end = 20.dp),
                            color = androidx.compose.ui.graphics.Color.White,
                            style = Typography.body1,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                pagerIndicator(size = item.size, currentPage = pagerState.currentPage)

            }
            Box(modifier = Modifier.align(Alignment.BottomCenter)){
                BottomSection(currentPager = pagerState.currentPage)
            }
        }
    }
    @ExperimentalPagerApi
    @Composable
    fun rememberPagerState(
        @androidx.annotation.IntRange(from = 0) pageCount : Int,
        @androidx.annotation.IntRange(from = 0) initialPager : Int = 0,
        @FloatRange(from = 0.0, to = 1.0) initialPageOffset : Float = 0f,
        @androidx.annotation.IntRange(from = 1) initialOffScreenLimit: Int = 1,
        infiniteLoop : Boolean = false
    ) : PagerState = rememberSaveable(saver = PagerState.Saver){
        PagerState(
            pageCount =pageCount,
            currentPage = initialPager,
            currentPageOffset = initialPageOffset,
            offscreenLimit = initialOffScreenLimit,
            infiniteLoop = infiniteLoop
        )
    }
    @Composable
    fun pagerIndicator(size : Int, currentPage : Int){
        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 60.dp)

        ) {
            repeat(size){
                Indicator(isSelected = it == currentPage)
            }
        }
    }
    @Composable
    fun Indicator(
        isSelected : Boolean
    ){
        val width = animateDpAsState(targetValue = if (isSelected) 25.dp else 10.dp)

        Box(modifier = Modifier
            .padding(1.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(if (isSelected) Orange else Grey300.copy(alpha = 0.5f))
        )
    }
    @Composable
    fun BottomSection(currentPager : Int){
        Row(modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth(),
            horizontalArrangement = if (currentPager != items.size-1) Arrangement.SpaceBetween else Arrangement.Center
        ) {
            if (currentPager == items.size-1){
                OutlinedButton(onClick = { },

                    shape = RoundedCornerShape(50),
                    modifier = Modifier.padding(start = 40.dp, end = 40.dp  )) {
                    Text(
                        text = "GetStarted",
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 40.dp),
                        color = Grey900
                    )
                }
            }
            else
            {
                SkipNextButton(text = "Skip", modifier = Modifier.padding(start = 20.dp))
                SkipNextButton(text = "Next", modifier = Modifier.padding(end = 20.dp))
            }



        }
    }
    @Composable
    fun SkipNextButton(text : String, modifier: Modifier){
        Text(text = text,
            modifier = modifier,
            style = Typography.body1,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            color = Grey300
        )
    }
}