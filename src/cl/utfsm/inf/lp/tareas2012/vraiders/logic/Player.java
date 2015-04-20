package cl.utfsm.inf.lp.tareas2012.vraiders.logic;

import java.awt.Point;
import java.io.IOException;

import cl.utfsm.inf.lp.tareas2012.vraiders.gui.BoardDrawer;

public class Player extends Character {
	
	/** UID generada */
	private static final long serialVersionUID = -7002535773591239182L;
	
	/** Limite derecho del tablero */
	private int rightLimit;
	
	public Player(String filepath, Point pos) throws IOException {
		super(filepath, Character.CHARACTER_PLAYER);
		this.setLocation(pos.x, pos.y);
		this.setVisible(true);
		this.setHealth(20);
	}
	
	@Override
	public void action(int signal) {
		switch(signal){
		case Character.MOVE_LEFT:
			if(this.getX() - 2*BoardDrawer.PIXEL > 0)
				this.setLocation(this.getX() - 2*BoardDrawer.PIXEL, this.getY());
			break;
		case Character.MOVE_RIGHT:
			if(this.getX() + this.getWidth() + 2*BoardDrawer.PIXEL < this.rightLimit)
				this.setLocation(this.getX() + 2*BoardDrawer.PIXEL, this.getY());
			break;
		default:
			break;
	}
}
	
	public void setMaxRightMov(int rightLimit){
		this.rightLimit = rightLimit;
	}
	
}