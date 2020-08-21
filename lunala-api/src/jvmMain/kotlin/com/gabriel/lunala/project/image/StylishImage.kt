package com.gabriel.lunala.project.image

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.awt.image.BufferedImage
import java.io.InputStream
import javax.imageio.ImageIO


data class StylishImage(val image: BufferedImage): Image {

    companion object {

        /**
         * @author https://stackoverflow.com/questions/13605248/java-converting-image-to-bufferedimage
         */
        suspend operator fun invoke(image: java.awt.Image): BufferedImage? {
            if (image is BufferedImage) return image

            // Create a buffered image with transparency
            val bufferedImage = BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB)

            // Draw the image on to the buffered image
            val base = bufferedImage.createGraphics()
            base.drawImage(image, 0, 0, null)
            base.dispose()

            // Return the buffered image
            return bufferedImage
        }

        suspend operator fun invoke(stream: InputStream): StylishImage = withContext(Dispatchers.IO) {
            StylishImage(ImageIO.read(stream))
        }

    }

}