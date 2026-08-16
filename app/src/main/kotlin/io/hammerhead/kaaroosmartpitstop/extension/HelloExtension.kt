package io.hammerhead.kaaroosmartpitstop.extension

import io.hammerhead.karooext.extension.DataTypeImpl
import io.hammerhead.karooext.extension.KarooExtension

class HelloExtension : KarooExtension("karoo-smart-pitstop", "3") {
    @Suppress("ACCIDENTAL_OVERRIDE")
    fun getTypes(): List<DataTypeImpl> = listOf(
        CyclingWordDataType(extension),
    )
}
