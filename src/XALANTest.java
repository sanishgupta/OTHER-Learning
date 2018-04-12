import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

//  w  ww.  j a  va2 s .c o m
public class XALANTest {

	public static void main(String args[]) throws Exception {
		//StreamSource source = new StreamSource(args[0]);
		//StreamSource stylesource = new StreamSource(args[1]);

		StreamSource source = new StreamSource("xml/phonebook.xml");
		StreamSource stylesource = new StreamSource("xsl/phonebook.xsl");
		
		TransformerFactory factory = TransformerFactory.newInstance();
		Transformer transformer = factory.newTransformer(stylesource);

		StreamResult result = new StreamResult(System.out);
		transformer.transform(source, result);
	}
}