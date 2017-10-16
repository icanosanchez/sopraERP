package logic.listener.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import dbmanager.Connector;
import dbmanager.SQLSentence;
import visual.PopUpMessage;

/**
 * @author daconcep
 *
 *AccionListener para ejecutar sentencias sql delete desde campos de texto
 *a una base de datos.
 */
public class ActionSQLSentenceDrop implements ActionListener {

	private JTextField[] dats;
	private Vector<String> nomFi;
	private String tab;
	private Set<String> pk;
	private Set<String> strField;
	private Connector c;

	/**
	 * @param con conexion
	 * @param nomTab nombre de la tabla
	 * @param campos datos en campos en orden
	 * @param nomFields nombre de los campos en orden
	 * @param primKeys vector de los indices de los campos de las primary keys
	 * @param str vector de los indices de los campos que deben ser tratados como cadenas de caracteres
	 * @throws SQLException 
	 */
	public ActionSQLSentenceDrop(Connector con,String nomTab,JTextField[] campos) throws SQLException{
		dats=campos;
		con.connect();
		c=con;
		
		ResultSet rs=con.getConnection().getMetaData().getPrimaryKeys(null, null, nomTab);
		
		pk=new HashSet<String>();
		while(rs.next()){
			pk.add(rs.getString("COLUMN_NAME"));
		}
		rs.close();
		
		ResultSetMetaData rsmd=con.getConnection().createStatement().executeQuery("select * from "+nomTab).getMetaData();
		
		nomFi=new Vector<String>();
		int i=rsmd.getColumnCount();
		while(i>0){
			nomFi.add(rsmd.getColumnLabel(i));
			i--;
		}
		
		strField=new HashSet<String>();
		i=rsmd.getColumnCount();
		while(i>0){
			if (rsmd.getColumnType(i) == java.sql.Types.VARCHAR){
				strField.add(rsmd.getColumnName(i));
			}
			i--;
		}
		
		tab=nomTab;
		
		con.close();
	}
	
	public String getText(int i) {
		return dats[i].getText();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		String st="where ";
		String st2="";
		SQLSentence sql;
		boolean b=true;
		int i;
		
		Iterator<String> it=pk.iterator();
		while (it.hasNext()) {
			String ss=it.next();
			i=nomFi.indexOf(ss);
			if (!dats[i].getText().isEmpty()) {
				if (strField.contains(ss)){
					st+=ss+" = '"+dats[dats.length-1-i].getText()+"' ";
				}
				else{
					st+=ss+" = "+dats[dats.length-1-i].getText()+" ";
				}
			}
			i--;
		}
		
		if(st.equals("where ")) {
			b=false;
		}
		
		if(b) {
			i=dats.length;
			st="where ";
			while (i>0) {
				if (strField.contains(nomFi.get(i-1))){
					st+=nomFi.get(i-1)+" = '"+dats[dats.length -i].getText()+"' AND ";
				}
				else{
					st+=nomFi.get(i-1)+" = "+dats[dats.length -i].getText()+" AND ";
				}
				i--;
			}
			st=st.substring(0,st.length()-5);
			String fin="select * from "+tab+" "+st;
			sql=new SQLSentence(c,fin);
			try {
				if(b && sql.execSentence().next()) {
					st2="delete from "+tab+" where ";
					i=dats.length;
					while (i>0) {
						if (strField.contains(nomFi.get(i-1))){
							st2+=nomFi.get(i-1)+" = '"+dats[dats.length -i].getText()+"' AND ";
						}
						else{
							st2+=nomFi.get(i-1)+" = "+dats[dats.length -i].getText()+" AND ";
						}
						
						i--;
					}
					st2=st2.substring(0,st2.length()-5);
					sql.close();
					sql=new SQLSentence(c,st2);
					String stt;
					stt=" delete ";
					PopUpMessage d=new PopUpMessage("Choose an option",
						    "This decision is going to"+stt +"data\n"
								    + "in your database.\n"
								    + "Are you sure that you want to continue?",JOptionPane.QUESTION_MESSAGE,JOptionPane.YES_NO_OPTION);
					if(d.getValue().equals(JOptionPane.YES_OPTION)){
						sql.execUpdate();
					}
				}
				else {
					JOptionPane.showMessageDialog(new JFrame(), "That row does not exist in the database.");
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				JOptionPane.showMessageDialog(new JFrame(), "There was a connection problem: "+e1.getMessage());
			}
			sql.close();
		}
	}

}
