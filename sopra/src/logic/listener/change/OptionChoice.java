package logic.listener.change;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JOptionPane;

import visual.PopUpMessage;

/**
 * Clase del listener sobre la eleccion hecha en un PopUpMessage
 * @author ddelaconcepcionsaez
 *
 */
public class OptionChoice implements PropertyChangeListener {

	private PopUpMessage dialg;
	
	public OptionChoice(PopUpMessage diag){
		dialg=diag;
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		String prop = e.getPropertyName();
		
		if (dialg.isVisible() && (e.getSource() == dialg.getJOption()) && (prop.equals(JOptionPane.VALUE_PROPERTY))) {
			dialg.setVisible(false);
		}
	}
}
