package cl.utfsm.inf.lp.tareas2012.vraiders.gui;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

import cl.utfsm.inf.lp.tareas2012.vraiders.gui.Utils;
import cl.utfsm.inf.lp.tareas2012.vraiders.logic.Manager;

/** Clase gr&aacute;fica principal de la aplicaci&oacute;n.
 * 
 * Implementa la ventana principal del juego, la cual muestra el tablero del
 * mismo y captura los eventos de teclado generados por el jugador. */
public class MainWindow {
	private JFrame frame;
	private Manager manager;
	
	/** Instancia la ventana de juego. 
	 * 
	 * @throws IOException Si el archivo con el icono de la ventana no existe o no se puede leer. 
	 */
	public MainWindow() throws IOException{
		BufferedImage icon = ImageIO.read(new File("res/fighter.png"));
		
		this.frame = new JFrame("Void Raiders by Codecido & Sarpi");
		this.frame.setSize(new Dimension(625,610));
		this.frame.setResizable(false);
		this.frame.setIconImage(icon);
		this.frame.add(BoardDrawer.getInstance());
		
		Utils.centreWindow(this.frame);
		
		KeyListener action = new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				switch(arg0.getKeyCode()){
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_KP_LEFT:
						MainWindow.this.manager.receiveAction(Manager.KEY_LEFT);
						break;
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_KP_RIGHT:
						MainWindow.this.manager.receiveAction(Manager.KEY_RIGHT);
						break;
					case KeyEvent.VK_SPACE:
					case KeyEvent.VK_0:
						MainWindow.this.manager.receiveAction(Manager.KEY_FIRE);
						break;
					default:
						break;
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				//Nada por hacer
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				//Nada por hacer
			}
		};
		
		this.frame.addKeyListener(action);
	}
	
	/** Muestra el di&aacute;logo. */
	public void show(){
		this.frame.setVisible(true);
	}
	
	/** Indica si el di&aacute;logo es visible. */
	public boolean isVisible(){
		return this.frame.isVisible();
	}
	
	/**Retorna el alto de la ventana.
	 * 
	 * @return Alto de la ventana, en p&iacute;xeles.
	 */
	public int getHeight(){
		return this.frame.getHeight();
	}
	
	/**Retorna el ancho de la ventana.
	 * 
	 * @return Ancho de la ventana, en p&iacute;xeles.
	 */
	public int getWidth(){
		return this.frame.getWidth();
	}
	
	/** Retorna un objeto de la clase {@link Dimension} con el alto y el ancho de la ventana.
	 * 
	 * @return La dimensi&oacute;n de la ventana.
	 */
	public Dimension getSize(){
		return this.frame.getSize();
	}
	
	/** Asigna un objeto de alguna clase que implemente la interfaz {@link Manager} como administrador l&oacute;gico de esta ventana.
	 * 
	 * @param manager Referencia a nuevo motor l&oacute;gico.
	 */
	public void setManager(Manager manager){
		this.manager = manager;
	}

	/** Actualiza la apariencia de la ventana y los objetos gr&aacute;ficos contenidos en la misma. */
	public void update(){
		this.frame.repaint();
	}
}
