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

import javax.swing.table.DefaultTableModel;

/**
 * Created by Saksham on 11/29/2016.
 */

public class AddEmployee {
    JTextField Name=new JTextField(10);
    JTextField Contact = new JTextField(15);
    JTextField Email = new JTextField(15);
    JTextField Position = new JTextField(15);
    JTextField Salary = new JTextField(15);
    JTextField output=new JTextField("Output",10);

    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton reset=new JButton("Reset");
    JButton search=new JButton("Submit");
    private DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JPanel centre;

    public void AddEmployeegui(JPanel westbottom,JPanel centr){
        westbottom.removeAll();
        this.centre=centr;
        centre.removeAll();
        westbottom.setLayout(new BoxLayout(westbottom, BoxLayout.Y_AXIS));
        JPanel p2=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Name"));
        p2.add(Name);
        JPanel p3=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Contact"));
        p3.add(Contact);
        JPanel p4=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p4.add(new JLabel("Email"));
        p4.add(Email);
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(new JLabel("Position"));
        p5.add(Position);
        JPanel p6=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p6.add(new JLabel("Salary"));
        p6.add(Salary);
        westbottom.add(p2);
        westbottom.add(p3);
        westbottom.add(p4);
        westbottom.add(p5);
        westbottom.add(p6);
        search.addActionListener(new submit());
        reset.addActionListener(new reset());
        base.add(search);
        base.add(reset);
        westbottom.add(base);
        centre.removeAll();
        centre.repaint();
        centre.revalidate();
        westbottom.revalidate();
        westbottom.repaint();
        centre.setLayout(new GridBagLayout());
        centre.add(output);
        centre.revalidate();
    }
    //seteditable
    public void reset(){
        centre.removeAll();
        Name.setText("");
        Contact.setText("");
        Email.setText("");
        Position.setText("");
        Salary.setText("");
        output.setText("Output");
    }
    public void runQuery(){

    	Integer sale_id;
        Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection("jdbc:mysql://localhost/dbms?"+"user=root&password=pass");
            statement = connect.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Employee1 ORDER BY Employee_ID DESC LIMIT 1");
            if(!resultSet.isBeforeFirst()){
                sale_id = 1;
            }else{
                resultSet.next();
                sale_id=Integer.parseInt(resultSet.getString("Employee_ID").substring(3))+1;
            }
            if(Contact.getText().length()<10){
            	Exception e = new Exception();
                throw e;
            }
            Float.parseFloat(Contact.getText());
            preparedStatement = connect.prepareStatement("INSERT INTO Employee1 Values(?,?,?,?,?)");
            preparedStatement.setString(1, "Emp"+sale_id.toString());
            preparedStatement.setString(2, Name.getText());
            preparedStatement.setString(3, Contact.getText());
            preparedStatement.setString(4, Email.getText());
            preparedStatement.setString(5, Position.getText());
            preparedStatement.executeUpdate();
            resultSet = statement.executeQuery("SELECT * FROM Employee0 WHERE Position='"+Position.getText()+"'");
            if(!resultSet.isBeforeFirst()){
                preparedStatement = connect.prepareStatement("INSERT INTO Employee0 Values(?,?)");
                preparedStatement.setInt(1,Integer.parseInt(Salary.getText()));
                preparedStatement.setString(2,Position.getText());
                preparedStatement.executeUpdate();
            }
            output.setText("Success");
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
        	output.setText("Failure");
            System.out.println(e);
        }


    }

    class reset implements ActionListener{
        public void actionPerformed(ActionEvent e){
            AddEmployee.this.reset();
        }
    }

    class submit implements ActionListener{
        public void actionPerformed(ActionEvent e){
            AddEmployee.this.runQuery();
        }
    }
}