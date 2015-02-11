package com.tonymanou.computerdb.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.tonymanou.computerdb.domain.Company;
import com.tonymanou.computerdb.domain.Computer;
import com.tonymanou.computerdb.service.ServiceManager;

public class ComputerDashboardTest {

  private static final String BASE_URL = "http://127.0.0.1:8080/computer-database";
  private WebDriver driver;

  @Before
  public void setUp() {
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void addANewComputer() {
    driver.get(BASE_URL + "/computer/add");

    Company company = ServiceManager.INSTANCE.getCompanyService().getFromId(1L);
    assertNotNull(company);

    WebElement element;

    element = driver.findElement(By.id("computerName"));
    element.clear();
    element.sendKeys("HAL 9000");

    element = driver.findElement(By.id("introduced"));
    element.clear();
    element.sendKeys("1986-09-30");

    element = driver.findElement(By.id("discontinued"));
    element.clear();

    element = driver.findElement(By.id("companyId"));
    new Select(element).selectByVisibleText(company.getName());

    element = driver.findElement(By.cssSelector("input.btn.btn-primary"));
    element.click();

    /*
     * The following code does not work in the case of an in-memory database. Indeed,
     * ServiceManager.INSTANCE is not the same in test case and in the server.
     */
    if ("".equals("1")) {
      // @formatter:off
      Computer computer = Computer.getBuilder("HAL 9000")
          .setIntroduced(LocalDate.of(1986, 9, 30))
          .setDiscontinued(null)
          .setCompany(company)
          .build();
      // @formatter:on

      List<Computer> list = ServiceManager.INSTANCE.getComputerService().findAll();
      assertTrue(list.contains(computer));
    }
  }

  @After
  public void tearDown() {
    driver.quit();
  }
}