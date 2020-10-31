package hr.algebra.parsers.rss;

import java.util.Optional;

/**
 *
 * @author filip
 */
public enum TagType {
    ITEM("item"),
    TITLE("title"),
    PUB_DATE("pubDate"),
    DESCRIPTION("description"),
    ORIG_TITLE("orignaziv"),
    DIRECTOR("redatelj"),
    ACTORS("glumci"),
    DURATION("trajanje"),
    GENRE("zanr"),
    IMAGE_URL("plakat"),
    LINK("link"),
    START_DATE("pocetak");

    private final String name;

    private TagType(String name) {
        this.name = name;
    }

    public static Optional<TagType> parseFromString(String name) {
        for (TagType type : values()) {
            if (type.name.equals(name)) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}
