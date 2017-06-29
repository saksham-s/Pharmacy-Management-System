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

public class GuiQuery10 {
    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JPanel centre;

    public void query10gui(JPanel westbottom,JPanel centr){
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
        model.addColumn("Drug Id");
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //DefaultTableModel mod=(DefaultTableModel)table.getModel();
        try{	
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/dbms?"+"user=root&password=pass");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT distinct Drug_ID FROM Sale_Transaction natural join Sells natural join (SELECT Drug_ID,Date As 'PuDate' FROM Sale_Transaction natural join Sells) AS temp where datediff(Date,'2010-04-12')>200");
	        while(resultSet.next()){	        	
	        	model.addRow(new Object[]{resultSet.getString("Drug_ID")});
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