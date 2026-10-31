package com.java.crv.BibleReaderCommentary.domainDTO;

public class VerseWithChapterAndBookInfoDTO {
	private Long verseId;
	private int verseNumber;
	private String verseText;
	private int chapterNumber;
	private String bookName;
	
	public VerseWithChapterAndBookInfoDTO(Long verseId, int verseNumber, String verseText, int chapterNumber, String bookName) {
		this.verseId = verseId;
		this.verseNumber = verseNumber;
		this.verseText = verseText;
		this.chapterNumber = chapterNumber;
		this.bookName = bookName;
	}
	
	public Long getVerseId() {
		return verseId;
	}
	public void setVerseId(Long verseId) {
		this.verseId = verseId;
	}
	public int getVerseNumber() {
		return verseNumber;
	}
	public void setVerseNumber(int verseNumber) {
		this.verseNumber = verseNumber;
	}
	public String getVerseText() {
		return verseText;
	}
	public void setVerseText(String verseText) {
		this.verseText = verseText;
	}
	public int getChapterNumber() {
		return chapterNumber;
	}
	public void setChapterNumber(int chapterNumber) {
		this.chapterNumber = chapterNumber;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
}
