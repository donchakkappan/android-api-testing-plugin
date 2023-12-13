plugins {
    id("maven-publish")
    id("signing")
}

val mavenCentralUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
val mavenSnapshotUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots")

val snapshotIdentifier = "-SNAPSHOT"

val developerName: String = project.findProperty("DEVELOPER_NAME") as String? ?: System.getenv("DEVELOPER_NAME")
val developerID: String = project.findProperty("DEVELOPER_ID") as String? ?: System.getenv("DEVELOPER_ID")
val developerMail: String = project.findProperty("DEVELOPER_MAIL") as String? ?: System.getenv("DEVELOPER_MAIL")

val groupIdTxt: String = project.findProperty("GROUP_ID") as String? ?: System.getenv("GROUP_ID")
val artifactIdTxt: String = project.findProperty("ARTIFACT_ID") as String? ?: System.getenv("ARTIFACT_ID")

val libraryName: String = project.findProperty("LIBRARY_NAME") as String? ?: System.getenv("LIBRARY_NAME")

val scmUrl: String = project.findProperty("SCM_URL") as String? ?: System.getenv("SCM_URL")
val scmConnection: String = project.findProperty("SCM_CONNECTION") as String? ?: System.getenv("SCM_CONNECTION")
val scmDeveloperConnection: String =
    project.findProperty("SCM_DEVELOPER_CONNECTION") as String? ?: System.getenv("SCM_DEVELOPER_CONNECTION")

val userName: String = System.getenv("USER_NAME") ?: ""
val pwd: String = System.getenv("PASSWORD") ?: ""


afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                if (plugins.hasPlugin("com.android.library")) {
                    from(components["release"]) // android-aar include code file and res
                } else {
                    from(components["java"]) //java-jar include code file
                }

                groupId = groupIdTxt
                artifactId = artifactIdTxt
                version = Sdk.VERSION_NAME

                pom {
                    name.set(libraryName)
                    description.set("Client Library for Android API Testing Plugin")
                    url.set("https://github.com/donchakkappan/android-api-testing-plugin/tree/main/api-monitor-client")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set(developerID)
                            name.set(developerName)
                            email.set(developerMail)
                        }
                    }
                    scm {
                        url.set(scmUrl)
                        connection.set(scmConnection)
                        developerConnection.set(scmDeveloperConnection)
                    }
                }
            }
        }

        repositories {
            maven {
                name = libraryName
                url = when {
                    Sdk.VERSION_NAME.endsWith(snapshotIdentifier) -> {
                        mavenSnapshotUrl
                    }

                    else -> {
                        mavenCentralUrl
                    }
                }
                credentials {
                    username = userName
                    password = pwd
                }
            }
        }
    }

    signing {
        useGpgCmd()
        sign(publishing.publications.getByName("release"))
    }
}