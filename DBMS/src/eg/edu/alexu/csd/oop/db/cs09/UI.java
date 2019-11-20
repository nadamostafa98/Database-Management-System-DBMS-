package eg.edu.alexu.csd.oop.db.cs09;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

import eg.edu.alexu.csd.oop.db.Database;

public class UI {
	String path = new String();
	Database db = new DB();
	public  String getPath() {
		return path;
	}
	public  void setPath(String s) {
		path= s;
	}
 public void theFunction() {
	 System.out.println("Press:");
	 System.out.println("1.Create Database");
	 System.out.println("2.Create/drop table or database");
	 System.out.println("3.Select");
	 System.out.println("4.Insert/update/delete");
 }
 public void parser (String s) {
	 
	 String lowerCaseS = s.toLowerCase();
	 
	if( lowerCaseS.contains("create") || lowerCaseS.contains("drop")) {
		try {
			boolean b = db.executeStructureQuery(s);
			if (b) {
				System.out.println("SUCCESSFULL OPERATION");
			}else {
				System.err.println("UNSUCCESSFULL OPERATION");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}else if (lowerCaseS.contains("select")) {
		try {
			Object[][] output =db.executeQuery(s);
			if (output.length==0) {
				System.err.println("YOUR CONDITION DOESN'T EXIST OR WRONG QUERY");
			}else {
			for (int i = 0; i < output.length; i++) {
				System.out.print("Elements in row"+(i+1)+" ");
				for (int j = 0; j < output[0].length; j++) {
					System.out.print(output[i][j]);
					if(j!=output[0].length-1) {
						System.out.print(" , ");
					}else {
						System.out.println();
					}
				}
			}
		}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}else if(lowerCaseS.contains("insert")|| lowerCaseS.contains("delete") || lowerCaseS.contains("update")) {
		try {
			int a=db.executeUpdateQuery(s);
			if (a==0) {
				System.err.println("YOUR CONDITION DOESN'T EXIST OR WRONG QUERY");	
			}else {
				System.out.println("Updated rows = "+a);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}else {
		System.err.println("WRONG QUERY!!!!");
	//	System.out.println("WRONG QUERY!!!!");
	}
 }
 public void execute (int a) {
	  File file = null;
	 DB db = new DB();
	 switch(a){
	 case 1:
		 System.out.println("Enter database name or path: ");
		 Scanner scanner = new Scanner(System.in);
		 String databaseName = scanner.nextLine();
		 db.createDatabase(databaseName, false);;
		 break;
	 case 2:
		 System.out.println("Enter Query: ");
		 Scanner scanner2 = new Scanner(System.in);
		 String query2 = scanner2.nextLine();
		 try {
			 System.out.println("***"+query2);
			db.executeStructureQuery(query2);
			break;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 case 3:
		 System.out.println("Enter Query: ");
		 Scanner scanner3 = new Scanner(System.in);
		 String query3 = scanner3.nextLine();
		 try {
			db.executeQuery(query3);
			break;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 case 4:
		 System.out.println("Enter Query: ");
		 Scanner scanner4 = new Scanner(System.in);
		 String query4 = scanner4.nextLine();
		 try {
			db.executeUpdateQuery(query4);
			break;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
 }
}
