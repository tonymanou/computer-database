package com.tonymanou.computerdb.dto;

public class CompanyDTO {

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

  public void copy(CompanyDTO companyDTO) {
    if (companyDTO == null) {
      id = null;
      name = null;
    } else {
      id = companyDTO.id;
      name = companyDTO.name;
    }
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
