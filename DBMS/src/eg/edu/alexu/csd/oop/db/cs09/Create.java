package eg.edu.alexu.csd.oop.db.cs09;

import java.io.File;
import java.util.LinkedList;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Create {

	private static File dir = new File("D:\\Tables");
	private static Boolean test;
	private static String tableN;

	Create(String tableN) {
		this.tableN = tableN;
		dir.mkdir();
	}
	private static void deleteDirectory(File theFile) {
		for (File fileEntry : theFile.listFiles()) {
			if (fileEntry.isDirectory()) {
				deleteDirectory(fileEntry);
			}
			fileEntry.delete();
		}
		theFile.delete();
	}

	public static void drop() {
		test = false;
		String path = null;
		int flag = 0;
		File dir2 = null;
		File toDelete = null;
		if (!tableN.contains("\\")) {
			for (File fileEntry : dir.listFiles()) {
				if (fileEntry.getName().equals(tableN)) {
					test = true;
					toDelete = fileEntry;
					fileEntry.delete();
				}
			}
		} else {
			flag = 1;
			File tempFile = new File(tableN);
			String s1 = tableN.substring(0,tableN.indexOf(tempFile.separator));
			for (File fileEntry : dir.listFiles()) {
				if (fileEntry.getName().equals(s1)) {
					test = true;
					toDelete = fileEntry;
					fileEntry.delete();
				}
			}
		}
		if (test) {
			if (toDelete.exists()) {
				deleteDirectory(toDelete);
			}
		}
		else {
			System.err.println("DATABASE DOESN'T EXIST");
		}
	}

	public static String create() {
		test = false;
		String path = null;
		int flag = 0;
		if (!tableN.contains("\\")) {
			for (File fileEntry : dir.listFiles()) {
				if (fileEntry.getName().equals(tableN)) {
					test = true;
					path = fileEntry.getAbsolutePath();
				}
			}
		} else {
			flag = 1;
			File tempFile = new File(tableN);
			for (File fileEntry : dir.listFiles()) {
				if (fileEntry.getName().equals(tempFile.getName())) {
					test = true;
					path = fileEntry.getAbsolutePath();
				}
			}
		}
		if (!test) {
			if (flag == 1) {
				String tempPath = dir.getAbsolutePath();
				File f = null;
				File ff = new File(tableN);
				LinkedList<String> folders = new LinkedList();
				String str = tableN;
				int testFlag = 0;
				for (int j = 0; j < str.length(); j++) {
					if (str.contains(ff.separator)) {
						int u = str.lastIndexOf(ff.separator);
						folders.add(str.substring(u + 1, str.length()));
						str = str.substring(0, u);
					} else {
						folders.add(str);
						break;
					}
				}
			
				for (int i = (folders.size() - 1); i >= 0; i--) {
					f = new File(tempPath + "\\" + folders.get(i));
					f.mkdir();
					tempPath = f.getAbsolutePath();
				}
				path = f.getAbsolutePath();
			} else {
				File f = new File(dir.getAbsolutePath() + "\\" + tableN);
				f.mkdir();
				path = f.getAbsolutePath();
			}

		} else

		{
		System.err.println("DATABASE ALREADY EXISTS");
		}
		return path;
	}

	
}
