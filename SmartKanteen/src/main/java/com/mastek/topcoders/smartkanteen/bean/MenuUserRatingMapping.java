package com.mastek.topcoders.smartkanteen.bean;

public class MenuUserRatingMapping {

		private Integer menuUserRatingMappingId;
		private Integer itemId;
		private Integer userId;
		private Integer rating;
		
		public Integer getMenuUserRatingMappingId() {
			return menuUserRatingMappingId;
		}
		public void setMenuUserRatingMappingId(Integer menuUserRatingMappingId) {
			this.menuUserRatingMappingId = menuUserRatingMappingId;
		}
		public Integer getItemId() {
			return itemId;
		}
		public void setItemId(Integer itemId) {
			this.itemId = itemId;
		}
		public Integer getUserId() {
			return userId;
		}
		public void setUserId(Integer userId) {
			this.userId = userId;
		}
		public Integer getRating() {
			return rating;
		}
		public void setRating(Integer rating) {
			this.rating = rating;
		}
		
		@Override
		public String toString() {
			return "MenuUserRatingMapping [menuUserRatingMappingId="
					+ menuUserRatingMappingId + ", itemId=" + itemId
					+ ", userId=" + userId + ", rating=" + rating + "]";
		}

		

}
