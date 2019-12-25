/**
 * Copyright (c) Rasheed Sulayman.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.r4sh33d.slidingupmenu.utils

// TODO calculate chunk size based on available space.
internal const val horizontalScrollChunkSize = 8

internal fun splitMenuList(
    list: MutableList<MenuModel>,
    scrollDirection: ScrollDirection
): List<List<MenuModel>> =
    list.chunked(if (scrollDirection == ScrollDirection.HORIZONTAL) horizontalScrollChunkSize else list.size)

internal fun getTopLeftCornerRadius(cornerRadius: Float): FloatArray = floatArrayOf(
    cornerRadius, cornerRadius, cornerRadius, cornerRadius,
    0f, 0f, 0f, 0f
)
