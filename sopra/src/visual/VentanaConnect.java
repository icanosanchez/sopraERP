package visual;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import dbmanager.Connector;
import logic.listener.action.ActionExit;
import logic.listener.action.ActionSQLConnect;

public class VentanaConnect extends JFrameConn {

	private JTextField[] imputs =new JTextField[4];
	private JLabel[] names =new JLabel[4];
	private JButton accept=new JButton("Connect");
	private JButton cancel =new JButton("Cancel");
	
	
	public VentanaConnect(Connector c) {
		super("Connect",c);
		
		JPanel pan=new JPanel(new BorderLayout());

		getContentPane().add(pan);

		pan.setVisible(true);
		
		JPanel form=new JPanel(new GridLayout(5,1,15,15));
		JPanel left=new JPanel(new BorderLayout());
		JPanel left1=new JPanel(new BorderLayout());
		JPanel right=new JPanel(new BorderLayout());
		JPanel right1=new JPanel(new BorderLayout());
		JPanel right2=new JPanel(new BorderLayout());

		imputs[0] = new JTextField(20);       //url
		imputs[1] = new JTextField(20);       //usuario
		imputs[2] = new JPasswordField(20);   //contrasena
		imputs[3] = new JTextField(20);       //base
		
		names[0]=new JLabel("URL");
		names[1]=new JLabel("Usuario");
		names[2]=new JLabel("Contrasena");
		names[3]=new JLabel("Base de datos");
		
		left.add(names[0],"North");
		left.add(imputs[0],"South");
		form.add(left);
		right.add(names[1],"North");
		right.add(imputs[1],"South");
		right1.add(names[2],"North");
		right1.add(imputs[2],"South");
		right2.add(names[3],"North");
		right2.add(imputs[3],"South");
		form.add(right);
		left1.add(accept,"West");
		left1.add(cancel,"East");
		form.add(right1);
		form.add(right2);
		form.add(left1);
		
		//Anadimos los actoin listeners de los botones
		
		cancel.addActionListener(new ActionExit());
		
		accept.addActionListener(new ActionSQLConnect(imputs,c,this));
		
		pan.add(form,"Center");
		pack();
		setResizable(false);
		
	}

}
