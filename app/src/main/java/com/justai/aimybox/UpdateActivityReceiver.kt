package com.justai.aimybox

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import com.justai.aimybox.MyCustomSkill.Companion.ACTION_EXIT
import com.justai.aimybox.MyCustomSkill.Companion.ACTION_UPDATE
import com.justai.aimybox.MyCustomSkill.Companion.PICTURE
import com.justai.aimybox.MyCustomSkill.Companion.PROGRESS_BAR
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UpdateActivityReceiver : BroadcastReceiver() {

    lateinit var activity: Activity

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action.equals(ACTION_UPDATE)) {
            val progress = intent.getIntExtra(PROGRESS_BAR, 0)
            if (progress != 0) {
                val progressBar = activity.findViewById<ProgressBar>(R.id.progressBar)
                progressBar.setProgress(progress, true)
            }

            val pictureName = intent.getStringExtra(PICTURE)
            if (pictureName != null && pictureName.isNotBlank()) {
                val mainContainer = activity.findViewById<View>(R.id.main_container)
                when (pictureName) {
                    "CatlinSmile.png" -> mainContainer.setBackgroundResource(R.mipmap.logo_smile)
                    "CatlinSadCry.png" -> mainContainer.setBackgroundResource(R.mipmap.logo_sad)
                    "CatlinHearts.png" -> mainContainer.setBackgroundResource(R.mipmap.logo_heart)
                    "CatlinStars.png" -> mainContainer.setBackgroundResource(R.mipmap.logo_stars)
                }
            }
        }
        if (intent.action.equals(ACTION_EXIT)) {
            GlobalScope.launch {
                runBlocking {
                    delay(10000)
                }
                activity.finish()
            }
        }
    }
}