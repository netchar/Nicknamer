/*
 * Copyright  (c) 2021 Eugene Glushankov
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

package com.netchar.nicknamer.presentation.ui.about

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.netchar.nicknamer.R

sealed class Contact(@DrawableRes val image: Int, @StringRes val description: Int) {
    object Instagram : Contact(R.drawable.ic_instagram, R.string.about_label_follow_on_instagram)
    object LinkedIn : Contact(R.drawable.ic_linkedin, R.string.about_label_connect_on_linked_id)
    object Mail : Contact(R.drawable.ic_gmail, R.string.about_label_send_email)
}