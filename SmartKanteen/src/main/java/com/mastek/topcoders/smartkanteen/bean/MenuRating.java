package com.mastek.topcoders.smartkanteen.bean;

public class MenuRating {
	
	private Integer menuRatingId;
	private Integer itemId;
	private Integer countOfPeopleWhoHaveRated;
	private Integer currentAverageRating;
	
	public Integer getMenuRatingId() {
		return menuRatingId;
	}
	public void setMenuRatingId(Integer menuRatingId) {
		this.menuRatingId = menuRatingId;
	}
	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}
	public Integer getCountOfPeopleWhoHaveRated() {
		return countOfPeopleWhoHaveRated;
	}
	public void setCountOfPeopleWhoHaveRated(Integer countOfPeopleWhoHaveRated) {
		this.countOfPeopleWhoHaveRated = countOfPeopleWhoHaveRated;
	}
	public Integer getCurrentAverageRating() {
		return currentAverageRating;
	}
	public void setCurrentAverageRating(Integer currentAverageRating) {
		this.currentAverageRating = currentAverageRating;
	}
	
	@Override
	public String toString() {
		return "MenuRating [menuRatingId=" + menuRatingId + ", itemId="
				+ itemId + ", countOfPeopleWhoHaveRated="
				+ countOfPeopleWhoHaveRated + ", currentAverageRating="
				+ currentAverageRating + "]";
	}
	
	

}
