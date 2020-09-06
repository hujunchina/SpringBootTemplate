package com.hujunchina.client.base;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 管仲（胡军 hujun@tuya.com）
 * @Date 2020/9/4 11:32 下午
 * @Version 1.0
 * 一个sql类
 */
public class SQL {
    class Soldier{
        int grade;
        String echo;
    }
    public SQL(){
        try{
            Class.forName("cn.mysql.jdbc.Driver");
        }catch (ClassNotFoundException e){
            System.out.println("[FATAL] mysql class not found");
        }
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/redalert?characterEncoding=utf-8", "root", "hujun");
    }
    public int getTotal(){
        int total = 0;
        try(Connection c = getConnection(); Statement s = c.createStatement();){
            String sql = "select count(*) from soldier";
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                total = rs.getInt(1);
            }
            System.out.println("total soldiers is "+total);
        } catch (SQLException e) {
            System.out.println("[FATAL] invoke getTotal method fatal");
        }
        return total;
    }

    public void updateSoldier(Soldier soldier){
        String sql = "update soldier set grade=?,echo=? where sid=?";
        try(Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql);){
            ps.setInt(1, soldier.grade);
            ps.setString(2, soldier.echo);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("[FATAL] invoke updateSoldier method fatal");
        }
    }

    public void deletSolder(int sid){
        try(Connection c = getConnection(); Statement s = c.createStatement()){
            String sql = "delete from soldier where sid = "+sid;
            s.execute(sql);
        }catch (SQLException e){
            System.out.println("[FATAL] invoke deleteSoldier method fatal");
        }
    }

    public Soldier getSoldier(int sid){
        Soldier soldier = new Soldier();
        try(Connection c = getConnection(); Statement s = c.createStatement()){
            String sql = "select * from soldier where sid="+sid;
            ResultSet rs = s.executeQuery(sql);
            while(rs.next()){
                soldier.grade = (rs.getInt("grade"));
                soldier.echo = (rs.getString("echo"));
            }
        }catch (SQLException e){
            System.out.println("[FATAL] invoke getSoldier method fatal");
            soldier = null;
        }
        return soldier;
    }

    public List<Soldier> list(){
        return list(0, Short.MAX_VALUE);
    }

    public List<Soldier> list(int start, int count){
        List<Soldier> soldiers = new ArrayList<>();
        String sql = "select * from soldier order by id desc limit ?,?";
        try(Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, start);
            ps.setInt(2, start+count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                Soldier soldier = new Soldier();
                soldier.echo = (rs.getString("echo"));
                soldiers.add(soldier);
            }
        }catch (SQLException e){
            System.out.println("[FATAL] invoke listSoldier method fatal");
            soldiers = null;
        }
        return soldiers;
    }
}
