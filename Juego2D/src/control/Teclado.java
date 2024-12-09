package control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public final class Teclado implements KeyListener{
	
	private final static int numeroTeclas = 120;
	private final boolean[] teclas = new boolean[numeroTeclas];
	
	public boolean arriba;
	public boolean abajo;
	public boolean izquierda;
	public boolean derecha;
	
	public boolean salir;
	
	public void actualizar() {
		//definimos las teclas para movernos
		arriba = teclas[KeyEvent.VK_W] || teclas[KeyEvent.VK_UP];
		abajo = teclas[KeyEvent.VK_S] || teclas[KeyEvent.VK_DOWN];
		izquierda = teclas[KeyEvent.VK_A] || teclas[KeyEvent.VK_LEFT];
		derecha = teclas[KeyEvent.VK_D] || teclas[KeyEvent.VK_RIGHT];
		salir = teclas[KeyEvent.VK_ESCAPE];
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		//Lo dejamos vacio
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		teclas[e.getKeyCode()] = true;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		teclas[e.getKeyCode()] = false;
		
	}
	
}
