package cl.utfsm.inf.lp.tareas2012.vraiders.logic;

import java.awt.Point;
import java.io.IOException;

import cl.utfsm.inf.lp.tareas2012.vraiders.gui.BoardDrawer;

public class Enemy extends Character {
	
	/** UID generada */
	private static final long serialVersionUID = 1568593626924049787L;
	
	public Enemy(String filepath, Point pos) throws IOException {
		super(filepath, Character.CHARACTER_ENEMY);
		this.setLocation(pos.x, pos.y);
		this.setVisible(true);
	}
	
	@Override
	public void action(int signal) {
		switch(signal){
			case Character.MOVE_LEFT:
				this.setLocation(this.getX() - 2 * BoardDrawer.PIXEL, this.getY());
				break;
			case Character.MOVE_RIGHT:
				this.setLocation(this.getX() + 2 * BoardDrawer.PIXEL, this.getY());
				break;
			case Character.MOVE_DOWN:
				this.setLocation(this.getX(), this.getY() + this.getHeight() + BoardDrawer.V_SEPARATION * BoardDrawer.PIXEL);
				break;
			default:
				break;
		}
	}
	
}