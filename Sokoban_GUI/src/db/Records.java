package db;

import javax.persistence.*;

@Entity(name = "Records")
public class Records implements DbInterface {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int recordId;
	@Column(name = "Nickname")
	private String nickname;
	@Column(name = "LevelId")
	private String levelId;
	@Column(name = "Steps")
	private int steps;
	@Column(name = "Time")
	private int time;

	public Records(String nickname, String levelId, int steps, int time) {
		super();
		this.nickname = nickname;
		this.levelId = levelId;
		this.steps = steps;
		this.time = time;
	}

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLevelId() {
		return this.levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public int getSteps() {
		return this.steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	public int getTime() {
		return this.time;
	}

	public void setTime(int time) {
		this.time = time;
	}


	public Records() {
	}

	@Override
	public String toString() {
		return "Records [RecordId=" + recordId + ", Nickname=" + nickname + ", LevelId=" + levelId + ", Steps=" + steps
				+ ", Time=" + time + "]";
	}

}