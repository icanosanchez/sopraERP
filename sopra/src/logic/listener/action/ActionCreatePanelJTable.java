package logic.listener.action;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import logic.listener.mouse.MouseJTable;
import visual.JFrameConn;

/**
 * Clase que crea un panel con un JTable de una tabla de la BD y JTextFields encima que sirven para anadir
 * valores a la tabla, modificar los ya existentes o borrar fila a fila.
 * 
 * Se trata de un ActionCreatePanel con un SQL de la forma 'select * from ' + nombreTabla, que muestra
 * una pantalla un JTable en el centro, una fila de JTextFields en la parte superior y los botones
 * de modificacion en la parte inferior.
 * 
 * @author ddelaconcepcionsaez
 *
 */
public class ActionCreatePanelJTable extends ActionCreatePanel {
	
	public ActionCreatePanelJTable(JPanel main, JFrameConn f,String nomTab) {
		super(main,f,"select * from "+nomTab);
		// TODO Auto-generated constructor stub
	}
	
	public String getTableName(){
		return getSQL().substring(14);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		//Creamos la interfaz grafica
		
		JPanel main=new JPanel(new BorderLayout());
		try{
			getMainPanel().removeAll();
			
			//Tomamos los datos de la tabla empresa
			
			JTable t=new JTable(getTable());

			JButton save=new JButton("Save");
			JButton refresh=new JButton("Refresh");
			JButton delete=new JButton("Delete");
			JTextField[] vtex=new JTextField[this.getColumnCount()];
			int i=0;
			while (i<vtex.length){
				vtex[i]=new JTextField(15);
				i++;
			}
			
			JPanel text=new JPanel(new GridLayout(1,getColumnCount()));
			i=0;
			while (i<vtex.length){
				text.add(vtex[i]);
				i++;
			}
			
			main.add(text,"North");
			JPanel but=new JPanel(new GridLayout(1,3,50,50));
			but.add(save);
			but.add(delete);
			but.add(refresh);
			main.add(but,"South");
			
			main.add(new JScrollPane(t),"Center");
			
			//Anadimos los listeners
			
			t.addMouseListener(new MouseJTable(t, vtex));
			
			save.addActionListener(new ActionSQLSentence(getFrame().getConnector(), getTableName(), vtex));
			refresh.addActionListener(new ActionCreatePanelJTable(this.getMainPanel(), this.getFrame(), "empresa"));
			delete.addActionListener(new ActionSQLSentenceDrop(getFrame().getConnector(),getTableName(), vtex));

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(new JFrame(), "There is an error with the connection:\n"+e1.getMessage());
		}			
		getMainPanel().add(main,"South");
		
		getFrame().pack();
	}

}
