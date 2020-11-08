package com.justai.aimybox

import android.app.Application
import android.content.Context
import com.justai.aimybox.api.aimybox.AimyboxDialogApi
import com.justai.aimybox.components.AimyboxProvider
import com.justai.aimybox.core.Config
import com.justai.aimybox.speechkit.google.platform.GooglePlatformSpeechToText
import java.util.*

class CatlinApplication : Application(), AimyboxProvider {

    companion object {
        private const val DIALOG_API_URL =
            "https://bot.jaicp.com/chatapi/webhook/zenbox/gnCUcwXC:54b68ae9b08ae3ae64047b7bd9627289204df921"
    }

    override val aimybox by lazy { createAimybox(this) }

    private fun createAimybox(context: Context): Aimybox {
        val unitId = UUID.randomUUID().toString()

        val textToSpeech = GooglePlatformTextToSpeechCustom(context, Locale.US, speechRate = 0.85F)
        val speechToText = GooglePlatformSpeechToText(context, Locale.US)

        val dialogApi = AimyboxDialogApi(
            "", unitId, DIALOG_API_URL,
            customSkills = linkedSetOf(MyCustomSkill(context))
        )

        return Aimybox(Config.create(speechToText, textToSpeech, dialogApi))
    }
}