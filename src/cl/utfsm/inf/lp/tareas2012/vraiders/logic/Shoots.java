package cl.utfsm.inf.lp.tareas2012.vraiders.logic;

import java.awt.Point;
import java.io.IOException;

public class Shoots extends Collider {
	
	/** UID generada */
	private static final long serialVersionUID = 8360541478120410877L;
	
	/** 
	 * 0 = PLAYER
	 * 1 = CAZA
	 * 2 = BOMBARDERO
	 * 3 = INTERCEPTOR
	 */
	private int tipo;
	private int damage;
	
	public Shoots (String filepath, int orientation, Point pos, int tipo, int damage) throws IOException {
		super(filepath);
		this.setColliderType(Collider.COLLIDABLE_AMMO);
		this.setColliderOrientation(orientation);
		if (tipo == 0)
			this.setLocation(pos.x+15, pos.y-10);
		else
			this.setLocation(pos.x+15, pos.y+20);
		setTipo(tipo);
		setDamage(damage);
		this.setVisible(true);
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getTipo() {
		return tipo;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public int getDamage() {
		return damage;
	}
}