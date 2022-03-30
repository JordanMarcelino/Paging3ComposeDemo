package com.example.unsplashcompose.ui.util

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.unsplashcompose.BuildConfig
import com.example.unsplashcompose.R
import com.example.unsplashcompose.data.model.UnsplashImage
import com.example.unsplashcompose.data.model.Urls
import com.example.unsplashcompose.data.model.User
import com.example.unsplashcompose.data.model.UserLinks

@Composable
fun ImageList(items: LazyPagingItems<UnsplashImage>) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = items, key = { image ->
            image.id
        }) { image ->
            UnsplashItem(image)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun UnsplashItem(
    item: UnsplashImage?
) {

    val context = LocalContext.current

    Box(
        modifier = Modifier
            .height(300.dp)
            .fillMaxWidth()
            .clickable {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://unsplash.com/@${item?.user?.username}?utm_source=ImageCompose&utm_medium=referral")
                )
                startActivity(context, browserIntent, null)
            },
        contentAlignment = Alignment.BottomCenter
    ) {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(context)
                .crossfade(1000)
                .data(item?.urls?.regular)
                .placeholder(R.drawable.ic_baseline_image_24)
                .error(R.drawable.ic_baseline_image_24)
                .build(),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Surface(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .alpha(ContentAlpha.medium),
            color = Color.Black
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Photo by ")
                        withStyle(
                            style = SpanStyle(
                                color = Color.White,
                                fontWeight = FontWeight.Black
                            )
                        ) {
                            append(item?.user?.username.toString())
                        }
                        append(" on Unsplash")
                    }, style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                LikeIcon(like = item?.likes, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
fun LikeIcon(
    modifier: Modifier = Modifier,
    like: Int?
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_heart_free_icon_font),
            contentDescription = "Like",
            tint = Color.Red
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = like.toString(),
            color = Color.White,
            fontWeight = FontWeight.ExtraBold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Preview
@Composable
fun ImagePreview() {
    val item = UnsplashImage(
        id = "a",
        likes = 100,
        urls = Urls("a"),
        user = User("Bambang", UserLinks(""))
    )
    UnsplashItem(item = item)
}