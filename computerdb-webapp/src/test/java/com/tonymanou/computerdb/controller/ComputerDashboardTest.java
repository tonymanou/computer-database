package com.tonymanou.computerdb.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tonymanou.computerdb.model.Company;
import com.tonymanou.computerdb.model.Computer;
import com.tonymanou.computerdb.service.ICompanyService;
import com.tonymanou.computerdb.service.IComputerService;

public class ComputerDashboardTest {

  private static final String BASE_URL = "http://127.0.0.1:8080/computerdb-webapp";

  private static ClassPathXmlApplicationContext context;
  private static ICompanyService companyService;
  private static IComputerService computerService;
  private static WebDriver driver;

  @BeforeClass
  public static void setUp() {
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    context = new ClassPathXmlApplicationContext("classpath:applicationContext-service.xml");
    companyService = context.getBean(ICompanyService.class);
    computerService = context.getBean(IComputerService.class);
  }

  @Test
  public void addANewComputer() {
    driver.get(BASE_URL + "/computer/add");

    Company company = companyService.getFromId(1L);
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

    Computer computer = Computer.getBuilder("HAL 9000")
        .setIntroduced(LocalDate.of(1986, 9, 30))
        .setDiscontinued(null)
        .setCompany(company)
        .build();

    List<Computer> list = computerService.findAll();
    assertTrue(list.contains(computer));
  }

  @AfterClass
  public static void tearDown() {
    if (driver != null) {
      driver.quit();
    }
    if (context != null) {
      context.close();
    }
  }
}