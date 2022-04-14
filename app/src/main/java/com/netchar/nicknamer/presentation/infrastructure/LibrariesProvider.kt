/*
 * Copyright (c) 2021 Eugene Glushankov
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

interface LibrariesProvider {
    data class Library(val name: String, val description: String, val link: String)

    fun getLibraries(): List<Library>
}

internal class LibraryProviderImpl : LibrariesProvider {
    private val libraries = mutableListOf<LibrariesProvider.Library>()

    init {
        libraries.add(LibrariesProvider.Library("Timber", "This is a logger with a small, extensible API which provides utility on top of Android's normal Log class.", "https://github.com/JakeWharton/timber#license"))
        libraries.add(LibrariesProvider.Library("Koin", "A pragmatic lightweight dependency injection framework for Kotlin developers.", "https://github.com/InsertKoinIO/koin"))
        libraries.add(LibrariesProvider.Library("Lingver", "Lingver is a library to manage your application locale and language.", "https://github.com/YarikSOffice/lingver"))
    }

    override fun getLibraries(): List<LibrariesProvider.Library> {
        return libraries
    }
}