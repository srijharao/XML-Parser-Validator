/**
 * StringTag class that records value and type of String Type.
 */
public class InfoTag extends Node {

  /**
   * Constructor assigns tagType and tagValue.
   *
   * @param tagValue the tag text
   */
  public InfoTag(String tagValue) {
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
    return getTagName();
  }

}
