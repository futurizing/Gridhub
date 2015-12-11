package util;

import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Stroke;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Resource {

	private static Resource instance = new Resource();

	/**
	 * Get an instance of {@code Resource}.
	 * 
	 * @return An instance of {@code Resource}
	 */
	public static Resource getInstance() {
		return instance;
	}

	private ClassLoader getClassLoader() {
		return this.getClass().getClassLoader();
	}

	private URI getResourceAsURI(String resourcePath) throws URISyntaxException {
		return getClassLoader().getResource("res/" + resourcePath).toURI();
	}

	/**
	 * Initialize the resource.
	 * 
	 * @return Whether the resource loading is successful.
	 */
	public boolean initialize() {
		try {
			loadFont();

			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public static enum FontWeight {
		THIN("Bebas Neue Thin", "BebasNeue Thin.ttf"), LIGHT("BebasNeueLight",
				"BebasNeue Light.ttf"), REGULAR("BebasNeueRegular", "BebasNeue Regular.ttf"), BOLD("BebasNeueBold",
						"BebasNeue Bold.ttf"), BOOK("BebasNeueBook", "BebasNeue Book.ttf");

		private final String fontName;
		private final String fontFileName;

		FontWeight(String fontName, String fontFileName) {
			this.fontName = fontName;
			this.fontFileName = fontFileName;
		}

		public String getFontName() {
			return fontName;
		}

		public String getFontFileName() {
			return fontFileName;
		}
	}

	public Font getDefaultFont(int size, FontWeight weight) {
		return new Font(weight.getFontName(), Font.PLAIN, size);
	}

	public Font getDefaultFont(int size) {
		return getDefaultFont(size, FontWeight.REGULAR);
	}

	private void loadFont() throws FontFormatException, IOException, URISyntaxException {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

		for (FontWeight fontWeight : FontWeight.values()) {
			Font newFont = Font.createFont(Font.TRUETYPE_FONT,
					new File(getResourceAsURI("fonts/" + fontWeight.getFontFileName())));
			ge.registerFont(newFont);
		}
	}

	private static final Stroke gameObjThickStroke = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
	private static final Stroke gameObjThinStroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

	public static Stroke getGameObjectThickStroke() {
		return gameObjThickStroke;
	}

	public static Stroke getGameObjectThinStroke() {
		return gameObjThinStroke;
	}

}
