package myFWProject.PageObjects;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import myFWProject.AbstractComponents.AbstractComponent;

public class HomePage extends AbstractComponent{

	WebDriver driver;
	public HomePage(WebDriver driver)
	{
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(css=".mb-3")
	List<WebElement> products;
	
	By allProducts = By.cssSelector(".mb-3");
	By addToCart = By.cssSelector(".btn.w-10.rounded");
	By toaster = By.cssSelector(".toast-bottom-right.toast-container");
	
	public  List<WebElement> getProductList()
	{
		waitForElementToAppear(allProducts);	
		return products;
		
	}
	public WebElement getProduct(String productToBuy)
	{
		WebElement myProduct = getProductList().stream().filter(product->product.findElement(By.cssSelector("b")).getText().equals(productToBuy)).findFirst().orElse(null);	//Identify the required product
		return myProduct;
	}
	
	public void addToCart(String productToBuy) throws InterruptedException
	{
		WebElement myProd =getProduct(productToBuy); 	
		myProd.findElement(addToCart).click();	//click the add to cart button
		waitForElementToAppear(toaster);
		waitForElementToDisappear();
	}
		
	
}
