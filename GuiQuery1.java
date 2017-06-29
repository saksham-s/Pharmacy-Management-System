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

public class GuiQuery1 {
    JTextField SearchMed=new JTextField(10);
    JButton find=new JButton("Find");
    JTextField TotalCost=new JTextField(10);
    JPanel p7=new JPanel(new FlowLayout(FlowLayout.LEFT));
    JTextField EmployeeId=new JTextField(10);
    JTextField CustId = new JTextField(15);
    JTextField Medicine1 = new JTextField(15);
    JTextField Med1Quantity = new JTextField(15);
    JPanel base=new JPanel(new FlowLayout(FlowLayout.CENTER));
    
    JTextField DoctorId=new JTextField(15);
    
    
    JButton reset=new JButton("Reset");
    JButton search=new JButton("Submit");
    JButton addcart=new JButton("Cart");
    private DefaultTableModel model = new DefaultTableModel();
    JTable table;
    JPanel centre;

    public void query1gui(JPanel westbottom,JPanel centr){
        westbottom.removeAll();
        this.centre=centr;
        centre.removeAll();
        westbottom.setLayout(new BoxLayout(westbottom, BoxLayout.Y_AXIS));
        JPanel p1=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p1.add(new JLabel("Employee Id"));
        p1.add(EmployeeId);
        JPanel p2=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p2.add(new JLabel("Cust Id"));
        p2.add(CustId);
        JPanel p3=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p3.add(new JLabel("Med1"));
        p3.add(Medicine1);
        JPanel p4=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p4.add(new JLabel("Quantity1"));
        p4.add(Med1Quantity);
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(new JLabel("Give Med Name"));
        p5.add(SearchMed);
        find.addActionListener(new find());
        p5.add(find);
        p7.add(new JLabel("Current Cost"));
        TotalCost.setEditable(false);
        p7.add(TotalCost);
        TotalCost.setEditable(false);
        JPanel p8=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p8.add(new JLabel("Doctor Id"));
        p8.add(DoctorId);
        
        westbottom.add(p1);
        westbottom.add(p2);
        westbottom.add(p8);
        westbottom.add(p3);
        westbottom.add(p4);
        search.addActionListener(new submit());
        reset.addActionListener(new reset());
        addcart.addActionListener(new addtocart());
        base.add(search);
        base.add(reset);
        base.add(addcart);
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
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        centre.add(p5, BorderLayout.NORTH);
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        centre.add(p7,BorderLayout.SOUTH);
        centre.revalidate();
    }
    //seteditable
    public void reset(){
        centre.removeAll();
        JPanel p5=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p5.add(new JLabel("Give Med Name"));
        p5.add(SearchMed);
        p5.add(find);
        Medicine1.setText("");
        EmployeeId.setText("");
        CustId.setText("");
        Med1Quantity.setText("");
        TotalCost.setText("");
        model = new DefaultTableModel();
        model.addColumn("DrugId");
        model.addColumn("Name");
        model.addColumn("Quantity");
        model.addColumn("Price");
        table = new JTable(model);
        centre.setLayout(new BorderLayout());
        centre.add(p5,BorderLayout.NORTH);
        centre.add( new JScrollPane( table ), BorderLayout.CENTER );
        centre.add(p7,BorderLayout.SOUTH);
        centre.repaint();
        centre.revalidate();
    }
    public void runQuery(){
    	Integer sale_id = 1;
    	Connection connect = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        //DefaultTableModel mod=(DefaultTableModel)table.getModel();
        try{	
        	Class.forName("com.mysql.jdbc.Driver");
 			connect = DriverManager.getConnection("jdbc:mysql://localhost/dbms?"+"user=root&password=pass");
 			statement = connect.createStatement();
 			resultSet = statement.executeQuery("SELECT * FROM Sale_Transaction ORDER BY Sale_ID DESC LIMIT 1");
 			if(!resultSet.isBeforeFirst()){
                sale_id = 1;
            }else{
                resultSet.next();
                sale_id=Integer.parseInt(resultSet.getString("Sale_ID").substring(4))+1;
            }
 			resultSet = statement.executeQuery("SELECT * FROM Doctor WHERE Doctor_ID='"+DoctorId.getText()+"'");
 			System.out.println("Yeah!");
 			
            if(!resultSet.isBeforeFirst()){
				Exception e = new Exception();
				throw e;
			}
            resultSet = statement.executeQuery("SELECT * FROM Patient WHERE Patient_ID='"+CustId.getText()+"'");
            if(!resultSet.isBeforeFirst()){
            	Exception e = new Exception();
				throw e;
			}
 	        preparedStatement = connect.prepareStatement("INSERT INTO Sale_Transaction Values(?,"+"curdate()"+",?)");
 	        preparedStatement.setString(1, "Sale"+sale_id.toString());
            preparedStatement.setString(2, EmployeeId.getText());
            preparedStatement.executeUpdate();
            
            System.out.println(model.getRowCount());
            for (int i = 0; i < model.getRowCount(); i++) {
				String drugid = (String)model.getValueAt(i,0);
				Integer Quantity=Integer.parseInt((String)model.getValueAt(i,2));
	 	        preparedStatement = connect.prepareStatement("INSERT INTO Sells Values(?,?,?,"+Quantity.toString()+")");
	 	        preparedStatement.setString(1, "Sale"+sale_id.toString());
	            preparedStatement.setString(2,drugid);
	            preparedStatement.setString(3,CustId.getText());
	            preparedStatement.executeUpdate();
				preparedStatement = connect.prepareStatement("INSERT INTO Prescribes Values(?,?,?)");
				preparedStatement.setString(1,CustId.getText());
	            preparedStatement.setString(2,DoctorId.getText());
	            preparedStatement.setString(3,drugid);
	            preparedStatement.executeUpdate();
				preparedStatement = connect.prepareStatement("UPDATE Drug1 SET Stock = Stock - "+Quantity.toString()+" WHERE Drug_ID=?");
				preparedStatement.setString(1,drugid);
				preparedStatement.executeUpdate();
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
       
        
    	model.getValueAt(0,0);
    	
    	
        //whatevers in the jtable update it to database
        //transaction is done

    }

    public void addalltocart(){
    	String medname=Medicine1.getText();
    	if(!medname.equals("")){
    		ArrayList<String> numdata = new ArrayList<String>();
            //perform query and store result in these arrays
            Connection connect = null;
            Statement statement = null;
            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            try{	
    			Class.forName("com.mysql.jdbc.Driver");
    			connect = DriverManager.getConnection("jdbc:mysql://localhost/dbms?"+"user=root&password=pass");
    			statement = connect.createStatement();
    			resultSet = statement.executeQuery("SELECT * FROM Drug1 WHERE Stock>="+Med1Quantity.getText()+" AND Drug_ID='"+medname+"'");
    			if(!resultSet.isBeforeFirst()){
    				//add warning no such data
    			}
    			else{
	    	        while(resultSet.next()){
	    	        	double cost=Double.parseDouble(resultSet.getString("MRP"))*(1-Double.parseDouble(resultSet.getString("Discount"))/100)*Double.parseDouble(Med1Quantity.getText());
	    	        	model.addRow(new Object[]{resultSet.getString("Drug_ID"),resultSet.getString("Name"),Med1Quantity.getText(),cost});
	    	        	if(!TotalCost.getText().equals("")){
	    	        		cost=cost+Double.parseDouble(TotalCost.getText());
	    	        	}
	    	        	TotalCost.setText(Double.toString(cost));
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
    public void searchmed(){
        String name=SearchMed.getText();
        //search medicines with name like this
        //2d array of results
        JFrame meds=new JFrame();
        JLabel heading = new JLabel("Chemist DBMS");
        heading.setFont(new Font("Verdana",1,40));
        meds.add(heading,BorderLayout.NORTH);
        DefaultTableModel model1 = new DefaultTableModel();
        model1.addColumn("DrugId");
        model1.addColumn("Name");
        model1.addColumn("Stock");
        model1.addColumn("Price");
        model1.addColumn("Location");
        model1.addColumn("Expiry");
        JTable tablemeds=new JTable(model1);
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
	        	model1.addRow(new Object[]{resultSet.getString("Drug_ID"),resultSet.getString("Name"),resultSet.getString("Stock"),resultSet.getString("MRP"),resultSet.getString("Location"),resultSet.getString("Drug_ID")});
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
        meds.add( new JScrollPane( tablemeds ), BorderLayout.CENTER );
        meds.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        meds.setSize(new Dimension(400,200));//600
        meds.setVisible(true);
        centre.repaint();
        centre.revalidate();

    }
    class reset implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GuiQuery1.this.reset();
        }
    }

    class find implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GuiQuery1.this.searchmed();
        }
    }
    class submit implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GuiQuery1.this.runQuery();
        }
    }

    class addtocart implements ActionListener{
        public void actionPerformed(ActionEvent e){
            GuiQuery1.this.addalltocart();
        }
    }
}
