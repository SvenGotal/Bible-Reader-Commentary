package com.java.crv.BibleReaderCommentary.domainDTO;

public class VerseWithChapterAndBookInfoDTO {
	private Long verseId;
	private Integer verseNumber;
	private String verseText;
	private Integer chapterNumber;
	private String bookName;
	
	public Long getVerseId() {
		return verseId;
	}
	public void setVerseId(Long verseId) {
		this.verseId = verseId;
	}
	public Integer getVerseNumber() {
		return verseNumber;
	}
	public void setVerseNumber(Integer verseNumber) {
		this.verseNumber = verseNumber;
	}
	public String getVerseText() {
		return verseText;
	}
	public void setVerseText(String verseText) {
		this.verseText = verseText;
	}
	public Integer getChapterNumber() {
		return chapterNumber;
	}
	public void setChapterNumber(Integer chapterNumber) {
		this.chapterNumber = chapterNumber;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
}
