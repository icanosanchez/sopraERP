package visual;

import java.awt.BorderLayout;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import dbmanager.Connector;
import logic.listener.action.ActionCreatePanelJTable;
import logic.listener.action.ActionExit;

public class VentanaPrincipal extends JFrameConn {

	private JPanel pan=new JPanel(new BorderLayout());
	
	public VentanaPrincipal(Connector cc) {
		super("SopraERP",cc);

		JPanel main=new JPanel();
		
		// Creamos la barra menu

		JMenu tprin=new JMenu("T. Principales");
		JMenu docs=new JMenu("Documentos");
		JMenu clis=new JMenu("Clientes");
		JMenu provs=new JMenu("Proveedores");
		JMenuBar bar=new JMenuBar();
		
		docs.add(clis);
		docs.add(provs);
		bar.add(tprin);
		bar.add(docs);
		
		JMenuItem empp=new JMenuItem("Empresa");
		JMenuItem clisim=new JMenuItem("Clientes");
		JMenuItem provsim=new JMenuItem("Proveedores");
		
		tprin.add(empp);
		tprin.addSeparator();
		tprin.add(clisim);
		tprin.add(provsim);
		tprin.addSeparator();
		
		JMenuItem mit=new JMenuItem("Salir");
		tprin.add(mit);
		
		// ActionListeners del menu

		empp.addActionListener(new ActionCreatePanelJTable(main,this, "empresa"));
		mit.addActionListener(new ActionExit());
		
		pan.add(main,"South");
		
		getContentPane().add(pan);
		
		pan.add(bar,"North");
		pack();
		setResizable(false);
	}

}
