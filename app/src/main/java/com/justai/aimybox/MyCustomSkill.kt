package com.justai.aimybox

import android.content.Context
import android.content.Intent
import com.justai.aimybox.api.aimybox.AimyboxRequest
import com.justai.aimybox.api.aimybox.AimyboxResponse
import com.justai.aimybox.core.CustomSkill
import com.justai.aimybox.model.Response

class MyCustomSkill(var context: Context) : CustomSkill<AimyboxRequest, AimyboxResponse> {
    companion object {
        const val ACTION_UPDATE = "action.update"
        const val ACTION_EXIT = "action.exit"
        const val PICTURE = "picture"
        const val PROGRESS_BAR = "progress"
    }

    override fun canHandle(response: AimyboxResponse) = true

    override suspend fun onRequest(request: AimyboxRequest): AimyboxRequest = request

    override suspend fun onResponse(
        response: AimyboxResponse,
        aimybox: Aimybox,
        defaultHandler: suspend (Response) -> Unit
    ) {
        val picture = response.data?.get("pic")?.toString()
        val progressBar = response.data?.get("bar")?.toString()?.toInt()
        if (picture != null || progressBar != null) {
            val intent = Intent(ACTION_UPDATE)
            intent.putExtra(PICTURE, picture)
            intent.putExtra(PROGRESS_BAR, progressBar)
            context.sendBroadcast(intent)
        }
        val exit = response.data?.get("end")?.toString()
        if (exit != null && exit == "true") {
            val intent = Intent(ACTION_EXIT)
            context.sendBroadcast(intent)
        }
        defaultHandler(response)
    }
}