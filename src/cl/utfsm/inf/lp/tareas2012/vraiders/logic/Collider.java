package cl.utfsm.inf.lp.tareas2012.vraiders.logic;

import java.io.IOException;

import cl.utfsm.inf.lp.tareas2012.vraiders.gui.Sprite;

/** Clase de objetos colisionantes.
 *
 * Clase que representa los objetos que pueden impactar con los personajes en el tablero de juego - disparos o <i>power-ups</i>.
 */
public abstract class Collider extends Sprite{
	
	/** UID generada de manera autom&aacute;tica. */
	private static final long serialVersionUID = 5082537419645073906L;
	
	/** Indica que el objeto corresponde a un disparo. */
	public static final int COLLIDABLE_AMMO = 0x01536;
	/** Indica que el objeto corresponde a un <i>power-up</i>. */
	public static final int COLLIDABLE_POWERUP = 0x01537;
	/** Indica que el objeto va de abajo hacia arriba en el tablero */
	public static final int COLLIDABLE_ORIENTATION_BOTTOM_UP = 0x01538;
	/** Indica que el objeto va de arriba hacia abajo en el tablero */
	public static final int COLLIDABLE_ORIENTATION_TOP_DOWN = 0x01539;
	
	//Tipo de objeto
	private int colliderType;
	//Direccion del objeto
	private int colliderOrientation;
	
	/** Instancia un nuevo objeto colisionador.
	 * 
	 * @param filepath Ruta del archivo de imagen a usar de base del objeto.
	 * @throws IOException Si el archivo de imagen no pudo ser abierto (ej.: no existe).
	 */
	public Collider(String filepath) throws IOException {
		super(filepath);
	}

	/** Retorna el tipo del objeto colisionador.
	 * 
	 * @return Tipo del objeto.
	 */
	public int getColliderType() {
		return this.colliderType;
	}

	/** Modifica el tipo del objeto colisionador.
	 * 
	 * @param colliderType Nuevo tipo del objeto.
	 */
	public void setColliderType(int colliderType) {
		this.colliderType = colliderType;
	}

	/** Retorna la orientaci&oacute;n del objeto en el tablero.
	 * 
	 * @return Orientaci&oacute;n del objeto.
	 */
	public int getColliderOrientation() {
		return this.colliderOrientation;
	}

	/** Modifica la orientaci&oacute;n del objeto en el tablero.
	 * 
	 * @param colliderOrientation Nueva orientaci&oacute;n del objeto.
	 */
	public void setColliderOrientation(int colliderOrientation) {
		this.colliderOrientation = colliderOrientation;
	}
	
}
