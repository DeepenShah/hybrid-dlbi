package common;

/**
 * Consists of list of user types in Idiom
 * 
 * @author Vikram Hegde
 *
 */
public enum UserTypeEnum {

  APP_USER("appUser"), CLIENT_ADMIN("clientAdmin"), USER_ADMIN("userAdmin"), DISBALED_USER("disabledUser");

  private final String attribute;

  /**
   * @param attribute
   */
  private UserTypeEnum(final String attribute) {
    this.attribute = attribute;
  }

  /**
   * Returns the value of workflowtype
   * 
   * @return
   */
  public String getAttribute() {
    return this.attribute;
  }

}
