package com.tonymanou.computerdb.dto;

public class CompanyDTO {

  private static final boolean EQUALS_WITH_ID = false;
  private Long id;
  private String name;

  private CompanyDTO() {
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    if (EQUALS_WITH_ID) {
      result = prime * result + ((id == null) ? 0 : id.hashCode());
    }
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
    CompanyDTO other = (CompanyDTO) obj;
    if (EQUALS_WITH_ID) {
      if (id == null) {
        if (other.id != null) {
          return false;
        }
      } else if (!id.equals(other.id)) {
        return false;
      }
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
    builder.append("Company [id=");
    builder.append(id);
    builder.append(", name=");
    builder.append(name);
    builder.append("]");
    return builder.toString();
  }

  public static Builder getBuilder(String companyName) {
    return new Builder(companyName);
  }

  public static class Builder {

    private CompanyDTO company;

    public Builder(String pName) {
      company = new CompanyDTO();
      company.name = pName;
    }

    public Builder setId(Long pId) {
      company.id = pId;
      return this;
    }

    public Builder setName(String pName) {
      company.name = pName;
      return this;
    }

    public CompanyDTO build() {
      return company;
    }
  }
}
