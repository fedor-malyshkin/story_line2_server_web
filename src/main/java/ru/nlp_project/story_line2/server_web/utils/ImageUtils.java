package ru.nlp_project.story_line2.server_web.utils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

public class ImageUtils {

	/**
	 * Выполнить необходимое масштабирование (с учтом пропорции по МИНИМАЛЬНОМУ аспекту
	 * ширины/высоты).
	 *
	 * @param imageIn входные данны
	 * @param widthIn требуемая ширина
	 * @param heightIn требуемая высота
	 * @param mediaType формат изображения (image/jpg, image/png....)
	 * @return требуемое изображение
	 */
	public static byte[] scale(byte[] imageIn, Integer widthIn, Integer heightIn, String mediaType)
			throws IOException {
		int height = validateHeight(heightIn);
		int width = validateWidth(widthIn);
		validateBytes(imageIn);
		BufferedImage imageScaled = scaleImage(imageIn, height, width);
		return writeBufferedImage(imageScaled, mediaType);
	}


	/**
	 * Выполнить необходимое масштабирование (с учтом пропорции по макимальному аспекту ширины/высоты)
	 * и выполнить размещение в середине указанной области и выполнить обрезку лишнего.
	 *
	 * @param imageIn входные данны
	 * @param widthIn требуемая ширина
	 * @param heightIn требуемая высота
	 * @param mediaType формат изображения (image/jpg, image/png....)
	 * @return поток байтов с готовым изображением
	 */
	public static byte[] crop(byte[] imageIn, Integer widthIn, Integer heightIn, String mediaType)
			throws IOException {
		int height = validateHeight(heightIn);
		int width = validateWidth(widthIn);
		validateBytes(imageIn);

		BufferedImage imageScaled = scaleImage(imageIn, height, width);

		int newWidth = imageScaled.getWidth();
		int newHeight = imageScaled.getHeight();
		int x = (newWidth - width) / 2;
		int y = (newHeight - height) / 2;
		int h = height + x > newHeight ? newHeight : height + x;
		int w = width + y > newWidth ? newWidth : width + y;
		BufferedImage resultImageCropped = imageScaled.getSubimage(x, y, w, h);
		resultImageCropped.flush();
		return writeBufferedImage(resultImageCropped, mediaType);
	}

	private static BufferedImage scaleImage(byte[] imageIn, int height, int width)
			throws IOException {
		BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageIn));
		int origHeight = bufferedImage.getHeight();
		int origWidth = bufferedImage.getWidth();

		// find biggest ratio
		float ratioWidth = (float) width / origWidth;
		float ratioHeight = (float) height / origHeight;
		float ratio = Float.max(ratioHeight, ratioWidth);

		int newWidth = (int) (origWidth * ratio);
		int newHeight = (int) (origHeight * ratio);

		return Scalr
				.resize(bufferedImage, Method.SPEED, Mode.AUTOMATIC, newWidth, newHeight);
	}


	/**
	 * Выполнить необходимое масштабирование (с учтом пропорции по макимальному аспекту ширины/высоты)
	 * и выполнить размещение в середине указанной области и выполнить обрезку лишнего.
	 *
	 * @param imageIn входные данны
	 * @param widthIn требуемая ширина
	 * @param heightIn требуемая высота
	 * @param mediaType формат изображения (image/jpg, image/png....)
	 * @return поток байтов с готовым изображением
	 */
	public static byte[] cropOld(byte[] imageIn, Integer widthIn, Integer heightIn, String mediaType)
			throws IOException {
		int height = validateHeight(heightIn);
		int width = validateWidth(widthIn);
		validateBytes(imageIn);

		BufferedImage scaleImage = scaleImageOld(imageIn, width, height);

		int newWidth = scaleImage.getWidth();
		int newHeight = scaleImage.getHeight();
		int x = (newWidth - width) / 2;
		int y = (newHeight - height) / 2;
		int h = height + x > newHeight ? newHeight : height + x;
		int w = width + y > newWidth ? newWidth : width + y;
		BufferedImage resultImageCropped = scaleImage.getSubimage(x, y, w, h);
		resultImageCropped.flush();
		return writeBufferedImage(resultImageCropped, mediaType);
	}

	/**
	 * * Выполнить валидацию "входящий" высоты.
	 */
	private static int validateHeight(Integer in) {
		if (in == null || in <= 0) {
			return 2048;
		}
		if (in > 2048) {
			return 2048;
		}
		return in;
	}


	/**
	 * Выполнить валидацию "входящий" ширины.
	 */
	private static int validateWidth(Integer in) {
		if (in == null || in <= 0) {
			return 2048;
		}
		if (in > 2048) {
			return 2048;
		}
		return in;
	}


	/**
	 * Выполнить необходимое масштабирование (с учтом пропорции по МИНИМАЛЬНОМУ аспекту
	 * ширины/высоты).
	 *
	 * @param imageIn входные данны
	 * @param widthIn требуемая ширина
	 * @param heightIn требуемая высота
	 * @param mediaType формат изображения (image/jpg, image/png....)
	 * @return требуемое изображение
	 */
	public static byte[] scaleOld(byte[] imageIn, Integer widthIn, Integer heightIn, String mediaType)
			throws IOException {
		int height = validateHeight(heightIn);
		int width = validateWidth(widthIn);
		validateBytes(imageIn);
		BufferedImage imageScaled = scaleImageOld(imageIn, width, height);
		return writeBufferedImage(imageScaled, mediaType);
	}

	private static void validateBytes(byte[] imageIn) {
		if (imageIn == null || imageIn.length == 0) {
			throw new IllegalArgumentException("Image bytes must be not null!");
		}
	}

	/**
	 * @param mediaType формат изображения (image/jpg, image/png....)
	 */
	private static byte[] writeBufferedImage(
			BufferedImage imageScaled, String mediaType) throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		if ("image/png".equalsIgnoreCase(mediaType)) {
			ImageIO.write(imageScaled, "png", outputStream);
		} else {
			ImageIO.write(imageScaled, "jpg", outputStream);
		}
		outputStream.flush();
		return outputStream.toByteArray();
	}

	private static BufferedImage scaleImageOld(byte[] imageIn, int width, int height)
			throws IOException {
		InputStream inputStream = new ByteArrayInputStream(imageIn);

		BufferedImage bufferedImage = ImageIO.read(inputStream);
		int origHeight = bufferedImage.getHeight();
		int origWidth = bufferedImage.getWidth();

		// find biggest ratio
		float ratioWidth = (float) width / origWidth;
		float ratioHeight = (float) height / origHeight;
		float ratio = Float.max(ratioHeight, ratioWidth);

		int newWidth = (int) (origWidth * ratio);
		int newHeight = (int) (origHeight * ratio);

		BufferedImage imageScaled = new BufferedImage(newWidth, newHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphicsScaled = imageScaled.createGraphics();
		AffineTransform tr = AffineTransform.getScaleInstance(ratio, ratio);
		graphicsScaled.drawRenderedImage(bufferedImage, tr);
		imageScaled.flush();
		return imageScaled;
	}
}
