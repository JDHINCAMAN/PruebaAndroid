import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        val buttonLoadImage: Button = findViewById(R.id.buttonLoadImage)
        val buttonResizeImage: Button = findViewById(R.id.buttonResizeImage)

        buttonLoadImage.setOnClickListener {
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, PICK_PHOTO)
        }

        buttonResizeImage.setOnClickListener {
            imageView.drawable?.let {
                val bitmap = (it as BitmapDrawable).bitmap
                val resized = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
                imageView.setImageBitmap(resized)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == PICK_PHOTO) {
            val imageUri = data?.data
            val imageStream: InputStream? = imageUri?.let { contentResolver.openInputStream(it) }
            val selectedImage = BitmapFactory.decodeStream(imageStream)
            imageView.setImageBitmap(selectedImage)
        }
    }

    companion object {
        private const val PICK_PHOTO = 1
    }
}
