package com.tonymanou.computerdb.entity;

import java.time.LocalDateTime;

/**
 * Describe a computer.
 *
 * @author tonymanou
 */
public class Computer {

  private Long id;
  private String name;
  private LocalDateTime introduced;
  private LocalDateTime discontinued;
  private Company company;

  public Long getId() {
    return id;
  }

  public void setId(Long pId) {
    id = pId;
  }

  public String getName() {
    return name;
  }

  public void setName(String pName) {
    name = pName;
  }

  public LocalDateTime getIntroduced() {
    return introduced;
  }

  public void setIntroduced(LocalDateTime pIntroduced) {
    introduced = pIntroduced;
  }

  public LocalDateTime getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(LocalDateTime pDiscontinued) {
    discontinued = pDiscontinued;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company pCompany) {
    company = pCompany;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((company == null) ? 0 : company.hashCode());
    result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Computer other = (Computer) obj;
    if (company == null) {
      if (other.company != null) {
        return false;
      }
    } else if (!company.equals(other.company)) {
      return false;
    }
    if (discontinued == null) {
      if (other.discontinued != null) {
        return false;
      }
    } else if (!discontinued.equals(other.discontinued)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (introduced == null) {
      if (other.introduced != null) {
        return false;
      }
    } else if (!introduced.equals(other.introduced)) {
      return false;
    }
    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Computer [id=");
    builder.append(id);
    builder.append(", name=");
    builder.append(name);
    if (introduced != null) {
      builder.append(", introduced=");
      builder.append(introduced);
    }
    if (discontinued != null) {
      builder.append(", discontinued=");
      builder.append(discontinued);
    }
    if (company != null) {
      builder.append(", company=");
      builder.append(company);
    }
    builder.append("]");
    return builder.toString();
  }
}
