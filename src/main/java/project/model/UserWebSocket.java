package project.model;

import java.security.Principal;

public final class UserWebSocket implements Principal {

  private final String name;

  public UserWebSocket(String name) {
      this.name = name;
  }

  @Override
  public String getName() {
      return name;
  }
}
