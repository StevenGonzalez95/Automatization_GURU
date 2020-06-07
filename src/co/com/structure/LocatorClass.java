package co.com.structure;

import org.openqa.selenium.By;

public class LocatorClass {

    public By locatorName(String name){
        return By.name(name);
    }

    public By locatorId(String id){
        return By.id(id);
    }

    public By locatorXpath(String path){
        return By.xpath(path);
    }

    public By locatorTag(String tagName){
        return By.tagName(tagName);
    }

    public By locatorLink(String text){
        return By.linkText(text);
    }

    public By locatorClass(String className){
        return By.className(className);
    }


}
