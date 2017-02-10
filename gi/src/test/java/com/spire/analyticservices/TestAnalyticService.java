package com.spire.analyticservices;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.spire.base.controller.Assertion;

//import org.testng.annotations.Test;

import com.spire.base.controller.TestPlan;
import com.spire.base.service.utils.PropertiesPicker;
import com.spire.base.service.utils.AnalyticsRestClient;

public class TestAnalyticService extends TestPlan {

	// public static WebDriver driver = null;
	public static String environment = null;
	public static String filePathForAnalytics = null;

	@Parameters({ "env" })
	@BeforeClass
	public static void init(String env) {

		filePathForAnalytics = "C:/Repos/gi/gi/src/test/java/src/test/resources/Analytic_Service.properties";
		System.out.println("Env from @Parameter" + " " + env);

		// driver = Driver.initBrowser(browser);
		environment = env;

	}

	@Test(groups = { "sanity" }) // , dataProvider = "postServiceResp",
									// dataProviderClass = RestUtil.class)

	public void testAnalyticsService() {

		AnalyticsRestClient utility = new AnalyticsRestClient(environment);
		int respValidate[] = utility.postServiceResp(PropertiesPicker.getValues(filePathForAnalytics, "reqBody"));
		for (int i = 0; i < respValidate.length; i++) {
			Assert.assertEquals(respValidate[0] == 200, true, "Service response is not true");
			Assert.assertEquals(respValidate[1] > 0, true, "Service response has results");
		}

	}

}
