package model;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import config.SocketConfig;
public class UserDAO {

	public  boolean  registerUser(Users u) {
		boolean result=false;
		try {
			Class.forName(SocketConfig.Driver);
			Connection con=DriverManager.getConnection(SocketConfig.jdbcURL, SocketConfig.Username,SocketConfig.Password);
			PreparedStatement  pre=con.prepareStatement("insert into user values(?,?,?,?,?,?,?)");
		    pre.setString(1, u.getUsername());
			pre.setString(2, u.getPassword());
			pre.setString(3, u.getSex());
			pre.setInt(4, u.getAge());
			pre.setString(5, u.getEmail());
			pre.setString(6, u.getTel());
			pre.setInt(7, 0);
	      int count=pre.executeUpdate();
			result=count>0?true:false;
			pre.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public Users  login(String username,String password) {
		Users user=null;
		try {
			Class.forName(SocketConfig.Driver);
			Connection con=DriverManager.getConnection(SocketConfig.jdbcURL, SocketConfig.Username,SocketConfig.Password);
			PreparedStatement  pre=con.prepareStatement("select * from user where username=? and password=?");
			pre.setString(1, username);
			pre.setString(2, password);
			ResultSet rs=pre.executeQuery();
			if(rs.next()) {
				user=new Users();
				user.setAge(rs.getInt("age"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setSex(rs.getString("sex"));
				user.setEmail(rs.getString("email"));
				user.setTel(rs.getString("telephone"));
				user.setPermission(rs.getInt("permission"));
			}
			rs.close();
			pre.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	public Users check(String name) {
		Users user=null;
		try {
			Class.forName(SocketConfig.Driver);
			Connection con=DriverManager.getConnection(SocketConfig.jdbcURL, SocketConfig.Username,SocketConfig.Password);
			String sql = "select * from user where username="+name;
			PreparedStatement  pre=con.prepareStatement(sql);
			ResultSet resultSet=pre.executeQuery();
			if(resultSet.next()) {
				user=new Users();
		
			}
			pre.close();
			con.close();
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
		
	}
	public Users update(String emailString,String passString) {
		Users user=null;
		try {
			Class.forName(SocketConfig.Driver);
			Connection con=DriverManager.getConnection(SocketConfig.jdbcURL, SocketConfig.Username,SocketConfig.Password);
			String sql = "update user set password ='"+passString+"'where email like'" +emailString+ "'";
			PreparedStatement  pre=con.prepareStatement(sql);
			int i=pre.executeUpdate();
			pre.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
		
		
	}
	public Users updatePer(String username) {
		Users user=null;
		try {
			Class.forName(SocketConfig.Driver);
			Connection con=DriverManager.getConnection(SocketConfig.jdbcURL, SocketConfig.Username,SocketConfig.Password);
			String sql = "update user set permission = 1 where username like'" +username+ "'";
			PreparedStatement  pre=con.prepareStatement(sql);
			int i=pre.executeUpdate();
			pre.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
		
		
	}
}
