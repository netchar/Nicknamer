/*
 * Copyright © 2021 Eugene Glushankov
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

package com.netchar.nicknamer.presentation.infrastructure

import com.netchar.nicknamer.presentation.infrastructure.LibrariesProvider.Library

internal class LibraryProviderImpl : LibrariesProvider {
    private val libraries = mutableListOf<Library>()

    init {
        libraries.add(Library("Timber", "This is a logger with a small, extensible API which provides utility on top of Android's normal Log class.", "https://github.com/JakeWharton/timber#license"))
        libraries.add(Library("Koin", "A pragmatic lightweight dependency injection framework for Kotlin developers.", "https://github.com/InsertKoinIO/koin"))
    }

    override fun getLibraries(): List<Library> {
        return libraries
    }
}