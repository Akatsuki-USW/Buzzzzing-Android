package com.onewx2m.core_ui.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageUtil @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    companion object {
        private const val MAX_IMAGE_WIDTH = 1280
        private const val MAX_IMAGE_HEIGHT = 1280
    }

    /**
     * 이미지 최대 사이즈 : 1280 * 1280
     * 이미지 최대 용량 : 6.25MB
     * 로 이미지 최적화한다.
     */
    fun uriToOptimizeImageFile(uri: Uri): File? {
        try {
            val storage = context.cacheDir
            val fileName = String.format("%s.%s", UUID.randomUUID(), "jpg")

            val tempFile = File(storage, fileName)
            tempFile.createNewFile()

            val fos = FileOutputStream(tempFile)

            decodeOptimizeBitmapFromUri(uri)?.apply {
                compress(Bitmap.CompressFormat.JPEG, 100, fos)
                recycle()
            } ?: throw NullPointerException()

            fos.flush()
            fos.close()

            return tempFile
        } catch (e: Exception) {
            Timber.e("${e.message}")
        }

        return null
    }

    /**
     * BitmapFactory를 사용하게 되면 Bitmap.Config는 기본으로 ARGB_8888으로 설정된다.
     * 이미지의 최대 가로/세로 길이가 1280이라고 할 때,
     * Bitmap의 최대 크기는 1280 * 1280 * 4 = 6.25MB가 된다.
     * 즉 해당 함수는 Bitmap을 6.25MB 이하로 만들어준다.
     */
    private fun decodeOptimizeBitmapFromUri(uri: Uri): Bitmap? {
        val input = BufferedInputStream(context.contentResolver.openInputStream(uri))

        input.mark(input.available())

        var bitmap: Bitmap?

        BitmapFactory.Options().run {
            inJustDecodeBounds = true
            bitmap = BitmapFactory.decodeStream(input, null, this)

            input.reset()

            inSampleSize = calculateInSampleSize(this)
            inJustDecodeBounds = false

            bitmap = BitmapFactory.decodeStream(input, null, this)?.apply {
                rotateImageIfRequired(context, this, uri)
            }
        }

        input.close()

        return bitmap
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > MAX_IMAGE_HEIGHT || width > MAX_IMAGE_WIDTH) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= MAX_IMAGE_HEIGHT && halfWidth / inSampleSize >= MAX_IMAGE_WIDTH) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    private fun rotateImageIfRequired(context: Context, bitmap: Bitmap, uri: Uri): Bitmap? {
        val input = context.contentResolver.openInputStream(uri) ?: return null

        val exif =
            ExifInterface(input)

        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotateImage(bitmap, 90)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotateImage(bitmap, 180)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotateImage(bitmap, 270)
            else -> bitmap
        }
    }

    private fun rotateImage(bitmap: Bitmap, degree: Int): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degree.toFloat())
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }
}
