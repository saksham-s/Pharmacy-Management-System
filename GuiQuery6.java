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

public class GuiQuery6 {
    JButton find=new JButton("Create Report");
    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton reset=new JButton("Reset");
    DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JPanel centre;

    public void query6gui(JPanel westbottom,JPanel centr){
        westbottom.removeAll();
        this.centre=centr;
        centre.removeAll();
        westbottom.setLayout(new BoxLayout(westbottom, BoxLayout.Y_AXIS));
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        find.addActionListener(new find());
        p5.add(find);
        reset.addActionListener(new reset());
        base.add(reset);
        westbottom.add(base);
        centre.removeAll();
        centre.repaint();
        centre.revalidate();
        westbottom.revalidate();
        westbottom.repaint();
        model.addColumn("Employee Id");
        model.addColumn("Employee Name");
        model.addColumn("Salary");
        model.addColumn("Total Sales for this Month");
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        centre.add(p5, BorderLayout.NORTH);
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        centre.revalidate();
    }
    public void reset(){
        centre.removeAll();
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(find);
        model = new DefaultTableModel();
        model.addColumn("Emloyee Id");
        model.addColumn("Employee Name");
        model.addColumn("Salary");
        model.addColumn("Total Sales for this Month");
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        centre.add(p5,BorderLayout.NORTH);
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        centre.repaint();
        centre.revalidate();
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
			resultSet = statement.executeQuery("SELECT * From Employee0 Natural Join Employee1 Natural JOIN(SELECT SUM(temp1.Profit) AS 'total',temp1.Employee_ID FROM (select Sale_ID,Employee_ID,Quantity*MRP*(1-Discount/100)-Cost_Price AS 'Profit' from Sale_Transaction natural join Sells natural join Drug1 where YEAR(curdate()) = YEAR(Date) and MONTH(curdate()) = MONTH(Date)) AS temp1 Group by temp1.Employee_ID) AS temp2");
	        while(resultSet.next()){	        	
	        	model.addRow(new Object[]{resultSet.getString("Employee_ID"),resultSet.getString("Name"),resultSet.getString("Salary"),resultSet.getString("total")});
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
    class reset implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GuiQuery6.this.reset();
        }
    }
    class find implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GuiQuery6.this.searchemployee();
        }
    }
}