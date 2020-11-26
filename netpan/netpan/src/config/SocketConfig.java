package config;

public interface SocketConfig {
	
	public String ServerIP="localhost";
	public String Driver="com.mysql.jdbc.Driver";
	public String jdbcURL="jdbc:mysql://"+ServerIP+":3306/netpan?useSSL=false";
	public String Username="root";
	public String Password="root";
	public int ServerPort=9999;
	public String FileBasePath="E:/";

}
