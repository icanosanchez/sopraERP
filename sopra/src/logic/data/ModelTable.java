package logic.data;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * Clase que implementa la interfaz TableModel con todas las entradas
 * como String y de tamano de filas aleatorio a lo largo de su vida en tiempo
 * de ejecucion.
 * 
 * @author ddelaconcepcionsaez
 *
 */
public class ModelTable extends AbstractTableModel {
	
	private String[] cols;
	private Vector<Vector<String>> data;

	/**
	 * @param colss es un array de cadenas con el nombre de las columnas.
	 */
	public ModelTable(String[] colss) {
		// TODO Auto-generated constructor stub
		data=new Vector<Vector<String>>();
		cols=colss;
	}

	@Override
	public String toString(){
		String st="";
		int i=0;
		while (i<data.size()){
			int j=0;
			st+="(";
			while (j<cols.length) {
				st+=data.get(i).get(j)+" , ";
				j++;
			}
			st=st.substring(0,st.length()-3);
			st+="\n";
			i++;
		}
		return st;
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return cols.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return data.get(rowIndex).get(columnIndex);
	}
	
	/**
	 * Metodo para introducir una fila en la tabla.
	 * 
	 * @param dats es el array de cadenas que introducimos como nueva fila.
	 */
	public void add(String [] dats) {
		Vector<String> v=new Vector<String>();
		int i=0;
		while (i<dats.length) {
			v.add(dats[i]);
			i++;
		}
		data.add(v);
		this.fireTableDataChanged();
	}
	
	public void clear(){
		data=new Vector<Vector<String>>();
	}
	
	public void copyInHere(ModelTable t){
		clear();
		for(int j=0;j<t.getRowCount();j++){
			for(int i=0;i<t.getColumnCount();i++){
				setValueAt(t.getValueAt(j, i),j,i);
			}
		}
		this.fireTableDataChanged();
	}

	@Override
	public String getColumnName(int i) {
		// TODO Auto-generated method stub
		return cols[i];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Metodo para modificar una entrada concreta de la tabla. Solo es posible 
	 * modificar un valor ya existente o colocar un valor en una nueva fila. La
	 * nueva fila se crea con cada entrada la cadena vacia.
	 * 
	 * @param aValue es un String que representa el valor que se desea modificar.
	 * @param rowIndex es el numero de la fila que se desea modificar
	 * @param columnIndex es el numero de la columna que se desea modificar
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		if(data.size()==rowIndex){
			data.add(new Vector<String>());
			for(int i=0;i<this.getColumnCount();i++){
				data.get(data.size()-1).add("");
			}
		}
		data.get(rowIndex).set(columnIndex,(String) aValue);
		this.fireTableDataChanged();
	}

}
