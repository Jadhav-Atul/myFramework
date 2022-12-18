package myFWProject.Tests;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//This test is to test the complete flow in a single class.
		//Here we are buying a product called ZARA COAT 3.
		
		//Variables:
		String productToBuy = "ZARA COAT 3";
		
		//Initialize the web-driver
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		//Open the site for testing
		driver.get("https://rahulshettyacademy.com/client");
		
		//Fill the user-name and password and login to the site.
		driver.findElement(By.id("userEmail")).sendKeys("jadhav.atul337-test@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Qwerty@123");
		driver.findElement(By.id("login")).click();
		
		//wait till all the products are loaded
		//Using explicit wait
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		
		//Find the product ZARA COAT 3
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));	//Fetch all listed products
		WebElement myProduct = products.stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productToBuy)).findFirst().orElse(null);	//Identify the required product
		myProduct.findElement(By.cssSelector(".btn.w-10.rounded")).click();	//click the add to cart button
		
		//Explicit Wait function until the toast container is shown
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".toast-bottom-right.toast-container")));
		Thread.sleep(2000);
		
		//click on the cart page button
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
		
		//Validate that the selected product is available in the cart
		List<WebElement> cartItems = driver.findElements(By.cssSelector(".cartSection h3"));
		boolean match = cartItems.stream().anyMatch(cartItem->cartItem.getText().equalsIgnoreCase(productToBuy));
		Assert.assertTrue(match);
		System.out.println(productToBuy+" is successully added to the cart.");
		
		//Click on the check-out button
		driver.findElement(By.cssSelector(".totalRow button")).click();
		
		//Fill the check out form and place the order
		driver.findElement(By.cssSelector("input[placeholder='Select Country']")).sendKeys("India");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[contains(@class,'ta-item')])[2]")));
		driver.findElement(By.xpath("(//button[contains(@class,'ta-item')])[2]")).click();
		
		//Click on the submit button to place the order
		driver.findElement(By.cssSelector(".btnn.action__submit.ng-star-inserted")).click();
		
		//Validate the thank you message after placing the order
		String message = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(message.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		System.out.println("The expected message:\""+ message+ "\" was seen after placing the order");

	}

}
