package com.ddowney.speedrunbrowser

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class ExampleCompose : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      NewsStory()
    }
  }
}

@Composable
fun NewsStory() {
  MaterialTheme {
    Column(
      modifier = Modifier.padding(16.dp),
    ) {
      Image(
        painter = painterResource(R.drawable.header),
        contentDescription = null,
        modifier = Modifier
          .height(180.dp)
          .fillMaxHeight()
          .clip(shape = RoundedCornerShape(4.dp)),
        contentScale = ContentScale.Crop,
      )
      Spacer(modifier = Modifier.height(16.dp))
      Text(
        text = "A day wandering through the sandhills " +
          "in Shark Fin Cove, and a few of the " +
          "sights I saw",
        style = typography.h6,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
      )
      Text(
        text = "Davenport, California",
        style = typography.body2,
      )
      Text(
        text = "December 2018",
        style = typography.body2,
      )
      Spacer(modifier = Modifier.height(16.dp))
      UserList(userList = listOf(
        ExampleUser("Dan Downey", "Android Engineer"),
        ExampleUser("Feibhár Baldwin-Wall", "Music Educator"),
      ))
    }
  }
}

@Preview
@Composable
fun PreviewNewsStory() {
  NewsStory()
}

data class ExampleUser(
  val name: String,
  val title: String,
)

@Composable
fun UserRow(user: ExampleUser, onClick: (ExampleUser) -> Unit) {
  Row(
    modifier = Modifier
      .clickable { onClick(user) }
      .fillMaxWidth()
      .padding(8.dp),
  ) {
    Column(
      modifier = Modifier.padding(
        start = 8.dp,
        end = 8.dp,
      )
    ) {
      Text(
        text = user.name,
        fontWeight = FontWeight.Bold,
        style = typography.h6,
      )
      Text(
        text = user.title,
        style = typography.body2,
      )
    }
  }
}

@Composable
fun UserList(userList: List<ExampleUser>) {
  LazyColumn(modifier = Modifier.padding(8.dp)) {
    items(userList) { user ->
      UserRow(user = user) {
        Log.d("wubalub", "Clicked on ${user.name}")
      }
    }
  }
}