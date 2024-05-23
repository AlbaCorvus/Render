package jschimera.loc.render.data.dao;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB8;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.BufferUtils;

import jschimera.loc.asset.domain.FontCharInfo;
import jschimera.loc.asset.domain.TextureDetails;

public class TextureFontDao implements DAO<TextureDetails> {

	private Map<Integer, FontCharInfo> characterMap = new HashMap<>();

	@Override
	public TextureDetails read(String value) {
		try {
			BufferedImage image = generateBitMap(value);

			int[] pixels = new int[image.getHeight() * image.getWidth()];
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
			// 4 bytes per INT so multiply by 4
			ByteBuffer byteBuffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
			for (int y = 0; y < image.getHeight(); y++) {
				for (int x = 0; x < image.getWidth(); x++) {
					int pixel = pixels[y * image.getWidth() + x];
					byte aplhaComponent = (byte) ((pixel >> 24) & 0xFF);
					byteBuffer.put(aplhaComponent);
					byteBuffer.put(aplhaComponent);
					byteBuffer.put(aplhaComponent);
					byteBuffer.put(aplhaComponent);
				}
			}

			byteBuffer.flip();
			TextureDetails fontDetails = new TextureDetails();
			fontDetails.setTextureId(glGenTextures());
			fontDetails.setHeight(image.getHeight());
			fontDetails.setWidth(image.getWidth());
			fontDetails.setCharacterInfoMap(characterMap);
			glBindTexture(GL_TEXTURE_2D, fontDetails.getTextureId());
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE,
					byteBuffer);
			byteBuffer.clear();

			return fontDetails;
		} catch (IOException | FontFormatException e) {
			throw new RuntimeException(e);
		}
	}

	public TextureDetails getDefault() {
		return new TextureDetails();
	}

	private BufferedImage generateBitMap(String value) throws IOException, FontFormatException {
		InputStream input = ClassLoader.getSystemResourceAsStream(value);
		byte[] bytes = new byte[input.available()];
		DataInputStream dataInputStream = new DataInputStream(input);
		dataInputStream.readFully(bytes);
		InputStream in = new ByteArrayInputStream(bytes);
		Font font = Font.createFont(Font.PLAIN, in).deriveFont(Font.PLAIN, 128f);
		// Fake graphics context for calculations
		BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.setFont(font);
		FontMetrics fontMetrics = g2d.getFontMetrics();

		int estimatedWidth = (int) Math.sqrt(font.getNumGlyphs()) * font.getSize() + 1;
		int width = 0;
		int height = fontMetrics.getHeight();

		int x = 0;
		int y = (int) (fontMetrics.getHeight() * 1.3f);

		for (int i = 0; i < font.getNumGlyphs(); i++) {
			if (font.canDisplay(i)) {
				FontCharInfo charInfo = new FontCharInfo(x, y, fontMetrics.charWidth(i), fontMetrics.getHeight());
				characterMap.put(i, charInfo);
				x += charInfo.getCharWidth();
				width = Math.max(x + fontMetrics.charWidth(i), width);
				if (x > estimatedWidth) {
					x = 0;
					y += fontMetrics.getHeight() * 1.3f;
					height += fontMetrics.getHeight() * 1.3f;
				}
			}
		}

		g2d.dispose();
		height += fontMetrics.getHeight() * 1.4f;

		bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = bufferedImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(font);
		g2d.setColor(Color.WHITE);

		for (Map.Entry<Integer, FontCharInfo> entry : characterMap.entrySet()) {
			FontCharInfo charInfo = entry.getValue();
			charInfo.initialiseTextureCoordinates(width, height);
			g2d.drawString("" + (char) entry.getKey().intValue(), charInfo.getSourceX(), charInfo.getSourceY());
		}

		g2d.dispose();
		return bufferedImage;
	}

}
