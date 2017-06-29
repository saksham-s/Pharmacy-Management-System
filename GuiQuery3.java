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
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Created by Saksham on 11/29/2016.
 */

public class GuiQuery3 {
    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    DefaultTableModel model;
    JTable table;
    JPanel centre;

    public void query3gui(JPanel westbottom,JPanel centr){
        westbottom.removeAll();
        this.centre=centr;
        centre.removeAll();
        centre.repaint();
        centre.revalidate();
        westbottom.revalidate();
        westbottom.repaint();
        model = new DefaultTableModel();
        model.addColumn("DrugId");
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Price");
        model.addColumn("Location");
        model.addColumn("Expiry");
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        try{	
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/dbms?"+"user=root&password=pass");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM Drug1 NATURAL JOIN Drug2");
			
			while(resultSet.next()){
	        	String s = resultSet.getString("Expiry");
	        	int exmonth = Integer.parseInt(s.split("/",2)[0]);
	        	int exyear = Integer.parseInt(s.split("/",2)[1])+2000;
	        	int month = Calendar.getInstance().get(Calendar.MONTH);
	        	int year = Calendar.getInstance().get(Calendar.YEAR);
	        	if(((year==exyear) && (exmonth - month)<=6) && (exmonth>month) || ((year==exyear-1) && (exmonth-month<=-6))){
	        		model.addRow(new Object[]{resultSet.getString("Drug_ID"),resultSet.getString("Name"),resultSet.getString("Stock"),resultSet.getString("MRP"),resultSet.getString("Location"),resultSet.getString("Expiry")});
	        		centre.repaint();
	        		centre.revalidate();
	        	}
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
