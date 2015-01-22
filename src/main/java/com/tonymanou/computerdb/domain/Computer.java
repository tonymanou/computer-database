package com.tonymanou.computerdb.domain;

import java.time.LocalDate;

/**
 * Describe a computer.
 *
 * @author tonymanou
 */
public class Computer {

  private static final boolean EQUALS_WITH_ID = false;
  private Long id;
  private String name;
  private LocalDate introduced;
  private LocalDate discontinued;
  private Company company;

  private Computer() {
  }

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

  public LocalDate getIntroduced() {
    return introduced;
  }

  public void setIntroduced(LocalDate pIntroduced) {
    introduced = pIntroduced;
  }

  public LocalDate getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(LocalDate pDiscontinued) {
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
    if (EQUALS_WITH_ID) {
      result = prime * result + ((id == null) ? 0 : id.hashCode());
    }
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
    if (EQUALS_WITH_ID) {
      if (id == null) {
        if (other.id != null) {
          return false;
        }
      } else if (!id.equals(other.id)) {
        return false;
      }
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
      builder.append(introduced.toString());
    }
    if (discontinued != null) {
      builder.append(", discontinued=");
      builder.append(discontinued.toString());
    }
    if (company != null) {
      builder.append(", company=");
      builder.append(company);
    }
    builder.append("]");
    return builder.toString();
  }

  public static Builder getBuilder(String computerName) {
    return new Builder(computerName);
  }

  public static class Builder {

    private Computer computer;

    public Builder(String computerName) {
      computer = new Computer();
      computer.name = computerName;
    }

    public Builder setName(String pName) {
      computer.name = pName;
      return this;
    }

    public Builder setId(Long pId) {
      computer.id = pId;
      return this;
    }

    public Builder setIntroduced(LocalDate pIntroduced) {
      computer.introduced = pIntroduced;
      return this;
    }

    public Builder setDiscontinued(LocalDate pDiscontinued) {
      computer.discontinued = pDiscontinued;
      return this;
    }

    public Builder setCompany(Company pCompany) {
      computer.company = pCompany;
      return this;
    }

    public Computer build() {
      return computer;
    }
  }
}
