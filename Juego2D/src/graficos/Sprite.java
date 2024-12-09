package graficos;

public final class Sprite {

	private final int lado;
	
	private int x;
	private int y;
	
	public int[] pixeles;
	private HojaSprites hoja;
	
	//Coleccion de sprites
	public final static Sprite VACIO = new Sprite(32, 0X000000);
	public final static Sprite ASFALTO = new Sprite(32, 0, 0, HojaSprites.desierto);
	public final static Sprite ARENA = new Sprite(32, 1, 0, HojaSprites.desierto);
	public final static Sprite BORDE_CARRETERA = new Sprite(32, 2, 0, HojaSprites.desierto);
	public final static Sprite CENTRO_CARRETERA = new Sprite(32, 3, 0, HojaSprites.desierto);
	public final static Sprite ESQUINA_CARRETERA = new Sprite(32, 4, 0, HojaSprites.desierto);
	public final static Sprite PARED_PIEDRA= new Sprite(32, 5, 0, HojaSprites.desierto);
	public final static Sprite PARED_PIEDRA_INFERIOR = new Sprite(32, 6, 0, HojaSprites.desierto);
	public final static Sprite PARED_PIEDRA_CARRETERA = new Sprite(32, 7, 0, HojaSprites.desierto);
	public final static Sprite PUERTA_SUPERIOR_IZQUIERDA = new Sprite(32, 8, 0, HojaSprites.desierto);
	public final static Sprite PUERTA_INTERMEDIA_IZQUIERDA = new Sprite(32, 9, 0, HojaSprites.desierto);
	public final static Sprite PUERTA_INFERIOR = new Sprite(32, 10, 0, HojaSprites.desierto);
	public final static Sprite OXIDO = new Sprite(32, 11, 0, HojaSprites.desierto);
	public final static Sprite PUERTA_SUPERIOR_CENTRAL = new Sprite(32, 12, 0, HojaSprites.desierto);
	//fin de la coleccion
	
	public Sprite(final int lado, final int columna, final int fila, final HojaSprites hoja) {
		this.lado = lado;
		pixeles = new int[this.lado * this.lado];
		
		this.x = columna * lado;
		this.y = fila * lado;
		this.hoja = hoja;
		
		for(int y = 0; y < lado; y++) {
			for(int x = 0; x < lado; x++) {
				pixeles[x + y * lado] = hoja.pixeles[(x + this.x) + (y + this.y) * hoja.getANCHO()];
			}
		}
		
	}
	
	public Sprite(final int lado, final int color) {
		this.lado = lado;
		pixeles = new int [lado*lado];
		for(int i = 0; i < pixeles.length; i++) {
			pixeles[i] = color;
		}
	}
	
	public int getLado() {
		return lado;
	}
	
	
}
