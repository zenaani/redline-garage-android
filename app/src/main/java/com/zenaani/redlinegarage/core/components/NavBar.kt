package com.zenaani.redlinegarage.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compare
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zenaani.redlinegarage.R

@Composable
fun NavBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(56.dp)
            .fillMaxWidth()
            .background(shape = CircleShape, color = Color(0xFF202024)),
    ) {

        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth(0.25f)
                .padding(horizontal = 8.dp)
                .background(shape = CircleShape, color = Color.White)
                .align(Alignment.CenterStart)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .height(24.dp),
                tint = Color(0xFF171719)
            )

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .height(24.dp),
                tint = Color(0xFF929299)
            )

            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .height(24.dp),
                tint = Color(0xFF929299)
            )

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .height(24.dp),
                tint = Color(0xFF929299)
            )

        }


    }

}

@Preview
@Composable
fun NavBarPreview() {
    NavBar()
}