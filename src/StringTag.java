/**
 * StringTag class that records value and type of String Type.
 */
public class StringTag extends Node {

  public StringTag(String tagValue) {
    super(tagValue);
    this.tagType = TagType.STRING_TYPE;
  }

  /**
   * Method to print status type.
   *
   * @return Characters tagName
   */
  @Override
  public String toString() {
    return "Characters:" + getTagName() + '\n';
  }

}
