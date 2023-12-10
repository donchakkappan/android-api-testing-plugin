# android-api-testing-plugin
This is an IntelliJ plugin to monitor all Android App network requests.You can also create custom requests using this plugin.

This is completely developed in **Kotlin**.

## Demo

https://github.com/donchakkappan/android-api-testing-plugin/assets/6335190/43d0ad1a-9769-42c1-a628-f515a3093134

## How to use

- Install IntelliJ Plugin from here https://plugins.jetbrains.com/plugin/23291-android-api-testing
- Add Android library dependency
```
implementation(project(":api-monitor-client"))
```
- Add Interceptor to your HTTP Client
```
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
