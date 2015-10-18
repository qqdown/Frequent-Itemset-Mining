package pers.itemsetming.algorithms;

import java.util.ArrayList;
import java.util.List;

import pers.itemsetmining.CandidateItems;
import pers.itemsetmining.Item;
import pers.itemsetmining.Transaction;
import pers.itemsetmining.util.HashTree;

public class Apriori 
{
	private List<Transaction> transactions;
	private List<CandidateItems> candi1itemset;
	private double minsup;
	
	public Apriori(Item[] itemset, List<Transaction> transactions, double minsup)
	{
		candi1itemset = new ArrayList<CandidateItems>();
		for(Item it : itemset)
		{
			candi1itemset.add(new CandidateItems(it));
		}
		this.transactions = transactions;
		this.minsup = minsup;
	}
	
	public void sort()
	{
		
	}
	
	public List<CandidateItems> run()
	{
		int k = 1;
		List<CandidateItems> freq1itemset = getFrequent1Itemset();
		List<CandidateItems> resultSet = new ArrayList<CandidateItems>();
		List<CandidateItems> candidateSet = freq1itemset;
		while(candidateSet != null && candidateSet.size()>0)
		{
			resultSet.addAll(candidateSet);
			candidateSet = join(candidateSet, k);
			k++;
			HashTree ht = new HashTree(k, (int)Math.sqrt(candidateSet.size()/k));
			//计数
			for(CandidateItems ci : candidateSet)
			{
				ht.add(ci);
			}

			for(Transaction trans : transactions)
			{
				ht.count(trans, k);
			}

			for(int i=candidateSet.size()-1; i>=0; i--)
			{
				if(candidateSet.get(i).count < transactions.size()*minsup)
					candidateSet.remove(i);
			}
		}
		return resultSet;
	}
	
	private List<CandidateItems> getFrequent1Itemset()
	{
		List<CandidateItems> frequentSet = new ArrayList<CandidateItems>();
		for(CandidateItems cand : candi1itemset)
		{
			for(Transaction t : transactions)
			{
				if(t.contains(cand.getItem(0)))
				{
					cand.count ++;
				}
			}
		}
		
		for(CandidateItems cand : candi1itemset)
		{
			if(cand.count >= transactions.size()*minsup)
				frequentSet.add(cand);
		}
		return frequentSet;
	}
	
	private List<CandidateItems> join(List<CandidateItems> fk, int k)
	{
		List<CandidateItems> candSet = new ArrayList<CandidateItems>();
		for(int i=0; i<fk.size(); i++)
		{
			CandidateItems candA = fk.get(i);
			for(int j=i+1; j<fk.size(); j++)
			{
				CandidateItems candB = fk.get(j);
				List<Item> items = candA.retain(candB);//对两个候选集合求交集
				if(items.size() == k-1)
				{
					if(!candA.getLastItem().equals(candB.getLastItem()))//最后一个不同，保证前k-1个相同
					{
						CandidateItems ck_new = candA.clone();
						//ck_new.count = 0;
						ck_new.addItem(candB.getLastItem());
						//prune 裁剪候选枝，ck_new的k-set都是频繁的
						if(isSubsetFrequent(ck_new, fk))
						{
							candSet.add(ck_new);
						}
					}
				}
			}
		}	
		
		return candSet;
	}
	
	//判断子串（去掉最后一个元素）是否为频繁项
	private boolean isSubsetFrequent(CandidateItems candidateSet, List<CandidateItems> freqSet)
	{
		CandidateItems cand = candidateSet.clone();
		for(int i=0; i<candidateSet.getSize(); i++)
		{
			Item it = cand.removeItem(i);
			if(!freqSet.contains(cand))
				return false;
			cand.insertItem(i, it);
		}
		return true;
	}
	
	public double getMinsup() {
		return minsup;
	}
	
	public void setMinsup(double minsup) {
		this.minsup = minsup;
	}
}
