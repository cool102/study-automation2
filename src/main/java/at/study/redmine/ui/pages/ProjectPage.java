package at.study.redmine.ui.pages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class ProjectPage extends Page {
    @FindBy(xpath = "//select[@id='add_filter_select']")
    public WebElement addFilter;

    @FindBy(xpath = "//tr[@id='tr_is_public']//select[@id='values_is_public_1']")
    public WebElement isPublicFilter;

    @FindBy(xpath = "//div[@id='query_form_content']/following-sibling::*//*[text()='Применить']")
    public WebElement filterSubmitButton;

    @FindBy(xpath = "//div[@id='content']")
    public WebElement projectsContent;


}
