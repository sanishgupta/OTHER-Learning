
package com.lmg.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import com.lmg.vpos.logs.LogType;
import com.lmg.vpos.logs.ObjectFactory;
import com.lmg.vpos.logs.RecordType;

public class Test1
{
	public static String CSV_SEPARATOR = ",";
	public static void main(String[] args) throws IOException
	{

		try
			{
	
				
				
				File file = new File("C:\\vposLog\\vpos.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance("com.lmg.vpos.logs");
				
				ObjectFactory obj = new ObjectFactory();
				LogType lt = obj.createLogType();
				
				Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
				Object objN = jaxbUnmarshaller.unmarshal(file);
				//List<LogType> customer = (List<LogType>) jaxbUnmarshaller.unmarshal(file);
				
				JAXBElement<LogType> logType;
				
				if (objN instanceof JAXBElement)
				{
					//lt = (LogType)objN;
					logType = (JAXBElement<LogType>) objN;
					//System.out.println(logType);
					logType.getValue();
					//System.out.println(logType.getValue());
					lt = logType.getValue();
					
					String pattern = "ddMMyyyy_hhmmss";

					SimpleDateFormat format = new SimpleDateFormat(pattern);
					String dateTime = format.format(new Date());
					
					File out = new File("C:\\vposLog\\vposLogOp_" + dateTime+ ".csv");
					FileWriter fw = new FileWriter(out.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);

					bw.write("Date and Time"+ CSV_SEPARATOR + "MESSAGE" + "\n" );
					
					for(RecordType rcrdT : lt.getRecord())
					{
					/*	System.out.print(rcrdT.getDate());
						System.out.print(CSV_SEPARATOR);
						System.out.println(rcrdT.getMessage());*/
						String str ="";
						if(!rcrdT.getMessage().contains("responseCode"))
						{	
								str = rcrdT.getDate() + CSV_SEPARATOR + rcrdT.getMessage() + "\n" ;
								bw.write(str);
						}
						//System.out.print(str);
					}
					
					
					bw.close();

					System.out.println("Done");

				}
				
				//System.out.println(lt);

		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

	}

}
