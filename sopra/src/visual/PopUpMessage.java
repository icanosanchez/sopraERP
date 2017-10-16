package visual;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import logic.listener.change.OptionChoice;

/**
 * Clase que lanza un pop-up con una pregunta y recoge una opcion del usuario
 * 
 * @author daconcep
 *
 */
public class PopUpMessage extends JDialog {
	
	private JOptionPane optionPane;

	/**
	 * @param title Titulo de la ventana
	 * @param mes Mensaje del pop-up
	 * @param opt Tipo de posibles opciones. Utilizar las opciones constantes en la clase JOptionPane.
	 * @param type Tipo de posibles mensajes. Utilizar las opciones constantes en la clase JOptionPane.
	 */
	public PopUpMessage(String title,String mes,int type ,int opt){
		super(new JFrame(),title,true);
		
		optionPane = new JOptionPane(mes,type,opt);
		
		setContentPane(optionPane);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		optionPane.addPropertyChangeListener( new OptionChoice(this));
		pack();
		setVisible(true);
		setResizable(false);
	}
	


	/**
	 * @param title Titulo de la ventana
	 * @param mes Mensaje del pop-up
	 * @param type Tipo de posibles mensajes. Utilizar las opciones constantes en la clase JOptionPane.
	 */
	public PopUpMessage(String title,String mes,int type){
		super(new JFrame(),title,true);
		
		optionPane = new JOptionPane(mes,type);
		
		setContentPane(optionPane);
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		optionPane.addPropertyChangeListener( new OptionChoice(this));
		pack();
		setVisible(true);
		setResizable(false);
	}
	
	public Object getValue(){
		return optionPane.getValue();
	}

	public JOptionPane getJOption() {
		// TODO Auto-generated method stub
		return optionPane;
	}

}
