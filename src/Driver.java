import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Driver {

	
	//URL # https://introjava.wordpress.com/category/xml-xsl-java/
    public static void main(String[] args)
    {
        try
        {
            TransformerFactory tFactory = TransformerFactory.newInstance();

            Source xslDoc = new StreamSource("xsl/catalog.xsl");
            Source xmlDoc = new StreamSource("xml/catalog.xml");

            String outputFileName = "xmlToHtml/catalog.html";
            OutputStream htmlFile = new FileOutputStream(outputFileName);

            Transformer transformer = tFactory.newTransformer(xslDoc);
            transformer.transform(xmlDoc, new StreamResult(htmlFile));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}