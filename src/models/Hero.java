/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Johan
 */
public class Hero {
    private String id;
    private String nickname;
    private int maxhp;
    private int exp;
    private int level;
    private int hp;
    private int atk;
    private int def;
    private int spd;
    private String password;
    public static String[] imgU = {"atas1.png", "atas2.png", "atas3.png"};
    public static String[] imgD = {"bawah1.png", "bawah2.png", "bawah3.png"};
    public static String[] imgL = {"kiri1.png", "kiri2.png", "kiri3.png"};
    public static String[] imgR = {"kanan1.png", "kanan2.png", "kanan3.png"};
    
    
    public String getid(){
        return id;
    }public void setid(String id){
        this.id = id;
    }
    public String getnickname(){
        return nickname;
    }
    public void setpassword(String password){
     this.password = password;   
    }
    public int getmaxhp(){
        return maxhp;
    }
    public int gethp(){
        return hp;
    }
    public void sethp(int hp){
        this.hp=hp;
    }
    public int getExp(){
        return this.exp;
    }
    public void setExp(int exp){
        this.exp = exp;
    }
    public int getLevel(){
        return this.level;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public int getAtk(){
        return this.atk;
    }
    public void setAtk(int atk){
        this.atk = atk;
    }
    public int getDef(){
        return this.def;
    }
    public void setDef(int def){
        this.def = def;
    }
    public int getSpd(){
        return this.spd;
    }
    public void setSpd(int spd){
        this.spd = spd;
    }

    public boolean insert(){
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try{
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("Insert into Hero " +
                    "(Id,Nickname,Exp,Level,Atk,Def,Spd, password, hp) values(?,?,?,?,?,?,?,?,?)");
            con.preparedStatement.setString(1, this.id);
            con.preparedStatement.setString(2, this.nickname);
            con.preparedStatement.setInt(3, this.exp);
            con.preparedStatement.setInt(4, this.level);
            con.preparedStatement.setInt(5, this.atk);
            con.preparedStatement.setInt(6, this.def);
            con.preparedStatement.setInt(7, this.spd);
            con.preparedStatement.setString(8, this.password);
            con.preparedStatement.setInt(9, this.hp);
            
            con.preparedStatement.executeUpdate();
            berhasil = true;
        }
        catch (Exception e){
            e.printStackTrace();
            berhasil = false;
        }
        finally{
            con.tutupKoneksi();
            return berhasil;
        }
    }
    
    public Vector Load(){
        try{
            Vector tableData = new Vector();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select Id,Nickname,Exp,Level,Atk,Def,Spd,Hp from Hero");
            int i = 1;
            while(rs.next()){
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("Id"));
                row.add(rs.getString("Nickname"));
                row.add(rs.getInt("Exp"));
                row.add(rs.getInt("Level"));
                row.add(rs.getInt("Atk"));
                row.add(rs.getInt("Def"));
                row.add(rs.getInt("Spd"));
                row.add(rs.getInt("Hp"));
                tableData.add(row);
                i++;
            }
            con.tutupKoneksi();
            return tableData;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public boolean delete(String Id){
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try{
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from Hero where Id = ?");
            con.preparedStatement.setString(1, Id);
            con.preparedStatement.executeUpdate();
            berhasil = true;
        }
        catch(Exception e){
            e.printStackTrace();
            berhasil = false;
        }
        finally{
            con.tutupKoneksi();
            return berhasil;
        }
     }
    
    public boolean select(String Id){
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select id,Nickname,Exp,Level,Atk,Def,Spd,hp from Hero where Id = '"+Id+"'");
            while(rs.next()){
                this.id = rs.getString("id");
                this.nickname = rs.getString("Nickname");
                this.exp = rs.getInt("Exp");
                this.level = rs.getInt("Level");
                this.atk = rs.getInt("Atk");
                this.def = rs.getInt("Def");
                this.spd = rs.getInt("Spd");
                this.hp = rs.getInt("hp");
                this.maxhp = rs.getInt("hp");
            }
            con.tutupKoneksi();
            return true;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    
    public int validasiId(String Id){
        int val= 0;
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from Hero where Id =  '"+Id+"'");
            while(rs.next()){
                val = rs.getInt("jml");
            }
            con.tutupKoneksi();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return val;
    }
    
    public boolean verifylogin(String Id,String password){
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select id, password from hero where id= '"+Id+"'");
            while(rs.next()){
                this.password = rs.getString("password");
            }
            con.tutupKoneksi();
            if(password.equals(this.password)){
               return true; 
            } else {return false;}
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean verifyregister(String Id){
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select id from hero where id= '"+Id+"'");
            while(rs.next()){
                this.id = rs.getString("id");
            }
            con.tutupKoneksi();
            if(Id.equals(this.id)){
               return true; 
            } else {return false;}
            
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    public boolean update(String Id){
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try{
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("update Hero set Nickname=?, Exp=?,Level=?,Atk=?,Def=?,Spd=?,hp=? where Id=?");
            con.preparedStatement.setString(1,nickname);
            con.preparedStatement.setInt(2,exp);
            con.preparedStatement.setInt(3, level);
            con.preparedStatement.setInt(4, atk);
            con.preparedStatement.setInt(5, def);
            con.preparedStatement.setInt(6, spd);
            con.preparedStatement.setInt(7, hp);
            con.preparedStatement.setString(8, Id);
            con.preparedStatement.executeUpdate();
            berhasil=true;
        }
        catch (Exception e){
            e.printStackTrace();
            berhasil=false;
        }
        finally {
            con.tutupKoneksi();
            return berhasil;
        }
    }
    public Vector LookUp(String fld,String dt){
        try{
            Vector tableData = new Vector();
            Koneksi con=new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT Id,Nickname,Exp,Level,Atk,Def,Spd,Hp FROM hero WHERE "+fld+" LIKE '%"+dt+"%'");
            int i=1;
            while(rs.next()){
            Vector<Object> row = new Vector <Object>();
            row.add(rs.getString("Id"));
            row.add(rs.getString("Nickname"));
            row.add(rs.getInt("Exp"));
            row.add(rs.getInt("Level"));
            row.add(rs.getInt("Atk"));
            row.add(rs.getInt("Def"));
            row.add(rs.getInt("Spd"));
            row.add(rs.getInt("Hp"));
            tableData.add(row);
            i++;
            }
            con.tutupKoneksi();
            return tableData;
        } catch (SQLException ex){ex.printStackTrace(); return null;}
    }
}
