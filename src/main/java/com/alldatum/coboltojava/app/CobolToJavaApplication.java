package com.alldatum.coboltojava.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

import javax.naming.directory.AttributeInUseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alldatum.coboltojava.app.pojo.Attribute;
import com.alldatum.coboltojava.app.pojo.FileCBL;
import com.alldatum.coboltojava.app.pojo.ValuesAttribute;
import com.alldatum.coboltojava.app.services.IFileCBLImpl;

class Variables{
	 static int bait=0;
	 static int vcampos=0;
	 static int comp3=0;
}

@SpringBootApplication
public class CobolToJavaApplication implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(CobolToJavaApplication.class);
	
	@Autowired
	private IFileCBLImpl iFileCBLImpl;
	
	public static void main(String[] args) {
		SpringApplication.run(CobolToJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String hola="hola   hola";
		 Variables.bait=7;
		 InputStream file = new FileInputStream("C:\\Users\\Alldatum Business\\Downloads\\CATEGOPL.CBL");
		 InputStream fileDat = new FileInputStream("C:\\Users\\Alldatum Business\\Downloads\\catego1.dat");
		 FileCBL cblPoliza = new FileCBL();
		 cblPoliza.setAttributes(iFileCBLImpl.attributes(file));
		 List<Attribute> attributes = cblPoliza.getAttributes();
		 HashMap<String, ValuesAttribute> mapValuesDAT = iFileCBLImpl.mapKeysCBL(attributes);
		 List<String> values = iFileCBLImpl.values(fileDat);
		 
		 attributes.forEach(attri -> {
			 System.out.println(attri.getName().concat(" ").concat(String.valueOf(attri.getBytes())).concat(" ").concat(String.valueOf(attri.getBytesDecimal())));
		 });
		 
		 
		 /*
		// int cadenaLength = 0;
		 values.forEach(cadena -> {
			 int cadenaLength = 4;
			while(cadenaLength > 0) {
				 attributes.forEach(attribute -> {
//					 System.out.println(attribute.getName());
					 mapValuesDAT.entrySet().forEach(campoKey -> {
//						 System.out.println(attribute.getName());
						 String value = "";
						 switch(attribute.getDataType()) {
						 case String:
//							 System.out.println(attribute.getName() + "String");
							if(campoKey.getKey() == attribute.getName()) {
								value = iFileCBLImpl.extractString(cadena, attribute.getBytes(), Variables.bait, false, Variables.vcampos);
								campoKey.getValue().addValue(value);
								//valueLength = value.length();  
							}
							break;
						 case Integer:
							if(campoKey.getKey() == attribute.getName()) {
								try {
									value = String.valueOf(iFileCBLImpl.stringComp3(cadena, attribute.getBytes(), Variables.bait));
									campoKey.getValue().addValue(value);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								//valueLength = value.length();  
							}
							break;
						 case Double:
								if(campoKey.getKey() == attribute.getName()) {
									try {
										value = String.valueOf(iFileCBLImpl.comp3decimal(cadena, attribute.getBytes(), 2, Variables.bait));
										campoKey.getValue().addValue(value);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									//valueLength = value.length();  
								}
							break;
						 case List:
							 if(campoKey.getKey() == attribute.getName()) {
								 try {
									 value = iFileCBLImpl.extractString(cadena, attribute.getBytes(), Variables.bait, true, Variables.vcampos);
									 campoKey.getValue().addValue(value);
								 } catch (Exception e) {
									 // TODO Auto-generated catch block
									 e.printStackTrace();
								 }
								 //valueLength = value.length();  
							 }
							 break;
						 }					 
					 });
				 });
				 cadenaLength--;
			 }
		 });
		 */
		 for(String campoKey: mapValuesDAT.keySet()) {
//			 if(campoKey.equals("CATEGOPL-RAMSUBRAMO")) {
				 mapValuesDAT.get(campoKey).getValues().forEach(value ->{
					 System.out.println(campoKey + " ----- " + value);
				 });				 
//			 }
		 }
	}

}
