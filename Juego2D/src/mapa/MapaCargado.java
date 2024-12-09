package mapa;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import mapa.cuadro.Cuadro;

public class MapaCargado extends Mapa{
	
	private int[] pixeles;
	
	public MapaCargado(String ruta) {
		super(ruta);
	}
	
	protected void cargarMapa(String ruta) {
		
		try {
			BufferedImage imagen = ImageIO.read(MapaCargado.class.getResource(ruta));
			
			ancho = imagen.getWidth();
			alto = imagen.getHeight();
			
			cuadrosCatalogo = new Cuadro[ancho * alto];
			pixeles = new int[ancho * alto];
			
			imagen.getRGB(0, 0, ancho, alto, pixeles, 0, ancho);			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void generarMapa() {
		for(int i = 0; i < pixeles.length; i++) {
			switch (pixeles[i]) {
			case 0xff000000:
				cuadrosCatalogo[i] = Cuadro.ASFALTO; //puesto
				continue;
			case 0xfffff3c1:
				cuadrosCatalogo[i] = Cuadro.ARENA; //puesto
				continue;
			case 0xffbda43e:
				cuadrosCatalogo[i] = Cuadro.BORDE_CARRETERA;
				continue;
			case 0xffbfbfbf:
				cuadrosCatalogo[i] = Cuadro.CENTRO_CARRETERA;
				continue;
			case 0xff8f8f8f:
				cuadrosCatalogo[i] = Cuadro.ESQUINA_CARRETERA;
				continue;
			case 0xff2b2626:
				cuadrosCatalogo[i] = Cuadro.PARED_PIEDRA; //puesto
				continue;
			case 0xff3c3322:
				cuadrosCatalogo[i] = Cuadro.PARED_PIEDRA_INFERIOR;
				continue;
			case 0xff67593c:
				cuadrosCatalogo[i] = Cuadro.PARED_PIEDRA_CARRETERA;
				continue;
			case 0xff523f35:
				cuadrosCatalogo[i] = Cuadro.PUERTA_INFERIOR;
				continue;
			case 0xff613d3d:
				cuadrosCatalogo[i] = Cuadro.PUERTA_SUPERIOR_IZQUIERDA; //puesto
				continue;
			case 0xff6a1515:
				cuadrosCatalogo[i] = Cuadro.PUERTA_INTERMEDIA_IZQUIERDA; //puesto
				continue;
			case 0xff6b2323:
				cuadrosCatalogo[i] = Cuadro.OXIDO; //puesto
				continue;
			case 0xff7b3939:
				cuadrosCatalogo[i] = Cuadro.PUERTA_SUPERIOR_CENTRAL; //puesto
				continue;			
			default:
				cuadrosCatalogo[i] = Cuadro.VACIO;
			}
		}
	}
	
}






