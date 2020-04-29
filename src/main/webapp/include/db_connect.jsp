<%@ page import="java.sql.*" %>
<%
String connectionURL = "jdbc:mysql://localhost:3306/remytutor";
Connection con = null;
Statement st = null;
Class.forName("com.mysql.jdbc.Driver").newInstance();
con = DriverManager.getConnection(connectionURL, "root", "root");
st = con.createStatement();
%>