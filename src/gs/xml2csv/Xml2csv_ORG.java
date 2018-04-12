
package gs.xml2csv;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.stream.*;


public class Xml2csv_ORG
{
	private static final File	DIR_ENTRADA		= new File("entrada"),
			DIR_SALIDA = new File("salida");
	private static final char	CVS_SEPARADOR	= new DecimalFormatSymbols().getPatternSeparator();

	public static void main(String args[]) throws IOException, XMLStreamException, FactoryConfigurationError
	{
		FilenameFilter fnf;
		XMLStreamReader xml;
		LinkedHashMap<String, Integer> columnas;
		Vector<Vector<String>> filas;
		String s;
		Integer i;
		Vector<String> v;
		FileWriter fw;
		Enumeration<String> en;

		DIR_ENTRADA.mkdir();
		DIR_SALIDA.mkdir();

		fnf = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name)
			{
				// return name.toLowerCase().endsWith(".xml");
				return name.toLowerCase().startsWith("vpos.log");
			}
		};
		for (File f : DIR_ENTRADA.listFiles(fnf))
		{
			// Procesa el XML
			System.out.println("##########################################################");
			System.out.println("Processing File Name =>" + f.getAbsolutePath());
			System.out.println("##########################################################");

			columnas = new LinkedHashMap<String, Integer>();
			filas = new Vector<Vector<String>>();
			xml = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(f));
			xml.toString().replace(";", "#");
			for (int ev = xml.next(); ev != XMLStreamReader.START_ELEMENT
					&& ev != XMLStreamReader.END_DOCUMENT; ev = xml.next())
				;
			if ( xml.getEventType() == XMLStreamReader.START_ELEMENT )
			{
				while (xml.nextTag() != XMLStreamReader.END_ELEMENT)
				{
					v = new Vector<String>();
					while (xml.nextTag() != XMLStreamReader.END_ELEMENT)
					{
						s = (xml.getAttributeCount() > 0 ? xml.getAttributeValue(0) : xml.getLocalName()).toUpperCase();
						i = columnas.get(s);

						if ( i == null )
						{
							i = columnas.size();
							columnas.put(s, i);
						}
						v.setSize(Math.max(i + 1, v.size()));					
						v.set(i, xml.getElementText());
					}
					filas.add(v);
				}
			}
			xml.close();

			String pattern = "_ddMMyyyy_hhmmss_";

			SimpleDateFormat format = new SimpleDateFormat(pattern);
			String dateTime = format.format(new Date());


			// Genera el archivo CVS
			fw = new FileWriter(new File(DIR_SALIDA,
					f.getName().substring(0, f.getName().lastIndexOf('.')) + dateTime + "ORG.csv"));
			en = new Vector<String>(columnas.keySet()).elements();
			while (en.hasMoreElements())
			{
				imprimir(fw, en.nextElement());
				if ( en.hasMoreElements() )
				{
					imprimir(fw, ",");
				}
			}
			imprimir(fw, "\r\n");
			for (Vector<String> vi : filas)
			{
				en = vi.elements();
				while (en.hasMoreElements())
				{
					imprimir(fw, en.nextElement());
					if ( en.hasMoreElements() )
					{
						imprimir(fw, ",");
					}
				}
				imprimir(fw, "\r\n");
			}
			fw.close();
		}
	}

	private static void imprimir(FileWriter fw, char datos) throws IOException
	{
		imprimir(fw, String.valueOf(datos));
	}

	private static void imprimir(FileWriter fw, String datos) throws IOException
	{
		System.out.print(datos);
		fw.append(datos);
	}
}
