package com.tonymanou.computerdb.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.tonymanou.computerdb.util.Util;

public class ComputerDTO {

  @DecimalMin("1")
  private Long id;
  @NotNull
  @Size(min = 1, max = 255)
  private String name;
  @Pattern(regexp = Util.REGEX_DATE_EN)
  private String introducedDate;
  @Pattern(regexp = Util.REGEX_DATE_EN)
  private String discontinuedDate;
  @Size(min = 1, max = 255)
  private String companyName;
  @Min(1)
  private Long companyId;

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

  public Long getCompanyId() {
    return companyId;
  }

  public void setCompanyId(Long pCompanyId) {
    companyId = pCompanyId;
  }

  public void copy(ComputerDTO computerDTO) {
    if (computerDTO == null) {
      id = null;
      name = null;
      introducedDate = null;
      discontinuedDate = null;
      companyName = null;
      companyId = null;
    } else {
      id = computerDTO.id;
      name = computerDTO.name;
      introducedDate = computerDTO.introducedDate;
      discontinuedDate = computerDTO.discontinuedDate;
      companyName = computerDTO.companyName;
      companyId = computerDTO.companyId;
    }
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
    if (companyId != null) {
      builder.append(", companyId=");
      builder.append(companyId);
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

    public Builder setCompanyId(Long pCompanyId) {
      computer.companyId = pCompanyId;
      return this;
    }

    public ComputerDTO build() {
      return computer;
    }
  }
}
