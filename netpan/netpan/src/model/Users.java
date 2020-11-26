package model;

import java.io.Serializable;

public class Users implements Serializable {

	public String toString() {
		return "username=" + username ;
	}

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 账户名. */
	private String username;

	/** 网盘密码. */
	private String password;

	/** 用户性别. */
	private String sex;

	/** 用户年龄. */
	private Integer age;

	/** 用户邮箱. */
	private String email;

	/** 用户电话. */
	private String tel;
    
	private int permission;
	
	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public Users() {
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getUsername() {
		return this.username;
	}


	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}


	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return this.sex;
	}

	public void setAge(Integer age) {
		this.age = age;
	}


	public Integer getAge() {
		return this.age;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTel() {
		return this.tel;
	}

	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode())+((email==null)?0:email.hashCode());
        return result;
        
	}


	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Users other = (Users) obj;
		if (username == null) {
			if (other.username != null) {
				return false;
			}
		} else if (!username.equals(other.username)) {
			return false;
		}
		if(email==null) {
			if(other.email!=null) {
				return false;
			}
		}
			else if(!email.equals(other.email)) {
				return false;
			}
		

		return true;
	}

}
