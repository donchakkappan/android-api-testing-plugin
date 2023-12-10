/*
 * Copyright [2023] [Don Chakkappan donchakkappan@gmail.com]
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

package com.don.test_app.models


import com.don.test_app.models.Data
import com.don.test_app.models.Support
import com.google.gson.annotations.SerializedName

data class ResponseListUsers(
    @SerializedName("data")
    var data: List<Data>,
    @SerializedName("page")
    var page: Int,
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("support")
    var support: Support,
    @SerializedName("total")
    var total: Int,
    @SerializedName(
        "total_pages")
    var totalPages: Int
)