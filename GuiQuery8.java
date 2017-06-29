package dbms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GuiQuery8 {
    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JPanel centre;

    public void query8gui(JPanel westbottom,JPanel centr){
        westbottom.removeAll();
        this.centre=centr;
        centre.removeAll();
        westbottom.setLayout(new BoxLayout(westbottom, BoxLayout.Y_AXIS));
        westbottom.add(base);
        centre.removeAll();
        centre.repaint();
        centre.revalidate();
        westbottom.revalidate();
        westbottom.repaint();
        model.addColumn("Drug_ID");
        model.addColumn("Quantity");
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        centre.revalidate();
        this.searchemployee();
    }

    public void searchemployee(){
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //DefaultTableModel mod=(DefaultTableModel)table.getModel();
        try{	
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/dbms?"+"user=root&password=pass");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT Drug_ID,(SELECT AVG(Stock) FROM Drug1)+5-Stock AS 'stocko' FROM Drug1 WHERE Stock<(SELECT AVG(Stock) FROM Drug1)+5");
	        while(resultSet.next()){	        	
	        	model.addRow(new Object[]{resultSet.getString("Drug_ID"),(int)Float.parseFloat(resultSet.getString("stocko"))});
	        }
			if (resultSet != null) {
                resultSet.close();
			}
	        if (statement != null) {
	                statement.close();
	        }
	
	        if (connect != null) {
	                connect.close();
	        }
        }catch(Exception e){
        	System.out.println(e);
        }	
    
        centre.repaint();
        centre.revalidate();

    }
}
