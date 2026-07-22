package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BuildConfig
import com.example.data.Product
import com.example.data.SampleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

@Serializable
data class GenerateContentRequest(val contents: List<Content>, val systemInstruction: Content? = null)
@Serializable
data class Content(val parts: List<Part>)
@Serializable
data class Part(val text: String)
@Serializable
data class GenerateContentResponse(val candidates: List<Candidate>)
@Serializable
data class Candidate(val content: Content)

interface GeminiApiService {
    @POST("v1beta/models/gemini-3.5-flash:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GenerateContentRequest
    ): GenerateContentResponse
}

object RetrofitClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .readTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .writeTimeout(60, java.util.concurrent.TimeUnit.SECONDS)
        .build()

    val service: GeminiApiService by lazy {
        val json = Json { ignoreUnknownKeys = true }
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
        retrofit.create(GeminiApiService::class.java)
    }
}

data class ChatMessage(val text: String, val isUser: Boolean)

class ChatViewModel : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(ChatMessage("سلام! من پشتیبان هوشمند لونا آرت هستم. چطور می‌تونم راهنماییتون کنم؟", false))
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val storeInfoContext = """
        شما پشتیبان هوشمند فروشگاه اینترنتی "لونا آرت" (Luna Art) هستید. این فروشگاه محصولات دکوراتیو سنگ مصنوعی و شمع‌های دست‌ساز لوکس می‌فروشد.
        قوانین:
        - مودب، حرفه‌ای و صمیمی باشید.
        - پاسخ‌ها را کوتاه و مفید ارائه دهید.
        - در مورد قیمت‌ها بگویید: "به دلیل تنوع طرح و سفارشی‌سازی، برای استعلام قیمت دقیق لطفاً از طریق فرم سفارش یا شبکه‌های اجتماعی اقدام کنید."
        - هزینه ارسال: ۳۵ هزار تومان.
        - اینستاگرام: @luna_art_80
        - روبیکا: @luna_Art___80
    """.trimIndent()

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        
        val userMsg = ChatMessage(text, true)
        _messages.value = _messages.value + userMsg
        
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiKey = BuildConfig.GEMINI_API_KEY
                if (apiKey.isEmpty() || apiKey == "MY_GEMINI_API_KEY") {
                    _messages.value = _messages.value + ChatMessage("خطا: کلید API تنظیم نشده است.", false)
                    _isLoading.value = false
                    return@launch
                }

                // Construct full prompt using conversation history to save tokens but keep context
                val promptBuilder = StringBuilder()
                val recentMessages = _messages.value.takeLast(5)
                recentMessages.forEach { msg ->
                    val role = if (msg.isUser) "کاربر" else "شما"
                    promptBuilder.append("$role: ${msg.text}\n")
                }

                val request = GenerateContentRequest(
                    contents = listOf(Content(listOf(Part(promptBuilder.toString())))),
                    systemInstruction = Content(listOf(Part(storeInfoContext)))
                )
                
                val response = RetrofitClient.service.generateContent(apiKey, request)
                val replyText = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text ?: "متأسفم، متوجه نشدم."
                
                _messages.value = _messages.value + ChatMessage(replyText, false)
            } catch (e: Exception) {
                _messages.value = _messages.value + ChatMessage("خطا در برقراری ارتباط با سرور.", false)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
