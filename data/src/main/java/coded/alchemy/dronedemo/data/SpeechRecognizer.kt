package coded.alchemy.dronedemo.data

import android.content.Context
import android.util.Log
import coded.alchemy.dronedemo.data.util.extractTextValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.vosk.LibVosk
import org.vosk.LogLevel
import org.vosk.Model
import org.vosk.Recognizer
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.SpeechStreamService
import org.vosk.android.StorageService
import java.io.IOException

/**
 * SpeechRecognizer.kt
 *
 * This class performs Speech Recognition and returns text.
 * Speech Recognition is performed by [LibVosk].
 * @author Taji Abdullah
 * */
object SpeechRecognizer : RecognitionListener {
    private val TAG = this.javaClass.simpleName
    private lateinit var model: Model
    private var speechService: SpeechService? = null
    private var speechStreamService: SpeechStreamService? = null
    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private var isListeningPaused = false

    private val _resultText = MutableStateFlow("")
    val resultText: StateFlow<String> = _resultText

    /**
     * Class initialization starts the Vosk logger.
     * */
    init {
        LibVosk.setLogLevel(LogLevel.INFO)
    }

    /**
     * Partial speech results returned in a json string.
     * @param hypothesis the json result string.
     * */
    override fun onPartialResult(hypothesis: String?) {
        Log.v(TAG, "onPartialResult: $hypothesis")
    }

    /**
     * Speech results returned in a json string.
     * @param hypothesis the json result string.
     * */
    override fun onResult(hypothesis: String?) {
        Log.d(TAG, "onResult: ${hypothesis?.extractTextValue()}")
        hypothesis?.let { string ->
            _resultText.value = string.extractTextValue().toString()
        }
    }

    /**
     * @param hypothesis the json result string.
     * */
    override fun onFinalResult(hypothesis: String?) {
        Log.d(TAG, "onFinalResult: $hypothesis")
    }

    /**
     * Error results
     * @param exception the error thrown.
     * */
    override fun onError(exception: Exception?) {
        Log.e(TAG, "onError: $exception")
        destroy()
    }

    /**
     * Timeout
     * */
    override fun onTimeout() {
        Log.w(TAG, "onTimeout: ")
        destroy()
    }

    /**
     * Initialize the speech recognition model.
     * */
    fun initSpeechModel(context: Context) {
        Log.d(TAG, "initSpeechModel: ")
        scope.launch {
            StorageService.unpack(
                context,
                "model-en-us",
                "model",
                { unpackedModel: Model ->
                    model = unpackedModel
                    Log.d(TAG, "speech model unpacked")
                },
                { exception: IOException ->
                    Log.e(TAG, "initSpeechModel: $exception")
                }
            )
        }
    }

    /**
     * start listening to perform speech recognition.
     * */
    fun startListening() {
        Log.d(TAG, "start: ")
        if (speechService == null) initializeSpeechRecognition()
        if (isListeningPaused) pause(false)
        speechService?.startListening(SpeechRecognizer)
    }

    /**
     * Pause speech recognition.
     * */
    fun pause(isPaused: Boolean) {
        speechService?.let { speechService ->
            Log.d(TAG, "pause: $isPaused")
            speechService.setPause(isPaused)
            isListeningPaused = isPaused
        }
    }

    /**
     * This function provides a way destroy resources.
     * */
    fun destroy() {
        Log.d(TAG, "destroy: ")
        speechService?.let { service ->
            service.stop()
            service.shutdown()
        }

        speechStreamService?.let { service ->
            service.stop()
        }

        scope.cancel()
    }

    /**
     * Initialization of speech recognition.
     * */
    private fun initializeSpeechRecognition() {
        Log.d(TAG, "initializeSpeechRecognition: ")
        try {
            val sampleRate = 16000.0f
            val recognizer = Recognizer(model, sampleRate)
            speechService = SpeechService(recognizer, sampleRate)
        } catch (exception: IOException) {
            Log.e(TAG, "initializeSpeechRecognition fail : $exception")
        }
    }
}