package eg.edu.alexu.csd.oop.db.cs09;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

import org.xml.sax.SAXException;

import eg.edu.alexu.csd.oop.db.Database;

public class DB implements Database {
	private File f;
	private LinkedList<String> tables = new LinkedList<>();

	@Override
	public String createDatabase(String databaseName, boolean dropIfExists) {
		Create c = new Create(databaseName);
		if (dropIfExists) {
			c.drop();
			String path = c.create();
			f = new File(path);
			return path;
		} else {
			String path = c.create();
			f = new File(path);
			return path;
		}
	}

	@Override
	public boolean executeStructureQuery(String query) throws SQLException {
		ParserData x = new ParserData(query);
		x.intepreter(query);
		if (x.getcheckcreate() && x.getOperation().equalsIgnoreCase("create")) {
			throw new SQLException();
		}
		String operation = x.getOperation();
		BuildTab y = new BuildTable();
		XsdCreator xsd = new XsdCreator();
		boolean check = x.getbooleancheck();
		if (operation.equalsIgnoreCase("CREATE") && check) {
			if (x.getdatabasename() != null) {
				String path = createDatabase(x.getdatabasename(), false);
				f = new File(path);
				return true;
			} else if (!tables.contains(x.getTable().toLowerCase())) {
				// System.out.println(f.getAbsolutePath());
				y.buildT(f.getAbsolutePath(), f, x.getTable(), x.getColumns());
				xsd.create(f.getAbsolutePath() + f.separator + x.getTable() + ".xml");
				tables.add(x.getTable().toLowerCase());
				return true;
			} else {
				return false;
			}
		}
		if (operation.equalsIgnoreCase("DROP")) {
			if (x.getdeleteDatabase() != null) {
				Create c = new Create(x.getdeleteDatabase());
				c.drop();
				f = null;
				return true;
			} else if (x.getdeletetable() != null && tables.size() != 0) {
				y.buildT(f.getAbsolutePath(), f, x.getdeletetable(), x.getColumns());
				File file = new File(f.getAbsolutePath() + f.separator + x.getdeletetable() + ".xml");
				file.delete();
				xsd.delete(f.getAbsolutePath() + f.separator + x.getdeletetable() + ".xsd");
				tables.remove(x.getdeletetable());
				return true;
			}

		}

		return false;
	}

	@Override
	public Object[][] executeQuery(String query) throws SQLException {
		ParserData x = new ParserData(query);
		BuildTab y = new BuildTable();
		Selected z = new Selected();
		XsdCreator xsd = new XsdCreator();
		if(f != null && tables.size() != 0) {
			x.intepreter(query);
			}
		else {
				return new Object[0][0];
			}
		String operation = x.getOperation();
		boolean check = x.getbooleancheck();
		String s1 = x.getTable().toLowerCase();
		if (tables.contains(s1) && f != null) {
			if (operation.equalsIgnoreCase("SELECT") && check) {
				ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
				Visitor get = new GetFromTable1();
				s.accepttable((GetFromTable1) get);
				Map<String, String> map1 = x.getToSelect1();
				Map<String, String[]> map2 = x.getToSelect2();
				Map<String, Boolean> map3 = x.getToSelect3();

				Object[][] a = z.fillselectarray(map1.get("colchange"), s.acceptcol((GetFromTable1) get),
						s.accepttable((GetFromTable1) get), map1.get("operation"), map1.get("value"), map1.get("logic"),
						map2.get("values"), map2.get("operations"), map2.get("colsChange"), map3.get("where"),
						map3.get("star"), map3.get("select"), map1.get("colSelect"));
				if ((z.getRows().length == s.accepttable((GetFromTable1) get).length) && !map3.get("where")) {
					return s.accepttable((GetFromTable1) get);
				}
				return a;
			}
		}
		return null;
	}

	@Override
	public int executeUpdateQuery(String query) throws SQLException {
		ParserData x = new ParserData(query);
		Selected k = new Selected();
		BuildTab y = new BuildTable();
		if(f != null && tables.size() != 0) {
		x.intepreter(query);
		}
		else {
			return 0;
		}
		XsdCreator xsd = new XsdCreator();
		String operation = x.getOperation();
		String s1 = x.getTable().toLowerCase();
		if (tables.contains(s1) && f != null && tables.size() != 0) {
			boolean check = x.getbooleancheck();
			if (operation.equalsIgnoreCase("INSERT") && check) {
				try {
					ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
					Visitor get = new GetFromTable1();
					y.insertT(x.getTable(), f.getAbsolutePath(), f, x.getcolumns(), x.getvalueinsert(),
							s.acceptcol((GetFromTable1) get));
					xsd.delete(f.getAbsolutePath() + f.separator + x.getTable() + ".xsd");
					xsd.create(f.getAbsolutePath() + f.separator + x.getTable() + ".xml");
					return 1;
				} catch (SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (operation.equalsIgnoreCase("DELETE") && check) {
				try {
					ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
					Visitor get = new GetFromTable1();
					Map<String, String> map1 = x.getToSelect1();
					Map<String, String[]> map2 = x.getToSelect2();
					Map<String, Boolean> map3 = x.getToSelect3();
					Object[][] a = k.fillselectarray(map1.get("colchange"), s.acceptcol((GetFromTable1) get),
							s.accepttable((GetFromTable1) get), map1.get("operation"), map1.get("value"),
							map1.get("logic"), map2.get("values"), map2.get("operations"), map2.get("colsChange"),
							map3.get("where"), map3.get("star"), map3.get("select"), map1.get("colSelect"));
					y.delete(x.getTable(), k.getRows(), f.getAbsolutePath(), f, x.theColumns);
					xsd.delete(f.getAbsolutePath() + f.separator + x.getTable() + ".xsd");
					xsd.create(f.getAbsolutePath() + f.separator + x.getTable() + ".xml");
					return k.getRows().length;
				} catch (SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (operation.equalsIgnoreCase("UPDATE") && check) {
				try {
					ItemElement s = new NodeElements(f.getAbsolutePath(), x.getTable(), f, x.getcolchange());
					Visitor get = new GetFromTable1();
					Map<String, String> map1 = x.getToSelect1();
					Map<String, String[]> map2 = x.getToSelect2();
					Map<String, Boolean> map3 = x.getToSelect3();
					Object[][] a = k.fillselectarray(map1.get("colchange"), s.acceptcol((GetFromTable1) get),
							s.accepttable((GetFromTable1) get), map1.get("operation"), map1.get("value"),
							map1.get("logic"), map2.get("values"), map2.get("operations"), map2.get("colsChange"),
							map3.get("where"), map3.get("star"), map3.get("select"), map1.get("colSelect"));
					boolean b = y.update(f.getAbsolutePath(), f, x.getTable(), k.getRows(), x.getcolumns(),
							x.getvalueinsert());
					xsd.delete(f.getAbsolutePath() + f.separator + x.getTable() + ".xsd");
					xsd.create(f.getAbsolutePath() + f.separator + x.getTable() + ".xml");
					if (b) {
						return k.getRows().length;
					} else {
						return 0;
					}
				} catch (SAXException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		throw new SQLException();
	}
}
