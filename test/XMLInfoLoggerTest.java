import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for XMLInfoLogger Class to check validity and function.
 */
public class XMLInfoLoggerTest {

  XMLParser xmlInfoLogger;

  @Before
  public void setUp() throws Exception {
    xmlInfoLogger = new XMLInfoLogger();
  }

  @Test
  public void testValidOutput() throws InvalidXMLException {

    xmlInfoLogger = xmlInfoLogger.input('<')
        .input('h')
        .input('t')
        .input('m')
        .input('l')
        .input('>');
    assertEquals(xmlInfoLogger.output(), "Started:html\n");

    xmlInfoLogger = xmlInfoLogger.input('<')
        .input('b')
        .input('o')
        .input('d')
        .input('y')
        .input('>');
    assertEquals(xmlInfoLogger.output(), "Started:html\n" + "Started:body\n");

    xmlInfoLogger = xmlInfoLogger
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
    assertEquals(xmlInfoLogger.output(), "Started:html\n" + "Started:body\n");

    xmlInfoLogger = xmlInfoLogger.input('<')
        .input('/')
        .input('b')
        .input('o')
        .input('d')
        .input('y')
        .input('>');
    assertEquals(xmlInfoLogger.output(), "Started:html\n"
        + "Started:body\n"
        + "Characters:this body!\n"
        + "Ended:body\n");

    xmlInfoLogger = xmlInfoLogger.input('<')
        .input('/')
        .input('h')
        .input('t')
        .input('m')
        .input('l')
        .input('>');
    assertEquals(xmlInfoLogger.output(), "Started:html\n"
        + "Started:body\n"
        + "Characters:this body!\n"
        + "Ended:body\n"
        + "Ended:html\n");
  }

  @Test(expected = InvalidXMLException.class)
  public void testLoggerInvalidXMLNesting() throws InvalidXMLException {
    xmlInfoLogger = xmlInfoLogger.input('<')
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
  public void testLoggerInvalidXMLNesting2() throws InvalidXMLException {
    xmlInfoLogger = xmlInfoLogger.input('<')
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
        .input('t')
        .input('m')
        .input('l')
        .input('>');
  }

  @Test(expected = InvalidXMLException.class)
  public void testLoggerInvalidTagNames() throws InvalidXMLException {
    xmlInfoLogger = xmlInfoLogger.input('<')
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

  //  Exception not thrown for invalid XML: <html><head><div></head><p></p><p></p></div></html>

  @Test(expected = InvalidXMLException.class)
  public void testLoggerInvalidNestedTags() throws InvalidXMLException {
    xmlInfoLogger = xmlInfoLogger.input('<')
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
    System.out.println(xmlInfoLogger.output());
    xmlInfoLogger = xmlInfoLogger.input('<')
        .input('h')
        .input('t')
        .input('m')
        .input('l')
        .input('<')
        .input('>');
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
}