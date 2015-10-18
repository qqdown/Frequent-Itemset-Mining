package pers.itemsetmining;

public class Item 
{
	private String content;//内容
	
	public Item(String content)
	{
		this.content = content;
	}
	
	public int compareTo(Item item)
	{
		return content.compareTo(item.content);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return content;
	}
	
	@Override
	public int hashCode() {
		return content.hashCode();
		//return Integer.parseInt(content)-1;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(this == obj)                                      //先检查是否其自反性，后比较obj是否为空。这样效率高
			return true;
		if(obj == null)         
			return false;
		if(!(obj instanceof Item))
			return false;
			  
		final Item item = (Item)obj;
			  
		return content.equals(item.content);
	};

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	

}
