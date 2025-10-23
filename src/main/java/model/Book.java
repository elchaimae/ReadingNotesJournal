package model;

import java.io.Serializable;

public class Book implements Serializable {
	
	    private int id;
	    private int emotionsId;
	    private int genreId;
	    private String title;
	    private int publishedYear;
	    private String booksImage;
	    private String status;
	    private int progress;
	    private String startedDay;
	    private String finishedDay;
	    private String text;
	    private String createDay;
	    private String reviewTag;   
	    private String genreName;
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getEmotionsId() {
			return emotionsId;
		}
		public void setEmotionsId(int emotionsId) {
			this.emotionsId = emotionsId;
		}
		public int getGenreId() {
			return genreId;
		}
		public void setGenreId(int genreId) {
			this.genreId = genreId;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public int getPublishedYear() {
			return publishedYear;
		}
		public void setPublishedYear(int publishedYear) {
			this.publishedYear = publishedYear;
		}
		public String getBooksImage() {
			return booksImage;
		}
		public void setBooksImage(String booksImage) {
			this.booksImage = booksImage;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public int getProgress() {
			return progress;
		}
		public void setProgress(int progress) {
			this.progress = progress;
		}
		public String getStartedDay() {
			return startedDay;
		}
		public void setStartedDay(String startedDay) {
			this.startedDay = startedDay;
		}
		public String getFinishedDay() {
			return finishedDay;
		}
		public void setFinishedDay(String finishedDay) {
			this.finishedDay = finishedDay;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getCreateDay() {
			return createDay;
		}
		public void setCreateDay(String createDay) {
			this.createDay = createDay;
		}
		public String getReviewTag() {
			return reviewTag;
		}
		public void setReviewTag(String reviewTag) {
			this.reviewTag = reviewTag;
		}
		public String getGenreName() {
			return genreName;
		}
		public void setGenreName(String genreName) {
			this.genreName = genreName;
		}

	    // --- getter & setter ---
	   
	}
	
	
