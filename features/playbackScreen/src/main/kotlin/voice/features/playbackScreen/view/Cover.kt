package voice.features.playbackScreen.view

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import voice.core.ui.ImmutableFile
import voice.features.playbackScreen.subtitle.SubtitleLine
import voice.features.playbackScreen.subtitle.SubtitleView
import voice.core.strings.R as StringsR
import voice.core.ui.R as UiR

@Composable
internal fun Cover(
  onDoubleClick: () -> Unit,
  cover: ImmutableFile?,
  subtitles: List<SubtitleLine> = emptyList(),
  showSubtitles: Boolean = false,
) {
  val modifier = Modifier
    .fillMaxSize()
    .pointerInput(Unit) {
      detectTapGestures(
        onDoubleTap = {
          onDoubleClick()
        },
      )
    }
    .clip(RoundedCornerShape(20.dp))
    
  if (showSubtitles && subtitles.isNotEmpty()) {
    SubtitleView(
      subtitles = subtitles,
      onDoubleClick = onDoubleClick,
      modifier = modifier
    )
  } else {
    AsyncImage(
      modifier = modifier,
      contentScale = ContentScale.Crop,
      model = cover?.file,
      placeholder = painterResource(id = UiR.drawable.album_art),
      error = painterResource(id = UiR.drawable.album_art),
      contentDescription = stringResource(id = StringsR.string.cover),
    )
  }
}
