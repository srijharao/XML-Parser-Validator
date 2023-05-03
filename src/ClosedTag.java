/**
 * ClosedTag class that records value and type of closed tag.
 */
public class ClosedTag extends Node {
  /**
   * Constructor checks whether the tag is valid.
   *
   * @param tagValue the closed tag text
   * @throws InvalidXMLException thrown when tag value is invalid
   */
  public ClosedTag(String tagValue) throws InvalidXMLException {
    super(tagValue);
    if ((!"".equalsIgnoreCase(tagValue) && (Character.isDigit(tagValue.charAt(0))
        || tagValue.charAt(0) == '-')) || !tagValue.matches("^[-a-zA-Z0-9_:]*$")) {
      throw new InvalidXMLException("Invalid start for closed tag: " + tagValue);
    }
    this.tagType = TagType.CLOSED;
  }

  /**
   * Method to print status type.
   *
   * @return Ended tagName
   */
  @Override
  public String toString() {
    return "Ended:" + getTagName() + '\n';
  }

}
