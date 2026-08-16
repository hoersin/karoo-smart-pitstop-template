package io.hammerhead.kaaroosmartpitstop.extension

import android.content.Context
import android.widget.RemoteViews
import io.hammerhead.karooext.extension.DataTypeImpl
import io.hammerhead.karooext.internal.Emitter
import io.hammerhead.karooext.internal.ViewEmitter
import io.hammerhead.karooext.models.DataPoint
import io.hammerhead.karooext.models.DataType
import io.hammerhead.karooext.models.StreamState
import io.hammerhead.karooext.models.ViewConfig
import io.hammerhead.kaaroosmartpitstop.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private val CYCLING_WORDS = listOf(
    "Grind", "Surge", "Crest", "Tempo", "Break",
    "Chase", "Climb", "Float", "Draft", "Punch",
    "Spin", "Stomp", "Glide", "Crush", "Flow",
    "Blast", "Grit", "Zone", "Push", "Fly",
)

private const val TYPE_ID = "cycling-word"
private const val WORD_INTERVAL_MS = 5000L

class CyclingWordDataType(extension: String) : DataTypeImpl(extension, TYPE_ID) {

    private var streamJob: Job? = null
    private var viewJob: Job? = null
    private var currentIndex = 0

    override fun startStream(emitter: Emitter<StreamState>) {
        streamJob?.cancel()
        streamJob = CoroutineScope(Dispatchers.IO).launch {
            emitter.onNext(StreamState.Searching)
            while (true) {
                val dataPoint = DataPoint(
                    dataTypeId = dataTypeId,
                    values = mapOf(DataType.Field.SINGLE to currentIndex.toDouble()),
                )
                emitter.onNext(StreamState.Streaming(dataPoint))
                delay(WORD_INTERVAL_MS)
                currentIndex = (currentIndex + 1) % CYCLING_WORDS.size
            }
        }
        emitter.setCancellable { streamJob?.cancel() }
    }

    override fun startView(context: Context, config: ViewConfig, emitter: ViewEmitter) {
        viewJob?.cancel()
        viewJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                val views = RemoteViews(context.packageName, R.layout.datatype_cycling_word)
                views.setTextViewText(R.id.cycling_word_text, CYCLING_WORDS[currentIndex])
                emitter.updateView(views)
                delay(WORD_INTERVAL_MS)
            }
        }
        emitter.setCancellable { viewJob?.cancel() }
    }
}
