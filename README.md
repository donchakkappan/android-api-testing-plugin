# android-api-testing-plugin


![Build](https://github.com/donchakkappan/android-api-testing-plugin/actions/workflows/android.yml/badge.svg)

This is an IntelliJ plugin to monitor all Android App network requests.You can also create custom requests using this plugin.

This is completely developed in **Kotlin**.

## Demo

https://github.com/donchakkappan/android-api-testing-plugin/assets/6335190/43d0ad1a-9769-42c1-a628-f515a3093134

## How to use

#### 1.] Install IntelliJ Plugin from marketplace
<img width="953" alt="plugin_marketplace" src="https://github.com/donchakkappan/android-api-testing-plugin/assets/6335190/268b0c4d-2b38-4345-8982-a62117f59b0b">

#### 2.] Add Android library dependency
```
implementation("io.github.donchakkappan:api-monitor-client:1.0.0")
```
#### 3.] Add Interceptor to your HTTP Client
```
val lib = APIMonitoringLib
val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(lib.monitoringInterceptor(context,"IP Address of your Machine"))
            .build()
```

## License

```license
Copyright [2023] [Don Chakkappan donchakkappan@gmail.com]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
