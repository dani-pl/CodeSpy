package com.danipl.codespy.util.ui

import android.net.Uri
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.danipl.codespy.R

@Composable
fun LoadImageWithPlaceholderAndError(imageUri: Uri) {
    AsyncImage(
        model = imageUri,
        contentDescription = "App icon",
        modifier = Modifier.size(40.dp),
        placeholder = painterResource(R.mipmap.ic_code_spy_round),   // Placeholder while loading
        error = painterResource(R.mipmap.ic_code_spy_round),         // Error image if loading fails
    )
}