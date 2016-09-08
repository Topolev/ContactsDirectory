package by.topolev.contacts.servlets.formdata;

public class InfoSortField {
	private boolean choosenField = false;
	private String sortType = "ASC";
	private String nameSortField;
	
	public InfoSortField(String nameSortField){
		this.nameSortField = nameSortField;
	}
	
	
	public boolean isChoosenField() {
		return choosenField;
	}

	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getNameSortField() {
		return nameSortField;
	}

	public void setNameSortField(String nameSortField) {
		this.nameSortField = nameSortField;
	}

	public void setChoosenField(boolean choosenField) {
		this.choosenField = choosenField;
	}

}
