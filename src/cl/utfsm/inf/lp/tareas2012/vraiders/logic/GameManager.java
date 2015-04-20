package cl.utfsm.inf.lp.tareas2012.vraiders.logic;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import cl.utfsm.inf.lp.tareas2012.vraiders.gui.BoardDrawer;
import cl.utfsm.inf.lp.tareas2012.vraiders.gui.MainWindow;
import cl.utfsm.inf.lp.tareas2012.vraiders.gui.Sprite;

public class GameManager implements Manager {
	
	//Dimension de Matriz de Enemigos
	private static final int ENEMY_COL_COUNT = 11;
	private static final int ENEMY_ROW_COUNT = 4;
	
	//Constantes de PLAYER
	private static final int PLAYER_SHOOT_SPEED = 100;
	private static final int PLAYER_SHOOT_DMG = 2;
	private static final int PLAYER_LIFES_REWARD = 200;
	
	//Constantes de CAZAS
	private static final int CAZAS_SHOOT_SPEED = 100;
	private static final int CAZAS_SHOOT_DMG = 2;
	
	//Constantes de BOMBARDEROS
	private static final int BOMBARDEROS_SHOOT_SPEED = 200;
	private static final int BOMBARDEROS_SHOOT_DMG = 4;
	
	//Constantes de INTERCEPTORES
	private static final int INTERCEPTORES_SHOOT_SPEED = 50;
	private static final int INTERCEPTORES_SHOOT_DMG = 1;
	
	//Constantes de Enemigos
	private static final double ENEMY_FIRE_CHANCE = 7.5;
	private static final int ENEMY_SCORE_REWARD = 100;
	
	//Atributos de Dibujo
	private BoardDrawer drawer = BoardDrawer.getInstance();
	private Enemy[][] enemies = new Enemy[ENEMY_ROW_COUNT][ENEMY_COL_COUNT];
	private Player player;
	private List<Shoots> shoots = new ArrayList<Shoots>();
	private MainWindow ventana;
	private int enemyDelay = 1000;
	private int aliveEnemies = 44;
	private int score = 0;
	
	//Timers
	private Timer enemyTimer;
	private Timer shootTimer[];
	
	//Constructor
	public GameManager (MainWindow ventana) {
		this.ventana = ventana;
		this.ventana.setManager(this);
	}
	
	//Inicializador del Juego y controlador del mismo
	public void startGame () throws IOException{
		Point pos = new Point (23*BoardDrawer.PIXEL, BoardDrawer.V_START_SEPARATION*BoardDrawer.PIXEL);
		
		//INTERCEPTORES
		for(int i = 0; i < ENEMY_COL_COUNT; i++){
			Enemy enemy = new Enemy(Sprite.INTERCEPTOR_SPRITE, pos);
			enemy.setHealth(2);
			pos.setLocation(pos.x + (enemy.getWidth() + BoardDrawer.H_SEPARATION*BoardDrawer.PIXEL), pos.y);
			this.enemies[0][i] = enemy;
			this.drawer.addBoardObject(enemy);
		}
		//BOMBARDEROS
		pos.setLocation(23*BoardDrawer.PIXEL, this.enemies[0][0].getHeight()+((BoardDrawer.V_START_SEPARATION+BoardDrawer.V_SEPARATION)*BoardDrawer.PIXEL));
		for(int i = 0; i < ENEMY_COL_COUNT; i++){
			Enemy enemy = new Enemy(Sprite.BOMBER_SPRITE, pos);
			enemy.setHealth(5);
			pos.setLocation(pos.x + (enemy.getWidth() + BoardDrawer.H_SEPARATION*BoardDrawer.PIXEL), pos.y);
			this.enemies[1][i] = enemy;
			this.drawer.addBoardObject(enemy);
		}
		//CAZAS 2
		pos.setLocation(23*BoardDrawer.PIXEL, 2*this.enemies[1][0].getHeight()+((BoardDrawer.V_START_SEPARATION+2*BoardDrawer.V_SEPARATION)*BoardDrawer.PIXEL));
		for(int i = 0; i < ENEMY_COL_COUNT; i++){
			Enemy enemy = new Enemy(Sprite.FIGHTER_SPRITE, pos);
			enemy.setHealth(2);
			pos.setLocation(pos.x + (enemy.getWidth() + BoardDrawer.H_SEPARATION*BoardDrawer.PIXEL), pos.y);
			this.enemies[2][i] = enemy;
			this.drawer.addBoardObject(enemy);
		}
		//CAZAS 1
		pos.setLocation(23*BoardDrawer.PIXEL, 3*this.enemies[2][0].getHeight()+((BoardDrawer.V_START_SEPARATION+3*BoardDrawer.V_SEPARATION)*BoardDrawer.PIXEL));
		for(int i = 0; i < ENEMY_COL_COUNT; i++){
			Enemy enemy = new Enemy(Sprite.FIGHTER_SPRITE, pos);
			enemy.setHealth(2);
			pos.setLocation(pos.x + (enemy.getWidth() + BoardDrawer.H_SEPARATION*BoardDrawer.PIXEL), pos.y);
			this.enemies[3][i] = enemy;
			this.drawer.addBoardObject(enemy);
		}
		//Player
		this.player = new Player(Sprite.TANK_SPRITE, new Point(300, 300));
		this.player.setLocation(new Point((this.ventana.getWidth() - this.player.getWidth())/2, this.ventana.getHeight() - (this.player.getHeight() + 15 * BoardDrawer.PIXEL)));
		this.player.setMaxRightMov(this.ventana.getWidth() - 2*BoardDrawer.PIXEL);
		this.player.addSprite(Sprite.getSprite(Sprite.TANK_FIGHTER_SPRITE));
		this.drawer.addBoardObject(this.player);
		//MainWindow
		this.ventana.show();
		//Timers
		ActionListener enemyListener = new ActionListener(){
			private static final int STATUS_RIGHT = 0x2358;
			private static final int STATUS_LEFT = 0x2359;
			
			private int currentStatus = STATUS_RIGHT;
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				boolean[] check = new boolean[GameManager.ENEMY_COL_COUNT];
				
				//Chequeo previo - determina margenes laterales de grupo de enemigos
				for(int i = 0; i < GameManager.ENEMY_COL_COUNT; i++){
					check[i] = false;
				}
				for(int i = 0; i < GameManager.ENEMY_COL_COUNT; i++){
					for(int j = 0; j < GameManager.ENEMY_ROW_COUNT; j++){
						if(GameManager.this.enemies[j][i].getHealth() > 0){
							check[i] = true;
							break;
						}
					}
				}
				//Movimiento en tablero
				switch(this.currentStatus){
					case STATUS_RIGHT://Si toca moverse a la derecha
						for(int i = GameManager.ENEMY_COL_COUNT - 1; i >= 0; i--){
							if(check[i]){
								for(int j = 0; j < GameManager.ENEMY_ROW_COUNT; j++){
									if(GameManager.this.enemies[j][i].getHealth() > 0){
										//Si aun queda espacio a la derecha
										if(GameManager.this.enemies[j][i].getX() + GameManager.this.enemies[j][i].getWidth() + 2*BoardDrawer.PIXEL < GameManager.this.ventana.getWidth() - 2*BoardDrawer.PIXEL){
											this.moveEnemies(Character.MOVE_RIGHT);
										}
										//Si ya no queda espacio
										else{
											this.moveEnemies(Character.MOVE_DOWN);
											this.currentStatus = STATUS_LEFT;
											//Si llego a la altura del PLAYER
											if (player.getLocation().y < GameManager.this.enemies[j][i].getLocation().y+120) {
												player.setHealth(0);
												drawer.setHealth(0);
												drawer.removeBoardObject(player);
												drawer.setStatus(STATUS_DEFEAT);
											}
										}
										break;
									}
								}
								break;
							}
						}
						break;
					case STATUS_LEFT://Si toca moverse a la izquierda
						for(int i = 0; i < GameManager.ENEMY_COL_COUNT; i++){
							if(check[i]){
								for(int j = 0; j < GameManager.ENEMY_ROW_COUNT; j++){
									if(GameManager.this.enemies[j][i].getHealth() > 0){
										//Si aun queda espacio a la izquierda
										if(GameManager.this.enemies[j][i].getX() - 2*BoardDrawer.PIXEL > 0){
											this.moveEnemies(Character.MOVE_LEFT);
										}
										//Si ya no queda espacio
										else{
											this.moveEnemies(Character.MOVE_DOWN);
											this.currentStatus = STATUS_RIGHT;
											//Si llego a la altura del PLAYER
											if (player.getLocation().y < GameManager.this.enemies[j][i].getLocation().y+120) {
												player.setHealth(0);
												drawer.setHealth(0);
												drawer.removeBoardObject(player);
												drawer.setStatus(STATUS_DEFEAT);
											}
										}
										break;
									}
								}
								break;
							}
						}
						break;
					default:
						break;
				}
				
				//Calculo y creacion de disparos enemigos
				for (int i = 0; i < GameManager.ENEMY_COL_COUNT; i++) {
					for (int j = 0; j < GameManager.ENEMY_ROW_COUNT; j++) {
						if (enemies[j][i].getHealth()>0 && Math.random()*100<=GameManager.ENEMY_FIRE_CHANCE) {
							boolean disparar = true;
							int aux = GameManager.ENEMY_ROW_COUNT - j - 1;
							//Si hay enemigos al frente no dispara
							while (aux > 0) {
								if (enemies[j+aux][i].getHealth()>0) {
									disparar = false;
									aux = 0;
								}
								aux--;
							}
							if (disparar) {
								try {
									if (j > 2)
										shoots.add(new Shoots(Collider.FIGHTER_AMMO_SPRITE,Collider.COLLIDABLE_ORIENTATION_TOP_DOWN,enemies[j][i].getLocation(),1,CAZAS_SHOOT_DMG));
									if (j == 1)
										shoots.add(new Shoots(Collider.BOMBER_AMMO_SPRITE,Collider.COLLIDABLE_ORIENTATION_TOP_DOWN,enemies[j][i].getLocation(),2,BOMBARDEROS_SHOOT_DMG));
									if (j == 0)
										shoots.add(new Shoots(Collider.INTERCEPTOR_AMMO_SPRITE,Collider.COLLIDABLE_ORIENTATION_TOP_DOWN,enemies[j][i].getLocation(),3,INTERCEPTORES_SHOOT_DMG));
									drawer.addBoardObject(shoots.get(shoots.size()-1));
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
			
			private void moveEnemies(int direction){
				for(int i = 0; i < GameManager.ENEMY_COL_COUNT; i++){
					for(int j = 0; j < GameManager.ENEMY_ROW_COUNT; j++){
						if(GameManager.this.enemies[j][i] != null){
							GameManager.this.enemies[j][i].action(direction);
						}
					}
				}
			}
		};
		this.enemyTimer = new Timer(enemyDelay, enemyListener);
		this.enemyTimer.start();
		
		shootTimer = new Timer[4];
		
		//Disparos PLAYER
		ActionListener updatePlayerShoots = new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < shoots.size(); i ++) {
					if (shoots.get(i).getColliderOrientation() == Collider.COLLIDABLE_ORIENTATION_BOTTOM_UP) {
						if (shoots.get(i).getTipo() == 0) {
							shoots.get(i).setLocation(shoots.get(i).getLocation().x, shoots.get(i).getLocation().y - 2*BoardDrawer.PIXEL);
							for (int k = 0; k < GameManager.ENEMY_COL_COUNT; k++) {
								for (int j = 0; j < GameManager.ENEMY_ROW_COUNT; j++) {
									if (!shoots.isEmpty()) {
										if (enemies[j][k].getHealth() > 0) {
											if (enemies[j][k].containsPoint(shoots.get(i).getLocation())) {
												enemies[j][k].setHealth(enemies[j][k].getHealth() - shoots.get(i).getDamage());
												if (enemies[j][k].getHealth() < 1) {
													score = score + ENEMY_SCORE_REWARD;
													drawer.setScore(score);
													drawer.removeBoardObject(enemies[j][k]);
													updateEnemies();
												}
												drawer.removeBoardObject(shoots.get(i));
												shoots.remove(i);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		};
		shootTimer[0] = new Timer(PLAYER_SHOOT_SPEED, updatePlayerShoots);
		shootTimer[0].start();
		
		//Disparos CAZA
		ActionListener updateCazaShoots = new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < shoots.size(); i ++) {
					if (shoots.get(i).getColliderOrientation() == Collider.COLLIDABLE_ORIENTATION_TOP_DOWN) {
						if (shoots.get(i).getTipo() == 1) {
							shoots.get(i).setLocation(shoots.get(i).getLocation().x, shoots.get(i).getLocation().y + 2*BoardDrawer.PIXEL);
							if (!shoots.isEmpty()) {
								if (player.getHealth() > 0) {
									if (player.containsPoint(shoots.get(i).getLocation())) {
										player.setHealth(player.getHealth() - shoots.get(i).getDamage());
										drawer.setHealth(player.getHealth());
										if (player.getHealth() < 1) {
											//Correccion si la Health queda negativa
											player.setHealth(0);
											drawer.setHealth(0);
											drawer.removeBoardObject(player);
											drawer.setStatus(STATUS_DEFEAT);
										}
										drawer.removeBoardObject(shoots.get(i));
										shoots.remove(i);
									}
								}
							}
						}
					}
				}
			}
		};
		shootTimer[1] = new Timer(CAZAS_SHOOT_SPEED, updateCazaShoots);
		shootTimer[1].start();
		
		//Disparos BOMBARDERO
		ActionListener updateBombarderoShoots = new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < shoots.size(); i ++) {
					if (shoots.get(i).getColliderOrientation() == Collider.COLLIDABLE_ORIENTATION_TOP_DOWN) {
						if (shoots.get(i).getTipo() == 2) {
							shoots.get(i).setLocation(shoots.get(i).getLocation().x, shoots.get(i).getLocation().y + 2*BoardDrawer.PIXEL);
							if (!shoots.isEmpty()) {
								if (player.getHealth() > 0) {
									if (player.containsPoint(shoots.get(i).getLocation())) {
										player.setHealth(player.getHealth() - shoots.get(i).getDamage());
										drawer.setHealth(player.getHealth());
										if (player.getHealth() < 1) {
											//Correccion si la Health queda negativa
											player.setHealth(0);
											drawer.setHealth(0);
											drawer.removeBoardObject(player);
											drawer.setStatus(STATUS_DEFEAT);
										}
										drawer.removeBoardObject(shoots.get(i));
										shoots.remove(i);
									}
								}
							}
						}
					}
				}
			}
		};
		shootTimer[2] = new Timer(BOMBARDEROS_SHOOT_SPEED, updateBombarderoShoots);
		shootTimer[2].start();
		
		//Disparos INTERCEPTOR
		ActionListener updateInterceptorShoots = new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < shoots.size(); i ++) {
					if (shoots.get(i).getColliderOrientation() == Collider.COLLIDABLE_ORIENTATION_TOP_DOWN) {
						if (shoots.get(i).getTipo() == 3) {
							shoots.get(i).setLocation(shoots.get(i).getLocation().x, shoots.get(i).getLocation().y + 2*BoardDrawer.PIXEL);
							if (!shoots.isEmpty()) {
								if (player.getHealth() > 0) {
									if (player.containsPoint(shoots.get(i).getLocation())) {
										player.setHealth(player.getHealth() - shoots.get(i).getDamage());
										drawer.setHealth(player.getHealth());
										if (player.getHealth() < 1) {
											//Correccion si la Health queda negativa
											player.setHealth(0);
											drawer.setHealth(0);
											drawer.removeBoardObject(player);
											drawer.setStatus(STATUS_DEFEAT);
										}
										drawer.removeBoardObject(shoots.get(i));
										shoots.remove(i);
									}
								}
							}
						}
					}
				}
			}
		};
		shootTimer[3] = new Timer(INTERCEPTORES_SHOOT_SPEED, updateInterceptorShoots);
		shootTimer[3].start();
		
	}
	
	@Override
	public void receiveAction (int key) {
		switch(key){
			case Manager.KEY_LEFT:
				this.player.action(Character.MOVE_LEFT);
				break;
			case Manager.KEY_RIGHT:
				this.player.action(Character.MOVE_RIGHT);
				break;
			case Manager.KEY_FIRE:
				if (player.getHealth() > 0) {
					try {
						shoots.add(new Shoots(Collider.FIGHTER_AMMO_SPRITE,Collider.COLLIDABLE_ORIENTATION_BOTTOM_UP,player.getLocation(),0,PLAYER_SHOOT_DMG));
						drawer.addBoardObject(shoots.get(shoots.size()-1));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				break;
			default:
				break;
		}
	}
	
	@Override
	public void collision (Collider collider, Character collided) {
		
	}
	
	public void updateEnemies() {
		aliveEnemies--;
		if (aliveEnemies == 0) {
			score = score + player.getHealth()*PLAYER_LIFES_REWARD;
			drawer.setScore(score);
			drawer.setStatus(STATUS_VICTORY);
			shootTimer[0].stop();
			shootTimer[1].stop();
			shootTimer[2].stop();
			shootTimer[3].stop();
		}
		else {
			enemyDelay = (aliveEnemies*1000)/44;
			this.enemyTimer.setDelay(enemyDelay);
		}
	}
	
}