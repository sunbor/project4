package com.revature.model;

public class Answer {
	private int id;
	private String answer;
	private int correct;
	private int question;
	public Answer() {
		// TODO Auto-generated constructor stub
	}
	public Answer(int id, String answer, int correct, int question) {
		super();
		this.id = id;
		this.answer = answer;
		this.correct = correct;
		this.question = question;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public int getCorrect() {
		return correct;
	}
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	public int getQuestion() {
		return question;
	}
	public void setQuestion(int question) {
		this.question = question;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result + correct;
		result = prime * result + id;
		result = prime * result + question;
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
		Answer other = (Answer) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (correct != other.correct)
			return false;
		if (id != other.id)
			return false;
		if (question != other.question)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Answer [id=" + id + ", answer=" + answer + ", correct=" + correct + ", question=" + question + "]";
	}

}
