package com.zenaani.redlinegarage.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.ThumbDown
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zenaani.redlinegarage.data.dto.UserReviewDto
import com.zenaani.redlinegarage.data.dto.UserReviewPagedDto
import com.zenaani.redlinegarage.ui.theme.BodoniModa
import com.zenaani.redlinegarage.ui.theme.BlackSecondary
import com.zenaani.redlinegarage.ui.theme.LightGrey
import com.zenaani.redlinegarage.ui.theme.NeutralBlack
import com.zenaani.redlinegarage.ui.theme.Red
import kotlin.math.roundToInt

// ---------------------------------------------------------------------------
// Star distribution helpers
// ---------------------------------------------------------------------------

/** Computes the fraction (0f..1f) of reviews for each star tier (5 down to 1). */
private fun starFractions(reviews: List<UserReviewDto>): List<Float> {
    if (reviews.isEmpty()) return List(5) { 0f }
    return (5 downTo 1).map { star ->
        reviews.count { it.stars.roundToInt() == star } / reviews.size.toFloat()
    }
}

// ---------------------------------------------------------------------------
// Section root
// ---------------------------------------------------------------------------

/**
 * Full "User Reviews" section: summary header with rating breakdown bars,
 * followed by individual review cards for every entry in [pagedDto].
 */
@Composable
fun UserReviewSection(
    pagedDto: UserReviewPagedDto,
    modifier: Modifier = Modifier,
) {
    val reviews = pagedDto.content
    val avgRating = if (reviews.isEmpty()) 0.0
    else reviews.sumOf { it.stars } / reviews.size
    val fractions = starFractions(reviews)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── Header row ───────────────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "User Reviews",
                fontSize = 22.sp,
                color = Color.White,
                fontFamily = BodoniModa,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "See all (${pagedDto.totalElements}) →",
                fontSize = 11.sp,
                color = Color.White,
                fontWeight = FontWeight.Light,
                letterSpacing = 0.5.sp
            )
        }

        // ── Summary card ─────────────────────────────────────────────────
        ReviewSummaryCard(
            avgRating = avgRating,
            totalReviews = pagedDto.totalElements,
            fractions = fractions
        )

        // ── Individual review cards ───────────────────────────────────────
        reviews.forEachIndexed { index, review ->
            ReviewCard(review = review)
            if (index < reviews.lastIndex) {
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = Color.White.copy(alpha = 0.08f)
                )
            }
        }
    }
}

@Composable
private fun ReviewSummaryCard(
    avgRating: Double,
    totalReviews: Int,
    fractions: List<Float>,       // index 0 = 5★, index 4 = 1★
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(BlackSecondary)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Left: Big number + stars + count
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "%.1f".format(avgRating),
                    fontSize = 36.sp,
                    color = Color.White,
                    fontFamily = BodoniModa,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 52.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                StarRow(rating = avgRating, starSize = 18.dp)
                Text(
                    text = "$totalReviews reviews",
                    fontSize = 10.sp,
                    color = LightGrey,
                    fontWeight = FontWeight.Light,
                    letterSpacing = 0.5.sp
                )
            }

            // Right: Breakdown bars (5★ → 1★)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                fractions.forEachIndexed { index, fraction ->
                    val starValue = 5 - index
                    RatingBar(star = starValue, fraction = fraction)
                }
            }
        }
    }
}

@Composable
private fun RatingBar(
    star: Int,
    fraction: Float,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = "$star",
            fontSize = 10.sp,
            color = LightGrey,
            fontWeight = FontWeight.Normal
        )
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = LightGrey,
            modifier = Modifier.size(10.dp)
        )
        // Track
        Box(
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White.copy(alpha = 0.08f))
        ) {
            // Fill
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction.coerceIn(0f, 1f))
                    .height(4.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Red)
            )
        }
        Text(
            text = "${(fraction * 100).roundToInt()}%",
            fontSize = 10.sp,
            color = LightGrey,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.width(26.dp)
        )
    }
}

@Composable
private fun ReviewCard(
    review: UserReviewDto,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Top row: avatar + name/badge + date + stars
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Avatar circle with first letter
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = review.name.firstOrNull()?.uppercaseChar()?.toString() ?: "?",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = review.name,
                        fontSize = 13.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                    // "Verified Buyer" badge
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star, // placeholder — reuse star for ✓ look
                            contentDescription = null,
                            tint = Red,
                            modifier = Modifier.size(9.dp)
                        )
                        Text(
                            text = "Verified Buyer",
                            fontSize = 9.sp,
                            color = Red,
                            fontWeight = FontWeight.Light,
                            letterSpacing = 0.3.sp
                        )
                    }
                }
            }

            // Right side: date + stars
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = formatReviewDate(review.id),
                    fontSize = 10.sp,
                    color = LightGrey,
                    fontWeight = FontWeight.Light
                )
                StarRow(rating = review.stars, starSize = 13.dp)
            }
        }

        // Review body text
        Text(
            text = review.message,
            fontSize = 12.sp,
            color = Color.White.copy(alpha = 0.75f),
            fontWeight = FontWeight.Light,
            lineHeight = 18.sp
        )

        // Helpful row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Helpful?",
                fontSize = 10.sp,
                color = LightGrey,
                fontWeight = FontWeight.Light
            )
            HelpfulButton(icon = "👍", count = 0)
            HelpfulButton(icon = "👎", count = 0)
        }
    }
}

@Composable
private fun HelpfulButton(icon: String, count: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = icon, fontSize = 12.sp)
        Text(
            text = count.toString(),
            fontSize = 10.sp,
            color = LightGrey,
            fontWeight = FontWeight.Light
        )
    }
}


@Composable
fun StarRow(
    rating: Double,
    modifier: Modifier = Modifier,
    starSize: androidx.compose.ui.unit.Dp = 16.dp,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(1.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..5) {
            val filled = rating >= i
            Icon(
                imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (filled) Red else LightGrey,
                modifier = Modifier.size(starSize)
            )
        }
    }
}

private fun formatReviewDate(id: Long): String {
    val days = listOf(
        "2 Jan 2026", "15 Mar 2026", "5 Oct 2026", "12 Oct 2026",
        "20 Jul 2026", "8 Aug 2026", "1 Nov 2025", "30 Sep 2026"
    )
    return days[(id % days.size).toInt()]
}

@Preview(showBackground = true, backgroundColor = 0xFF0B0B0E, widthDp = 400)
@Composable
private fun UserReviewSectionPreview() {
    UserReviewSection(
        pagedDto = UserReviewPagedDto(
            content = listOf(
                UserReviewDto(
                    id = 3,
                    name = "Sanjay P.",
                    stars = 5.0,
                    message = "Super solid and reliable. Been using this for 2 months now. Holds my phone really well even on bumpy roads. No vibrations, no issues. Installation was easy and it looks premium too."
                ),
                UserReviewDto(
                    id = 2,
                    name = "Anjali R.",
                    stars = 4.0,
                    message = "Good product, minor issues. Overall a great holder. Fits my phone perfectly and the grip is strong. The only reason for 4 stars is that the rotation is a bit stiff at first, but it gets better with use."
                ),
            ),
            last = true,
            page = 0,
            pageSize = 10,
            totalElements = 98,
            totalPages = 10
        )
    )
}
