import com.soywiz.korge.gradle.*
import com.ncorti.ktfmt.gradle.tasks.*

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
	alias(libs.plugins.korge)
    id("com.ncorti.ktfmt.gradle") version "0.11.0"
    kotlin("multiplatform") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
}

korge {
	id = "io.github.mrt_map.orient"
// To enable all targets at once

	//targetAll()

// To enable targets based on properties/environment variables
	//targetDefault()

// To selectively enable targets

	targetJvm()
	//targetJs()
	//targetDesktop()
	//targetIos()
	//targetAndroidIndirect() // targetAndroidDirect()

}

ktfmt {
    kotlinLangStyle()
}
tasks.register<KtfmtFormatTask>("ktfmtPrecommit") {
    source = project.fileTree(rootDir)
    include("**/*.kt")
}
dependencies {
    commonMainImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
}
