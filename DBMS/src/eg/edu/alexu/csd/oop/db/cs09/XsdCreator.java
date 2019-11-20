package eg.edu.alexu.csd.oop.db.cs09;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.wiztools.xsdgen.ParseException;
import org.wiztools.xsdgen.XsdGen;

public class XsdCreator {
	public void create(String path) {
		XsdGen gen = new XsdGen();
		try {
			gen.parse(new File(path));
			String xsdPath = path.substring(0, path.length()-3)+"xsd";
			File out = new File(xsdPath);
			FileOutputStream fo = new FileOutputStream(out);
			gen.write(fo);
			fo.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	public void delete(String path) {
        File file = new File(path);
        XsdGen x = new XsdGen();

      if(file.delete())
        {
            //System.out.println("File deleted successfully");
        }
        else
        {
            //System.out.println("Failed to delete the file");
        }
	}
}
