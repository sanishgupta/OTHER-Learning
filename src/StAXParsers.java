import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
/*from w  w w.  ja  v a 2  s . c om*/
import javax.xml.XMLConstants;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class StAXParsers {

  public static void main(String[] args) throws Exception {

    SchemaFactory sf = SchemaFactory
        .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    System.out.println("schema factory instance obtained is " + sf);

    Schema schema = sf.newSchema(new File(args[0]));
    System.out.println("schema obtained is = " + schema);
    Validator validator = schema.newValidator();

    String fileName = args[1].toString();
    String fileName2 = args[2].toString();
    javax.xml.transform.Result xmlResult = new javax.xml.transform.stax.StAXResult(
        XMLOutputFactory.newInstance().createXMLStreamWriter(
            new FileWriter(fileName2)));
    javax.xml.transform.Source xmlSource = new javax.xml.transform.stax.StAXSource(
        getXMLEventReader(fileName));
    validator.validate(new StreamSource(args[1]));
    validator.validate(xmlSource, xmlResult);

  }

  private static XMLEventReader getXMLEventReader(String filename)
      throws Exception {
    XMLInputFactory xmlif = null;
    XMLEventReader xmlr = null;
    xmlif = XMLInputFactory.newInstance();
    xmlif.setProperty(XMLInputFactory.IS_REPLACING_ENTITY_REFERENCES,
        Boolean.TRUE);
    xmlif.setProperty(XMLInputFactory.IS_SUPPORTING_EXTERNAL_ENTITIES,
        Boolean.FALSE);
    xmlif.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, Boolean.TRUE);
    xmlif.setProperty(XMLInputFactory.IS_COALESCING, Boolean.TRUE);

    FileInputStream fis = new FileInputStream(filename);
    xmlr = xmlif.createXMLEventReader(filename, fis);

    return xmlr;
  }

}