package pers.itemsetmining;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Candidate 
{
	private List<Item> items;
	
	public int count = 0;//计数
	
	public Candidate()
	{
		count = 0;
		items = new ArrayList<Item>();
	}
	
	public Candidate(Item item)
	{
		count = 0;
		items = new ArrayList<Item>();
		items.add(item);
	}
	
	public Candidate(Collection<? extends Item> items)
	{
		count = 0;
		this.items = new ArrayList<Item>();
		this.items.addAll(items);
	}
	
	public void insertItem(int index, Item item)
	{
		items.add(index, item);
	}
	
	public void addItem(Item item)
	{
		items.add(item);
	}
	
	public void addItems(Collection<? extends Item> items)
	{
		this.items.addAll(items);
	}
	
	public Item removeItem(int index)
	{
		return items.remove(index);
	}
	
	public Item removeLastItem()
	{
		return items.remove(items.size()-1);
	}
	
	public boolean contains(Item item)
	{
		return items.contains(item);
	}
	
	public boolean contains(Candidate cand)
	{
		return items.containsAll(cand.items);
	}
	
	public Candidate sub(int startIndex, int endIndex)
	{
		Candidate cand = new Candidate();
		if(endIndex>items.size())
			endIndex = items.size();
		for(int i=startIndex; i<endIndex; i++)
		{
			cand.addItem(items.get(i));
		}
		return cand;
	}
	
	@Override
	public String toString() {
		String str = "";
		if(items.size()==0)
			return str;
		for(Item i : items)
		{
			str += i.toString()+",";
		}
		return str.substring(0, str.length()-1);
	}
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return items.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)                                      //先检查是否其自反性，后比较obj是否为空。这样效率高
			return true;
		if(obj == null)         
			return false;
		if(!(obj instanceof Candidate))
			return false;
			  
		final Candidate cand = (Candidate)obj;
			  
		return items.equals(cand.items);
	}
	
	//按位相交
	public List<Item> retain(Candidate cand)
	{
		int i=0,j=0;
		List<Item> retainItems = new ArrayList<Item>();
		while(i<items.size() && j<cand.items.size())
		{
			if(items.get(i).equals(cand.items.get(j)))
				retainItems.add(items.get(i));
			i++;
			j++;
		}
		return retainItems;
	}
	
	public Candidate clone()
	{
		List<Item> its = new ArrayList<Item>();
		its.addAll(items);
		return new Candidate(its);
	}
	
	public List<Item> cloneItems()
	{
		List<Item> its = new ArrayList<Item>();
		its.addAll(items);
		return its;
	}
	
	public Item getItem(int i)
	{
		if(i>=getSize())
			return null;
		return items.get(i);
	}
	
	public Item getLastItem()
	{
		return items.get(items.size()-1);
	}
	
	//候选集长度
	public int getSize()
	{
		return items.size();
	}
}
