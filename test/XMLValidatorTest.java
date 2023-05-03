import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for XMLValidator Class to check validity and function.
 */
public class XMLValidatorTest {

  XMLParser xmlValidator;

  @Before
  public void setUp() throws Exception {
    xmlValidator = new XMLValidator();
  }

  @Test
  public void testValidInput() throws InvalidXMLException {

    assertEquals(xmlValidator.output(), "Status:Empty");

    xmlValidator = xmlValidator.input('<')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>');
    assertEquals(xmlValidator.output(), "Status:Incomplete");

    xmlValidator = xmlValidator.input('<')
            .input('b')
            .input('o')
            .input('d')
            .input('y')
            .input('>');
    assertEquals(xmlValidator.output(), "Status:Incomplete");

    xmlValidator = xmlValidator
            .input('t')
            .input('h')
            .input('i')
            .input('s')
            .input(' ')
            .input('b')
            .input('o')
            .input('d')
            .input('y')
            .input('!');
    assertEquals(xmlValidator.output(), "Status:Incomplete");

    xmlValidator = xmlValidator.input('<')
            .input('/')
            .input('b')
            .input('o')
            .input('d')
            .input('y')
            .input('>');
    assertEquals(xmlValidator.output(), "Status:Incomplete");

    xmlValidator = xmlValidator.input('<')
            .input('/')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>');
    assertEquals(xmlValidator.output(), "Status:Valid");
  }

  @Test(expected = InvalidXMLException.class)
  public void testValidatorSingleRoot() throws InvalidXMLException {
    xmlValidator = xmlValidator.input('<')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>')

            .input('<')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>');
  }

  @Test(expected = InvalidXMLException.class)
  public void testValidatorEmptyTags() throws InvalidXMLException {
    xmlValidator = xmlValidator.input('<')
            .input('>');
  }

  @Test(expected = InvalidXMLException.class)
  public void testValidatorInvalidXMLNesting() throws InvalidXMLException {
    xmlValidator = xmlValidator.input('<')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>')

            .input('<')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('d')
            .input('i')
            .input('v')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('/')
            .input('d')
            .input('i')
            .input('v')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>');
  }

  @Test(expected = InvalidXMLException.class)
  public void testValidatorInvalidXMLNesting3() throws InvalidXMLException {
    xmlValidator = xmlValidator.input('<')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>')

            .input('<')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('0')
            .input('d')
            .input('i')
            .input('v')
            .input('>')

            .input('<')
            .input('/')
            .input('0')
            .input('d')
            .input('i')
            .input('v')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>');
  }

  @Test(expected = InvalidXMLException.class)
  public void testValidatorInvalidXMLNesting2() throws InvalidXMLException {
    xmlValidator = xmlValidator.input('<')
            .input('h')
            .input('t')
            .input(' ')
            .input('m')
            .input('l')
            .input('>')

            .input('<')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('d')
            .input('i')
            .input('v')
            .input('>')

            .input('<')
            .input('/')
            .input('d')
            .input('i')
            .input('v')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('t')
            .input(' ')
            .input('m')
            .input('l')
            .input('>');
  }

  @Test(expected = InvalidXMLException.class)
  public void testValidatorInvalidTagNames() throws InvalidXMLException {
    xmlValidator = xmlValidator.input('<')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>')

            .input('<')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('d')
            .input('i')
            .input('v')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('/')
            .input('d')
            .input('i')
            .input('v')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('<')
            .input('t')
            .input('m')
            .input('l')
            .input('>');
  }

  //  Exception not thrown for invalid XML: <html><head></hea>d><div><p></p><p></p></div></html>
  //Exception not thrown for invalid XML: <html><head><div></head><p></p><p></p></div></html>
  @Test(expected = InvalidXMLException.class)
  public void testValidatorInvalidNestedTags() throws InvalidXMLException {
    xmlValidator = xmlValidator.input('<')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>')

            .input('<')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('d')
            .input('i')
            .input('v')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('e')
            .input('a')
            .input('d')
            .input('>')

            .input('<')
            .input('p')
            .input('>')

            .input('<')
            .input('/')
            .input('p')
            .input('>')

            .input('<')
            .input('p')
            .input('>')

            .input('<')
            .input('/')
            .input('p')
            .input('>')

            .input('<')
            .input('/')
            .input('d')
            .input('i')
            .input('v')
            .input('>')

            .input('<')
            .input('/')
            .input('h')
            .input('<')
            .input('t')
            .input('m')
            .input('l')
            .input('>');
  }

  @Test(expected = InvalidXMLException.class)
  public void testInvalidInput() throws InvalidXMLException {
    assertEquals("Status:Empty", String.valueOf(xmlValidator.output()));
    xmlValidator = xmlValidator.input('<')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('<')
            .input('>');
  }

  @Test(expected = InvalidXMLException.class)
  public void testInvalidInput2() throws InvalidXMLException {
    assertEquals("Status:Empty", String.valueOf(xmlValidator.output()));
    xmlValidator = xmlValidator.input('<')
            .input('h')
            .input('t')
            .input('m')
            .input('l')
            .input('>')
            .input('l')
            .input('>');
  }


  @Test
  public void nodeClassClosedTag() throws InvalidXMLException {
    Node nd = new ClosedTag("Hhtml:-_0H");
    assertEquals("Hhtml:-_0H", nd.getTagName());
    assertEquals("closedTag", String.valueOf(nd.getTagType()));
  }

  @Test
  public void validInputInOpenTagDigitsCharacters() throws InvalidXMLException {
    Node nd = new OpenTag("html:-_0H");
    assertEquals("html:-_0H", nd.getTagName());
    assertEquals("openTag", String.valueOf(nd.getTagType()));
  }

  @Test
  public void nodeClassStringTag() throws InvalidXMLException {
    Node nd = new StringTag("html:-_0H");
    assertEquals("html:-_0H", nd.getTagName());
    assertEquals("stringType", String.valueOf(nd.getTagType()));
  }


  @Test(expected = InvalidXMLException.class)
  public void invalidInputInOpenTagHyphen() throws InvalidXMLException {
    Node nd = new OpenTag("-html:-_0H");
  }

  @Test(expected = InvalidXMLException.class)
  public void invalidInputInOpenTagDigit() throws InvalidXMLException {
    Node nd = new OpenTag("0html:-_0H");
  }

  @Test(expected = InvalidXMLException.class)
  public void invalidInputInCloseTagHyphen() throws InvalidXMLException {
    Node nd = new OpenTag("-html:-_0H");
  }

  @Test(expected = InvalidXMLException.class)
  public void invalidInputInCloseTagDigit() throws InvalidXMLException {
    Node nd = new OpenTag("9html:-_0H");
  }

  @Test(expected = InvalidXMLException.class)
  public void invalidInputInCloseTagSpace() throws InvalidXMLException {
    Node nd = new OpenTag("9html:-_ 0H");
  }
}