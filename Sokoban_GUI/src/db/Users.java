package db;

import javax.persistence.*;

@Entity(name = "Users")
public class Users implements DbInterface {
	@Id
	private String nickname;

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Users(String nickname) {
		this.nickname = nickname;
	}

	public Users() {
	}

	@Override
	public String toString() {
		return "Users [Nickname=" + this.nickname + "]";
	}

}
