package cl.utfsm.inf.lp.tareas2012.vraiders.gui;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JComponent;

/** Clase abstracta que sirve de base a todos los elementos visibles en el tablero de juego.
 * 
 * Implementa todas las operaciones necesarias
 * para el manejo y despliegue de im&aacute;genes. Se declara como clase abstracta para forzar la implementaci&oacute;n de otras clases
 * que agreguen el componente l&oacute;gico faltante (ej.: manejo de salud, evento de disparo, etc.). */
public abstract class Sprite extends JComponent {

	/** UID generada de manera autom&aacute;tica. */
	private static final long serialVersionUID = -7756719805969031466L;
	/** Im&aacute;genes a dibujar. */
	private ArrayList<BufferedImage> sprite;
	
	/** Ruta del archivo con la imagen de los caza. */
	public static final String FIGHTER_SPRITE = "res/fighter.png";
	/** Ruta del archivo con la imagen de los bombarderos. */
	public static final String BOMBER_SPRITE = "res/bomber.png";
	/** Ruta del archivo con la imagen de los interceptores. */
	public static final String INTERCEPTOR_SPRITE = "res/interceptor.png";
	/** Ruta del archivo con la imagen base del personaje del jugador. */
	public static final String TANK_SPRITE = "res/tank.png";
	/** Ruta del archivo con la imagen del arma caza para el personaje del jugador. */
	public static final String TANK_FIGHTER_SPRITE = "res/tank_fighter.png";
	/** Ruta del archivo con la imagen del arma bombardero para el personaje del jugador. */
	public static final String TANK_BOMBER_SPRITE = "res/tank_bomber.png";
	/** Ruta del archivo con la imagen del arma interceptor para el personaje del jugador. */
	public static final String TANK_INTERCEPTOR_SPRITE = "res/tank_interceptor.png";
	/** Ruta del archivo con la imagen del disparo de los caza. */
	public static final String FIGHTER_AMMO_SPRITE = "res/laser.png";
	/** Ruta del archivo con la imagen del disparo de los bombarderos. */
	public static final String BOMBER_AMMO_SPRITE = "res/bomb.png";
	/** Ruta del archivo con la imagen del disparo del tanque con el arma de los bombarderos. */
	public static final String BOMBER_TANK_AMMO_SPRITE = "res/bomb_inv.png";
	/** Ruta del archivo con la imagen del disparo de los interceptores. */
	public static final String INTERCEPTOR_AMMO_SPRITE = "res/pellet.png";
	/** Ruta del archivo con la imagen de la mejora de arma de los caza. */
	public static final String FIGHTER_POWERUP_SPRITE = "res/powerup_fighter.png";
	/** Ruta del archivo con la imagen de la mejora de arma de los bombarderos. */
	public static final String BOMBER_POWERUP_SPRITE = "res/powerup_bomber.png";
	/** Ruta del archivo con la imagen de la mejora de arma de los interceptores. */
	public static final String INTERCEPTOR_POWERUP_SPRITE = "res/powerup_interceptor.png";
	
	/** Inicializa un nuevo objeto de sprite con la imagen indicada por par&aacute;metro como base del mismo.
	 * 
	 * @param filepath Ruta al archivo de imagen a usar de base para este sprite.
	 * @throws IOException Si hubo un error al leer el archivo (ej.: archivo inexistente).
	 */
	public Sprite(String filepath) throws IOException{
		this.sprite = new ArrayList<BufferedImage>(); 
		this.sprite.add(ImageIO.read(new File(filepath)));
		this.setSize(this.sprite.get(0).getWidth(), this.sprite.get(0).getHeight());
		this.setMinimumSize(getSize());
		this.setMaximumSize(getSize());
	}
	
	/**Retorna la referencia a un objeto de imagen que contiene los datos del archivo indicado por par&aacute;metro.
	 * 
	 * @param filepath Ruta al archivo de imagen a cargar.
	 * @return La referencia a un objeto {@code BufferedImage} con la informaci&oacute;n de la imagen indicada.
	 * @throws IOException Si hubo un error al leer el archivo (ej.: archivo inexistente).
	 */
	public static BufferedImage getSprite(String filepath) throws IOException{
		return ImageIO.read(new File(filepath));
	}
	
	/** Inserta un elemento en la lista de im&aacute;genes a dibujar en este sprite. 
	 * 
	 * @param sprite Referencia a la imagen a insertar en la lista. 
	 */
	public void addSprite(BufferedImage sprite){
		this.sprite.add(sprite);
	}
	
	/** Elimina un elemento de la lista de im&aacute;genes a dibujar en este sprite. 
	 * 
	 * @param sprite Referencia a la imagen a eliminar de la lista. 
	 */
	public void removeSprite(BufferedImage sprite){
		this.sprite.remove(sprite);
	}
	
	/** Indica si un punto en particular est&aacute; dentro de los m&aacute;rgenes de este sprite.
	 * 
	 * @param point Punto a revisar.
	 * @return {@code true} si es que el punto est&aacute; dentro de los m&aacute;rgenes de este sprite, {@code false} en caso contrario.
	 */
	public boolean containsPoint(Point point){
		return ((this.getX() <= point.x && this.getX() + this.getWidth() >= point.x) && (this.getY() <= point.y && this.getY() + this.getHeight() >= point.y));
	}
	
	@Override
	protected void paintComponent(Graphics g){
		for(int i = 0; i < this.sprite.size(); i++)
			g.drawImage(this.sprite.get(i),this.getX(),this.getY(),null);
	}
}
