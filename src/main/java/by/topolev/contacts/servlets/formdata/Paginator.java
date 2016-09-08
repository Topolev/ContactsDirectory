package by.topolev.contacts.servlets.formdata;

import java.util.ArrayList;
import java.util.List;

public class Paginator {
	boolean skipLeft;
	boolean skipRight;
	
	boolean buttonPrev;
	boolean buttonNext;
	
	List<Integer> listPages = new ArrayList<>();

	public boolean isSkipLeft() {
		return skipLeft;
	}

	public void setSkipLeft(boolean skipLeft) {
		this.skipLeft = skipLeft;
	}

	public boolean isSkipRight() {
		return skipRight;
	}

	public void setSkipRight(boolean skipRight) {
		this.skipRight = skipRight;
	}

	public boolean isButtonPrev() {
		return buttonPrev;
	}

	public void setButtonPrev(boolean buttonPrev) {
		this.buttonPrev = buttonPrev;
	}

	public boolean isButtonNext() {
		return buttonNext;
	}

	public void setButtonNext(boolean buttonNext) {
		this.buttonNext = buttonNext;
	}

	public List<Integer> getListPages() {
		return listPages;
	}

	public void setListPages(List<Integer> listPages) {
		this.listPages = listPages;
	}
	
	
}
