package dbms;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Scanner;
//author Saksham Suri-2015082 Tushar Kataria-2015184
/*
 * Created by Saksham on 11/13/2016.
 */
public class Main {
    public static void main(String[] args){
        //Parser p=new Parser();
        //Scanner in =new Scanner(System.in);
        //String name=in.nextLine();
        System.setProperty("jdk.xml.entityExpansionLimit", "0");
//        Query1 q = new Query1();
//        ArrayList<Publication> res=q.parse(name,1);
//        String [][]a=Dataconverter.convert(res);
//        for (String []s:
//             a) {
//            for (String b:s){
//                System.out.print(b+",");
//            }
//            System.out.print("\n");
//        }
//        System.out.println("Query 2 a 0");
//        for(Publication pu: res){
//            System.out.println(pu);
//        }
//        System.out.println("Query 2 a i");
//        res = q.sortit(1);
//        for(Publication pu: res){
//            System.out.println(pu);
//        }
//        System.out.println("Query 2 a i");
//        res = q.sortit(2);
//        for(Publication pu: res){
//            System.out.println(pu);
//        }
//        q.setYear(2000);
//        System.out.println("Query 2 a iii");
//        res = q.sortit(3);
//        for(Publication pu: res){
//            System.out.println(pu);
//        }
//        System.out.println("Query 2 a iv");
//        q.setYear(2002,2005);
//        res = q.sortit(4);
//        for(Publication pu: res){
//            System.out.println(pu);
//        }
//        EntityResolutionParser e = new EntityResolutionParser();
//        e.parse("dblp.xml");

//      Query1 hey=new Query1();
//        Query2 mainhoonpro=new Query2();
//        ArrayList<String> a=mainhoonpro.parse(1000);
//        for(String i:a){
//            System.out.println(i);
//        }
//        Query3 run=new Query3();
//        System.out.println(run.predict("H. Vincent Poor",2015));
       
        Gui run=new Gui();
        run.createGui();
    }
}