package dbmanager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SQLSentence implements AutoCloseable {
	
	private String sent;
	private Connector con;
	
	public SQLSentence(Connector c, String st){
		con=c;
		sent=st;
	}
	
	/**
	 * Ejecuta sentencia sent, en caso de que sea tipo select
	 * @return
	 * @throws SQLException 
	 */
	public ResultSet execSentence() throws SQLException{
		
		if(!sent.startsWith("select")){
			throw new SQLException("You're trying to execute a non-select statement with the wrong method.");
		}
		
		con.connect();
		ResultSet re=null;
		
		Statement se;
		try {
			se = con.getConnection().createStatement();
			re= se.executeQuery(sent);
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(new JFrame(), "There is an error with the connection:\n"+e.getMessage());
			
		}
		return re;
	}
	
	public void execUpdate() throws SQLException{
		
		if(sent.startsWith("select")){
			throw new SQLException("You're trying to execute a select statement with the wrong method.");
		}
		
		con.connect();
		
		try {
			Statement se = con.getConnection().createStatement();
	        con.getConnection().setAutoCommit(false);
			se.executeUpdate(sent);
			con.getConnection().commit();
		} catch (SQLException e ) {
	        e.printStackTrace();
	    }
		finally {
	        if (con.getConnection() != null) {
                try {
					con.getConnection().rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(new JFrame(), "There is an error with the connection:\n"+e1.getMessage());
				}
	        }
	    } 
		try {
			con.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(new JFrame(), "There is an error with the connection:\n"+e.getMessage());
		}
		
		close();
	}
	
	public void close(){
		con.close();
	}
	
}
