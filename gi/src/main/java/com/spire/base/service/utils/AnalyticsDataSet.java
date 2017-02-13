package com.spire.base.service.utils;

import com.spire.base.controller.Logging;

public class AnalyticsDataSet {


		String valueForUsecase;
		String valueForSiteId;
		String valueForFrom;
		String valueForTo;

		public String getvalueForUsecase() {

			Logging.log("Getting valueForUsecase from Property File");
			return valueForUsecase;
		}

		public void setvalueForUsecase(String valueForUsecase) {
			this.valueForUsecase = valueForUsecase;
		}

		public String getvalueForSiteId() {

			Logging.log("Getting valueForSiteId from Property File");
			return valueForSiteId;
		}

		public void setvalueForSiteId(String valueForSiteId) {
			this.valueForSiteId = valueForSiteId;
		}

		public String getvalueForFrom() {
			return valueForFrom;
		}

		public void setvalueForFrom(String valueForFrom) {
			this.valueForFrom = valueForFrom;
		}

		public String getvalueForTo() {

			Logging.log("Getting valueForTo for the test case");

			return valueForTo;
		}

		public void setvalueForTo(String valueForTo) {
			this.valueForTo = valueForTo;
		}

	}

	
	

