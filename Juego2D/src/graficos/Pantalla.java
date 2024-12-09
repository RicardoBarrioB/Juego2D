package graficos;

import mapa.cuadro.Cuadro;

public final class Pantalla {

	private final int ANCHO;
	private final int ALTO;

	private int diferenciaX;
	private int diferenciaY;
		
	
	public int[] pixeles;
	
	/*//Temporal
	private final static int LADO_SPRITE = 32;
	private final static int MASCARA_SPRITE = LADO_SPRITE - 1;
	//fin temporal*/
	
	public Pantalla(final int ancho, final int alto) {
		this.ALTO = alto;
		this.ANCHO = ancho;
		
		pixeles = new int[ancho * alto];
	}
	
	public void limpiar() {
		for(int i = 0; i < pixeles.length; i++) {
			pixeles[i] = 0;
		}
	}
	/*//temporal
	public void mostrar(final int compensacionX, final int compensacionY) {
		for(int y = 0; y < ALTO; y++) {
			int posicionY = y + compensacionY;
			if(posicionY < 0 || posicionY >= ALTO) {
				continue;
			}
			
			for(int x = 0; x < ANCHO; x++) {
				int posicionX = x + compensacionX;
				if(posicionX < 0 || posicionX >= ANCHO) {
					continue;
				}
				
				//Temporal
				pixeles[posicionX + posicionY * ANCHO] = Sprite.ASFALTO.pixeles[(x & MASCARA_SPRITE) + (y & MASCARA_SPRITE) * LADO_SPRITE];
			}
		}
	}
	//FIN TEMPORAL*/
	
	public void mostrarCuadro(int compensacionX, int compensacionY, Cuadro cuadro) {
		compensacionX -= diferenciaX;
		compensacionY -= diferenciaY;
		
		for(int y = 0; y < cuadro.sprite.getLado(); y++) {
			int posicionY = y + compensacionY;
			for(int x = 0; x < cuadro.sprite.getLado(); x++) {
				int posicionX = x + compensacionX;
				if(posicionX < -cuadro.sprite.getLado() || posicionX >= ANCHO || posicionY < 0 || posicionY >= ALTO) {
					break;
				}
				if(posicionX < 0) {
					posicionX = 0;
				}
				pixeles[posicionX + posicionY * ANCHO] = cuadro.sprite.pixeles[x + y * cuadro.sprite.getLado()];
			}
		}
	}
	
	public void estableceDiferencia(final int diferenciaX, final int diferenciaY) {
		this.diferenciaX = diferenciaX;
		this.diferenciaY = diferenciaY;
	}

	public int getANCHO() {
		return ANCHO;
	}

	public int getALTO() {
		return ALTO;
	}
	
}






