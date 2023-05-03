import java.util.Stack;

/**
 * This class acts as a validator of XML, reporting whether the characters given to it collectively
 * form valid XML.
 */
public abstract class AbstractXMLParser implements XMLParser {

  protected Status status;
  protected Boolean isConsistent;
  protected String rootNodeValue;
  protected Stack<Character> charStack;
  protected Stack<Node> nodeStack;

  protected Boolean rootClosed;

  /**
   * Constructor that initializes status and stacks.
   */
  public AbstractXMLParser() {
    charStack = new Stack<>();
    nodeStack = new Stack<>();
    status = Status.EMPTY;
    isConsistent = true;
    rootNodeValue = "";
    rootClosed = false;
  }

  /**
   * Accept a single character as input, and return the new parser as a result of handling this
   * character.
   *
   * @param c the input character
   * @return the parser after handling the provided character
   * @throws InvalidXMLException if the input causes the XML to be invalid
   */
  @Override
  public XMLParser input(char c) throws InvalidXMLException {

    StringBuilder sb = new StringBuilder();

    if (rootClosed) {
      throw new InvalidXMLException("invalid XML - not consistent");
    }

    if (!isConsistent) {
      throw new InvalidXMLException("invalid XML - not consistent");
    }

    if (status == Status.EMPTY) {
      status = Status.INCOMPLETE;
    }

    if (status == Status.VALID) {
      return this;
    }

    if (c != '>') {
      validateTagOrder(c);
    }

    if (c != '>' && c != '<') {
      if (containsAngularOpen(charStack)) {
        if ((c >= 'a' && c <= 'z')
                || (c >= 'A' && c <= 'Z')
                || (c >= '0' && c <= '9')
                || c == ':' || c == '-' || c == '_' || c == '/') {
          // nop
        } else {
          isConsistent = false;
          throw new InvalidXMLException("invalid XML - not consistent");
        }

        if (((c >= '0' && c <= '9') || c == '-') && charStack.size() == 1) {
          isConsistent = false;
          throw new InvalidXMLException("invalid XML - not consistent");
        }
      }
      charStack.push(c);
    } else if (c == '<') {
      if (charStack.isEmpty()) {
        charStack.push(c);
      } else {
        if (containsAngularOpen(charStack)) {
          isConsistent = false;
          throw new InvalidXMLException("invalid XML - not consistent");
        }
        // Empty the stack until the stack is empty. If we run into another
        // open angular brace, we throw an exception
        while (!charStack.isEmpty()) {
          if (charStack.peek() == '<') {
            isConsistent = false;
            throw new InvalidXMLException("invalid XML - not consistent");
          }
          sb.append(charStack.pop());
        }
        // reverse the string buffer and create a string node and push it to the node stack
        sb.reverse();
        nodeStack.push(new StringTag(sb.toString()));
        charStack.push(c);
      }
    } else {
      // if c == '>'
      if (!containsAngularOpen(charStack)) {
        isConsistent = false;
        throw new InvalidXMLException("invalid XML - not consistent");
      }
      boolean isOpen = charStack.isEmpty() || !containsSlash(charStack);
      while (!charStack.isEmpty()) {
        if (charStack.peek() == '<') {
          charStack.pop();
          if (charStack.isEmpty()) {
            // if angular open was the last element on the stack
            sb.reverse();
            if (isOpen) {
              // then push open if isOpen is true
              if (isTagSameAsRootTag(sb.toString())) {
                isConsistent = false;
                throw new InvalidXMLException("invalid XML - not consistent");
              }
              nodeStack.push(new OpenTag(sb.toString()));
            } else {
              if (!containsTag(sb.toString())) {
                isConsistent = false;
                throw new InvalidXMLException("invalid XML - not consistent");
              }
              if (checkIfCanBeInserted(sb.toString())) {
                // push closed if isOpen is false
                if (sb.toString().equalsIgnoreCase(rootNodeValue)) {
                  rootClosed = true;
                }
                nodeStack.push(new ClosedTag(sb.toString()));
              } else {
                isConsistent = false;
                throw new InvalidXMLException("invalid XML - not consistent");
              }
            }
          } else {
            // if angular open was not the last element on the stack,
            // then the charStack is inconsistent
            isConsistent = false;
            throw new InvalidXMLException("invalid XML - not consistent");
          }
        } else {
          if (charStack.peek() == '/') {
            // pass if the char in the stack is '/'
            charStack.pop();
            continue;
          }
          // as long as there is no '<' in the stack, keep appending to the StringBuffer
          sb.append(charStack.pop());
        }
      }
    }

    if (charStack.isEmpty() && nodeStack.size() == 1) {
      // Setting the value of root node, helps us ensure the root tag occurs only once.
      rootNodeValue = nodeStack.peek().getTagName();
    }
    checkAndSetValidStatus();
    return this;
  }

  private void validateTagOrder(char c) throws InvalidXMLException {
    Stack<Node> tempDestNodeStack = (Stack<Node>) nodeStack.clone();
    Stack<Node> tempSrcNodeStack = new Stack<>();
    Stack<Character> tempCharStack = (Stack<Character>) charStack.clone();
    tempCharStack.push(c);
    if (!containsSlash(tempCharStack)) {
      return;
    }

    StringBuffer sb = new StringBuffer();
    while (tempCharStack.peek() != '/') {
      sb.append(tempCharStack.pop());
    }
    sb.reverse();

    while (!tempDestNodeStack.isEmpty()) {
      tempSrcNodeStack.push(tempDestNodeStack.pop());
    }
    while (!tempSrcNodeStack.isEmpty()) {
      Node node = tempSrcNodeStack.pop();
      if (node.getTagType() != TagType.CLOSED) {
        tempDestNodeStack.push(node);
      } else {
        while (!tempDestNodeStack.isEmpty()
                && tempDestNodeStack.peek().getTagType() != TagType.OPEN) {
          tempDestNodeStack.pop();
        }
        if (tempDestNodeStack.peek().getTagName().equalsIgnoreCase(node.getTagName())) {
          tempDestNodeStack.pop();
          continue;
        } else {
          isConsistent = false;
          throw new InvalidXMLException("invalid XML - not consistent");
        }
      }
    }

    while (!tempDestNodeStack.isEmpty() && tempDestNodeStack.peek().getTagType() != TagType.OPEN) {
      tempDestNodeStack.pop();
    }

    if (!tempDestNodeStack.peek().getTagName().startsWith(sb.toString())) {
      isConsistent = false;
      throw new InvalidXMLException("invalid XML - not consistent");
    }
  }

  private boolean checkIfCanBeInserted(String tagName) throws InvalidXMLException {
    Stack<Node> tempDestNodeStack = (Stack<Node>) nodeStack.clone();
    Stack<Node> tempSrcNodeStack = new Stack<>();
    tempDestNodeStack.push(new ClosedTag(tagName));

    while (!tempDestNodeStack.isEmpty()) {
      tempSrcNodeStack.push(tempDestNodeStack.pop());
    }
    while (!tempSrcNodeStack.isEmpty()) {
      Node node = tempSrcNodeStack.pop();
      if (node.getTagType() != TagType.CLOSED) {
        tempDestNodeStack.push(node);
      } else {
        while (!tempDestNodeStack.isEmpty()
                && tempDestNodeStack.peek().getTagType() != TagType.OPEN) {
          tempDestNodeStack.pop();
        }
        if (tempDestNodeStack.peek().getTagName().equalsIgnoreCase(node.getTagName())) {
          tempDestNodeStack.pop();
          continue;
        } else {
          isConsistent = false;
          throw new InvalidXMLException("invalid XML - not consistent");
        }
      }
    }
    return true;
  }

  private boolean containsTag(String tagName) {
    Stack<Node> tempNodeStack = (Stack<Node>) nodeStack.clone();
    while (!tempNodeStack.isEmpty()) {
      if (tempNodeStack.pop().getTagName().equalsIgnoreCase(tagName)) {
        return true;
      }
    }
    return false;
  }

  private boolean containsAngularOpen(Stack<Character> charStack) {
    Stack<Character> tempCharStack = (Stack<Character>) charStack.clone();
    while (!tempCharStack.isEmpty()) {
      if (tempCharStack.pop() == '<') {
        return true;
      }
    }
    return false;
  }

  private boolean containsAngularClose(Stack<Character> charStack) {
    Stack<Character> tempCharStack = (Stack<Character>) charStack.clone();
    while (!tempCharStack.isEmpty()) {
      if (tempCharStack.pop() == '>') {
        return true;
      }
    }
    return false;
  }

  private boolean containsSlash(Stack<Character> charStack) {
    Stack<Character> tempCharStack = (Stack<Character>) charStack.clone();
    while (!tempCharStack.isEmpty()) {
      if (tempCharStack.pop() == '/') {
        return true;
      }
    }
    return false;
  }

  private boolean isTagSameAsRootTag(String tagValue) {
    if (rootNodeValue != null) {
      return rootNodeValue.equalsIgnoreCase(tagValue);
    }
    return false;
  }

  /**
   * Checks whether inputs provided so far form a valid XML. all tag names are valid (character
   * validations) each start tag has a corresponding end tag tags are properly nested (tags can have
   * string or sub tags under them) root tag occurs only once
   *
   * @return
   */
  private void checkAndSetValidStatus() throws InvalidXMLException {
    //all tag names are valid
    //start tag has corresponding end tag
    //tags are properly nested
    //root tag occurs only once:
    //rootNodeValue received a close tag and xml continues to progress,
    //fail on next character after rootNodeValue is closed
    Stack<Node> tempNodeStack = new Stack<>();
    Stack<Node> currNodeStack = (Stack<Node>) nodeStack.clone();
    if (charStack.isEmpty() && currNodeStack.size() > 0 && isTagSameAsRootTag(
            currNodeStack.peek().getTagName())
            && currNodeStack.peek().getTagType() == TagType.CLOSED) {
      while (!currNodeStack.isEmpty()) {
        tempNodeStack.push(currNodeStack.pop());
      }
      while (!tempNodeStack.isEmpty()) {
        Node node = tempNodeStack.pop();
        if (node.getTagType() == TagType.OPEN) {
          currNodeStack.push(node);
        } else if (node.getTagType() == TagType.CLOSED) {
          if (currNodeStack.peek().getTagType() == TagType.OPEN && currNodeStack.peek().getTagName()
                  .equalsIgnoreCase(node.getTagName())) {
            currNodeStack.pop();
          } else {
            isConsistent = false;
            throw new InvalidXMLException("invalid XML - not consistent");
          }
        }
      }
      if (currNodeStack.isEmpty() && tempNodeStack.isEmpty()) {
        status = Status.VALID;
      } else {
        isConsistent = false;
        throw new InvalidXMLException("invalid XML - not consistent");
      }
    }
  }

}