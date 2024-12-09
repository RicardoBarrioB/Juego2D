package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class HojaSprites {

	private final int ANCHO;
	private final int ALTO;
	public final int[] pixeles;
	
	//coleccion hojas de sprites
	
	public static HojaSprites desierto = new HojaSprites("/texturas/desierto.png", 320, 320);
	
	//fin de la coleccion
	
	public HojaSprites(final String ruta, final int ancho, final int alto) {
		this.ALTO = alto;
		this.ANCHO = ancho;
		
		pixeles = new int [ANCHO*ALTO];
		
		BufferedImage imagen;
		try {
			imagen = ImageIO.read(HojaSprites.class.getResource(ruta));
			
			imagen.getRGB(0,  0, ancho, alto, pixeles, 0, ancho);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[] getPixeles() {
		return pixeles;
	}

	public int getANCHO() {
		return ANCHO;
	}

	public int getALTO() {
		return ALTO;
	}

}
