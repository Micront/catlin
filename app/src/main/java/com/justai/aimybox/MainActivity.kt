package com.justai.aimybox

import android.content.IntentFilter
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.justai.aimybox.MyCustomSkill.Companion.ACTION_EXIT
import com.justai.aimybox.MyCustomSkill.Companion.ACTION_UPDATE
import com.justai.aimybox.components.AimyboxAssistantFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_main)

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        val assistantFragment = AimyboxAssistantFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.assistant_container, assistantFragment)
            commit()
        }

        val receiver = UpdateActivityReceiver()
        receiver.activity = this
        this.registerReceiver(
            receiver, IntentFilter(ACTION_UPDATE)
        )
        this.registerReceiver(
            receiver, IntentFilter(ACTION_EXIT)
        )

    }

    override fun onBackPressed() {
        val assistantFragment = (supportFragmentManager.findFragmentById(R.id.assistant_container)
                as? AimyboxAssistantFragment)
        if (assistantFragment?.onBackPressed() != true) super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        android.os.Process.killProcess(android.os.Process.myPid())
    }
}