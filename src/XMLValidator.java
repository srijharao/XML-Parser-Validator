/**
 * XMLValidator class to validate and output status of XML.
 */
public class XMLValidator extends AbstractXMLParser {

  /**
   * Provide the output of the parser, given all the inputs it has been provided so far. The content
   * and format of this output is defined by individual implementations.
   *
   * @return the output of the parser as a String object
   */
  @Override
  public String output() {
    return String.valueOf(status);
  }

  @Override
  public String toString() {
    return "Initializing XMLValidator \n";
  }
}
