package teammates.test.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import teammates.common.util.ThreadHelper;

public class InstructorHomePage extends AppPage {
    
    @FindBy(id = "searchBox")
    private WebElement searchBox;
    
    @FindBy(id = "buttonSearch")
    private WebElement searchButton;
    
    @FindBy(id = "sortById")
    private WebElement sortByIdButton;

    @FindBy(id = "sortByName")
    private WebElement sortByNameButton;
    
    @FindBy(id = "sortByDate")
    private WebElement sortByDateButton;

    @FindBy(className = "button_sortname")
    private List<WebElement> tablesSortByName;

    @FindBy(className = "button_sortstartdate")
    private List<WebElement> tablesSortByStartDate;

    @FindBy(className = "button_sortenddate")
    private List<WebElement> tablesSortByEndDate;

    
    public InstructorCopyFsToModal fsCopyModal;
    
    public InstructorHomePage(Browser browser) {
        super(browser);
        if (InstructorCopyFsToModal.isPresentOnPage(browser)) {
            this.fsCopyModal = new InstructorCopyFsToModal(browser);
        }
    }

    @Override
    protected boolean containsExpectedPageContents() {
        return containsExpectedPageContents(getPageSource());
    }
    
    public static boolean containsExpectedPageContents(String pageSource) {
        return pageSource.contains("<h1>Instructor Home</h1>");
    }

    public InstructorHelpPage clickHelpLink() {
        instructorHelpTab.click();
        waitForPageToLoad();
        switchToNewWindow();
        return changePageType(InstructorHelpPage.class);
    }
    
    public void clickSortByIdButton() {
        sortByIdButton.click();
        waitForPageToLoad();
    }
    
    public void clickSortByNameButton() {
        sortByNameButton.click();
        waitForPageToLoad();
    }
    
    public void clickSortByDateButton() {
        sortByDateButton.click();
        waitForPageToLoad();
    }

    private void clickElements(List<WebElement> elements) {
        for (WebElement ele : elements) {
            ele.click();
        }
    }
    public void sortTablesByName() {
        clickElements(tablesSortByName);
    }

    public void sortTablesByStartDate() {
        clickElements(tablesSortByStartDate);
    }

    public void sortTablesByEndDate() {
        clickElements(tablesSortByEndDate);
    }

    public InstructorCourseEnrollPage clickCourseErollLink(String courseId) {
        getCourseLinkInRow("course-enroll-for-test", getCourseRowId(courseId)).click();
        waitForPageToLoad();
        return changePageType(InstructorCourseEnrollPage.class);
    }
    
    public InstructorCourseDetailsPage clickCourseViewLink(String courseId) {
        getCourseLinkInRow("course-view-for-test", getCourseRowId(courseId)).click();
        waitForPageToLoad();
        return changePageType(InstructorCourseDetailsPage.class);
    }
    
    public InstructorCourseEditPage clickCourseEditLink(String courseId) {
        getCourseLinkInRow("course-edit-for-test", getCourseRowId(courseId)).click();
        waitForPageToLoad();
        return changePageType(InstructorCourseEditPage.class);
    }
    
    //TODO: rename course-add-eval-for-test
    public InstructorFeedbacksPage clickCourseAddEvaluationLink(String courseId) {
        getCourseLinkInRow("course-add-eval-for-test", getCourseRowId(courseId)).click();
        waitForPageToLoad();
        ThreadHelper.waitBriefly();
        return changePageType(InstructorFeedbacksPage.class);
    }
    
    /**
     * This is for customized feedback session 
     */
    public InstructorFeedbackResultsPage clickFeedbackSessionViewResultsLink(String courseId, String fsName) {
        getViewResultsLink(courseId, fsName).click();
        waitForPageToLoad();
        return changePageType(InstructorFeedbackResultsPage.class);
    }
    
    
    /**
     * This is for customized feedback session 
     */
    public InstructorFeedbackEditPage clickFeedbackSessionEditLink(String courseId, String fsName) {
        getEditLink(courseId, fsName).click();
        waitForPageToLoad();
        return changePageType(InstructorFeedbackEditPage.class);
    }
    
    /**
     * This is for customized feedback session 
     */
    public InstructorFeedbacksPage clickFeedbackSessionDeleteLink(String courseId, String fsName) {
        clickAndConfirm(getDeleteEvalLink(courseId, fsName));
        waitForPageToLoad();
        switchToNewWindow();
        return changePageType(InstructorFeedbacksPage.class);
    }
    
    /**
     * This is for customized feedback session 
     */
    public FeedbackSubmitPage clickFeedbackSessionSubmitLink(String courseId, String fsName) {
        this.getSubmitLink(courseId, fsName).click();
        waitForPageToLoad();
        switchToNewWindow();
        return changePageType(FeedbackSubmitPage.class);
    }
    
    /**
     * This is for customized feedback session 
     */
    public InstructorHomePage clickFeedbackSessionRemindLink(String courseId, String fsName) {
        clickAndConfirm(getRemindLink(courseId, fsName));
        waitForPageToLoad();
        switchToNewWindow();
        return changePageType(InstructorHomePage.class);
    }
    
    /**
     * This is for customized feedback session 
     */
    public InstructorHomePage clickFeedbackSessionUnpublishLink(String courseId, String fsName) {
        clickAndConfirm(getUnpublishLink(courseId, fsName));
        waitForPageToLoad();
        switchToNewWindow();
        return changePageType(InstructorHomePage.class);
    }
    
    /**
     * This is for customized feedback session 
     */
    public InstructorHomePage clickFeedbackSessionPublishLink(String courseId, String fsName) {
        clickAndConfirm(getPublishLink(courseId, fsName));
        waitForPageToLoad();
        switchToNewWindow();
        return changePageType(InstructorHomePage.class);
    }
    
    
    
    public void clickHomeTab() {
        instructorHomeTab.click();
        waitForPageToLoad();
    }
    
    public InstructorStudentListPage searchForStudent(String studentName) {
        searchBox.clear();
        searchBox.sendKeys(studentName);
        searchButton.click();
        waitForPageToLoad();
        return changePageType(InstructorStudentListPage.class);
    }
    
    public WebElement getViewResponseLink(String courseId, String evalName) {
        int evaluationRowId = getEvaluationRowId(courseId, evalName);
        String xpathExp = "//tr[@id='session" + evaluationRowId + "']/td[contains(@class,'session-response-for-test')]/a";

        return browser.driver.findElement(By.xpath(xpathExp));
    }
    
    public void setViewResponseLinkValue(WebElement element, String newValue) {
        JavascriptExecutor js = (JavascriptExecutor) browser.driver; 
        js.executeScript("arguments[0].href=arguments[1]", element, newValue);
    }

    public WebElement getViewResultsLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-view-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public WebElement getEditLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-edit-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public WebElement getSubmitLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-submit-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public WebElement getPreviewLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-preview-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public WebElement getRemindLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-remind-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public WebElement getRemindOptionsLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-remind-options-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public void clickRemindOptionsLink(String courseId, String evalName) {
        getRemindOptionsLink(courseId, evalName).click();
    }
    
    public WebElement getRemindInnerLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-remind-inner-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public WebElement getRemindParticularUsersLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-remind-particular-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public void clickRemindParticularUsersLink(String courseId, String evalName) {
        getRemindParticularUsersLink(courseId, evalName).click();
        ThreadHelper.waitFor(1000);
    }
    
    public void cancelRemindParticularUsersForm() {
        WebElement remindModal = browser.driver.findElement(By.id("remindModal"));
        remindModal.findElement(By.tagName("button")).click();
    }
    
    public void fillRemindParticularUsersForm() {
        WebElement remindModal = browser.driver.findElement(By.id("remindModal"));
        List<WebElement> usersToRemind = remindModal.findElements(By.name("usersToRemind"));
        for (WebElement e : usersToRemind) {
            markCheckBoxAsChecked(e);
        }
    }
    
    public void submitRemindParticularUsersForm() {
        WebElement remindModal = browser.driver.findElement(By.id("remindModal"));
        remindModal.findElement(By.name("form_remind_list")).submit();
    }
    
    public WebElement getPublishLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-publish-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public WebElement getUnpublishLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-unpublish-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public WebElement getDeleteEvalLink(String courseId, String evalName) {
        return getSessionLinkInRow("session-delete-for-test", getEvaluationRowId(courseId, evalName));
    }
    
    public WebElement getDeleteCourseLink(String courseId) {
        return getCourseLinkInRow("course-delete-for-test", getCourseRowId(courseId));
    }

    public InstructorHomePage clickArchiveCourseLinkAndConfirm(String courseId) {
        clickAndConfirm(getCourseLinkInRow("course-archive-for-test", getCourseRowId(courseId)));
        waitForPageToLoad();
        return this;
    }

    public InstructorHomePage clickArchiveCourseLinkAndCancel(String courseId) {
        clickAndCancel(getCourseLinkInRow("course-archive-for-test", getCourseRowId(courseId)));
        waitForPageToLoad();
        return this;
    }
    
    public String getArchiveCourseLink(String courseId) {
        return getCourseLinkInRow("course-archive-for-test", getCourseRowId(courseId)).getAttribute("href");
    }
    
    private WebElement getSessionLinkInRow(String elementClassNamePrefix, int rowId) {
        waitForElementPresence(By.id("session" + rowId));
        waitForElementPresence(By.className(elementClassNamePrefix));
        return browser.driver.findElement(By.id("session" + rowId)).findElement(By.className(elementClassNamePrefix));
    }
    
    private WebElement getCourseLinkInRow(String elementClassNamePrefix, int rowId) {
        waitForElementPresence(By.id("course-" + rowId));
        waitForElementPresence(By.className(elementClassNamePrefix));
        return browser.driver.findElement(By.id("course-" + rowId)).findElement(By.className(elementClassNamePrefix));
    }

    private int getEvaluationRowId(String courseId, String evalName) {
        int courseRowID = getCourseRowId(courseId);
        if (courseRowID == -1)
            return -2;
        String template = "//div[@id='course-%d']//tr[@id='session%d']";
        int max = browser.driver.findElements(By.xpath("//div[starts-with(@id, 'course-')]//tr")).size();
        for (int id = 0; id < max; id++) {
            if (getElementText(
                    By.xpath(String.format(template + "//td[1]", courseRowID,
                            id))).equals(evalName)) {
                return id;
            }
        }
        return -1;
    }
    
    private int getCourseRowId(String courseId) {
        waitForAjaxLoaderGifToDisappear();
        int id = 0;
        while (isElementPresent(By.id("course-" + id))) {
            if (getElementText(
                    By.xpath("//div[@id='course-" + id
                            + "']//strong"))
                    .startsWith("[" + courseId + "]")) {
                return id;
            }
            id++;
        }
        return -1;
    }
    
    private String getElementText(By locator) {
        waitForElementPresence(locator);
        return browser.driver.findElement(locator).getText();
    }
    
    public void changeFsCopyButtonActionLink(String courseId, String feedbackSessionName, String newActionLink) {
        String id = "button_fscopy" + "-" + courseId + "-" + feedbackSessionName;
        By element = By.id(id);
        waitForElementPresence(element);
        
        JavascriptExecutor js = (JavascriptExecutor) browser.driver;
        js.executeScript("document.getElementById('" + id + "').setAttribute('data-actionlink', '" + newActionLink + "')");
        
    }
    
    public void clickFsCopyButton(String courseId, String feedbackSessionName) {
        By fsCopyButtonElement = By.id("button_fscopy" + "-" + courseId + "-" + feedbackSessionName);
        
        // give it some time to load as it is loaded via AJAX
        waitForElementPresence(fsCopyButtonElement);
        
        WebElement fsCopyButton = browser.driver.findElement(fsCopyButtonElement);
        waitForElementNotCovered(fsCopyButton);
        fsCopyButton.click();
    }
}
