package com.revature.dto;

import java.util.List;

import com.revature.model.Answer;

public class QuestionDisplay {
	private int id;
	private String question;
	private List<Answer> answerList;
	public QuestionDisplay() {
		// TODO Auto-generated constructor stub
	}
	public QuestionDisplay(int id, String question, List<Answer> answerList) {
		super();
		this.id = id;
		this.question = question;
		this.answerList = answerList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public List<Answer> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<Answer> answerList) {
		this.answerList = answerList;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answerList == null) ? 0 : answerList.hashCode());
		result = prime * result + id;
		result = prime * result + ((question == null) ? 0 : question.hashCode());
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
		QuestionDisplay other = (QuestionDisplay) obj;
		if (answerList == null) {
			if (other.answerList != null)
				return false;
		} else if (!answerList.equals(other.answerList))
			return false;
		if (id != other.id)
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "QuestionDisplay [id=" + id + ", question=" + question + ", answerList=" + answerList + "]";
	}
	
	

}
