package com.example.geminapp

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class Viewmodel:ViewModel() {
    private val generativeModel = GenerativeModel(
        // The Gemini 1.5 models are versatile and work with both text-only and multimodal prompts
        modelName = "gemini-1.5-flash",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = BuildConfig.AI_API_KEY
    )
    private val _responseText = MutableLiveData<String>()
    val responseText: LiveData<String>
        get() = _responseText

    fun generateContent(image1: Bitmap?, image2: Bitmap?, occasion: String, season: String, location: String, preferences: String?) {
        val prompt = "Suggest an outfit for a $occasion during $season in $location. Preferences: $preferences."
        viewModelScope.launch {
            val inputContent = content {
                if(image1!=null) image(image1)
                if(image2!=null) image(image2)
                text(prompt)
            }
            val response = generativeModel.generateContent(inputContent)
            _responseText.postValue(response.text)
        }
    }

}