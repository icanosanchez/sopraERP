package logic.listener.action;

import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dbmanager.Connector;
import dbmanager.SQLSentence;
import logic.data.ModelTable;
import visual.JFrameConn;

/**
 * Clase abstracta de la que heredan todos las clases que modifican el panel principal
 * de la ventana principal
 * 
 * @author ddelaconcepcionsaez
 *
 */
public abstract class ActionCreatePanel implements ActionListener {

	private JPanel pan;
	private JFrameConn fra;
	private String sql;
	private Vector<String> vs;
	
	public int getColumnCount(){
		return vs.size();
	}
	
	public String getSQL(){
		return sql;
	}
	
	public String getColumnName(int i){
		return vs.get(i);
	}
	
	public ActionCreatePanel(JPanel main,JFrameConn f, String sqql) {
		pan=main;
		fra=f;
		sql=sqql;
		vs=new Vector<String>();
	}
	
	/**
	 * @param sql comando SQL de tipo select
	 * @return un ModelTable con los datos de la tabla
	 * @throws SQLException
	 */
	protected ModelTable getTable(){
		
		ModelTable tt=null;
		
		try(Connector c=getFrame().getConnector()){
			c.connect();
			int i=1;
			
			// Obtenemos los nombres de las columnas de nuestra tabla
			
			try(SQLSentence s=new SQLSentence(c,sql)){
				ResultSetMetaData rsmd=s.execSentence().getMetaData();
				
				int rsd=rsmd.getColumnCount();
				
				while(rsd>=i){
					vs.add(rsmd.getColumnName(i));
					i++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(new JFrame(), "There is an error with the connection:\n"+e.getMessage());
			}
	
			String[] vvst=vs.toArray(new String[1]);
			tt=new ModelTable(vvst);
			
			// Obtenemos los datos de la tabla y los introducimos en un ModelTable
			
			try(SQLSentence ss=new SQLSentence(c,sql)){
				try(ResultSet rs= ss.execSentence()){
					while (rs.next()) {
						String[] vst=new String[vvst.length];
					    i=0;
					    while (i<vst.length){
					    	vst[i]=rs.getString(vvst[i]);
					    	i++;
					    }
					    tt.add(vst);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(new JFrame(), "There is an error with the connection:\n"+e.getMessage());
				}
			}
		}
		
		//Devolvemos nuestro ModelTable
		
		return tt;
	}
	
	public JPanel getMainPanel(){
		return pan;
	}
	
	public JFrameConn getFrame(){
		return fra;
	}

}
