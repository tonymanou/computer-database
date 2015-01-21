package com.tonymanou.computerdb.dto;

public class ComputerDTO {

  private static final boolean EQUALS_WITH_ID = false;
  private Long id;
  private String name;
  private String introducedDate;
  private String discontinuedDate;
  private String companyName;

  private ComputerDTO() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id_) {
    id = id_;
  }

  public String getName() {
    return name;
  }

  public void setName(String name_) {
    name = name_;
  }

  public String getIntroducedDate() {
    return introducedDate;
  }

  public void setIntroducedDate(String introducedDate_) {
    introducedDate = introducedDate_;
  }

  public String getDiscontinuedDate() {
    return discontinuedDate;
  }

  public void setDiscontinuedDate(String discontinuedDate_) {
    discontinuedDate = discontinuedDate_;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName_) {
    companyName = companyName_;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
    result = prime * result + ((discontinuedDate == null) ? 0 : discontinuedDate.hashCode());
    if (EQUALS_WITH_ID) {
      result = prime * result + ((id == null) ? 0 : id.hashCode());
    }
    result = prime * result + ((introducedDate == null) ? 0 : introducedDate.hashCode());
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
    ComputerDTO other = (ComputerDTO) obj;
    if (companyName == null) {
      if (other.companyName != null) {
        return false;
      }
    } else if (!companyName.equals(other.companyName)) {
      return false;
    }
    if (discontinuedDate == null) {
      if (other.discontinuedDate != null) {
        return false;
      }
    } else if (!discontinuedDate.equals(other.discontinuedDate)) {
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
    if (introducedDate == null) {
      if (other.introducedDate != null) {
        return false;
      }
    } else if (!introducedDate.equals(other.introducedDate)) {
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
    builder.append("ComputerDTO [id=");
    builder.append(id);
    builder.append(", name=");
    builder.append(name);
    if (introducedDate != null) {
      builder.append(", introducedDate=");
      builder.append(introducedDate);
    }
    if (discontinuedDate != null) {
      builder.append(", discontinuedDate=");
      builder.append(discontinuedDate);
    }
    if (companyName != null) {
      builder.append(", companyName=");
      builder.append(companyName);
    }
    builder.append("]");
    return builder.toString();
  }

  public static Builder getBuilder(String computerName) {
    return new Builder(computerName);
  }

  public static class Builder {

    private ComputerDTO computer;

    public Builder(String computerName) {
      computer = new ComputerDTO();
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

    public Builder setIntroduced(String pIntroduced) {
      computer.introducedDate = pIntroduced;
      return this;
    }

    public Builder setDiscontinued(String pDiscontinued) {
      computer.discontinuedDate = pDiscontinued;
      return this;
    }

    public Builder setCompany(String pCompanyName) {
      computer.companyName = pCompanyName;
      return this;
    }

    public ComputerDTO build() {
      return computer;
    }
  }
}
