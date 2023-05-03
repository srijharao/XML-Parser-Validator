/**
 * Status enum to output per xml validity. is one of - Valid, Incomplete, Empty
 */
public enum Status {
  VALID("Status:Valid"), INCOMPLETE("Status:Incomplete"), EMPTY("Status:Empty");
  private final String text;

  Status(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
