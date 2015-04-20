package cl.utfsm.inf.lp.tareas2012.vraiders;

import java.io.IOException;

import cl.utfsm.inf.lp.tareas2012.vraiders.gui.MainWindow;
import cl.utfsm.inf.lp.tareas2012.vraiders.logic.GameManager;

public class VoidRaiders {
	/**Metodo llamado al ejecutar la aplicacion
	 * 
	 * @param args
	 */
	public static void main (String args[]) {
		
		MainWindow ventana;
		GameManager manager;
		
		try {
			ventana = new MainWindow();
			manager = new GameManager(ventana);
			
			manager.startGame();
			
			//Ciclo de ejecucion
			while (ventana.isVisible()) {
				ventana.update();
			}
			
			System.exit(0);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
}