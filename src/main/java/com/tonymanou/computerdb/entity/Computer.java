package com.tonymanou.computerdb.entity;

import java.util.Date;

/**
 * Describe a computer.
 *
 * @author tonymanou
 */
public class Computer {

	private Long id;
	private String name;
	private Date introduced;
	private Date discontinued;
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

	public Date getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Date pIntroduced) {
		introduced = pIntroduced;
	}

	public Date getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Date pDiscontinued) {
		discontinued = pDiscontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company pCompany) {
		company = pCompany;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Computer [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", introduced=");
		builder.append(introduced);
		builder.append(", discontinued=");
		builder.append(discontinued);
		builder.append(", company=");
		builder.append(company);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * A better {@code toString()} that ignore null members.
	 *
	 * @return a clever string representation of the object.
	 */
	public String toCleverString() {
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
