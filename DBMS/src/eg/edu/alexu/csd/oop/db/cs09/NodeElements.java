package eg.edu.alexu.csd.oop.db.cs09;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

interface Visitor {

	 public String[] visitValues(NodeElements node);
	 public String[] visitCol(NodeElements node);
	 public String[][] visitTable(NodeElements node);

}

interface ItemElement {
	public String[] acceptvalue(GetFromTable1 visitor);
	public String[] acceptcol(GetFromTable1 visitor);
	public String[][] accepttable(GetFromTable1 visitor);
	
}


public class NodeElements implements ItemElement {
	private String dbpath;
	private String tableName;
	private File db;
	private String colName;
	private Document doc;

	public NodeElements(String dbpath, String tableName, File db, String colchange) {
		this.tableName = tableName;
		this.db = db;
		this.dbpath = dbpath;
		this.colName = colName;

	}

	public NodeList nodeList() throws ParserConfigurationException, SAXException, IOException {
		File inputFile = new File(dbpath + db.separator + tableName + ".xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		NodeList nList = doc.getElementsByTagName("row");
		return nList;
	}
	public  String getColName() {
		return colName;
	}
	@Override
	public String[] acceptvalue(GetFromTable1 visitor) {
		// TODO Auto-generated method stub
		return visitor.visitValues(this);
	}

	@Override
	public String[] acceptcol(GetFromTable1 visitor) {
		// TODO Auto-generated method stub
		return visitor.visitCol(this);
	}

	@Override
	public String[][] accepttable(GetFromTable1 visitor) {
		// TODO Auto-generated method stub
		return visitor.visitTable(this);
	}

}

class GetFromTable1 implements Visitor {
	
	@Override
	public String[] visitValues(NodeElements node) {
		NodeList nList;
		try {
			nList = node.nodeList();
			String[] a = new String[nList.getLength()];
			String colName = node.getColName();
			for(int i = 0; i < nList.getLength(); i++) {
				Node n = nList.item(i);
				Element e = (Element) n;
				Element e1 = (Element) e.getElementsByTagName(colName).item(0);
				a[i] = e1.getTextContent();
				}
			return a;
		} catch (ParserConfigurationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SAXException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}		
		return null;
	}

	@Override
	public String[] visitCol(NodeElements node) {
		NodeList nList;
		try {
			nList = node.nodeList();
			Node nodex = nList.item(0);
			Element element = (Element) nodex;
			String[] a = new String[element.getChildNodes().getLength()];
			for (int j = 0; j < element.getChildNodes().getLength(); j++) {
				a[j] = element.getChildNodes().item(j).getNodeName();
			}
			return a;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	}


	@Override
	public String[][] visitTable(NodeElements node) {
		NodeList nList;
		try {
			nList = node.nodeList();
			Node nodex = nList.item(0);
			Element element = (Element) nodex;
			String[][] a = new String[nList.getLength()][element.getChildNodes().getLength()];
			for (int i = 0; i < nList.getLength(); i++) {
				Node node1 = nList.item(i);
				Element element1 = (Element) node1;
				for (int j = 0; j < element1.getChildNodes().getLength(); j++) {
					a[i][j] = element1.getChildNodes().item(j).getTextContent();
				}
			}
			return a;
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	}
}