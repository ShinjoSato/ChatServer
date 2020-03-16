package server;

import common.User;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
  
//    public static void go() {
//        String url = "mod-msc-sw1.cs.bham.ac.uk"; 
//        String sshurl = "tinky-winky.cs.bham.ac.uk"; 
//        String username = "zxs944"; 
//        String password = "Qwe13579"; 
//        
//        try{   
//            Class.forName("org.postgreql.Driver") ;    
//       }catch(ClassNotFoundException e){    
//            System.out.println("Can't find driver, driver loading failed!");    
//            e.printStackTrace() ;    
//       }    
//
//        try {
//          Connection conn = DriverManager.getConnection(url , username , password );
//
//        } catch (Exception e) {
//        	System.out.println("Database connected failed!");
//            e.printStackTrace();
//        }
//    }
//  
//	public static void go() {
//        String url = "mod-msc-sw1.cs.bham.ac.uk"; //远程PostgreSQL服务器
//        String sshurl = "tinky-winky.cs.bham.ac.uk"; //SSH服务器
//        String sshuser = "zxs944"; //SSH连接用户名
//        String sshpassword = "Qwe13579"; //SSH连接密码
//        try {
//            JSch jsch = new JSch();
//            Session session = jsch.getSession(sshuser, sshurl, 22);
//            session.setPassword(sshpassword);
//            session.setConfig("StrictHostKeyChecking", "no");
//            session.connect();
//            System.out.println(session.getServerVersion());//这里打印SSH服务器版本信息
//
//            int assinged_port = session.setPortForwardingL(5433, url, 5432);//端口映射 转发  数据库服务器地址url
//
//            System.out.println("localhost:" + assinged_port);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    public void createTable() throws SQLException{
//	   Statement stmt = null;
//	   Connection conn = null;
//	   conn = DriverManager.getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/wadirum");
//	   String sql = 
//			"CREATE TABLE t_user" + "(userID varchar(255) NOT NULL," +
//	        "userName String NOT NULL, " + "password String NOT NULL, " +
//	        "email String NOT NULL, " + "state String NOT NULL, " + "PRIMARY KEY (userID)";
//
//	    try {stmt = conn.createStatement();
//           stmt.executeUpdate(sql);
//      } catch (SQLException e) {
//	     e.printStackTrace();
//      } finally {
//      if (stmt != null) {stmt.close(); }
//   }
//		
//	}
    
    public void insertUser(User user) {
        
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       
        Connection conn = null;
        PreparedStatement ps = null;
        try {
      
            conn = DriverManager.getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/wadirum","wadirum","977fggzmir");
        	
            String sql = "INSERT INTO user_1(userid,username,password,email,state) VALUES(?,?,?,?,?);";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.setBoolean(5, user.isState());
           
//            System.out.print(ps.toString());
            ps.execute();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void updateUser(User user) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/wadirum");
            String sql = "update user set username = ?, name = ?, password = ? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserID());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void deleteUser(String id) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/wadirum");
            String sql = "delete from user where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public User selectUserById(String id) {
        User user = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;


        ResultSet rs = null;
        String sql = "select * from t_user where id = ?";
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/wadirum");
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);

            
            rs = ps.executeQuery();
          
            while (rs.next()) {
                user = new User();
                user.setUserID(rs.getString("userid"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return user;
    }

    public List<User> selectAll() {
        List<User> list = new ArrayList<>();
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/wadirum");
            sql = "SELECT * FROM user "+"";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getString("id"));
                user.setUserName(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return list;

    }
    
    
    
    public List<User> selectAllBut(String userID) {
        List<User> list = new ArrayList<>();
        list.add(new User("01", "Shinjo Shinjo",true));
        list.add(new User("02", "Yi-Ming Chen", true));
	    list.add(new User("03", "Zhengnan Sun", true));
        list.add(new User("04", "Saba Akhlagh-Nejat", true));
        list.add(new User("05", "Ibiyemi Ogunyemi", false));
       
        
        for(int i=0; i<list.size();i++) {
        	if(list.get(i).getUserID().equals(userID)) {
        		list.remove(i);
        	}
        }
        
        return list;

    }
    
      public static void main(String[] args) {
//    	  UserDao userDao = new UserDao();
//          UserDao a = new UserDao();
//          User DataUser = a.selectUserById("03");
//            User user = new User();
////          User user1 = new User();
//          user.setUserID("今晚打老虎");
////          user.setPassword("12345");
////          user.setUserName("qwe");
//          if(user.getUserID().equals(DataUser.getUserID())) {
//        	  System.out.println(true);
//          }else {
//        	  System.out.println(false);
//          }
          
//         
//          
//          user1.setUserID("99");
//          user1.setPassword("12345");
//          user1.setUserName("今晚打老虎");
//          userDao.insertUser(user1);
 //       userDao.updateUser(user);
//        userDao.deleteUser(05);
          // single search
 //         user = userDao.selectUserById("03");
  //        System.out.println(user.toString());
          //multiple search
//          List<User> userList = userDao.selectAll();
//          for (User e : userList){
//              System.out.println(e.toString());
//          }
          
      }
  }

