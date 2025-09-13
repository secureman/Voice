package voice.features.playbackScreen.subtitle

import java.io.File
import java.io.InputStream
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

/**
 * Represents a single subtitle line with timing information.
 */
data class SubtitleLine(
    val index: Int,
    val startTime: Duration,
    val endTime: Duration,
    val text: String,
    val isActive: Boolean = false
)

/**
 * Parses an SRT file into a list of SubtitleLine objects.
 */
fun parseSrtFile(file: File): List<SubtitleLine> {
    return file.inputStream().use { parseSrtStream(it) }
}

/**
 * Parses an SRT input stream into a list of SubtitleLine objects.
 */
fun parseSrtStream(inputStream: InputStream): List<SubtitleLine> {
    val lines = inputStream.bufferedReader().readLines()
    return parseSrtLines(lines)
}

/**
 * Parses SRT content as a list of strings into SubtitleLine objects.
 */
fun parseSrtLines(lines: List<String>): List<SubtitleLine> {
    val subtitles = mutableListOf<SubtitleLine>()
    var index = 0
    var startTime: Duration? = null
    var endTime: Duration? = null
    val textBuilder = StringBuilder()
    var lineIndex = 0
    
    while (lineIndex < lines.size) {
        val line = lines[lineIndex].trim()
        lineIndex++
        
        if (line.isEmpty()) {
            // End of a subtitle entry
            if (index > 0 && startTime != null && endTime != null && textBuilder.isNotEmpty()) {
                subtitles.add(
                    SubtitleLine(
                        index = index,
                        startTime = startTime,
                        endTime = endTime,
                        text = textBuilder.toString().trim()
                    )
                )
            }
            
            // Reset for next subtitle
            index = 0
            startTime = null
            endTime = null
            textBuilder.clear()
            continue
        }
        
        if (index == 0) {
            // Parse subtitle index
            try {
                index = line.toInt()
            } catch (e: NumberFormatException) {
                // Skip invalid index
                continue
            }
        } else if (startTime == null && endTime == null && line.contains("-->")) {
            // Parse timestamp line
            val times = line.split("-->").map { it.trim() }
            if (times.size == 2) {
                startTime = parseTimestamp(times[0])
                endTime = parseTimestamp(times[1])
            }
        } else {
            // Parse subtitle text
            if (textBuilder.isNotEmpty()) {
                textBuilder.append("\n")
            }
            textBuilder.append(line)
        }
    }
    
    // Add the last subtitle if there is one
    if (index > 0 && startTime != null && endTime != null && textBuilder.isNotEmpty()) {
        subtitles.add(
            SubtitleLine(
                index = index,
                startTime = startTime,
                endTime = endTime,
                text = textBuilder.toString().trim()
            )
        )
    }
    
    return subtitles
}

/**
 * Parses an SRT timestamp (hh:mm:ss,SSS) into milliseconds.
 */
private fun parseTimestamp(timestamp: String): Duration {
    val parts = timestamp.split(":", ",")
    if (parts.size != 4) return Duration.ZERO
    
    try {
        val hours = parts[0].toLong()
        val minutes = parts[1].toLong()
        val seconds = parts[2].toLong()
        val milliseconds = parts[3].toLong()
        
        return ((hours * 3600 + minutes * 60 + seconds) * 1000 + milliseconds).milliseconds
    } catch (e: NumberFormatException) {
        return Duration.ZERO
    }
}

/**
 * Finds the active subtitle for the current playback position.
 */
fun findActiveSubtitle(subtitles: List<SubtitleLine>, currentPosition: Duration): SubtitleLine? {
    return subtitles.find { subtitle ->
        currentPosition >= subtitle.startTime && currentPosition <= subtitle.endTime
    }
}

/**
 * Updates the active state of subtitles based on the current playback position.
 */
fun updateSubtitlesActiveState(subtitles: List<SubtitleLine>, currentPosition: Duration): List<SubtitleLine> {
    return subtitles.map { subtitle ->
        subtitle.copy(isActive = currentPosition >= subtitle.startTime && currentPosition <= subtitle.endTime)
    }
}