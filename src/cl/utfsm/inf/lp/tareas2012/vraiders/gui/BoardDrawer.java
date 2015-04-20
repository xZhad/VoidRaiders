package cl.utfsm.inf.lp.tareas2012.vraiders.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.TextAttribute;
import java.text.AttributedString;
import java.util.ArrayList;
import javax.swing.JComponent;

import cl.utfsm.inf.lp.tareas2012.vraiders.logic.Manager;

/** Motor gr&aacute;fico de la aplicaci&oacute;n.
 *
 * Clase encargada del despliegue gr&aacute;fico de los elementos que componen el tablero de juego, adem&aacute;s de datos como el
 * puntaje y la salud del jugador. Implementada como clase <i>singleton</i>. 
 */
public class BoardDrawer extends JComponent {

	/** UID generada de manera autom&aacute;tica. */
	private static final long serialVersionUID = 3534775807547963432L;
	/* Instancia unica de BoardDrawer. */
	private static BoardDrawer instance = new BoardDrawer();
	/* Arreglo dinamico de enemigos. */
	private ArrayList<Sprite> objects;
	/* Puntaje en el juego (el que, por cierto, perdieron). */
	private int score = 0;
	/* Salud del jugador. */
	private int health = 20;
	/* Estado de ejecucion del juego. */
	private int status = Manager.STATUS_RUNNING;
	
	/** Tama&ntilde;o del "pixel" en el juego (en p&iacute;xeles reales). */
	public static final int PIXEL = 3;
	/** Distancia de separaci&oacute;n horizontal entre enemigos (en p&iacute;xeles del juego). */
	public static final int H_SEPARATION = 4;
	/** Distancia de separaci&oacute;n vertical entre enemigos (en p&iacute;xeles del juego). */
	public static final int V_SEPARATION = 3;
	/** Distancia de separaci&oacute;n vertical inicial entre enemigos y borde superior de la ventana (en p&iacute;xeles del juego). */
	public static final int V_START_SEPARATION = 15;

	/** Constructor privado. */
	private BoardDrawer(){
		this.objects = new ArrayList<Sprite>();
	}
	
	/** Retorna una referencia al &uacute;nico objeto instanciado de esta clase.
	 * 
	 * @return Referencia al objeto singleton de esta clase.
	 */
	public static BoardDrawer getInstance(){
		return instance;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		//Fondo
		this.setBackground(Color.BLACK);
		this.setForeground(Color.BLACK);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		//Texto - puntaje y salud
		AttributedString scoreString = new AttributedString("SCORE: " + Integer.toString(this.score));
		scoreString.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);
		scoreString.addAttribute(TextAttribute.FONT, new Font("Monospaced", Font.PLAIN, 14));
		g.drawString(scoreString.getIterator(), 10, 20);
		AttributedString healthString = new AttributedString("HP: " + Integer.toString(this.health));
		healthString.addAttribute(TextAttribute.FOREGROUND, Color.WHITE);
		healthString.addAttribute(TextAttribute.FONT, new Font("Monospaced", Font.PLAIN, 14));
		g.drawString(healthString.getIterator(), this.getWidth()-60, 20);
		//Texto - estado
		AttributedString statusString = null;
		if(this.status == Manager.STATUS_VICTORY){
			statusString = new AttributedString("YOU ARE VICTORIOUS!");
			statusString.addAttribute(TextAttribute.FOREGROUND, Color.GREEN);
			statusString.addAttribute(TextAttribute.FONT, new Font("Monospaced", Font.PLAIN, 14));
		}
		else if (this.status == Manager.STATUS_DEFEAT){
			statusString = new AttributedString("YOU HAVE BEEN DEFEATED.");
			statusString.addAttribute(TextAttribute.FOREGROUND, Color.RED);
			statusString.addAttribute(TextAttribute.FONT, new Font("Monospaced", Font.PLAIN, 14));
		}
		if(statusString != null)
			g.drawString(statusString.getIterator(), 10, 40);
		//Objetos
		for(int i = 0; i < this.objects.size(); i++)
			this.objects.get(i).paintComponent(g);
	}
	
	/** Inserta un objeto dibujable (sprite) en el tablero.
	 * 
	 * @param object Referencia al objeto a insertar en el tablero.
	 */
	public void addBoardObject(Sprite object){
		this.objects.add(object);
	}
	
	/** Extrae un objeto del tablero.
	 * 
	 * @param object Referencia al objeto a remover del tablero.
	 */
	public void removeBoardObject(Sprite object){
		this.objects.remove(object);
	}
		
	/** Actualiza el puntaje a mostrar por pantalla.
	 * 
	 * @param score Puntaje a mostrar por pantalla.
	 */
	public void setScore(int score){
		this.score = score;
	}
	
	/** Actualiza el nivel de salud del jugador a mostrar por pantalla. 
	 * 
	 * @param health Nivel de salud a mostrar por pantalla.
	 */
	public void setHealth(int health){
		this.health = health;
	}
	
	/** Cambia el estado de juego a representar en pantalla.
	 * 
	 * @param status Estado a utilizar para mostrar por pantalla.
	 * @see cl.utfsm.inf.lp.tareas2012.vraiders.logic.Manager#STATUS_RUNNING
	 * @see cl.utfsm.inf.lp.tareas2012.vraiders.logic.Manager#STATUS_VICTORY
	 * @see cl.utfsm.inf.lp.tareas2012.vraiders.logic.Manager#STATUS_DEFEAT
	 */
	public void setStatus(int status){
		this.status = status;
	}
	
}
