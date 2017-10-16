package visual;

import javax.swing.JFrame;

import dbmanager.Connector;

/**
 * Un JFrame con conexion a base de datos.
 * 
 * @author ddelaconcepcionsaez
 *
 */
public abstract class JFrameConn extends JFrame {

	private Connector c;
	
	public JFrameConn(String st, Connector cc) {
		super(st);
		c=cc;
	}
	
	public Connector getConnector() {
		return c;
	}

}
