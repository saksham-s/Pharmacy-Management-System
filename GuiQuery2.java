package dbms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.sql.*;
import java.util.*;
/**
 * Created by Saksham on 11/29/2016.
 */

public class GuiQuery2 {
    JTextField SearchMed=new JTextField(10);
    JButton find=new JButton("Find");
    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton reset=new JButton("Reset");
    DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JPanel centre;

    public void query2gui(JPanel westbottom,JPanel centr){
        westbottom.removeAll();
        this.centre=centr;
        centre.removeAll();
        westbottom.setLayout(new BoxLayout(westbottom, BoxLayout.Y_AXIS));
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(new JLabel("Give Med Name"));
        p5.add(SearchMed);
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
        model.addColumn("DrugId");
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Price");
        model.addColumn("Location");
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        centre.add(p5, BorderLayout.NORTH);
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        centre.revalidate();
    }
    //seteditable
    public void reset(){
        centre.removeAll();
        SearchMed.setText("");
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(new JLabel("Give Med Name"));
        p5.add(SearchMed);
        p5.add(find);
        model = new DefaultTableModel();
        model.addColumn("DrugId");
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Price");
        model.addColumn("Location");
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        centre.add(p5,BorderLayout.NORTH);
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        centre.repaint();
        centre.revalidate();
    }

    public void searchmed(){
        String name=SearchMed.getText();
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //DefaultTableModel mod=(DefaultTableModel)table.getModel();
        try{	
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/dbms?"+"user=root&password=pass");
			statement = connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM Drug1 NATURAL JOIN Drug2 Where Drug1.Name LIKE \"%"+name+"%\"");
	        while(resultSet.next()){
	        	
	        	model.addRow(new Object[]{resultSet.getString("Drug_ID"),resultSet.getString("Name"),resultSet.getString("Stock"),resultSet.getString("MRP"),resultSet.getString("Location")});
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
        
        //search medicines with name like this
        //2d array of results
        //add data to table
        centre.repaint();
        centre.revalidate();

    }
    class reset implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GuiQuery2.this.reset();
        }
    }
    class find implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GuiQuery2.this.searchmed();
        }
    }
}
