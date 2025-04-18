/* AppVersionController
 * Cont. for inserting additional data into the Model of the app. In this case
 * it inserts app version from application.properties file which in turn draws
 * this data from <version> tag in pom.xml file. In order to display version
 * number it is only necessary to change it in pom file instead of writing it
 * in a footer in every View - centralized app version info.
 * */

package com.java.crv.BibleReaderCommentary.advices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

/**Support controller for inserting app version info into every View available (through Model)
 * 
 * */
@ControllerAdvice
public class AppVersionAdvice {
	
	/*This annotation will pull the data from the application.properties file,
	 * specifically app.version string.*/
	@Value("${app.version}")
	private String appVersion;
	
	/*ModelAttribute is anotation for inserting additional data into the Model.
	 *It will add this Attribute into every Model in each View and it can be
	 *accessed via thymeleaf. */
	@ModelAttribute
	public void displayAppVersion(Model model) {
		model.addAttribute("appVersion", "Version: " + this.appVersion);
	}

}
