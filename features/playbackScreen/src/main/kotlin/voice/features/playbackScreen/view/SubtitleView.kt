package voice.features.playbackScreen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import voice.features.playbackScreen.subtitle.SubtitleLine

/**
 * A composable that displays subtitles in a scrollable list with the active subtitle highlighted.
 * This component is designed to replace the Cover component in the player UI.
 */
@Composable
internal fun SubtitleView(
    subtitles: List<SubtitleLine>,
    activeSubtitleIndex: Int,
    onDoubleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()
    val hasSubtitles by remember(subtitles) { derivedStateOf { subtitles.isNotEmpty() } }
    
    // Scroll to active subtitle
    LaunchedEffect(activeSubtitleIndex) {
        if (activeSubtitleIndex >= 0 && activeSubtitleIndex < subtitles.size) {
            listState.animateScrollToItem(activeSubtitleIndex)
        }
    }
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
    ) {
        if (hasSubtitles) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(subtitles) { subtitle ->
                    val isActive = subtitle.index == activeSubtitleIndex + 1
                    SubtitleItem(
                        text = subtitle.text,
                        isActive = isActive
                    )
                }
            }
        } else {
            // Show a message when no subtitles are available
            Text(
                text = "No subtitles available",
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun SubtitleItem(
    text: String,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isActive) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        Color.Transparent
    }
    
    val textColor = if (isActive) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    
    val fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
    
    Text(
        text = text,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = fontWeight,
        color = textColor,
        textAlign = TextAlign.Center
    )
}