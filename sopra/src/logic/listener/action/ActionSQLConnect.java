package logic.listener.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JTextField;

import dbmanager.Connector;
import visual.VentanaPrincipal;

/**
 * Clase para el listener que crea un conector.
 * @author ddelaconcepcionsaez
 *
 */
public class ActionSQLConnect implements ActionListener {
	
	private JTextField[] imputs;
	private Connector c;
	private JFrame pre;
	
	public ActionSQLConnect(JTextField[] vText,Connector cc,JFrame pre) {
		imputs=vText;
		c=cc;
		this.pre=pre;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	    c=new Connector(imputs[0].getText(),imputs[1].getText(),imputs[2].getText(),imputs[3].getText());
	    
	    //Si ha habido un error con la conexion, no cambiamos la pantalla
	    if (c.getConnection()!=null){
		    pre.dispose();
		    VentanaPrincipal vv= new VentanaPrincipal(c);
			vv.setVisible(true);
		}
	}

}
