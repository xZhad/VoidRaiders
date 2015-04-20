package cl.utfsm.inf.lp.tareas2012.vraiders.logic;

/** Interfaz que declara el comportamiento m&iacute;nimo esperado, por parte de {@link cl.utfsm.inf.lp.tareas2012.vraiders.gui.MainWindow}, con respecto a la clase que
 * act&uacute;e como motor l&oacute;gico de la aplicaci&oacute;n.
 * 
 * Tambi&eacute;n define algunas constantes l&oacute;gicas usadas en {@link cl.utfsm.inf.lp.tareas2012.vraiders.gui.MainWindow} y {@link cl.utfsm.inf.lp.tareas2012.vraiders.gui.BoardDrawer}.
 */
public interface Manager {
	/** Indica que la tecla izquierda (ya sea la est&aacute;ndar o la del teclado num&eacute;rico) fue presionada. */
	public static final int KEY_LEFT = 0x0001;
	/** Indica que la tecla derecha (ya sea la est&aacute;ndar o la del teclado num&eacute;rico) fue presionada. */
	public static final int KEY_RIGHT = 0x0002;
	/** Indica que la tecla de disparo (ya sea la barra espaciadora o la tecla 0 del teclado num&eacute;rico) fue presionada. */
	public static final int KEY_FIRE = 0x0003;
	/** Indica que el juego se encuentra en ejecuci&oacute;n y desarrollo. */
	public static final int STATUS_RUNNING = 0x00511;
	/** Indica que el juego termin&oacute; con una victoria para el jugador. */
	public static final int STATUS_VICTORY = 0x00512;
	/** Indica que el juego termin&oacute; con una derrota para el jugador. */
	public static final int STATUS_DEFEAT = 0x00513;
	
	/** Recibe desde un objeto {@code MainWindow} el c&oacute;digo de una tecla presionada, si es que &eacute;sta corresponde a una de
	 * las teclas asociadas a un evento en el juego (moverse, disparar).
	 * 
	 * @param key C&oacute;digo de la tecla presionada ({@code KEY_LEFT}, {@code KEY_RIGHT} o {@code KEY_FIRE}).
	 */
	public void receiveAction(int key);
	/** Recibe las referencias de un objeto colisionante (ej.: disparo) y el personaje impactado por el mismo, cuando ocurre una
	 * colisi&oacute;n, de manera de que se pueda determinar los efectos de la misma.
	 * 
	 * @param collider Referencia al objeto que impacta a un personaje del tablero (enemigo o jugador).
	 * @param collided Referencia al objeto impactado.
	 */
	public void collision(Collider collider, Character collided);
}
