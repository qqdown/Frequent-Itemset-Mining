package pers.itemsetmining;


public class Transaction 
{
	public CandidateItems cand;
	
	public Transaction()
	{
		cand = new CandidateItems();
	}
	
	public boolean contains(Item item)
	{
		return cand.contains(item);
	}
	
	public void addItem(Item item)
	{
		cand.addItem(item);
	}
	
	public Item getItem(int index)
	{
		return cand.getItem(index);
	}
	
	public int getSize()
	{
		return cand.getSize();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return cand.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj)                                      //�ȼ���Ƿ����Է��ԣ���Ƚ�obj�Ƿ�Ϊ�ա�����Ч�ʸ�
			return true;
		if(obj == null)         
			return false;
		if(!(obj instanceof Transaction))
			return false;
			  
		final Transaction trans = (Transaction)obj;
			  
		return cand.equals(trans.cand);
	}
}
