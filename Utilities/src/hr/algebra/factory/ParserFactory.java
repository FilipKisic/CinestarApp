package hr.algebra.factory;

import java.io.InputStream;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author filip
 */
public class ParserFactory {
    
    public static XMLEventReader createStaxParser(InputStream inputStream) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLEventReader reader = factory.createXMLEventReader(inputStream);
        return reader;
    }
    
}
