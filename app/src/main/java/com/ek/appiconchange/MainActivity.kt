package com.ek.appiconchange

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {
    private lateinit var holidayBtn: Button
    private lateinit var newYearBtn: Button
    private lateinit var birthdayBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        handleClickEvents()


    }
    private fun handleClickEvents() {
        newYearBtn.setOnClickListener {
            changeIcon(AppIconEnum.NEWYEAR)
        }
        birthdayBtn.setOnClickListener {
            changeIcon(AppIconEnum.BIRTHDAY)
        }
        holidayBtn.setOnClickListener {
            changeIcon(AppIconEnum.HOLIDAY)
        }
    }

    private fun changeIcon(activeAppIcon: AppIconEnum) {
        for (value in AppIconEnum.values()) {
            val action = if (value == activeAppIcon) {
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED
            } else {
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED
            }
            packageManager.setComponentEnabledSetting(
                ComponentName("com.ek.appiconchange", "com.ek.appiconchange.${value.name}"),
                action, PackageManager.DONT_KILL_APP
            )
        }
    }

    private fun initialize() {
        holidayBtn = findViewById(R.id.holiday_btn)
        newYearBtn = findViewById(R.id.new_year_btn)
        birthdayBtn = findViewById(R.id.birthday_btn)
    }

    fun Context.updateAppIcon(mipmapResId: Int) {
        // Uygulama simgesini değiştirin.
        val icon = ResourcesCompat.getDrawable(resources, mipmapResId, null).toString()
        val appInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        val appIcon = appInfo.icon
        appInfo.icon = Integer.parseInt(icon)
        packageManager.updateApplicationInfo(appInfo, 0)
    }
}