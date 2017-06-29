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

public class GuiQuery5 {
    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JPanel centre;

    public void query5gui(JPanel westbottom,JPanel centr){
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
        model.addColumn("Sale ID");
        model.addColumn("Profit");
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
			resultSet = statement.executeQuery("SELECT SUM(temp1.Profit),temp1.Sale_ID FROM (select Sale_ID,Employee_ID,Quantity*MRP*(1-Discount/100)-Cost_Price AS 'Profit' from Sale_Transaction natural join Sells natural join Drug1 ) AS temp1 Group by temp1.Sale_ID;");
	        while(resultSet.next()){	        	
	        	model.addRow(new Object[]{resultSet.getString("Sale_ID"),resultSet.getString("SUM(temp1.Profit)")});
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
