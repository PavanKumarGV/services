package spire.talent.gi.beans;

import java.util.Date;
import java.util.List;
import java.util.Map;

import spire.talent.gi.utils.DynamicPropertiesReader;
import spire.talent.gi.utils.DynamicPropertyNames;

/**
 * 
 * @author Vikash Kumar Sinha
 *
 */
public class SearchRequisitionRequestBean {

	private static final int DEDAULT_LIMIT = 25;

	private static final int DEFAULT_OFFSET = 0;

	
	private static final int DEFAULT_DIRECTION = 1;

	private Map<String, List<String>> inSearchCriteria;

	private Map<String, Object> eqSearchCriteria;

	private Map<String, String> patternSearchCriteria;
	
	//private Map<String,SearchRequisitionRangeQueryBean>rangeSearchCriteria;

	private Integer exprncFromMnth;

	private Integer exprncToMnth;

	private Date fromCreatedDate;

	private Date toCreatedDate;

	//private String sortBy = DEFAULT_SORT_KEY;

	private int direction = DEFAULT_DIRECTION;

	private Integer limit = DEDAULT_LIMIT;

	private Integer offset = DEFAULT_OFFSET;
	
	private String facetType;

	private boolean calculateRecordCount;

	public Map<String, List<String>> getInSearchCriteria() {
		return inSearchCriteria;
	}

	public void setInSearchCriteria(Map<String, List<String>> inSearchCriteria) {
		this.inSearchCriteria = inSearchCriteria;
	}

	public Map<String, Object> getEqSearchCriteria() {
		return eqSearchCriteria;
	}

	public void setEqSearchCriteria(Map<String, Object> eqSearchCriteria) {
		this.eqSearchCriteria = eqSearchCriteria;
	}

	public Integer getExprncFromMnth() {
		return exprncFromMnth;
	}

	public void setExprncFromMnth(Integer exprncFromMnth) {
		this.exprncFromMnth = exprncFromMnth;
	}

	public Integer getExprncToMnth() {
		return exprncToMnth;
	}

	public void setExprncToMnth(Integer exprncToMnth) {
		this.exprncToMnth = exprncToMnth;
	}

	public Date getFromCreatedDate() {
		return fromCreatedDate;
	}

	public void setFromCreatedDate(Date fromCreatedDate) {
		this.fromCreatedDate = fromCreatedDate;
	}

	public Date getToCreatedDate() {
		return toCreatedDate;
	}

	public void setToCreatedDate(Date toCreatedDate) {
		this.toCreatedDate = toCreatedDate;
	}

	/*public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}*/

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public boolean isCalculateRecordCount() {
		return calculateRecordCount;
	}

	public void setCalculateRecordCount(boolean calculateRecordCount) {
		this.calculateRecordCount = calculateRecordCount;
	}

	public Map<String, String> getPatternSearchCriteria() {
		return patternSearchCriteria;
	}

	public void setPatternSearchCriteria(Map<String, String> patternSearchCriteria) {
		this.patternSearchCriteria = patternSearchCriteria;
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	/*public Map<String, SearchRequisitionRangeQueryBean> getRangeSearchCriteria() {
		return rangeSearchCriteria;
	}

	public void setRangeSearchCriteria(Map<String, SearchRequisitionRangeQueryBean> rangeSearchCriteria) {
		this.rangeSearchCriteria = rangeSearchCriteria;
	}*/
	
	public String getFacetType() {
		return facetType;
	}

	public void setFacetType(String facetType) {
		this.facetType = facetType;
	}

}
