/**
 * Handles open tag value and marks its type as open.
 */
public class OpenTag extends Node {

  /**
   * Constructor checks whether the tag is valid.
   *
   * @param tagValue the closed tag text
   * @throws InvalidXMLException thrown when tag value is invalid
   */
  public OpenTag(String tagValue) throws InvalidXMLException {
    super(tagValue);
    if ((!"".equalsIgnoreCase(tagValue) && (Character.isDigit(tagValue.charAt(0))
        || tagValue.charAt(0) == '-')) || !tagValue.matches("^[-a-zA-Z0-9_:]*$")) {
      throw new InvalidXMLException("Invalid start for open tag: " + tagValue);
    }

    this.tagType = TagType.OPEN;
  }

  /**
   * Method to print status type.
   *
   * @return Started tagName
   */
  @Override
  public String toString() {
    return "Started:" + getTagName() + '\n';
  }
}
