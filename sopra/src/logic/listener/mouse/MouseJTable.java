package logic.listener.mouse;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Mouse Listener para pintar la fila clicada de una tabla en un vector de campos de texto.
 * @author ddelaconcepcionsaez
 *
 */
public class MouseJTable implements MouseListener {
	
	private JTable t;
	private JTextField[] vtext;

	public MouseJTable(JTable tab,JTextField[] text) {
		t=tab;
		vtext=text;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
        int row = t.rowAtPoint(e.getPoint());
        if (row >= 0){
        	int i=vtext.length;
        	while (i>0) {
	            vtext[i-1].setText((String) t.getValueAt(row, i-1));
	            i--;
        	}
        }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
