package pers.itemsetmining.util;

import pers.itemsetmining.Candidate;
import pers.itemsetmining.Transaction;

public class HashTree 
{
	private HashTreeNode root;
	private int degree;
	private int splitThreashold;

	public HashTree(int degree, int splitThreshold)
	{
		this.degree = degree;
		this.splitThreashold = splitThreshold;
		root = new HashTreeNode(0, degree);//创建根节点
	}
	
	public void add(Candidate candi)
	{
		HashTreeNode node = root;
		int curLevel = 0;//当前层数,0代表root
		while(true)//查找插入位置
		{
			int h = candi.getItem(curLevel).hashCode() % degree;
			if(node.branches[h] == null)//还未插入则直接添加
			{
				node.branches[h] = new HashTreeNode(curLevel+1, degree);
				node.branches[h].candidateSets.add(candi);
				node.isLeaf = false;
				break;
			}
			else
			{
				if(node.branches[h].isLeaf)//待插入节点为根节点
				{
					//插入
					node.branches[h].candidateSets.add(candi);
					//候选节点已满，则进行分裂
					if(node.branches[h].candidateSets.size() > splitThreashold && curLevel+1<candi.getSize())
					{
						node.branches[h].isLeaf = false;
						for(Candidate c : node.branches[h].candidateSets)
						{
							int hash = c.getItem(curLevel+1).hashCode() % degree;
							if(node.branches[h].branches[hash] == null)
								node.branches[h].branches[hash] = new HashTreeNode(curLevel+2, degree);
							node.branches[h].branches[hash].candidateSets.add(c);
						}
						node.branches[h].candidateSets.clear();
					}
					
					break;
				}
				else//寻找下一个节点
				{
					curLevel++;
					node = node.branches[h];
				}
			}
		}
	}

	//计数，初始节点的count值都为0
	public void count(Transaction trans, int k)
	{
		Candidate cand = trans.cand;

		count(new Candidate(), cand, k, root);
	}
	
	//计数的递归函数，初始节点的count值都为0
	private void count(Candidate prefixCand, Candidate searchCand, int k, HashTreeNode rootnode)
	{
		//System.out.println(prefixCand.toString() + " | " + searchCand.toString());
		
		HashTreeNode node = rootnode;
		if(node == null)
			return;
		//判断是否为子集
		if(node.isLeaf)
		{
			for(Candidate c : node.candidateSets)
			{
				boolean isContain = true;
				//前缀相同，后缀包含
				if(!prefixCand.equals(c.sub(0, prefixCand.getSize())))
				{
					isContain = false;
				}
				else
				{
					Candidate remainCand = c.sub(prefixCand.getSize(), c.getSize());
					for(int j=0; j<remainCand.getSize(); j++)
					{
						if(!searchCand.contains(remainCand.getItem(j)))
						{
							isContain = false;
							break;
						}
					}
				}
				if(isContain)
				{	
					c.count ++;
				}
			}
		}
		else
		{
			Candidate newSearchCand = searchCand.clone();
			for(int i=0; i<searchCand.getSize(); i++)
			{
				Candidate newPrefixCand = prefixCand.clone();
				newPrefixCand.addItem(newSearchCand.removeItem(0));//每次SEARCH减少一个，prefix增加一个
					
				int h = searchCand.getItem(i).hashCode() % degree;
				count(newPrefixCand, newSearchCand, k, node.branches[h]);
				
			}
		}
	}
	
	public void output()
	{
		outputTree(root, 0);
	}
	
	private void outputTree(HashTreeNode root, int i)
	{
		if(root == null)
			return;
		if(root.isLeaf)
		{
			System.out.print(root.getLevel() + "," + i + ": ");
			for(Candidate ci : root.candidateSets)
			{
				System.out.print(ci.toString() + " count:" + ci.count + "   ");
			}
			System.out.println();
		}
		for(i=0; i<root.branches.length; i++)
		{
			outputTree(root.branches[i], i);
		}
	}

	public int getSplitThreashold() {
		return splitThreashold;
	}
}
