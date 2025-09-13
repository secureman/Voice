# Subtitle Feature Testing Guide

## Overview
This guide explains how to test the new subtitle synchronization feature that replaces the cover image in the audiobook player.

## Setup

1. **Prepare an SRT file**:
   - A sample SRT file (`sample_subtitle.srt`) has been provided in the project root.
   - To use it with your audiobook, copy this file to the same directory as your audiobook file.
   - Rename the SRT file to match your audiobook filename (keeping the .srt extension).
   - For example, if your audiobook is named `MyBook.mp3`, rename the subtitle file to `MyBook.srt`.

2. **Build and install the app**:
   - Build the app using Android Studio or Gradle.
   - Install it on your device or emulator.

## Testing the Feature

1. **Open an audiobook**:
   - Launch the app and select an audiobook that has a matching SRT file.

2. **Toggle subtitles**:
   - When the player screen opens, you'll see the cover image by default.
   - Tap the subtitle toggle button in the app bar (looks like a subtitle icon).
   - The cover image should be replaced with the subtitle view.

3. **Play the audiobook**:
   - Start playing the audiobook.
   - The subtitles should automatically scroll and highlight based on the current playback position.
   - The current subtitle line should be highlighted.

4. **Toggle back to cover**:
   - Tap the subtitle toggle button again to switch back to the cover image view.

## Expected Behavior

- Subtitles should be synchronized with audio playback.
- The current subtitle line should be highlighted.
- Toggling between cover and subtitles should work seamlessly.
- All existing player functionality (play/pause, seek, etc.) should continue to work.

## Troubleshooting

- If subtitles don't appear, check that the SRT file is correctly named and placed in the same directory as the audiobook file.
- If synchronization seems off, verify the timestamps in your SRT file match the audio content.

## Implementation Details

The subtitle feature has been implemented using Jetpack Compose with the following components:

- `SubtitleLine.kt`: Data class for subtitle entries
- `SubtitleUtils.kt`: Parser for SRT files
- `SubtitleView.kt`: Composable for displaying subtitles
- `SubtitleToggleButton.kt`: UI control for toggling subtitle display

The implementation preserves all existing audio playback functionality while adding the new subtitle display capability.