package com.beehomie.wallpaperjsonbuilder.presentation.util_ui_components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ChipInputField(
    label: String,
    suggestions: List<String>,
    selectedItems: MutableList<String>,
) {
    var inputText by remember { mutableStateOf("") }
    val filteredSuggestions = suggestions.filter {
        (it.contains(inputText, ignoreCase = true) || it.startsWith(inputText, ignoreCase = true)) && it !in selectedItems
    }

    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var highlightedIndex by remember { mutableStateOf(0) }
    var textFieldSize by remember { mutableStateOf(IntSize.Zero) }

    val focusManager = LocalFocusManager.current
    val listState = rememberLazyListState()

    // Keep highlighted item visible
    LaunchedEffect(highlightedIndex) {
        if (expanded && highlightedIndex in filteredSuggestions.indices) {
            listState.animateScrollToItem(highlightedIndex)
        }
    }

    Box(
        modifier = Modifier
            .onPointerEvent(PointerEventType.Press) {
                if (!isFocused) expanded = false
            }
    ) {
        Column {
            FlowRow {
                selectedItems.forEach { item ->
                    Chip(
                        text = item,
                        onRemove = { selectedItems.remove(item) }
                    )
                }
            }

            Box {
                TextField(
                    value = inputText,
                    onValueChange = {
                        inputText = it
                        expanded = true
                    },
                    label = { Text(label) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                            if (!focusState.isFocused) expanded = false
                        }
                        .onGloballyPositioned { coordinates ->
                            textFieldSize = coordinates.size
                        }
                        .onPreviewKeyEvent { event ->
                            if (event.type == KeyEventType.KeyDown) {
                                when (event.key) {
                                    Key.Enter -> {
                                        if (expanded && highlightedIndex in filteredSuggestions.indices) {
                                            selectedItems.add(filteredSuggestions[highlightedIndex])
                                            inputText = ""
                                            expanded = false
                                            true
                                        } else if (inputText.isNotBlank()) {
                                            selectedItems.add(inputText)
                                            inputText = ""
                                            expanded = false
                                            true
                                        } else false
                                    }

                                    Key.DirectionDown -> {
                                        highlightedIndex = (highlightedIndex + 1).coerceAtMost(filteredSuggestions.lastIndex)
                                        true
                                    }

                                    Key.DirectionUp -> {
                                        highlightedIndex = (highlightedIndex - 1).coerceAtLeast(0)
                                        true
                                    }

                                    else -> false
                                }
                            } else false
                        }
                )

                if (expanded && filteredSuggestions.isNotEmpty()) {
                    val itemHeight = 48.dp

                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                            .offset(y = with(LocalDensity.current) { textFieldSize.height.toDp() })
                            .heightIn(max = itemHeight * 3)
                            .background(MaterialTheme.colorScheme.surface)
                            .border(1.dp, MaterialTheme.colorScheme.outline)
                    ) {
                        itemsIndexed(filteredSuggestions) { index, suggestion ->
                            val isSelected = index == highlightedIndex
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.1f) else Color.Transparent)
                                    .clickable {
                                        selectedItems.add(suggestion)
                                        inputText = ""
                                        expanded = false
                                        focusRequester.requestFocus()
                                    }
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = suggestion,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(inputText, isFocused, filteredSuggestions) {
        expanded = isFocused && inputText.isNotBlank() && filteredSuggestions.isNotEmpty()
        highlightedIndex = if (filteredSuggestions.isNotEmpty()) 0 else -1
    }
}



@Composable
fun Chip(text: String, onRemove: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.LightGray,
        modifier = Modifier.padding(end = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text, modifier = Modifier.padding(start = 8.dp))
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Close, contentDescription = "Remove")
            }
        }
    }
}
