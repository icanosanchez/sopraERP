package logic;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import dbmanager.Connector;
import visual.VentanaConnect;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connector c=null;
		
		VentanaConnect v=new VentanaConnect(c);

		v.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				System.exit(0);
			}
		});
		v.setVisible(true);
	}

}
