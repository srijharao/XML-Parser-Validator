/**
 * abstract node.NodeClass works with strings in the stack and classifies them as open, closed or
 * string values. This class also handles the validity checks of XML tags
 */
public abstract class Node {

  protected TagType tagType;
  private String tagName;

  /**
   * Constructor for Node class.
   *
   * @param tagValue value of the tag in Node class
   */
  public Node(String tagValue) {
    // System.out.println(tagValue);
    this.tagName = tagValue;
  }

  /**
   * Type of xml value that was parsed.
   *
   * @return OpenTag, ClosedTag or StringTag
   */
  public TagType getTagType() {
    return tagType;
  }

  public String getTagName() {
    return this.tagName;
  }

}
