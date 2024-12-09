package juego;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import control.Teclado;
import graficos.Pantalla;
import mapa.Mapa;
import mapa.MapaCargado;
import mapa.MapaGenerado;

//implemento canvas para definir un plano donde realizar la parte grafica, runnable 
//para el metodo run que se ejecuta al crear un nuevo hilo con esta clase como parametro
public class Juego extends Canvas implements Runnable{  

	//por si exportamos el proyecto
	private static final long serialVersionUID = 1L;
	
	//Definimos las diferentes constantes del juego
	private static final int ANCHO = 800;
	private static final int ALTO = 600;
	
	//incluimos volatile para que la variable no interfiera con los hilos
	public static volatile boolean enFuncionamiento;
	
	private static final String NOMBRE = "Epic Radiance";
	private static  String CONTADOR_APS = "";
	private static  String CONTADOR_FPS = "";
	
	
	
	private static int aps = 0;
	private static int fps = 0;
	
	private static int x = 0;
	private static int y = 0;
	
	private static JFrame ventana;
	private static Thread thread;
	private Teclado teclado;
	private Pantalla pantalla;
	
	private static Mapa mapa;
	
	private static BufferedImage imagen = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_RGB);
	private static int[] pixeles = ((DataBufferInt) imagen.getRaster().getDataBuffer()).getData();
	private static final ImageIcon icono = new ImageIcon(Juego.class.getResource("/icono/icono.png"));
	
	private Juego() {
		
		setPreferredSize(new Dimension(ANCHO, ALTO));
		
		pantalla = new Pantalla(ANCHO, ALTO);
		
		//mapa = new MapaGenerado(128, 128);
		mapa = new MapaCargado("/mapas/mapaDesierto.png");
		
		teclado = new Teclado();
		addKeyListener(teclado);
		
		//creamos la ventana al ejecutar eljuego
		ventana = new JFrame();
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ventana.setResizable(false);
		ventana.setIconImage(icono.getImage());
		ventana.setLayout(new BorderLayout());
		ventana.add(this, BorderLayout.CENTER);
		ventana.setUndecorated(true);
		ventana.pack();
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		Juego juego = new Juego();
		juego.iniciar();
	}
	
	//se inicia el juego creando un nuevo hilo y ejecutandose run, ponemos en true enFuncionamiento para que el run se ejecute en bucle
	private synchronized void iniciar() {
		enFuncionamiento = true;
		thread = new Thread(this, "Graficos");
		thread.start();
		
	}
	
	//detenemos el funcionamiento del juego al cambiar la variable enFuncionamiento para que en run sea false
	private synchronized void detener() {
		enFuncionamiento = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ponemos los datos que vayamos a actualizar (atributos jugador, posicion, vida monstruos, etc)
	private void actualizar() {
		teclado.actualizar();
		
		if(teclado.arriba)
			y--;
		
		if(teclado.abajo)
			y++;
		
		if(teclado.izquierda)
			x--;
		
		if(teclado.derecha)
			x++;
		
		if(teclado.salir)
			System.exit(0);
		aps++;
		
	}
	
	//iremos mostrando la parte grafica a la vez que se ejecute actualizar
	private void mostrar() {
		BufferStrategy estrategia = getBufferStrategy();
		
		if(estrategia == null) {
			createBufferStrategy(3);
			return;
		}
		
		pantalla.limpiar();
		mapa.mostrar(x, y, pantalla);
		
		//pantalla.mostrar(x, y);
		
		/*for(int i = 0; i < pixeles.length; i++) {
			pixeles[i] = pantalla.pixeles[i];
		}*/
		//Hace lo mismo que lo de arriba
		System.arraycopy(pantalla.pixeles, 0, pixeles, 0, pixeles.length);
		
		Graphics g = estrategia.getDrawGraphics();
		
		g.drawImage(imagen, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.white);
		g.fillRect(ANCHO/2, ALTO/2, 32, 32);
		g.drawString(CONTADOR_APS, 10, 20);
		g.drawString(CONTADOR_FPS, 10, 35);
		g.dispose();
		
		estrategia.show();
		
		fps++;
	}

	//implmentamos el bucle a ejecutar al iniciar el juego, bucle en el que se llamará a los metodos para actualizar los atributos y mostrarlos
	@Override
	public void run() {
		final int NS_POR_SEGUNDO = 1000000000; //ponemos cuantos nanoSeg hay en 1 seg
		final byte APS_OBJETIVO = 100; //actualizaciones por segundo que queremos lograr
		final double NS_POR_ACTUALIZACION = NS_POR_SEGUNDO / APS_OBJETIVO; //Saber cuantos nanosegundos tiene que pasar para que consigamos el objetivo de 60 actualizaciones por segundo
		
		long referenciaActualizacion = System.nanoTime(); //Se atribuye una cant tiempo en nanoSeg
		long referenciaContador = System.nanoTime();
		
		double tiempoTranscurrido; // 
		double delta = 0; //por convencion se llama delta, tiempo trancurrido hasta una actualizacion
		
		requestFocus();//para poner el foco del teclado en el juego	
		
		while(enFuncionamiento) {
			final long inicioBucle = System.nanoTime(); //iniciamos el cronometro
			
			tiempoTranscurrido = inicioBucle - referenciaActualizacion; //tiempo pasado entre el primer nanoTime y despues de entrar al bucle
			referenciaActualizacion = inicioBucle; //le damos el tiempo donde se ha ejecutado el bucle para en la linea anterior tener el tiempo entre esta iteracion y la siguiente
			delta += tiempoTranscurrido / NS_POR_ACTUALIZACION; //irá sumando cantidades de tiempo pequeñas para llegar a 1 que así actualizará el juego en el siguiente bucle
			
			while (delta >= 1) {
				actualizar();
				delta--;
			}
			
			mostrar();
			
			//contador de actualizaciones, frame por seg
			if(System.nanoTime() - referenciaContador > NS_POR_SEGUNDO) {
				CONTADOR_APS = "APS: " + aps;
				CONTADOR_FPS = "FPS: " + fps;
				
				aps = 0;
				fps = 0;
				referenciaContador = System.nanoTime();
			}
			
		}
		
	}

}























