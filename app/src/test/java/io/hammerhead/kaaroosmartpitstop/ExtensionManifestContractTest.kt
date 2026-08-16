package io.hammerhead.kaaroosmartpitstop

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class ExtensionManifestContractTest {
    @Test
    fun manifest_must_not_register_a_launcher_activity() {
        val manifest = loadExistingFile("AndroidManifest.xml")

        val hasLauncher = manifest.contains("android.intent.action.MAIN") &&
            manifest.contains("android.intent.category.LAUNCHER")

        assertFalse(
            "Karoo extensions must not register as a launcher activity.",
            hasLauncher,
        )
    }

    @Test
    fun manifest_must_register_the_karoo_extension_service() {
        val manifest = loadExistingFile("AndroidManifest.xml")

        assertTrue(
            "KAROO_EXTENSION action must be present.",
            manifest.contains("io.hammerhead.karooext.KAROO_EXTENSION"),
        )
        assertTrue(
            "EXTENSION_INFO metadata must be present.",
            manifest.contains("io.hammerhead.karooext.EXTENSION_INFO"),
        )
    }

    @Test
    fun extension_metadata_must_define_the_extension_id() {
        val metadata = loadExistingFile("extension_info.xml")

        assertTrue(
            "Extension id must be set for the Karoo extension.",
            metadata.contains("id=\"karoo-smart-pitstop\""),
        )
    }

    private fun loadExistingFile(fileName: String): String {
        val start = File(System.getProperty("user.dir"))
        val relativePaths = listOf(
            "app/src/main/$fileName",
            "app/src/main/res/xml/$fileName",
            "src/main/$fileName",
            "src/main/res/xml/$fileName",
            fileName,
        )

        val candidates = buildList {
            var current = start
            while (current != null) {
                relativePaths.forEach { relative ->
                    add(File(current, relative))
                }
                current = current.parentFile
            }
        }

        val found = candidates.firstOrNull { it.exists() && it.isFile }
            ?: throw IllegalStateException(
                "Could not locate $fileName from ${start.absolutePath}. " +
                    "Tried: ${candidates.joinToString { it.absolutePath }}",
            )

        return found.readText()
    }
}
