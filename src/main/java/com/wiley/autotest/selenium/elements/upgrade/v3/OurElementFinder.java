package com.wiley.autotest.selenium.elements.upgrade.v3;

import com.wiley.autotest.selenium.elements.upgrade.OurWebElement;
import com.wiley.autotest.selenium.elements.upgrade.v3.conditions.OurConditionFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.wiley.autotest.selenium.elements.upgrade.OurWebElementFactory.wrap;
import static com.wiley.autotest.selenium.elements.upgrade.OurWebElementFactory.wrapList;


/**
 * Created by vefimov on 03/05/2017.
 */
public class OurElementFinder {

    private OurSearchStrategy strategy;

    private OurWebElement context;

    private FluentWaitFinder fluentWait;

    public OurElementFinder(WebDriver driver, OurSearchStrategy strategy) {
        this.strategy = strategy;
        this.fluentWait = new FluentWaitFinder(driver);
        fluentWait.withTimeout(strategy.getTimeout(), TimeUnit.SECONDS);
        fluentWait.pollingEvery(strategy.getPoolingEvery(), strategy.getUnit());
        fluentWait.setNullOnFailure(strategy.isNullOnFailure());
    }

    public OurElementFinder(WebDriver driver, OurSearchStrategy strategy, OurWebElement context) {
        this(driver, strategy);
        this.context = context;
    }

    public OurWebElement visibleElement(By locator) {
        Function<WebDriver, List<WebElement>> condition = new OurConditionFactory(context).get(strategy.getFrameStrategy())
                .visibility(locator);
        if (context == null) {
            return wrap(fluentWait.waitFor(condition).get(0), locator);
        } else {
            return wrap(context, fluentWait.waitFor(condition).get(0), locator);
        }
    }

    public List<OurWebElement> visibleElements(By locator) {
        Function<WebDriver, List<WebElement>> condition = new OurConditionFactory(context).get(strategy.getFrameStrategy())
                .visibility(locator);
        if (context == null) {
            return wrapList(fluentWait.waitFor(condition), locator);
        } else {
            return wrapList(context, fluentWait.waitFor(condition), locator);
        }
    }

    public OurWebElement presentInDomElement(By locator) {
        Function<WebDriver, List<WebElement>> condition = new OurConditionFactory(context).get(strategy.getFrameStrategy())
                .presence(locator);
        if (context == null) {
            return wrap(fluentWait.waitFor(condition).get(0), locator);
        } else {
            return wrap(context, fluentWait.waitFor(condition).get(0), locator);
        }
    }

    public List<OurWebElement> presentInDomElements(By locator) {
        Function<WebDriver, List<WebElement>> condition = new OurConditionFactory(context).get(strategy.getFrameStrategy())
                .presence(locator);
        if (context == null) {
            return wrapList(fluentWait.waitFor(condition), locator);
        } else {
            return wrapList(context, fluentWait.waitFor(condition), locator);
        }
    }

    //this is not "OUR" alert yet, so should probably become "OUR"
    public Alert alert() {
        return fluentWait.waitFor(ExpectedConditions.alertIsPresent());
    }
}
