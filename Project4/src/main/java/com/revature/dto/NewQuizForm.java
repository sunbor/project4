package com.revature.dto;

import java.util.Arrays;

public class NewQuizForm {
	private String title;
	private int[] questionIds;
	public NewQuizForm() {
		// TODO Auto-generated constructor stub
	}
	public NewQuizForm(String title, int[] questionIds) {
		super();
		this.title = title;
		this.questionIds = questionIds;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int[] getQuestionIds() {
		return questionIds;
	}
	public void setQuestionIds(int[] questionIds) {
		this.questionIds = questionIds;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(questionIds);
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		NewQuizForm other = (NewQuizForm) obj;
		if (!Arrays.equals(questionIds, other.questionIds))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NewQuiz [title=" + title + ", questionIds=" + Arrays.toString(questionIds) + "]";
	}
	
	

}
