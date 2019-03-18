package ro.quickorder.backend.converter;

import org.junit.Test;
import ro.quickorder.backend.model.Language;
import ro.quickorder.backend.model.UserAttribute;
import ro.quickorder.backend.model.dto.UserAttributeDto;

import static org.junit.Assert.*;

/**
 * Unit test for {@link UserAttributeConverter}
 *
 * @author R. Lupoaie
 */
public class UserAttributeConverterTest {

    private UserAttributeConverter userAttributeConverter = new UserAttributeConverter();

    @Test
    public void testConverterUserAttributeToDto() {
        UserAttribute userAttribute = new UserAttribute(Language.EN);

        UserAttributeDto userAttributeDto = userAttributeConverter.toUserAttributeDto(userAttribute);

        assertEquals(userAttribute.getLanguage(), userAttributeDto.getLanguage());
    }

    @Test
    public void testConverterUserAttributeToDtoWhenUserAttributeIsNull() {
        UserAttributeDto userAttributeDto = userAttributeConverter.toUserAttributeDto(null);

        assertNull(userAttributeDto);
    }

    @Test
    public void testConverterDtoToUserAttribute() {
        UserAttributeDto userAttributeDto = new UserAttributeDto(Language.EN);

        UserAttribute userAttribute = userAttributeConverter.toUserAttribute(userAttributeDto);

        assertEquals(userAttributeDto.getLanguage(), userAttribute.getLanguage());
        assertNull(userAttribute.getUser());
    }

    @Test
    public void testConverterDtoToUserAttributeWhenDtoIsNull() {
        UserAttribute userAttribute = userAttributeConverter.toUserAttribute(null);

        assertNull(userAttribute);
    }
}