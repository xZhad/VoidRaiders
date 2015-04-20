package cl.utfsm.inf.lp.tareas2012.vraiders.logic;

import java.io.IOException;

import cl.utfsm.inf.lp.tareas2012.vraiders.gui.Sprite;

/** Clase abstracta que representa a los personajes del tablero de juego (enemigos y jugador).
 * 
 * Implementa funcionalidad com&uacute;n y define algunas constantes.
 */
public abstract class Character extends Sprite {
	
	/** UID generada de manera autom&aacute;tica. */
	private static final long serialVersionUID = 5482808989494970178L;
	
	/** Constante utilizada para indicar un movimiento a la derecha. */
	public static final int MOVE_RIGHT = 0x2358;
	/** Constante utilizada para indicar un movimiento a la izquierda. */
	public static final int MOVE_LEFT = 0x2359;
	/** Constante utilizada para indicar un movimiento hacia abajo (para el caso de los enemigos). */
	public static final int MOVE_DOWN = 0x2400;
	/** Constante utilizada para indicar una acci&oacute;n de disparo. */
	public static final int SHOOT = 0x2401;
	
	/** Constante utilizada para indicar que un objeto corresponde a un enemigo. */
	public static final int CHARACTER_ENEMY = 0x00401;
	/** Constante utilizada para indicar que un objeto corresponde al personaje del jugador. */
	public static final int CHARACTER_PLAYER = 0x00402;
	
	//Salud del personaje
	private int health;
	//Tipo del personaje - enemigo o jugador
	private int type;
	
	/** Instancia un nuevo personaje.
	 * 
	 * @param filepath Ruta del archivo de imagen base del personaje.
	 * @param type Tipo del personaje (enemigo, jugador).
	 * @throws IOException Si el archivo de imagen no pudo ser abierto (ej.: no existe).
	 * @see #CHARACTER_ENEMY
	 * @see #CHARACTER_PLAYER
	 */
	public Character(String filepath, int type) throws IOException {
		super(filepath);
		this.type = type;
	}
	
	/** Indica la realizaci&oacute;n de una acci&oacute;n por parte del personaje.
	 * 
	 * @param signal Se&ntilde;al de la acci&oacute;n a realizar.
	 * @see #MOVE_RIGHT
	 * @see #MOVE_LEFT
	 * @see #MOVE_DOWN
	 * @see #SHOOT
	 */
	public abstract void action(int signal);
	
	/** Retorna la salud del personaje.
	 * 
	 * @return Salud del personaje.
	 */
	public int getHealth(){
		return this.health;
	}
	
	/** Modifica la salud del personaje.
	 * 
	 * @param health Nueva salud del personaje.
	 */
	public void setHealth(int health){
		this.health = health;
	}
	
	/** Indica el tipo del personaje.
	 * 
	 * @return Tipo del personaje.
	 * @see #CHARACTER_ENEMY
	 * @see #CHARACTER_PLAYER
	 */
	public int getType(){
		return this.type;
	}
}