package com.inspius.sportsevent.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.inspius.sportsevent.R
import com.inspius.sportsevent.base.BaseActivity
import com.inspius.sportsevent.databinding.ActivityMainBinding
import com.inspius.sportsevent.extensions.toast
import com.inspius.sportsevent.ui.matches.MatchesFragment
import com.inspius.sportsevent.ui.teams.TeamsFragment

class MainActivity : BaseActivity<ActivityMainBinding>() {

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissions = arrayOf(
        android.Manifest.permission.POST_NOTIFICATIONS,
        android.Manifest.permission.SCHEDULE_EXACT_ALARM
    )

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(), ::onRequestPermissionResult
    )

    override fun createViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.bottomNavigationView.setOnItemSelectedListener(this::onBottomNavItemSelected)
        binding.bottomNavigationView.selectedItemId = R.id.item_teams
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    private fun onBottomNavItemSelected(menuItem: MenuItem): Boolean {
        val fragment = when (menuItem.itemId) {
            R.id.item_teams -> TeamsFragment()
            else -> MatchesFragment()
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, "ActiveFragment").commit()

        return true
    }

    private fun checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (permissions.any { permission -> checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED }) {
                permissionLauncher.launch(permissions)
            }
        }
    }

    private fun onRequestPermissionResult(result: Map<String, Boolean>) {
        if (result.keys.any { key -> result[key] != true }) {
            toast(getString(R.string.grant_permission_warning))
            finish()
        }
    }
}