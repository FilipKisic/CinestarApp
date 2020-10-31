package hr.algebra.model;

import hr.algebra.model.PersonAdapter.AdaptedPerson;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author filip
 */
public class PersonAdapter extends XmlAdapter<AdaptedPerson, Person> {

    @Override
    public Person unmarshal(AdaptedPerson adaptedPerson) throws Exception {
        if (adaptedPerson == null) {
            return null;
        }
        if (adaptedPerson.type == "actor") {
            return new Actor(adaptedPerson.id, adaptedPerson.firstname, adaptedPerson.lastname);
        } else {
            return new Director(adaptedPerson.id, adaptedPerson.firstname, adaptedPerson.lastname);
        }
    }

    @Override
    public AdaptedPerson marshal(Person person) throws Exception {
        if (person == null) {
            return null;
        }
        AdaptedPerson adaptedPerson = new AdaptedPerson();
        adaptedPerson.id = person.getId();
        adaptedPerson.firstname = person.getFirstName();
        adaptedPerson.lastname = person.getLastName();
        adaptedPerson.type = person instanceof Actor ? "actor" : "director";
        return adaptedPerson;
    }

    public static class AdaptedPerson {

        @XmlAttribute
        public int id;

        @XmlElement
        public String firstname;

        @XmlElement
        public String lastname;

        public String type;
    }

}
