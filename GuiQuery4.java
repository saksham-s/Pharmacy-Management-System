package dbms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class GuiQuery4 {
    JTextField SearchPerson=new JTextField(10);
    JButton find=new JButton("Find");
    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton reset=new JButton("Reset");
    DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JPanel centre;
    public void query4gui(JPanel westbottom,JPanel centr){
        westbottom.removeAll();
        this.centre=centr;
        centre.removeAll();
        westbottom.setLayout(new BoxLayout(westbottom, BoxLayout.Y_AXIS));
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(new JLabel("Give Customer Name"));
        p5.add(SearchPerson);
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
        model.addColumn("Drug Name");
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Date");
        model.addColumn("Employee Name");
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        centre.add(p5, BorderLayout.NORTH);
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        centre.revalidate();
    }
    public void reset(){
        centre.removeAll();
        SearchPerson.setText("");
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(new JLabel("Give Customer Name"));
        p5.add(SearchPerson);
        p5.add(find);
        model = new DefaultTableModel();
        model.addColumn("DrugId");
        model.addColumn("Drug Name");
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Date");
        model.addColumn("Employee Name");
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        centre.add(p5,BorderLayout.NORTH);
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        centre.repaint();
        centre.revalidate();
    }

    public void history(){
    	String Id=SearchPerson.getText();
    	this.reset();
        //using Select * FROM Customer Natural Join SELLS Natural Join Sale Transaction
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
//        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/dbms?"+"user=root&password=pass");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Patient NATURAL JOIN Sells NATURAL JOIN Sale_Transaction,Drug1,Employee1 WHERE Drug1.Drug_ID=Sells.Drug_ID AND Employee1.Employee_ID=Sale_Transaction.Employee_ID AND Patient_Id='"+Id+"'");

            while(resultSet.next()){
            		model.addRow(new Object[]{resultSet.getString("Drug1.Drug_ID"),resultSet.getString("Drug1.Name"),resultSet.getString("Patient.Name"),resultSet.getString("Sells.Quantity"),resultSet.getString("Sale_Transaction.Date"),resultSet.getString("Employee1.Name")});
                    centre.repaint();
                    centre.revalidate();
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
            GuiQuery4.this.reset();
        }
    }
    class find implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GuiQuery4.this.history();
        }
    }
}
