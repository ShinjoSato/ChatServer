package server;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import common.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class d {
    public static void go() {
        String url = "mod-msc-sw1.cs.bham.ac.uk"; 
        String sshurl = "tinky-winky.cs.bham.ac.uk"; 
        String sshuser = "zxs944"; 
        String sshpassword = "123456"; 
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(sshuser, sshurl, 22);
            session.setPassword(sshpassword);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            
            int assinged_port = session.setPortForwardingL(5455, url, 5432);
            System.out.println("localhost:" + assinged_port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    //For register 
    public void insertUser(User user) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            go();
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5455/wadirum","wadirum","977fggzmir");
            
            String sql = "insert into t_user(username,password,name)values(?,?,?)";
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserID());  
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
            //ps.setString(5, user.isState());
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
    
    //edit adn upadte user profile
    public void updateUser(User user) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5455/postgres");
            String sql = "update t_user set username = ?, name = ?, password = ? where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUserID());  
            ps.setString(2, user.getUserName());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getEmail());
         //   ps.setString(5, user.isState());
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
    
    
    //delete user 
    public void deleteUser(String id) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5455/postgres");
            String sql = "delete from t_user where id = ?";
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

    
    public static User selectUserById(String ID) {
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
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5455/postgres");
            ps = conn.prepareStatement(sql);
            ps.setString(1, ID);

            rs = ps.executeQuery();
           
            while (rs.next()) {
                user = new User();
                
                user.setUserID(rs.getString("ID"));
                user.setUserName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                //user.isState(rs.get); //get boolean
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
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5455/postgres");
            sql = "SELECT * FROM t_user";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
//                user.setUsername(rs.getString("username"));
//                user.setPassword(rs.getString("password"));
//                user.setName(rs.getString("name"));
//                user.setId(rs.getLong("id"));
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