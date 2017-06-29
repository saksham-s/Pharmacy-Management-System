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

public class AddPatient {
    JTextField Name=new JTextField(10);
    JTextField Address = new JTextField(15);
    JTextField Contact = new JTextField(10);
    JTextField Email = new JTextField(10);
    JTextField output=new JTextField("Output",10);

    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton reset=new JButton("Reset");
    JButton search=new JButton("Submit");
    JPanel centre;

    public void AddPatientgui(JPanel westbottom,JPanel centr){
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
        p4.add(new JLabel("Address"));
        p4.add(Address);
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p4.add(new JLabel("Email"));
        p4.add(Email);
        westbottom.add(p2);
        westbottom.add(p3);
        westbottom.add(p4);
        westbottom.add(p5);
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
        Address.setText("");
        Email.setText("");
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
            resultSet = statement.executeQuery("SELECT * FROM Patient ORDER BY Patient_ID DESC LIMIT 1");
            if(!resultSet.isBeforeFirst()){
                sale_id = 1;
            }else{
                resultSet.next();
                sale_id=Integer.parseInt(resultSet.getString("Patient_ID").substring(4))+1;
            }
            if(Contact.getText().length()<10){
            	Exception e = new Exception();
                throw e;
            }
            Float.parseFloat(Contact.getText());
            preparedStatement = connect.prepareStatement("INSERT INTO Patient Values(?,?,?,?,?)");
            preparedStatement.setString(1, "Cust"+sale_id.toString());
            preparedStatement.setString(2, Name.getText());
            preparedStatement.setString(3, Address.getText());
            preparedStatement.setString(4, Contact.getText());
            preparedStatement.setString(5, Email.getText());
            preparedStatement.executeUpdate();
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

        //patient to be updated

    }

    class reset implements ActionListener{
        public void actionPerformed(ActionEvent e){
            AddPatient.this.reset();
        }
    }

    class submit implements ActionListener{
        public void actionPerformed(ActionEvent e){
            AddPatient.this.runQuery();
        }
    }
}