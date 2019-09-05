package azhengye.com.hodgepodge.activity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.os.Bundle
import android.widget.ImageView
import azhengye.com.hodgepodge.R

class ImageTransformationActivity : BaseActivity() {
    lateinit var originImg: ImageView
    lateinit var transformImg: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_transformation_layout)
        originImg = findViewById(R.id.origin_img)
        transformImg = findViewById(R.id.transform_img)
        var thread = Thread(Runnable {
            var bitmap = BitmapFactory.decodeResource(resources, R.drawable.girl)

            runOnUiThread {
                transformImg.setImageBitmap(transform(bitmap))
            }
        })
        thread.start()
    }

    private fun transform(bitmap: Bitmap): Bitmap {
        var canvas = Canvas()
        var outBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)

        canvas.setBitmap(outBitmap)

        var matrix = Matrix()
        matrix.postScale(-1F, 1F, bitmap.width / 2F, bitmap.height / 2F)
        canvas.drawBitmap(bitmap, matrix, null)

        return outBitmap
    }

}