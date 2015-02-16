package com.tonymanou.computerdb.dao;

import com.tonymanou.computerdb.model.User;

public interface IUserDAO {

  /**
   * Retrieve an user from the database thanks to his name.
   *
   * @param name
   *          The name of the user to retrieve.
   * @return The {@link User}, or null if no matching user was found.
   */
  User findByName(String name);
}
