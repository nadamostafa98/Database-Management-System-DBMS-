package eg.edu.alexu.csd.oop.db.cs09;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.Database;

interface TablePlan 
{ 
	public void setTable(String table); 
	
    public void setRows(int row); 
  
    public void setCols(int col); 
  
    
    public void setPath(String path);
    
	public String getTable(); 
	
    public int getRows(); 
  
    public int getCols(); 
      
    public String getPath();
}

class Table implements TablePlan {
	private int row; 
    private int col; 
    private String val; 
    private String table; 
    private String path;
    
    public void setTable(String table)  
    { 
        this.table = table; 
    } 
    
    public void setRows(int row)  
    { 
        this.row = row; 
    } 
  
    public void setCols(int col)  
    { 
        this.col = col; 
    } 
  
    public void setPath(String path)  
    { 
        this.path = path; 
    }
	public String getTable() {
		return table;
	}
	public int getRows() {
		return row;
	}

	public int getCols() {
		return col;
	}

	public String getPath() {
		return path;
	} 
}
interface TBuilder 
{ 
    public void buildTable(); 
  
    public void buildRow(); 
  
    public void buildCol(); 
    
    public void buildAll();
    
    public Table getTable();
    
} 

class Build implements TBuilder
{ 
    private Table table; 
    private String tableName;
    private Document doc;
    private Element t;
    private Element row;
    private Element col;
    private  String a [][];
    private String dbPath;
    private File db;
    public Build(String dbPath , File db , String tableName, String a [][])  
    { 
        this.table = new Table(); 
        this.tableName = tableName;
        this .a = a;
        this.db = db;
        this.dbPath = dbPath;
				try {
					DocumentBuilderFactory dbFactory =
					DocumentBuilderFactory.newInstance();
					DocumentBuilder dBuilder;
					dBuilder = dbFactory.newDocumentBuilder();
					doc = dBuilder.newDocument();

				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    } 
    public void buildTable()  
    { 
        t = doc.createElement("table");
		doc.appendChild(t);
    } 
  
    public void buildRow()  
    { 
    	row = doc.createElement("row");
		t.appendChild(row);
    } 
  
    public void buildCol()  
    { 
    	for(int i = 0; i < a.length; i++) {
    		a[i][0] = a[i][0].toLowerCase();
    	col = doc.createElement(a[i][0]);
		//col.appendChild(doc.createTextNode(" "));
		row.appendChild(col);
    	}
    } 
    
    public void buildAll() {
    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			File f = new File(dbPath + db.separator + tableName +".xml");
		//	table.setPath(f.getAbsolutePath());
	        StreamResult result = new StreamResult(f);
	        try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public Table getTable()  
    { 
        return this.table; 
    }
} 

interface TInserter 
{ 
    public void buildVal(); 
      
    public void buildAll();
    
    public Table getTable();
    
} 
class Insert implements TInserter
{ 
    private Table table; 
    private String tableName;
    private Document doc;
    private  String[] c;
    private  String[] v;
    private  String[] allColumns;
    private File inputFile;
    public Insert(String dbPath , File db , String tableName, String []c, String[]v, String[]allColumns) throws SAXException, IOException  
    { 
        this.table = new Table(); 
        this.tableName = tableName;
        this .c = c;
        this .v = v;
        this.allColumns = allColumns;
				try {
			         inputFile = new File(dbPath + db.separator + tableName +".xml");
			         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			         DocumentBuilder dBuilder;
					dBuilder = dbFactory.newDocumentBuilder();
					doc = dBuilder.parse(inputFile);
			        doc.getDocumentElement().normalize();

				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    } 

    public void buildVal() {
    	 NodeList nList = doc.getElementsByTagName("row");
    	  int i = 0;
    	  Node nNode = nList.item(0);
    	  Element eElement = (Element) nNode;
    	  if(c.length == 0) {
    		  allColumns[i] = allColumns[i].toLowerCase();
    			 if(nList.getLength() == 1 && !eElement.getElementsByTagName((allColumns[i])).item(0).hasChildNodes()) {
    			    	while(i < allColumns.length) {
    			    	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
    						eElement = (Element) nNode;
    						eElement.getElementsByTagName(allColumns[i]).item(0).appendChild(doc.createTextNode(v[i++]));
    			    		}
    			    	}
    			    }
    		        else {
    		        	NodeList list = doc.getElementsByTagName("table");
    		        	Node e = list.item(0);
    		        	Element row = doc.createElement("row");
    		        	e.appendChild(row);
    		        for(int k = 0; k < allColumns.length; k++) {
    		    	        	Element col = doc.createElement(allColumns[k]);
    		    				col.appendChild(doc.createTextNode(v[k]));
    		    				row.appendChild(col);
    		        	}
    		        }
    		    }
    	  else {
    	  c[i] = c[i].toLowerCase();
		 if(nList.getLength() == 1 && !eElement.getElementsByTagName((c[i])).item(0).hasChildNodes()) {
		    	while(i < c.length) {
		    	if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					eElement = (Element) nNode;
					c[i] = c[i].toLowerCase();
					eElement.getElementsByTagName(c[i]).item(0).appendChild(doc.createTextNode(v[i++]));
		    		}
		    	}
		    }
	        else {
	        	NodeList list = doc.getElementsByTagName("table");
	        	Node e = list.item(0);
	        	Element row = doc.createElement("row");
	        	e.appendChild(row);
	        for(int k = 0; k < allColumns.length; k++) {
	        	for(int j = 0; j < c.length; j++) {
	        		c[j] = c[j].toLowerCase();
	        		if(c[j].equals(allColumns[k])) {
	    	        	Element col = doc.createElement(c[j]);
	    	        	
	    				col.appendChild(doc.createTextNode(v[j]));
	    				row.appendChild(col);
	        		}
	        	}
	        }
	    }
	}
    }
    public void buildAll() {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(inputFile);
	        try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public Table getTable()  
    { 
        return this.table; 
    }
} 
interface TDeleter{
	
    public void deleteVal(); 
    
    public void deleteAll();
    
    public Table getTable();
}
class Delete implements TDeleter
{ 
    private Table table; 
    private String tableName;
    private Document doc;
    private File inputFile;
    private File db;
    private String dbPath;
    private String[][] a;
    private int[] arrayRows;
    public Delete(String tableName,int[] arrayRows ,String dbPath, File db, String[][] a) throws SAXException, IOException  
    { 
        this.table = new Table(); 
        this.tableName = tableName;
        this .arrayRows = arrayRows;
        this.db = db;
        this.dbPath = dbPath;
        this.a = a;
				try {
			         inputFile = new File(dbPath + db.separator + tableName +".xml");
			         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			         DocumentBuilder dBuilder;
					dBuilder = dbFactory.newDocumentBuilder();
					doc = dBuilder.parse(inputFile);
			        doc.getDocumentElement().normalize();

				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    } 

    public void deleteVal() {
    	 NodeList nList = doc.getElementsByTagName("row");
		    int i;
		    for(i = nList.getLength()-1; i >= 0 ; i--) {
		    	Node node = nList.item(i);
		    	Element element = (Element) node;
		    	for(int j = arrayRows.length-1; j >= 0; j--) {
		    		if(arrayRows[j] == i) {
		    			Node parent = element.getParentNode();
		    			parent.removeChild(element);
		    			parent.normalize();
		    		}
		    	}
		    }
    }
    
    public void deleteAll() {
    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(inputFile);
	        try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    if(doc.getElementsByTagName("row").getLength() == 0) {
	    	BuildTable x = new BuildTable();
	    	x.buildT(dbPath, db, tableName, a);
	    }
    }
    public Table getTable()  
    { 
        return this.table; 
    }
} 
interface TUpdater{
	
    public void updateVal(); 
    
    public void updateAll();
    
    public Table getTable();

	public boolean getCheck();
}
class Update implements TUpdater
{ 
    private Table table; 
    private String tableName;
    private Document doc;
    private  String colName;
    private String[] c;
    private String[]v;
    private File inputFile;
    private int[] arrayRows;
    private boolean updatecheck; 

    public Update (String dbPath, File db,String tableName, int[] arrayRows, String[] c, String[]v) throws SAXException, IOException  
    { 
        this.table = new Table(); 
        this.tableName = tableName;
        this.v = v;
        this.c = c;
        this .arrayRows = arrayRows;
				try {
			         inputFile = new File(dbPath + db.separator + tableName +".xml");
			         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			         DocumentBuilder dBuilder;
					dBuilder = dbFactory.newDocumentBuilder();
					doc = dBuilder.parse(inputFile);
			        doc.getDocumentElement().normalize();

				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    } 

    public void updateVal() {
    	 NodeList nList = doc.getElementsByTagName("row");
    	 if(nList.getLength() != 1) {
    		updatecheck = true;
		    for(int i = 0; i < nList.getLength(); i++) {
		    	Node node = nList.item(i);
		    	Element element = (Element) node;
		    	for(int j = 0; j < arrayRows.length; j++) {
		    		if(i == arrayRows[j]) {
		    			for(int k =0; k < c.length; k++) {
		    				c[k] = c[k].toLowerCase();
			    			element.getElementsByTagName(c[k]).item(0).setTextContent(v[k]);
		    			}
		    		}
		    	}
		    }
    }
    }
    public void updateAll() {
    	TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
	        StreamResult result = new StreamResult(inputFile);
	        try {
				transformer.transform(source, result);
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public Table getTable()  
    { 
        return this.table; 
    }
	public boolean getCheck() {
    	return this.updatecheck;
    }
} 
class Creating  
{ 
  
    private TBuilder TBuilder; 
  
    public Creating(TBuilder TBuilder) 
    { 
        this.TBuilder = TBuilder; 
    } 
  
    public Table getTable() 
    { 
        return this.TBuilder.getTable(); 
    } 
  
    public void constructTable() 
    { 
        this.TBuilder.buildTable(); 
        this.TBuilder.buildRow(); 
        this.TBuilder.buildCol(); 
        this.TBuilder.buildAll();
    } 
} 
class Inserting{
	private TInserter tInserter;
	
	public Inserting(TInserter tInserter) {
		this.tInserter = tInserter;
	}
    public Table getTable() 
    { 
        return this.tInserter.getTable(); 
    } 
    public void constructTable() 
    { 
        this.tInserter.buildVal(); 
        this.tInserter.buildAll();
    } 
  
}
class Deleting{
	private TDeleter tDeleter;
	
	public Deleting(TDeleter tDeleter) {
		this.tDeleter = tDeleter;
	}
    public Table getTable() 
    { 
        return this.tDeleter.getTable(); 
    } 
    public void constructTable() 
    { 
        this.tDeleter.deleteVal(); 
        this.tDeleter.deleteAll();
}
}
class Updating{
	private TUpdater tUpdater;
	
	public Updating(TUpdater tUpdater) {
		this.tUpdater = tUpdater;
	}
    public Table getTable() 
    { 
        return this.tUpdater.getTable(); 
    } 
    public void constructTable() 
    { 
        this.tUpdater.updateVal(); 
        this.tUpdater.updateAll();
}
}
interface BuildTab{
	void buildT(String dbPath , File db , String tableName , String a [][]);
	void insertT (String tableName, String dbPath, File db, String[] col, String[] val, String[] allColumns)throws SAXException, IOException;
	void delete(String tableName,int[] arrayRows ,String dbPath, File db, String[][] a) throws SAXException, IOException;
	boolean update(String dbPath, File db,String tableName, int[] arrayRows, String[] c, String[]v) throws SAXException, IOException;
}
class BuildTable implements BuildTab 
{ 
	public void buildT (String dbPath , File db , String tableName , String a [][]) {
		
        TBuilder tBuilder = new Build(dbPath,db,tableName,a); 
        Creating engineer = new Creating(tBuilder); 
        engineer.constructTable(); 
	}
	public void insertT (String tableName, String dbPath, File db, String[] col, String[] val, String[] allColumns) throws SAXException, IOException {
		TInserter tInserter = new Insert(dbPath, db, tableName, col, val,allColumns);
		Inserting engineer = new Inserting(tInserter);
		engineer.constructTable();  
		
	}
	public void delete(String tableName,int[] arrayRows ,String dbPath, File db, String[][] a) throws SAXException, IOException {
		TDeleter tDeleter = new Delete(tableName, arrayRows, dbPath, db, a);
		Deleting engineer = new Deleting(tDeleter);
		engineer.constructTable();		
	}
	public boolean update(String dbPath, File db,String tableName, int[] arrayRows, String[] c, String[]v) throws SAXException, IOException {
		
		TUpdater tUpdater = new Update(dbPath, db,tableName, arrayRows, c, v);
		Updating engineer = new Updating(tUpdater);
		engineer.constructTable();
		boolean b = tUpdater.getCheck();
		return b;
	}
}