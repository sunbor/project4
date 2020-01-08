package com.revature.dto;

import java.util.Arrays;

public class QuizAssignment {
	private int[] userIds;
	private int[] quizIds;
	public QuizAssignment() {
		// TODO Auto-generated constructor stub
	}
	public QuizAssignment(int[] userIds, int[] quizIds) {
		super();
		this.userIds = userIds;
		this.quizIds = quizIds;
	}
	public int[] getUserIds() {
		return userIds;
	}
	public void setUserIds(int[] userIds) {
		this.userIds = userIds;
	}
	public int[] getQuizIds() {
		return quizIds;
	}
	public void setQuizIds(int[] quizIds) {
		this.quizIds = quizIds;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(quizIds);
		result = prime * result + Arrays.hashCode(userIds);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuizAssignment other = (QuizAssignment) obj;
		if (!Arrays.equals(quizIds, other.quizIds))
			return false;
		if (!Arrays.equals(userIds, other.userIds))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "QuizAssignment [userIds=" + Arrays.toString(userIds) + ", quizIds=" + Arrays.toString(quizIds) + "]";
	}
	

}
