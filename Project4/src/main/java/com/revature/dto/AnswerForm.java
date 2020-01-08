package com.revature.dto;

import java.util.Arrays;

public class AnswerForm {
	private int[] answers;
	private int[] questions;
	private int size;
	private int quizId;
	public AnswerForm() {
		// TODO Auto-generated constructor stub
	}
	public AnswerForm(int[] answers, int[] questions, int size, int quizId) {
		super();
		this.answers = answers;
		this.questions = questions;
		this.size = size;
		this.quizId = quizId;
	}
	public int[] getAnswers() {
		return answers;
	}
	public void setAnswers(int[] answers) {
		this.answers = answers;
	}
	public int[] getQuestions() {
		return questions;
	}
	public void setQuestions(int[] questions) {
		this.questions = questions;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(answers);
		result = prime * result + Arrays.hashCode(questions);
		result = prime * result + quizId;
		result = prime * result + size;
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
		AnswerForm other = (AnswerForm) obj;
		if (!Arrays.equals(answers, other.answers))
			return false;
		if (!Arrays.equals(questions, other.questions))
			return false;
		if (quizId != other.quizId)
			return false;
		if (size != other.size)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AnswerForm [answers=" + Arrays.toString(answers) + ", questions=" + Arrays.toString(questions)
				+ ", size=" + size + ", quizId=" + quizId + "]";
	}
	
}
