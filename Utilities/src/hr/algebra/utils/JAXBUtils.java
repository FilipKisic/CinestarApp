package hr.algebra.utils;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author filip
 */
public class JAXBUtils {

    public static void save(Object object, String filename) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(object, new File(filename));
    }

    public static Object load(Object object, String filename) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return unmarshaller.unmarshal(new File(filename));
    }
}
