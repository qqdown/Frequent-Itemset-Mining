package pers.itemsetmining.util;

import java.util.ArrayList;
import java.util.List;

import pers.itemsetmining.CandidateItems;

public class HashTreeNode 
{
	public HashTreeNode[] branches;//子节点
	public List<CandidateItems> candidateSets;//候选项集合
	public boolean isLeaf;
	
	private int level;//所在层,根节点为0，不包含候选项
	private int degree;//散步程度
	
	public HashTreeNode(int level, int degree)
	{
		this.isLeaf = true;
		this.level = level;
		this.degree = degree;
		branches = new HashTreeNode[degree];
		candidateSets = new ArrayList<CandidateItems>();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}
}
