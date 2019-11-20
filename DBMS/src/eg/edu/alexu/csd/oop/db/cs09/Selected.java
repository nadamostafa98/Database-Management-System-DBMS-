package eg.edu.alexu.csd.oop.db.cs09;

public class Selected {
	private int[] selectedRows;

	public int[] getRows() {
		return selectedRows;
	}

	public Object[][] fillselectarray(String colchange, String[] columns, String[][] arr, String m, String value,
			String logic, String[] values, String[] operations, String[] colsChange, Boolean where, Boolean star,
			Boolean select, String colSelect) {
		int[] rows = new int[arr.length];
		int index2 = 0;
		if (!where) {
			selectedRows = new int[arr.length];
			for (int i = 0; i < arr.length; i++) {
				selectedRows[i] = i;
			}
		} else {
			if (logic == null) {
				Boolean flag = false;
				int count = 0;
				for (int i = 0; i < columns.length; i++) {
					if (colchange.equalsIgnoreCase(columns[i])) {
						count = i;
						flag = true;
						break;
					}
				}
				if (flag) {
					Object[][] x = new Object[arr.length][columns.length];
					Object[][] x2 = new Object[arr.length][1];
					int index = 0;
					int selectCol = 0;
					Boolean flagCol = false;
					if (colSelect != null) {
						for (int i = 0; i < columns.length; i++) {
							if (colSelect.equalsIgnoreCase(columns[i])) {
								selectCol = i;
								flagCol = true;
								break;
							}
						}
						if (!flagCol) {
							System.out.println("This Column Is Not Exist");
						}
					}
					if (m.equals("=")) {
						for (int i = 0; i < arr.length; i++) {
							if ((arr[i][count]).equalsIgnoreCase(value)) {
								for (int j = 0; j < columns.length; j++) {
									if (!arr[i][j].contains("'")) {
										x[index][j] = Integer.valueOf(arr[i][j]);
									} else {
										x[index][j] = arr[i][j];
									}

									rows[index2] = i;
								}
								if (!star && select) {
									if (!arr[i][selectCol].contains("'")) {
										x2[index][0] = Integer.valueOf(arr[i][selectCol]);
									} else {
										x2[index][0] = arr[i][selectCol];
									}
								}
								index2++;
								index++;
							}
						}
						Object[][] toReturn = new Object[index][columns.length];
						for (int i = 0; i < index; i++) {
							for (int j = 0; j < columns.length; j++) {
								toReturn[i][j] = x[i][j];
							}
						}
						selectedRows = new int[index2];
						for (int i = 0; i < index2; i++) {
							selectedRows[i] = rows[i];
						}
						if (!star && select) {
							Object[][] toReturn2 = new Object[index][1];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < 1; j++) {
									toReturn2[i][j] = x2[i][j];
								}
							}
							return toReturn2;
						}
						return toReturn;

					}

					if (m.equals(">")) {
						for (int i = 0; i < arr.length; i++) {
							if (Integer.parseInt(arr[i][count]) > Integer.parseInt(value)) {
								for (int j = 0; j < columns.length; j++) {
									if (!arr[i][j].contains("'")) {
										x[index][j] = Integer.valueOf(arr[i][j]);

									} else {
										x[index][j] = arr[i][j];
									}
									rows[index2] = i;
								}
								if (!star && select) {
									if (!arr[i][selectCol].contains("'")) {
										x2[index][0] = Integer.valueOf(arr[i][selectCol]);
									} else {
										x2[index][0] = arr[i][selectCol];
									}
								}
								index2++;
								index++;
							}
						}
						Object[][] toReturn = new Object[index][columns.length];
						for (int i = 0; i < index; i++) {
							for (int j = 0; j < columns.length; j++) {
								toReturn[i][j] = x[i][j];

							}
						}
						selectedRows = new int[index2];
						for (int i = 0; i < index2; i++) {
							selectedRows[i] = rows[i];
						}
						if (!star && select) {
							Object[][] toReturn2 = new Object[index][1];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < 1; j++) {
									toReturn2[i][j] = x2[i][j];
								}
							}
							return toReturn2;
						}
						return toReturn;
					}
					if (m.equals("<")) {

						for (int i = 0; i < arr.length; i++) {

							if (Integer.parseInt(arr[i][count]) < Integer.parseInt(value)) {
								for (int j = 0; j < columns.length; j++) {
									if (!arr[i][j].contains("'")) {
										x[index][j] = Integer.valueOf(arr[i][j]);
									} else {
										x[index][j] = arr[i][j];
									}
									rows[index2] = i;
								}
								if (!star && select) {
									if (!arr[i][selectCol].contains("'")) {
										x2[index][0] = Integer.valueOf(arr[i][selectCol]);
									} else {
										x2[index][0] = arr[i][selectCol];
									}
								}
								index2++;
								index++;
							}
						}
						Object[][] toReturn = new Object[index][columns.length];
						for (int i = 0; i < index; i++) {
							for (int j = 0; j < columns.length; j++) {
								toReturn[i][j] = x[i][j];
							}
						}
						selectedRows = new int[index2];
						for (int i = 0; i < index2; i++) {
							selectedRows[i] = rows[i];
						}
						if (!star && select) {
							Object[][] toReturn2 = new Object[index][1];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < 1; j++) {
									toReturn2[i][j] = x2[i][j];
								}
							}
							return toReturn2;
						}
						return toReturn;
					}
				}
			} else {
				String value2 = values[0];
				String column2 = colsChange[0];
				String o2 = operations[0];
				Boolean flag = false;
				int count2 = 0;
				for (int i = 0; i < columns.length; i++) {
					if (column2.equalsIgnoreCase(columns[i])) {
						count2 = i;
						flag = true;
						break;
					}
				}
				if (!flag) {
					//// erooooor
				}

				if (logic.equalsIgnoreCase("or") || logic.equalsIgnoreCase("and")) {
					String value1 = values[1];
					String column1 = colsChange[1];
					String o1 = operations[1];
					Boolean flag1 = flag;
					int count1 = 0;
					for (int i = 0; i < columns.length; i++) {
						if (column1.equalsIgnoreCase(columns[i])) {
							count1 = i;
							flag1 = true;
							break;
						}
					}
					if (!flag1) {
						//// erooooor
					}
					int index = 0;
					Object[][] xy = new Object[arr.length][columns.length];
					if (logic.equalsIgnoreCase("and")) {
						if ((o1.equals("=") && o2.equals(">"))) {
							for (int i = 0; i < arr.length; i++) {
								if (((arr[i][count1]).equalsIgnoreCase(value1))
										&& (Integer.parseInt(arr[i][count2]) > Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals(">") && o2.equals("="))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) > Integer.parseInt(value1))
										&& ((arr[i][count2]).equalsIgnoreCase(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals("=") && o2.equals("<"))) {
							for (int i = 0; i < arr.length; i++) {
								if (((arr[i][count1]).equalsIgnoreCase(value1))
										&& (Integer.parseInt(arr[i][count2]) < Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals("<") && o2.equals("="))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) < Integer.parseInt(value1))
										&& ((arr[i][count2]).equalsIgnoreCase(value2))) {
									for (int j = 0; j < columns.length; j++) {
										xy[index][j] = arr[i][j];
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals("<") && o2.equals(">"))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) < Integer.parseInt(value1))
										&& (Integer.parseInt(arr[i][count2]) > Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals(">") && o2.equals("<"))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) > Integer.parseInt(value1))
										&& (Integer.parseInt(arr[i][count2]) < Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals("<") && o2.equals("<"))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) < Integer.parseInt(value1))
										&& (Integer.parseInt(arr[i][count2]) < Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals(">") && o2.equals(">"))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) > Integer.parseInt(value1))
										&& (Integer.parseInt(arr[i][count2]) > Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals("=") && o2.equals("="))) {
							for (int i = 0; i < arr.length; i++) {
								if (((arr[i][count1]).equalsIgnoreCase(value1))
										&& ((arr[i][count2]).equalsIgnoreCase(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						}

					} else if (logic.equalsIgnoreCase("or")) {
						if ((o1.equals("=") && o2.equals(">"))) {
							for (int i = 0; i < arr.length; i++) {
								if (((arr[i][count1]).equalsIgnoreCase(value1))
										|| (Integer.parseInt(arr[i][count2]) > Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals(">") && o2.equals("="))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) > Integer.parseInt(value1))
										|| ((arr[i][count2]).equalsIgnoreCase(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals("=") && o2.equals("<"))) {
							for (int i = 0; i < arr.length; i++) {
								if (((arr[i][count1]).equalsIgnoreCase(value1))
										|| (Integer.parseInt(arr[i][count2]) < Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals("<") && o2.equals("="))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) < Integer.parseInt(value1))
										|| ((arr[i][count2]).equalsIgnoreCase(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals("<") && o2.equals(">"))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) < Integer.parseInt(value1))
										|| (Integer.parseInt(arr[i][count2]) > Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals(">") && o2.equals("<"))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) > Integer.parseInt(value1))
										|| (Integer.parseInt(arr[i][count2]) < Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals("<") && o2.equals("<"))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) < Integer.parseInt(value1))
										|| (Integer.parseInt(arr[i][count2]) < Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals(">") && o2.equals(">"))) {
							for (int i = 0; i < arr.length; i++) {
								if ((Integer.parseInt(arr[i][count1]) > Integer.parseInt(value1))
										|| (Integer.parseInt(arr[i][count2]) > Integer.parseInt(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						} else if ((o1.equals("=") && o2.equals("="))) {
							for (int i = 0; i < arr.length; i++) {
								if (((arr[i][count1]).equalsIgnoreCase(value1))
										|| ((arr[i][count2]).equalsIgnoreCase(value2))) {
									for (int j = 0; j < columns.length; j++) {
										if (!arr[i][j].contains("'")) {
											xy[index][j] = Integer.valueOf(arr[i][j]);
										} else {
											xy[index][j] = arr[i][j];
										}
										rows[index2] = i;
									}
									index2++;
									index++;
								}
							}
							Object[][] toReturn = new Object[index][columns.length];
							for (int i = 0; i < index; i++) {
								for (int j = 0; j < columns.length; j++) {
									toReturn[i][j] = xy[i][j];
								}
							}
							selectedRows = new int[index2];
							for (int i = 0; i < index2; i++) {
								selectedRows[i] = rows[i];
							}
							return toReturn;
						}

					}
				} else if (logic.equalsIgnoreCase("not")) {
					int index = 0;
					Object[][] xy = new Object[arr.length][columns.length];
					if ((o2.equals("="))) {
						for (int i = 0; i < arr.length; i++) {
							if (!((arr[i][count2]).equalsIgnoreCase(value2))) {
								for (int j = 0; j < columns.length; j++) {
									if (!arr[i][j].contains("'")) {
										xy[index][j] = Integer.valueOf(arr[i][j]);
									} else {
										xy[index][j] = arr[i][j];
									}
									rows[index2] = i;
								}
								index2++;
								index++;
							}
						}
						Object[][] toReturn = new Object[index][columns.length];
						for (int i = 0; i < index; i++) {
							for (int j = 0; j < columns.length; j++) {
								toReturn[i][j] = xy[i][j];
							}
						}
						selectedRows = new int[index2];
						for (int i = 0; i < index2; i++) {
							selectedRows[i] = rows[i];
						}
						return toReturn;
					} else if ((o2.equals(">"))) {
						for (int i = 0; i < arr.length; i++) {
							if (!(Integer.parseInt(arr[i][count2]) > Integer.parseInt(value2))) {
								for (int j = 0; j < columns.length; j++) {
									if (!arr[i][j].contains("'")) {
										xy[index][j] = Integer.valueOf(arr[i][j]);
									} else {
										xy[index][j] = arr[i][j];
									}
									rows[index2] = i;
								}
								index2++;
								index++;
							}
						}
						Object[][] toReturn = new Object[index][columns.length];
						for (int i = 0; i < index; i++) {
							for (int j = 0; j < columns.length; j++) {
								toReturn[i][j] = xy[i][j];
							}
						}
						selectedRows = new int[index2];
						for (int i = 0; i < index2; i++) {
							selectedRows[i] = rows[i];
						}
						return toReturn;
					} else if ((o2.equals("<"))) {
						for (int i = 0; i < arr.length; i++) {
							if (!(Integer.parseInt(arr[i][count2]) < Integer.parseInt(value2))) {
								for (int j = 0; j < columns.length; j++) {
									if (!arr[i][j].contains("'")) {
										xy[index][j] = Integer.valueOf(arr[i][j]);
									} else {
										xy[index][j] = arr[i][j];
									}
									rows[index2] = i;
								}
								index2++;
								index++;
							}
						}
						Object[][] toReturn = new Object[index][columns.length];
						for (int i = 0; i < index; i++) {
							for (int j = 0; j < columns.length; j++) {
								toReturn[i][j] = xy[i][j];
							}
						}
						selectedRows = new int[index2];
						for (int i = 0; i < index2; i++) {
							selectedRows[i] = rows[i];
						}
						return toReturn;
					}
				}
				Object[][] xy = new Object[0][0];
				return xy;
			}
		}
		return new Object[0][0];
	}
}
