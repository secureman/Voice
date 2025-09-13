package voice.features.playbackScreen.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Subtitles
import androidx.compose.material.icons.filled.SubtitlesOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import voice.core.strings.R as StringsR

@Composable
fun SubtitleToggleButton(
  showSubtitles: Boolean,
  onToggleSubtitles: () -> Unit,
  modifier: Modifier = Modifier,
) {
  IconButton(
    onClick = onToggleSubtitles,
    modifier = modifier,
  ) {
    Icon(
      imageVector = if (showSubtitles) Icons.Filled.Subtitles else Icons.Filled.SubtitlesOff,
      contentDescription = stringResource(id = StringsR.string.subtitles),
    )
  }
}