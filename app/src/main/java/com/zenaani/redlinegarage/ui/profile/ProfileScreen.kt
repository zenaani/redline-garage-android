import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.zenaani.redlinegarage.R
import com.zenaani.redlinegarage.core.components.BikeCard
import com.zenaani.redlinegarage.core.components.performance.PerfColors
import com.zenaani.redlinegarage.ui.profile.ProfileViewModel
import com.zenaani.redlinegarage.ui.theme.BodoniModa

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    Scaffold(
        topBar = { },
    ) { innerPadding ->

//        val uiState = viewModel.uiState.collectAsState().value

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {

                Box() {
                    Image(
                        painter = painterResource(R.drawable.honda_cb300r),
                        contentDescription = null,
                        modifier = Modifier
                            .offset(x = 40.dp)
                            .height(250.dp)
                    )

                    // Gradient Overlay (left -> right)
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Black, Color.Transparent
                                    )
                                )
                            )
                    )

                    // Gradient Overlay (bottom -> top)
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .height(50.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent, Color.Black
                                    )
                                )
                            )
                    )

                    // Profile Intro
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(start = 8.dp, end = 8.dp, bottom = 24.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(66.dp)
                                .background(Color.White, shape = CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.honda_cb300r),
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop,
                                contentDescription = null
                            )
                        }
                        Spacer(modifier = Modifier.size(4.dp))
                        Text(
                            "Zenaani Suresh",
                            fontSize = 32.sp,
                            lineHeight = 2.sp,
                            color = Color.White,
                            fontFamily = BodoniModa,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            "zenaani@gmail.com",
                            fontSize = 12.sp,
                            lineHeight = 2.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                RiderStatsRow(
                    bikesOwned = 2,
                    favouriteBrand = "Honda",
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "MY GARAGE",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontFamily = BodoniModa,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "View all →",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.ExtraLight,
                        letterSpacing = 1.sp
                    )
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .height(600.dp)
                        .padding(horizontal = 12.dp)
                ) {
                    items(2) {
                        BikeCard(
                            brand = "Honda",
                            model = "CB300R",
                            segment = "Naked Sport",
                            variant = "Standard",
                            onClick = { }
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun RiderStatsRow(
    bikesOwned: Int,
    favouriteBrand: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = PerfColors.Card,
        border = BorderStroke(1.dp, PerfColors.CardBorder),
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Min),
        ) {
            StatCell(
                label = "BIKES OWNED",
                value = bikesOwned.toString(),
                modifier = Modifier.weight(1f),
            )
            VerticalDivider(
                thickness = 1.dp,
                color = PerfColors.CardBorder,
            )
            StatCell(
                label = "FAVOURITE BRAND",
                value = favouriteBrand,
                modifier = Modifier.weight(1f),
            )
        }
    }
}

@Composable
private fun StatCell(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        Text(
            text = label,
            fontSize = 9.sp,
            color = PerfColors.TextSecondary,
            fontWeight = FontWeight.Medium,
            letterSpacing = 2.sp,
        )
        Text(
            text = value,
            fontSize = 26.sp,
            color = Color.White,
            fontFamily = BodoniModa,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 28.sp,
        )
    }
}
