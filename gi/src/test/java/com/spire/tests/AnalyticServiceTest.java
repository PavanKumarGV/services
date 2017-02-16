package com.spire.tests;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.spire.base.controller.TestPlan;
import com.spire.base.service.utils.AnalyticsDataSet;
import com.spire.base.service.utils.AnalyticsRestClient;
import com.spire.base.service.utils.PropertiesPicker;
import com.spire.base.util.SpireCsvUtil;
import com.spire.base.util.internal.entity.SpireTestObject;

public class AnalyticServiceTest extends TestPlan {

	public static String clientName = null;
	public static String environment = null;
	public static String filePathForAnalytics = null;
	public static String fileName = null;

	@Parameters({ "env", "client" })
	@BeforeClass(alwaysRun=true)
	public static void init(String env, String client) {

		environment = env;
		clientName = client;

		System.out.println("Env being used" + " " + environment);
		System.out.println("Client app for which tests being run is" + " " + clientName);

		fileName = "./src/test/java/com/spire/tests/" + clientName + "TestData.csv";
		filePathForAnalytics = "./src/test/java/com/spire/tests/" + clientName + ".properties";

		System.out.println("CSV file with test Data is" + " " + fileName);
		System.out.println("Properties file with config details" + " " + filePathForAnalytics);
	}

	@DataProvider(name = "AnalyticsTestData")
	//@BeforeSuite(alwaysRun=true)
	public static Iterator<Object[]> gettestData(Method method) {

		Iterator<Object[]> objectsFromCsv = null;
		 
		try {

			LinkedHashMap<String, Class<?>> entityClazzMap = new LinkedHashMap<String, Class<?>>();
			LinkedHashMap<String, String> methodFilter = new LinkedHashMap<String, String>();
			methodFilter.put(SpireTestObject.TEST_TITLE, method.getName());
			entityClazzMap.put("SpireTestObject", SpireTestObject.class);
			entityClazzMap.put("AnalyticsDataSet", AnalyticsDataSet.class);
			objectsFromCsv = SpireCsvUtil.getObjectsFromCsv(AnalyticServiceTest.class, entityClazzMap, fileName, null,
					methodFilter);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return objectsFromCsv;
	}

	@Test(groups = { "sanity" }, dataProvider = "AnalyticsTestData")
	public void testAnalyticsService(SpireTestObject testObject, AnalyticsDataSet testData) {

		AnalyticsRestClient<AnalyticsDataSet> utility = new AnalyticsRestClient<AnalyticsDataSet>(environment,
				filePathForAnalytics);
		int respValidate[] = utility.postServiceResp(PropertiesPicker.getValues(filePathForAnalytics, "reqBody"),
				testData);
		for (int i = 0; i < respValidate.length; i++) {
			Assert.assertEquals(respValidate[0] == 200, true, "Service response is not true");
			Assert.assertEquals(respValidate[1] > 0, true, "Service response has results");
		}

	}

	@Test(groups = { "Regression" }, dataProvider = "AnalyticsTestData")
	public void testAnalyticsServiceReg(SpireTestObject testObject, AnalyticsDataSet testData) {

		AnalyticsRestClient<AnalyticsDataSet> utility = new AnalyticsRestClient<AnalyticsDataSet>(environment,
				filePathForAnalytics);
		int respValidate[] = utility.postServiceResp(PropertiesPicker.getValues(filePathForAnalytics, "reqBody"),
				testData);
		for (int i = 0; i < respValidate.length; i++) {
			Assert.assertEquals(respValidate[0] == 200, true, "Service response is not true");
			Assert.assertEquals(respValidate[1] > 0, true, "Service response has results");
		}

	}

}
