package ru.nlp_project.story_line2.server_web.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;
import org.junit.Before;
import org.junit.Test;

public class ImageUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test()
	public void scaleMy() throws Exception {
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("ru/nlp_project/story_line2/server_web/utils/IMG_9825.jpg");
		ByteArrayOutputStream byteOUS = new ByteArrayOutputStream();
		IOUtils.copy(inputStream, byteOUS);
		IntStream.range(0, 1000).forEach((i) -> {
			try {
				ImageUtils.scale(byteOUS.toByteArray(), 200, 200, "image/png");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Test()
	public void scaleImageScalr() throws Exception {
		InputStream inputStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("ru/nlp_project/story_line2/server_web/utils/IMG_9825.jpg");
		BufferedImage bufferedImage = ImageIO.read(inputStream);

		IntStream.range(0, 1000).forEach((i) -> {
			try {
				BufferedImage image = Scalr.resize(bufferedImage, Method.SPEED, Mode.AUTOMATIC, 200, 200);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				ImageIO.write(image, "png", outputStream);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}