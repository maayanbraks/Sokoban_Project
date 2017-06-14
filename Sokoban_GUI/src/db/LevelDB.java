package db;

import javax.persistence.*;

@Entity(name = "Level")
public class LevelDB implements DbInterface {

	@Id
	private String levelId;
	@Column(name = "Difficulty")
	private String difficulty;

	public LevelDB(String levelId, String difficulty) {
		this.levelId = levelId;
		this.difficulty = difficulty;
	}

	public String getLevelId() {
		return this.levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public String getDifficulty() {
		return this.difficulty ;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty  = difficulty;
	}

	@Override
	public String toString() {
		return "LevelDB [LevelId=" + this.levelId + ", Difficulty=" + this.difficulty  + "]";
	}
}
