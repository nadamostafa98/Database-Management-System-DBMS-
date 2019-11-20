package eg.edu.alexu.csd.oop.db.cs09;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

interface Exepretion {
	Boolean intepreter(String con);
}

public class ParserData implements Exepretion {
	private String data;
	private String operation;
	static String table;
	private int start1;
	private int end1;
	private int start2;
	private int end2;
	private String[] columns;
	static String[][] theColumns;
	private String[][] selectedarray;
	private String column;
	private String[] valueinsert;
	private boolean booleancheck = true;
	private String colchange;
	private boolean booleandelete = false;
	private String[] arritems;
	private boolean checkcol;
	private String valuecol;
	private String databasename = null;
	private String deleteTable = null;
	private String deleteDatabase = null;
	private boolean checkcreatedb = false;
	private LinkedList<String> pathes = new LinkedList<>();
	private String toList;
	private Map<String, String> toSelect1 = new HashMap<>();
	private Map<String, String[]> toSelect2 = new HashMap<>();
	private Map<String, Boolean> toSelect3 = new HashMap<>();
	private boolean checkcreate = false;

	// CONSTRUCTOR
	public ParserData(String data) {
		this.data = data;
	}

	// GETTER
	public String[][] getColumns() {
		return theColumns;
	}

	public String[][] getselectedarray() {
		return selectedarray;
	}

	public String getOperation() {
		return operation;
	}

	public String getdeletetable() {
		return deleteTable;
	}

	public boolean getcheckcreate() {
		return checkcreate;
	}

	public String getdeleteDatabase() {
		return deleteDatabase;
	}

	public Map getToSelect1() {
		return toSelect1;
	}

	public Map getToSelect2() {
		return toSelect2;
	}

	public Map getToSelect3() {
		return toSelect3;
	}

	public String getdatabasename() {
		return databasename;
	}

	public String getTable() {
		return table;
	}

	public String[] getcolumns() {
		return columns;
	}

	public String[] getvalueinsert() {
		return valueinsert;
	}

	public boolean getbooleancheck() {
		return booleancheck;
	}

	public String getcolchange() {
		return colchange;
	}

	public String getvaluecol() {
		return valuecol;
	}

	public String[] getarrdeleted() {
		return arritems;
	}

	private void returned_value_array(String colchange, String matcher, String value) {

	}

	private Boolean checkTable(String theTableName) {
		String str = theTableName;
		if (pathes.contains(str)) {
			return true;
		}
		return false;
	}

	private Boolean checktype(String valueinsert[], String[] columns, String[][] array2d) {
		for (int i = 0; i < valueinsert.length; i++) {
			for (int j = 0; j < valueinsert.length; j++) {
				if (columns.length != 0) {
					String s1 = columns[i];
					String s2 = array2d[j][0];
					s1 = s1.replace(" ", "");
					s2 = s2.replace(" ", "");
					if (s1.equalsIgnoreCase(s2)) {
						if (array2d[j][1].equalsIgnoreCase("int")) {
							if (valueinsert[i].contains("\'")) {
								return false;
							}
						}
						if (array2d[j][1].equalsIgnoreCase("varchar")) {
							if (!valueinsert[i].contains("\'")) {
								return false;
							}
						} else {

						}

					}
				}
			}

		}
		return true;
	}

	private boolean checkcol(String namecol, String matchgroup) {
		for (int i = 0; i < theColumns.length; i++) {
			String name = theColumns[i][0];
			if (namecol.equalsIgnoreCase(name)) {
				// fe function tanya
				/// if(we kman lesa el mfrood check value mn nada xml)
				/// for loop 3la > || < w a7otha f arrayitems w de ely byt3mlha return
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean intepreter(String con) {
		Boolean select = false;
		checkcreate = false;
		deleteDatabase = null;
		deleteTable = null;
		databasename = null;
		if ((con.contains("(") && !con.contains(")")) || (con.contains(")") && !con.contains("("))) {
			operation = "none";
			return false;
		}
		Pattern pattern = Pattern.compile("CREATE|insert|SELECT|UPDATE|DELETE|drop", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(con);
		if (matcher.find()) {
			operation = matcher.group();
			end1 = matcher.end();
		} else {
			// throw
		}
		pattern = Pattern.compile("TABLE", Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(con);
		if (operation.equalsIgnoreCase("create") && !con.contains("(") && matcher.find()) {
			checkcreate = true;
			return null;
		}
		if (operation.equalsIgnoreCase("INSERT") && con.contains("*")) {
		} else if (operation.equalsIgnoreCase("drop")) {
			pattern = Pattern.compile("TABLE|DATABASE", Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(con);
			if (matcher.find()) {
				if (matcher.group().equalsIgnoreCase("table")) {
					start1 = matcher.end();
					deleteTable = con.substring(start1, con.length());
					deleteTable = deleteTable.replaceAll(" ", "");
				}
				if (matcher.group().equalsIgnoreCase("database")) {
					start1 = matcher.end();
					deleteDatabase = con.substring(start1, con.length());
					deleteDatabase = deleteDatabase.replaceAll(" ", "");
				}
			} else {
				// errrorr
			}

		} else {
			pattern = Pattern.compile("\\(");
			matcher = pattern.matcher(con);
			if (matcher.find()) {
				end1 = matcher.start();
				start2 = matcher.end();
				pattern = Pattern.compile("\\)");
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					end2 = matcher.start();

				} else {
					///// throw
				}
			} else {
				///// throw
			}
			if (operation.equalsIgnoreCase("CREATE")) {
				pattern = Pattern.compile("TABLE|DATABASE", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					start1 = matcher.end();
				} else {
					booleancheck = false;
					return false;
				}
				if (matcher.group().equalsIgnoreCase("table")) {
					table = con.substring(start1, end1);
					table = table.replaceAll(" ", "");
					pathes.add(table);
				}
				if (matcher.group().equalsIgnoreCase("database")) {
					checkcreatedb = true;
					databasename = con.substring(start1, con.length());
					databasename = databasename.replaceAll(" ", "");
				}
			}
			if (operation.equalsIgnoreCase("INSERT")) {
				pattern = Pattern.compile("INTO", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					start1 = matcher.end();
				} else {
					return false;
				}

			}
			if (operation.equalsIgnoreCase("DELETE")) {
				String colSelect = null;
				pattern = Pattern.compile("select", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					int i1 = matcher.end();
					pattern = Pattern.compile("from", Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(con);
					if (matcher.find()) {
						int i2 = matcher.start();
						colSelect = con.substring(i1, i2);
						colSelect = colSelect.replaceAll(" ", "");
					}
				}
				toSelect1.put("colSelect", colSelect);
				Boolean where = false;
				pattern = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					where = true;
				}
				toSelect3.put("where", where);
				Boolean star = false;
				if (con.contains("*")) {
					star = true;
				}
				toSelect3.put("star", star);
				pattern = Pattern.compile("from", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					start1 = matcher.end();
					pattern = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(con);
					if (matcher.find()) {
						end1 = matcher.start();
						start2 = matcher.end();
						String tablestr = new String();
						tablestr = con.substring(start1, end1);
						tablestr = tablestr.replaceAll(" ", "");
						if (booleancheck /* && checkTable(tablestr) */) {
							pattern = Pattern.compile("\\=|\\>|\\<", Pattern.CASE_INSENSITIVE);
							matcher = pattern.matcher(con);
							if (matcher.find()) {
								if (matcher.group().equals("=") || matcher.group().equals("<")
										|| matcher.group().equals(">")) {
									String matchgroup = matcher.group();
									end2 = matcher.start();
									colchange = con.substring(start2, end2);
									if((colchange.toLowerCase()).contains("not ")) {
										colchange = colchange.toLowerCase();
										colchange = colchange.replaceAll("not ", "");
									}
									colchange = colchange.replaceAll(" ", "");
									booleancheck = checkcol(colchange, matchgroup);
									String value = con.substring(matcher.end(), con.length());
									value = value.replaceAll(" ", "");
									if (booleancheck) {
										String[][] nada = null;
										String logic = null;
										pattern = Pattern.compile("and|or|not", Pattern.CASE_INSENSITIVE);
										Matcher x = pattern.matcher(con);
										String[] values = null;
										String[] operations = null;
										String[] colsChange = null;
										if (x.find()) {
											logic = x.group();
											int s1, s2, e1, e2;
											s2 = x.end();
											e2 = con.length();
											e1 = x.start();
											String op2 = con.substring(s2, e2);
											op2 = op2.replaceAll(" ", "");
											pattern = Pattern.compile("\\=|\\>|\\<", Pattern.CASE_INSENSITIVE);
											x = pattern.matcher(op2);
											if (x.find()) {
												String o2 = x.group();
												String value2 = op2.substring(x.end(), op2.length());
												value2 = value2.replaceAll(" ", "");
												String column2 = op2.substring(0, x.start());
												column2 = column2.replaceAll(" ", "");
												if (logic.equalsIgnoreCase("or") || logic.equalsIgnoreCase("and")) {
													pattern = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
													x = pattern.matcher(con);
													if (x.find()) {

													}
													s1 = x.end();
													String op1 = con.substring(s1, e1);
													op1 = op1.replaceAll(" ", "");
													pattern = Pattern.compile("\\=|\\>|\\<", Pattern.CASE_INSENSITIVE);
													x = pattern.matcher(op1);
													if (x.find()) {
														String o1 = x.group();
														String value1 = op1.substring(x.end(), op1.length());
														value1 = value1.replaceAll(" ", "");
														String column1 = op1.substring(0, x.start());
														column1 = column1.replaceAll(" ", "");
														values = new String[2];
														operations = new String[2];
														colsChange = new String[2];
														values[0] = value2;
														operations[0] = o2;
														colsChange[0] = column2;
														values[1] = value1;
														operations[1] = o1;
														colsChange[1] = column1;

													}

												} else {
													values = new String[1];
													operations = new String[1];
													colsChange = new String[1];
													values[0] = value2;
													operations[0] = o2;
													colsChange[0] = column2;
												}
											}

										}

										Selected s = new Selected();
										toSelect1.put("colchange", colchange);
										toSelect1.put("operation", matchgroup);
										toSelect1.put("value", value);
										toSelect1.put("logic", logic);
										toSelect2.put("operations", operations);
										toSelect2.put("values", values);
										toSelect2.put("colsChange", colsChange);
										returned_value_array(colchange, matchgroup, value);
									}
								} else {
									System.err.println("INCOMPLETE EXPRESSION");
									booleancheck = false;
								}
							} /*
								 * else if(matcher.group().equalsIgnoreCase("and") ) {
								 *
								 * }
								 */
						}

					}
				} else {
					// ERROR
				}

			}
			if (operation.equalsIgnoreCase("select") /* && con.contains("*") */) {
				String colSelect = null;
				pattern = Pattern.compile("select", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					int i1 = matcher.end();
					pattern = Pattern.compile("from", Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(con);
					if (matcher.find()) {
						int i2 = matcher.start();
						colSelect = con.substring(i1, i2);
						colSelect = colSelect.replaceAll(" ", "");
					}
				}
				toSelect1.put("colSelect", colSelect);
				select = true;
				Boolean where = false;
				pattern = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					where = true;
				}
				toSelect3.put("where", where);
				Boolean star = false;
				if (con.contains("*")) {
					star = true;
				}
				toSelect3.put("star", star);
				pattern = Pattern.compile("from", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					start1 = matcher.end();
				} else {
					// ERROR
				}
				pattern = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					end1 = matcher.start();
					start2 = matcher.end();
					String tablestr = new String();
					tablestr = con.substring(start1, end1);
					tablestr = tablestr.replaceAll(" ", "");
					/*
					 * if (table.equalsIgnoreCase(tablestr)) { booleancheck = true; }
					 */
					pattern = Pattern.compile("\\=|\\>|\\<");
					matcher = pattern.matcher(con);
					if (matcher.find()) {
						String matchgroup = matcher.group();
						end2 = matcher.start();
						colchange = con.substring(start2, end2);
						colchange = con.substring(start2, end2);
						if((colchange.toLowerCase()).contains("not ")) {
							colchange = colchange.toLowerCase();
							colchange = colchange.replaceAll("not ", "");
						}
						colchange = colchange.replaceAll(" ", "");
						Pattern s11 = Pattern.compile("not ", Pattern.CASE_INSENSITIVE);
						Matcher s22 = s11.matcher(colchange);
						if (s22.find()) {
							colchange = colchange.replaceAll(s22.group(), "");
						}
						booleancheck = checkcol(colchange, matchgroup);
						String value = con.substring(matcher.end(), con.length());
						value = value.replaceAll(" ", "");

						if (booleancheck) {
							String[][] nada = null;
							String logic = null;
							pattern = Pattern.compile("and|or|not", Pattern.CASE_INSENSITIVE);
							Matcher x = pattern.matcher(con);
							String[] values = null;
							String[] operations = null;
							String[] colsChange = null;
							if (x.find()) {
								logic = x.group();
								int s1, s2, e1, e2;
								s2 = x.end();
								e2 = con.length();
								e1 = x.start();
								String op2 = con.substring(s2, e2);
								op2 = op2.replaceAll(" ", "");
								pattern = Pattern.compile("\\=|\\>|\\<", Pattern.CASE_INSENSITIVE);
								x = pattern.matcher(op2);
								if (x.find()) {
									String o2 = x.group();
									String value2 = op2.substring(x.end(), op2.length());
									value2 = value2.replaceAll(" ", "");
									String column2 = op2.substring(0, x.start());
									column2 = column2.replaceAll(" ", "");
									if (logic.equalsIgnoreCase("or") || logic.equalsIgnoreCase("and")) {
										pattern = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
										x = pattern.matcher(con);
										if (x.find()) {

										}
										s1 = x.end();
										String op1 = con.substring(s1, e1);
										op1 = op1.replaceAll(" ", "");
										pattern = Pattern.compile("\\=|\\>|\\<", Pattern.CASE_INSENSITIVE);
										x = pattern.matcher(op1);
										if (x.find()) {
											String o1 = x.group();
											String value1 = op1.substring(x.end(), op1.length());
											value1 = value1.replaceAll(" ", "");
											String column1 = op1.substring(0, x.start());
											column1 = column1.replaceAll(" ", "");
											values = new String[2];
											operations = new String[2];
											colsChange = new String[2];
											values[0] = value2;
											operations[0] = o2;
											colsChange[0] = column2;
											values[1] = value1;
											operations[1] = o1;
											colsChange[1] = column1;

										}

									} else {
										values = new String[1];
										operations = new String[1];
										colsChange = new String[1];
										values[0] = value2;
										operations[0] = o2;
										colsChange[0] = column2;
									}
								}
							}

							Selected s = new Selected();
							toSelect1.put("colchange", colchange);
							toSelect1.put("operation", matchgroup);
							toSelect1.put("value", value);
							toSelect1.put("logic", logic);
							toSelect2.put("operations", operations);
							toSelect2.put("values", values);
							toSelect2.put("colsChange", colsChange);
							//// Object[][] array = s.fillselectarray(colchange, columns, nada, matchgroup,
							//// value, logic,
							/// values, operations, colsChange);
						}
					}
				}
			}
			if (operation.equalsIgnoreCase("INSERT")) {
				column = con.substring(start2, end2);
				pattern = Pattern.compile(" ");
				matcher = pattern.matcher(column);
				columns = column.split(",");
				for (int i = 0; i < columns.length; i++) {
					columns[i] = columns[i].replaceAll(" ", "");
					if (!checkcol(columns[i], "aa")) {
						columns = new String[0];
						break;
					}
				}
			}
			if (operation.equalsIgnoreCase("CREATE")) {

				column = con.substring(start2, end2);
				pattern = Pattern.compile(" ");
				matcher = pattern.matcher(column);
				columns = column.split(",");
				for (int i = 0; i < columns.length; i++) {
					columns[i] = columns[i].replaceAll(" ", "");
				}
			}
			if (/* matcher.find() && */(operation.equalsIgnoreCase("CREATE") && !checkcreatedb)) {

				if (operation.equalsIgnoreCase("CREATE")) {
					theColumns = new String[columns.length][2];
					for (int i = 0; i < columns.length; i++) {
						pattern = Pattern.compile("int|varchar", Pattern.CASE_INSENSITIVE);
						matcher = pattern.matcher(columns[i]);
						if (matcher.find()) {
							if (matcher.group().equalsIgnoreCase("int")) {
								theColumns[i][1] = "int";
								theColumns[i][0] = columns[i].replaceAll("int", "");
								theColumns[i][0] = theColumns[i][0].replaceAll(" ", "");
							} else if (matcher.group().equalsIgnoreCase("varchar")) {
								theColumns[i][1] = "varchar";
								theColumns[i][0] = columns[i].replaceAll("varchar", "");
								theColumns[i][0] = theColumns[i][0].replaceAll(" ", "");
							} else {
								booleancheck = false;
								///// throw
							}
						} else {
							///// throw
							booleancheck = false;
						}
						pattern = Pattern.compile("varchar", Pattern.CASE_INSENSITIVE);
						matcher = pattern.matcher(column);
					}

				} else {
				}
			}

			if (operation.equalsIgnoreCase("INSERT")) {
				pattern = Pattern.compile("VALUES", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					String a = con.substring(matcher.end(), con.length());
					pattern = Pattern.compile("\\(");
					matcher = pattern.matcher(a);
					// matcher.find();
					if (matcher.find()) {
						end1 = matcher.start();
						start2 = matcher.end();
						pattern = Pattern.compile("\\)");
						matcher = pattern.matcher(a);
						if (matcher.find()) {
							end2 = matcher.start();
							column = new String();
							column = a.substring(start2, end2);
							// pattern = Pattern.compile(" ");
							// matcher = pattern.matcher(column);
							int numcl = columns.length;
							valueinsert = new String[numcl];
							valueinsert = column.split(",");
							for (int i = 0; i < valueinsert.length; i++) {
								valueinsert[i] = valueinsert[i].replaceAll(" ", "");
							}
							booleancheck = checktype(valueinsert, columns, theColumns);
						} else {
							booleancheck = false;
						}

					} else {
						booleancheck = false;
					}
				}

			}

		}
		if (operation.equalsIgnoreCase("UPDATE")) {
			String colSelect = null;
			pattern = Pattern.compile("select", Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(con);
			if (matcher.find()) {
				int i1 = matcher.end();
				pattern = Pattern.compile("from", Pattern.CASE_INSENSITIVE);
				matcher = pattern.matcher(con);
				if (matcher.find()) {
					int i2 = matcher.start();
					colSelect = con.substring(i1, i2);
					colSelect = colSelect.replaceAll(" ", "");
				}
			}
			toSelect1.put("colSelect", colSelect);
			Boolean where = false;
			pattern = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(con);
			if (matcher.find()) {
				where = true;
			}
			toSelect3.put("where", where);
			Boolean star = false;
			if (con.contains("*")) {
				star = true;
			}
			toSelect3.put("star", star);
			pattern = Pattern.compile("SET", Pattern.CASE_INSENSITIVE);
			matcher = pattern.matcher(con);
			if (matcher.find()) {
				start1 = matcher.start();
				String str = con.substring(end1, start1);
				end1 = matcher.end();
				str = str.replaceAll(" ", "");
				table = str;
				if (str.equalsIgnoreCase(table)) {
					pattern = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
					matcher = pattern.matcher(con);
					if (matcher.find()) {
						column = new String();
						column = con.substring(end1, matcher.start());
						matcher = pattern.matcher(column);
						columns = column.split(",");
						valueinsert = new String[columns.length];
						for (int i = 0; i < columns.length; i++) {
							pattern = Pattern.compile("\\=", Pattern.CASE_INSENSITIVE);
							matcher = pattern.matcher(columns[i]);
							if (matcher.find()) {
								String a = columns[i].substring(0, matcher.start());
								a = a.replaceAll(" ", "");
								String b = columns[i].substring(matcher.end(), columns[i].length());
								b = b.replaceAll(" ", "");
								valueinsert[i] = b;
								columns[i] = a;
								booleancheck = checkcol(columns[i], "none");
								if (!booleancheck) {
									break;
								}
							} else {
								booleancheck = false;
							}

						}
						if (booleancheck) {
							booleancheck = checktype(valueinsert, columns, theColumns);
							if (booleancheck) {
								pattern = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
								matcher = pattern.matcher(con);
								if (matcher.find()) {
									
									start2 = matcher.end();
									String c = con.substring(start2, con.length());
									c = c.replaceAll(" ", "");
									pattern = Pattern.compile("\\=", Pattern.CASE_INSENSITIVE);
									matcher = pattern.matcher(c);
									if (matcher.find()) {
										
										String namecol = c.substring(0, matcher.start());
										String value = c.substring(matcher.end(), c.length());
										
										if((namecol.toLowerCase()).contains("not")) {
											namecol= namecol.toLowerCase();
											namecol = namecol.replaceAll("not", "");
										}
										namecol = namecol.replaceAll(" ", "");
										booleancheck = checkcol(namecol, "none");
										if (booleancheck) {
											colchange = namecol;
											value = value.replaceAll(" ", "");
											valuecol = value;
											String myStr = con.substring(start2, con.length());
											pattern = Pattern.compile("\\=|\\>|\\<");
											Matcher x = pattern.matcher(myStr);
											String matchgroup = null;
											if (x.find()) {
												matchgroup = x.group();
											}
											String[][] nada = null;
											String logic = null;
											pattern = Pattern.compile("and|or|not", Pattern.CASE_INSENSITIVE);
											x = pattern.matcher(con);
											String[] values = null;
											String[] operations = null;
											String[] colsChange = null;
											if (x.find()) {
												logic = x.group();
												int s1, s2, e1, e2;
												s2 = x.end();
												e2 = con.length();
												e1 = x.start();
												String op2 = con.substring(s2, e2);
												op2 = op2.replaceAll(" ", "");
												pattern = Pattern.compile("\\=|\\>|\\<", Pattern.CASE_INSENSITIVE);
												x = pattern.matcher(op2);
												if (x.find()) {
													String o2 = x.group();
													String value2 = op2.substring(x.end(), op2.length());
													value2 = value2.replaceAll(" ", "");
													String column2 = op2.substring(0, x.start());
													column2 = column2.replaceAll(" ", "");
													if (logic.equalsIgnoreCase("or") || logic.equalsIgnoreCase("and")) {
														pattern = Pattern.compile("where", Pattern.CASE_INSENSITIVE);
														x = pattern.matcher(con);
														if (x.find()) {

														}
														s1 = x.end();
														String op1 = con.substring(s1, e1);
														op1 = op1.replaceAll(" ", "");
														pattern = Pattern.compile("\\=|\\>|\\<",
																Pattern.CASE_INSENSITIVE);
														x = pattern.matcher(op1);
														if (x.find()) {
															String o1 = x.group();
															String value1 = op1.substring(x.end(), op1.length());
															value1 = value1.replaceAll(" ", "");
															String column1 = op1.substring(0, x.start());
															column1 = column1.replaceAll(" ", "");
															values = new String[2];
															operations = new String[2];
															colsChange = new String[2];
															values[0] = value2;
															operations[0] = o2;
															colsChange[0] = column2;
															values[1] = value1;
															operations[1] = o1;
															colsChange[1] = column1;

														}

													} else {
														values = new String[1];
														operations = new String[1];
														colsChange = new String[1];
														values[0] = value2;
														operations[0] = o2;
														colsChange[0] = column2;
													}
												}
											}

											Selected s = new Selected();

											toSelect1.put("colchange", colchange);
											toSelect1.put("operation", matchgroup);
											toSelect1.put("value", value);
											toSelect1.put("logic", logic);
											toSelect2.put("operations", operations);
											toSelect2.put("values", values);
											toSelect2.put("colsChange", colsChange);
											returned_value_array(colchange, matchgroup, value);
										} else {
											booleancheck = false;
										}
									} else {
										booleancheck = false;
									}

								} else {
									booleancheck = false;
								}

							}
						} else {
							booleancheck = false;
						}

					} else {
						column = con.substring(end1, con.length());
						matcher = pattern.matcher(column);
						columns = column.split(",");
						for (int i = 0; i < columns.length; i++) {

							columns[i] = columns[i].replaceAll(" ", "");
						}
						valueinsert = new String[columns.length];
						for (int i = 0; i < columns.length; i++) {
							pattern = Pattern.compile("\\=", Pattern.CASE_INSENSITIVE);
							matcher = pattern.matcher(columns[i]);
							if (matcher.find()) {
								String a = columns[i].substring(0, matcher.start());
								a = a.replaceAll(" ", "");
								String b = columns[i].substring(matcher.start(), columns[i].length());
								b = b.replaceAll(" ", "");
								valueinsert[i] = b;
								columns[i] = a;
								booleancheck = checkcol(columns[i], "none");
								if (!booleancheck) {
									break;
								}
							} else {
								booleancheck = false;
							}

						}
					}
				}
			} else {
				booleancheck = false;

			}

		}
		toSelect3.put("select", select);
		return null;
	}
}
