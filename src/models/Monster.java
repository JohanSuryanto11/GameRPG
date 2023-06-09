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
public class Monster {
    private int id;
    private String nama;
    private int maxhp;
    private int hp;
    private int atk;
    private int def;
    private int spd;
    
    public int getmaxhp(){
        return maxhp;
    }
    public int gethp(){
        return hp;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getnama(){
        return nama;
    }
    public void setnama(String nama){
        this.nama = nama;
    }
    public void sethp(int hp){
        this.hp=hp;
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("Insert into Monster " +
                    "(Id,Nama,Atk,Def,Spd,hp) values(?,?,?,?,?,?)");
            con.preparedStatement.setInt(1, this.id);
            con.preparedStatement.setString(2, this.nama);
            con.preparedStatement.setInt(3, this.atk);
            con.preparedStatement.setInt(4, this.def);
            con.preparedStatement.setInt(5, this.spd);
            con.preparedStatement.setInt(6, this.hp);
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
            ResultSet rs = con.statement.executeQuery("Select Id,Nama,Atk,Def,Spd,hp from Monster");
            int i = 1;
            while(rs.next()){
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getInt("Id"));
                row.add(rs.getString("Nama"));
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from Monster where Id = ?");
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
            ResultSet rs = con.statement.executeQuery("select id,Nama,Atk,Def,Spd,hp from Monster where Id = '"+Id+"'");
            while(rs.next()){
                this.id = rs.getInt("id");
                this.nama = rs.getString("Nama");
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
    
    public int validasiId(int Id){
        int val= 0;
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from Monster where Id =  '"+Id+"'");
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
    
    public boolean update(int Id){
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try{
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("update Monster set Nama=?,Atk=?,Def=?,Spd=?,hp=? where Id=?");
            con.preparedStatement.setString(1, nama);
            con.preparedStatement.setInt(2, atk);
            con.preparedStatement.setInt(3, def);
            con.preparedStatement.setInt(4, spd);
            con.preparedStatement.setInt(5, hp);
            con.preparedStatement.setInt(6, Id);
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
            ResultSet rs = con.statement.executeQuery("SELECT Id,Nama,Atk,Def,Spd,Hp FROM monster WHERE "+fld+" LIKE '%"+dt+"%'");
            int i=1;
            while(rs.next()){
            Vector<Object> row = new Vector <Object>();
            row.add(rs.getInt("Id"));
            row.add(rs.getString("Nama"));
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
    public int idbaru(){
        int val= 0;
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select max(idmonster) as jml from monster");
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
}
