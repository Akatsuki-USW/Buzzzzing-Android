package com.onewx2m.core_ui.util

import android.Manifest
import android.os.Build
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission

object PermissionManager {

    private fun setPermissionListener(doWhenPermissionGranted: () -> Unit): PermissionListener {
        val permissionListener: PermissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                doWhenPermissionGranted()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {}
        }
        return permissionListener
    }

    fun createGetImageAndCameraPermission(doWhenPermissionGranted: () -> Unit) {
        val permissionListener = setPermissionListener { doWhenPermissionGranted() }

        val permissions = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                arrayOf(
                    Manifest.permission.READ_MEDIA_IMAGES,
                    Manifest.permission.CAMERA,
                )
            }

            else -> {
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                )
            }
        }

        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setPermissions(
                *permissions,
            )
            .check()
    }
}
