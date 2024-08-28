package com.example.coroutinesvideopdf

import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private val pdfUrl = "https://www.iitk.ac.in/esc101/2009Jan/lecturenotes/timecomplexity/TimeComplexity_using_Big_O.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)

        // Launch a coroutine to download and display the PDF
        lifecycleScope.launch(Dispatchers.IO) {
            // Update UI: Showing status in TextView (Switch to the main thread)
            withContext(Dispatchers.Main) {
                textView.text = "Downloading PDF..."
            }

            val pdfFile = downloadPdf(pdfUrl)
            if (pdfFile != null) {
                // Update UI: Inform that the PDF has been downloaded
                withContext(Dispatchers.Main) {
                    textView.text = "PDF downloaded! Rendering the first page..."
                }

                // Render the first page of the downloaded PDF
                val pdfBitmap = renderPdf(pdfFile)
                pdfBitmap?.let {
                    // Switch to the main thread to display the image
                    withContext(Dispatchers.Main) {
                        imageView.setImageBitmap(it)
                        textView.text = "PDF rendered successfully!"
                    }
                }
            } else {
                // Update UI: Inform that the download failed
                withContext(Dispatchers.Main) {
                    textView.text = "Failed to download PDF. Please try again."
                }
            }
        }
    }

    // Suspending function to download the PDF
    private suspend fun downloadPdf(url: String): File? {
        return withContext(Dispatchers.IO) {

            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                // Create a temporary file to store the downloaded PDF
                val pdfFile = File(cacheDir, "downloaded_pdf.pdf")
                val fos = FileOutputStream(pdfFile)

                response.body?.byteStream()?.use { inputStream ->
                    fos.use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                pdfFile
            } else {
                null
            }
        }
    }
}
// Suspending function to render the first page of the PDF
private suspend fun renderPdf(file: File): Bitmap? {
    return withContext(Dispatchers.IO) {

        val fileDescriptor = ParcelFileDescriptor.open(file,
            ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(fileDescriptor)

        // Render the first page of the PDF
        val page = pdfRenderer.openPage(0)
        val width = page.width
        val height = page.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        page.close()

        pdfRenderer.close()
        bitmap
    }
}
