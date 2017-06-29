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

public class AddDrug {
    JTextField DrugName=new JTextField(10);
    JTextField CostPrice = new JTextField(15);
    JTextField Stock = new JTextField(15);
    JTextField Discount = new JTextField(15);
    JTextField MRP = new JTextField(15);
    JTextField Company_Id = new JTextField(15);
    JTextField Expiry = new JTextField(15);
    JTextField Type = new JTextField(15);
    JTextField Location = new JTextField(15);
    JTextField output=new JTextField("Output",10);

    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    JButton reset=new JButton("Reset");
    JButton search=new JButton("Submit");
    private DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JPanel centre;

    public void AddDruggui(JPanel westbottom,JPanel centr){
        westbottom.removeAll();
        this.centre=centr;
        centre.removeAll();
        westbottom.setLayout(new BoxLayout(westbottom, BoxLayout.Y_AXIS));
 
        JPanel p2=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Drug Name"));
        p2.add(DrugName);
        JPanel p3=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Cost Price"));
        p3.add(CostPrice);
        JPanel p4=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p4.add(new JLabel("Stock"));
        p4.add(Stock);
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(new JLabel("Discount"));
        p5.add(Discount);
        JPanel p6=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p6.add(new JLabel("MRP"));
        p6.add(MRP);
        JPanel p7=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p7.add(new JLabel("Company Id"));
        p7.add(Company_Id);
        JPanel p8=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p8.add(new JLabel("Expiry"));
        p8.add(Expiry);
        JPanel p9=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p9.add(new JLabel("Type"));
        p9.add(Type);
        JPanel p10=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p10.add(new JLabel("Location"));
        p10.add(Location);
        westbottom.add(p2);
        westbottom.add(p3);
        westbottom.add(p4);
        westbottom.add(p5);
        westbottom.add(p6);
        westbottom.add(p7);
        westbottom.add(p8);
        westbottom.add(p9);
        westbottom.add(p10);
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
        DrugName.setText("");
        Stock.setText("");
        Expiry.setText("");
        Company_Id.setText("");
        CostPrice.setText("");
        MRP.setText("");
        Discount.setText("");
        Location.setText("");
        Type.setText("");
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
            resultSet = statement.executeQuery("SELECT * FROM Drug1 ORDER BY Drug_ID DESC LIMIT 1");
            if(!resultSet.isBeforeFirst()){
                sale_id = 1;
            }else{
                resultSet.next();
                sale_id=Integer.parseInt(resultSet.getString("Drug_ID").substring(4))+1;
            }
            preparedStatement = connect.prepareStatement("INSERT INTO Drug1 Values(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(4, "Drug"+sale_id.toString());
            preparedStatement.setString(1, Type.getText());
            preparedStatement.setInt(3, Integer.parseInt(Stock.getText()));
            preparedStatement.setString(5, DrugName.getText());
            preparedStatement.setInt(2, Integer.parseInt(CostPrice.getText()));
            preparedStatement.setInt(6, Integer.parseInt(Discount.getText()));
            preparedStatement.setInt(7, Integer.parseInt(MRP.getText()));
            preparedStatement.setString(8, Company_Id.getText());
            preparedStatement.setString(9, Expiry.getText());
            preparedStatement.executeUpdate();
            resultSet = statement.executeQuery("SELECT * FROM Drug2 WHERE Type='"+Type.getText()+"'");
            if(!resultSet.isBeforeFirst()){
                preparedStatement = connect.prepareStatement("INSERT INTO Drug2 Values(?,?)");
                preparedStatement.setString(1,Type.getText());
                preparedStatement.setString(2,Location.getText());
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

        //ddrug both tables to be updated

    }

    class reset implements ActionListener{
        public void actionPerformed(ActionEvent e){
            AddDrug.this.reset();
        }
    }

    class submit implements ActionListener{
        public void actionPerformed(ActionEvent e){
            AddDrug.this.runQuery();
        }
    }
}