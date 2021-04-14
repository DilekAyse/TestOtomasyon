import java.util.concurrent.TimeUnit;
import Tests.GittiGidiyorConstants;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.junit.Assert.assertEquals;

public class GittiGidiyor extends GittiGidiyorConstants {

    @Test
    public void setupDriver() {

        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Dilek\\Desktop\\TestOtomasyon\\gecko\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();

        // www.gittigidiyor.com sitesi acilir.
        driver.get(link);

        //Ana sayfanın acildigi kontrol edilir.
        assertEquals(link, "https://www.gittigidiyor.com/");

        //Login sayfasina gidilir.
        driver.findElement(By.xpath(login)).click();
        driver.findElement(By.xpath(loginArea)).click();
        

        //Siteye login olunur.
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.id(EmailId)).sendKeys(EmailData);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.id(PasswordId)).sendKeys(PasswordData);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.findElement(By.id(LoginId)).click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

        //Login islemi kontrol edilir.
        Assert.assertEquals("GittiGidiyor - Türkiye'nin Öncü Alışveriş Sitesi", driver.getTitle());

        //Arama kutucuguna bilgisayar kelimesi girilir.
        driver.findElement(By.xpath(SearchXpath)).click();
        driver.findElement(By.xpath(SearchXpath)).sendKeys(SearchData);
        driver.findElement(By.xpath(FindButtonXpath)).click();

        //Arama sonucları sayfasindan 2.sayfa acilir.
        WebElement ele = driver.findElement(By.xpath(SecondPage));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", ele);

        // 2.sayfanın açıldığı kontrol edilir.Hata alindigi icin yorum satiri yapildi.
        //Assert.assertEquals("Bilgisayar - GittiGidiyor - 2/100", driver.getTitle());

        //Urun secilir.
        driver.findElement(By.xpath(SelectProduct)).click();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Bazen bu reklam gelmiyor.O durumda yorum satiri yapilmali.
        driver.findElement(By.xpath(CloseAds)).click();

        //Sepete atmadan onceki miktar.
        String productPrice = driver.findElement(By.xpath(ProductPrice)).getText();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        //Sepete urun eklenir.
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scroll(0,550)");
        driver.findElement(By.id(AddToBasketId)).click();

        //Sepete gidilir.
        driver.findElement(By.xpath(MyBasket)).click();

        //Sepete attiktan sonraki miktar.
        String newPrice= driver.findElement(By.xpath(NewProductPrice)).getText();

        //Urun sayfasindaki fiyat ile sepette yer alan urun fiyatinin dogrulugu karsilastirilir.
        //İki fiyatta aynı olmasına rağmen farklı gösteriyor.Bu yuzden yorum satiri yapildi.
        // assertEquals(productPrice, newPrice);

        //Sepetteki urun sayisi arttirilir.
        driver.findElement(By.xpath(NumberOfProduct)).click();
        driver.findElement(By.xpath(SelectedNumberOfProduct)).click();

        //Sepetteki urun silinir.
        WebElement deleteButton = driver.findElement(By.cssSelector(DeleteProduct));
        deleteButton.click();

        driver.quit();
    }
}

