package com.theapache64.readgen.core

import com.theapache64.readgen.model.ProjectType
import java.io.File


/**
 * A class to identify project related things.
 */
object ProjectExpert {

    private const val PLUGIN_ANDROID = "com.android.tools.build:gradle"
    private const val PLUGIN_KOTLIN = "org.jetbrains.kotlin.jvm"

    /**
     * To get project type from given project root
     */
    fun getProjectType(projectRoot: File): ProjectType {
        val gradleFile = getGradleFile(projectRoot)
        checkIfGradleProject(gradleFile)

        val gradleContent = gradleFile.readText()
        return when {
            gradleContent.contains(PLUGIN_ANDROID) -> {
                ProjectType.ANDROID
            }
            gradleContent.contains(PLUGIN_KOTLIN) -> {
                ProjectType.JAR
            }
            else -> {
                ProjectType.GENERIC
            }
        }
    }

    private fun checkIfGradleProject(gradleFile: File) {
        if (!gradleFile.exists()) {
            error("${gradleFile.parentFile.name} is not a gradle project")
        }
    }

    private fun getGradleFile(projectRoot: File): File {
        var gradleFile = projectRoot.resolve("build.gradle")
        if (gradleFile.exists().not()) {
            gradleFile = projectRoot.resolve("build.gradle.kts")
        }
        return gradleFile
    }
}