import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * Logger class to log the tags and contents of XML.
 */
public class XMLInfoLogger extends AbstractXMLParser {

  /**
   * Provide the output of the parser, given all the inputs it has been provided so far. The content
   * and format of this output is defined by individual implementations.
   *
   * @return the output of the parser as a String object
   */
  @Override
  public String output() {
    Stack<Node> tempDestStack = (Stack<Node>) nodeStack.clone();
    Stack<Node> tempSrcStack = new Stack<>();

    while (!tempDestStack.isEmpty()) {
      tempSrcStack.push(tempDestStack.pop());
    }

    while (!tempSrcStack.isEmpty()) {
      Node node = tempSrcStack.pop();
      if (tempDestStack.isEmpty()) {
        tempDestStack.push(node);
        continue;
      }
      if (node.getTagType() != TagType.CLOSED) {
        tempDestStack.push(node);
      } else {
        ArrayList<String> arrayList = new ArrayList<>();
        while (!tempDestStack.isEmpty()) {
          Node curr = tempDestStack.pop();
          if (curr.getTagType() == TagType.OPEN) {
            if (curr.getTagName().equalsIgnoreCase(node.getTagName())) {
              Collections.reverse(arrayList);
              String temp = String.join("", arrayList);
              tempDestStack.push(new InfoTag(curr + temp + node));
              break;
            } else {
              return "";
            }
          } else {
            // curr is of tag type string
            arrayList.add(curr.toString());
          }
        }
      }
    }

    ArrayList<String> ret = new ArrayList<>();
    while (!tempDestStack.isEmpty()) {
      ret.add(tempDestStack.pop().toString());
    }
    Collections.reverse(ret);
    String retString = String.join("", ret);
    return retString;
  }
}
