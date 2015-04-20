package cl.utfsm.inf.lp.tareas2012.vraiders.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Window;

/** Clase <i>toolkit</i> para manejo de gr&aacute;ficos. */
public final class Utils {
	
	private Utils(){}
	
	/** M&eacute;todo para centrar ventanas y di&aacute;logos.
	 * 
	 * Extra&iacute;do desde {@link http://stackoverflow.com/questions/144892/how-to-centre-a-window-in-java}
	 * 
	 * @param frame Ventana a centrar.
	 */
	public static void centreWindow(Window frame) {
	    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    frame.setLocation(x, y);
	}
}
