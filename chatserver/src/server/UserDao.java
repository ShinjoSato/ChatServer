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
//        String url = "mod-msc-sw1.cs.bham.ac.uk"; //餈�ostgreSQL���
//        String sshurl = "tinky-winky.cs.bham.ac.uk"; //SSH���
//        String sshuser = "zxs944"; //SSH餈�����
//        String sshpassword = "Qwe13579"; //SSH餈撖��
//        try {
//            JSch jsch = new JSch();
//            Session session = jsch.getSession(sshuser, sshurl, 22);
//            session.setPassword(sshpassword);
//            session.setConfig("StrictHostKeyChecking", "no");
//            session.connect();
//            System.out.println(session.getServerVersion());//餈��SSH�����靽⊥
//
//            int assinged_port = session.setPortForwardingL(5433, url, 5432);//蝡臬���� 頧砍��  ��摨�����url
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
        	
            String sql = "insert into user (username,password,email,state)values(?,?,?,?)";
       
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
      //      ps.setBoolean(4, user.getState());
            
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
            String sql = "update t_user set username = ?, name = ?, password = ? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getUserID());
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

    public void deleteUser(Long id) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://mod-msc-sw1.cs.bham.ac.uk/wadirum");
            String sql = "delete from t_user where id = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
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
                user.setUserID(rs.getString("userID"));
                user.setUserName(rs.getString("userName"));
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
            sql = "SELECT * FROM t_user";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUserID(rs.getString("ID"));
                user.setUserName(rs.getString("userName"));
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
}
    

