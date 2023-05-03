/**
 * TagType enum to record whether XML data is a open or closed tag or is a String between tags.
 */
public enum TagType {
  OPEN("openTag"), CLOSED("closedTag"), STRING_TYPE("stringType");
  private final String text;

  TagType(String text) {
    this.text = text;
  }

  /**
   * Method to print status type.
   *
   * @return
   */
  @Override
  public String toString() {
    return text;
  }
}
