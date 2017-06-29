package dbms;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
/**
 * Created by Saksham on 11/27/2016.
 */
/*! \class GUI
 * \brief Creates the GUI for the program
 *
 * This class creates the initial gui basically it creates the main frames
 * and adds the basic outer panels and a combobox visible on the initial gui screen
 */
public class Gui {

    JFrame mainframe=new JFrame();
    JPanel top=new JPanel();
    JPanel centrebig=new JPanel();
    JPanel west=new JPanel();
    JPanel centre=new JPanel();
    JPanel westtop=new JPanel();
    JPanel westbottom=new JPanel();
    public void createGui(){
        JLabel jlabel = new JLabel("Chemist DBMS");
        jlabel.setFont(new Font("Verdana",1,40));
        top.add(jlabel);
        mainframe.setSize(new Dimension(800,650));//600
        top.setBorder(new EmptyBorder(16, 16, 16, 16));
        mainframe.add(top,BorderLayout.NORTH);
        centrebig.setLayout(new BorderLayout());
        centrebig.setBorder(new EmptyBorder(16, 16, 16, 16));
        mainframe.add(centrebig,BorderLayout.CENTER);
        centrebig.add(west,BorderLayout.WEST);
        centrebig.add(centre,BorderLayout.CENTER);
        west.setPreferredSize(new Dimension(250,400));
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));
        JComboBox qtype=createList1();
        qtype.setBorder(new EmptyBorder(16, 16, 16, 16));
        westtop.add(qtype);
        west.setBorder(BorderFactory.createLineBorder(Color.blue));
        centre.setBorder(BorderFactory.createLineBorder(Color.blue));
        west.add(westtop);
        west.add(westbottom);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.setVisible(true);

    }

    public JComboBox createList1(){
        JComboBox comboBox=new JComboBox();
        comboBox.setEditable(true);
        comboBox.addActionListener(new listQuerytype());
        comboBox.addItem("Choose any 1");
        comboBox.addItem("Sale");
        comboBox.addItem("Find");
        comboBox.addItem("Expiry");
        comboBox.addItem("History");
        comboBox.addItem("Salewise Profits");
        comboBox.addItem("Employee Report");
        comboBox.addItem("Slow Moving");
        comboBox.addItem("Daily Profits");
        comboBox.addItem("Purchasing Assistant");
        comboBox.addItem("Purchase");
        comboBox.addItem("Add a new Distributor");
        comboBox.addItem("Add a new Doctor");
        comboBox.addItem("Add a new Drug");
        comboBox.addItem("Add a new Employee");
        comboBox.addItem("Add a new Patient");
        comboBox.setEditable(false);
        return comboBox;
    }

    class listQuerytype implements ActionListener {
        public void actionPerformed(ActionEvent e){
            JComboBox temp = (JComboBox) e.getSource();
            Object selected = temp.getSelectedItem();
            if(selected.toString().equals("Sale")){
                GuiQuery1 q1=new GuiQuery1();
                q1.query1gui(westbottom,centre);
            }
            else if(selected.toString().equals("Find")){
                GuiQuery2 q2=new GuiQuery2();
                q2.query2gui(westbottom,centre);
            }

            else if(selected.toString().equals("Expiry")){
                GuiQuery3 q3=new GuiQuery3();
                q3.query3gui(westbottom,centre);
            }
            else if(selected.toString().equals("History")){
                GuiQuery4 q4=new GuiQuery4();
                q4.query4gui(westbottom,centre);
            }
            else if(selected.toString().equals("Salewise Profits")){
                GuiQuery5 q5=new GuiQuery5();
                q5.query5gui(westbottom,centre);
            }
            else if(selected.toString().equals("Employee Report")){
                GuiQuery6 q6=new GuiQuery6();
                q6.query6gui(westbottom,centre);
            }

            else if(selected.toString().equals("Daily Profits")){
                GuiQuery7 q7=new GuiQuery7();
                q7.query7gui(westbottom,centre);
            }
            else if(selected.toString().equals("Purchasing Assistant")){
                GuiQuery8 q7=new GuiQuery8();
                q7.query8gui(westbottom,centre);
            }
            else if(selected.toString().equals("Purchase")){
                GuiQuery9 q9=new GuiQuery9();
                q9.query9gui(westbottom,centre);
            }
            else if(selected.toString().equals("Slow Moving")){
                GuiQuery10 q10=new GuiQuery10();
                q10.query10gui(westbottom,centre);
            }
            else if(selected.toString().equals("Add a new Doctor")){
            	AddDoctor q10=new AddDoctor();
                q10.AddDoctorgui(westbottom,centre);
            }
            else if(selected.toString().equals("Add a new Distributor")){
            	AddDistributor q10=new AddDistributor();
                q10.AddDistributorgui(westbottom,centre);
            }
            else if(selected.toString().equals("Add a new Drug")){
            	AddDrug q10=new AddDrug();
                q10.AddDruggui(westbottom,centre);
            }
            else if(selected.toString().equals("Add a new Employee")){
            	AddEmployee q10=new AddEmployee();
                q10.AddEmployeegui(westbottom,centre);
            }
            else if(selected.toString().equals("Add a new Patient")){
            	AddPatient q10=new AddPatient();
                q10.AddPatientgui(westbottom,centre);
            }
 
            
        }
    }

}





