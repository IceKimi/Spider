package Com.Rantron.Spider.bean;

import java.util.Arrays;

public class TaoBaoItemBase {

	private String title;
	private int totalSoldQuantity;
	private double price;
	private long categoryId;
	private long ItemId;
	private int favCount;
	private String[] props;
	private String location;
	private String sellerNick;
	private String shopTitle;
	private String keywords;
	
	
	
	
	@Override
	public String toString() {
		return "TaoBaoItem [title=" + title + ", totalSoldQuantity=" + totalSoldQuantity + ", price=" + price
				+ ", categoryId=" + categoryId + ", ItemId=" + ItemId + ", favCount=" + favCount + ", props="
				+ Arrays.toString(props) + ", location=" + location + ", sellerNick=" + sellerNick + ", shopTitle="
				+ shopTitle + ", keywords=" + keywords + ", shopType=" + shopType + "]";
	}
	private String shopType;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTotalSoldQuantity() {
		return totalSoldQuantity;
	}
	public void setTotalSoldQuantity(int totalSoldQuantity) {
		this.totalSoldQuantity = totalSoldQuantity;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String[] getProps() {
		return props;
	}
	public String getPropsStr()
	{
		StringBuffer sb = new StringBuffer();
		for(String prop:props)
		{
			sb.append(prop);
			sb.append("^");
		}
		return sb.substring(0, sb.length()-1);
	}
	public void setProps(String[] props) {
		this.props = props;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getSellerNick() {
		return sellerNick;
	}
	public void setSellerNick(String sellerNick) {
		this.sellerNick = sellerNick;
	}
	public String getShopTitle() {
		return shopTitle;
	}
	public void setShopTitle(String shopTitle) {
		this.shopTitle = shopTitle;
	}
	public String getShopType() {
		return shopType;
	}
	public void setShopType(String shopType) {
		this.shopType = shopType;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	public long getItemId() {
		return ItemId;
	}
	public void setItemId(long itemId) {
		ItemId = itemId;
	}
	public int getFavCount() {
		return favCount;
	}
	public void setFavCount(int favCount) {
		this.favCount = favCount;
	}
}
