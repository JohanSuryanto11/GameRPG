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
 * @author johan
 */
public class Iventori {
    private int id;
    private String idhero;
    private String nama;
    private String tipe;
    private int hp;
    private int atk;
    private int def;
    private int spd;
    
    public int getid(){
        return id;
    }
    public void setid(int id){
        this.id = id;
    }
    public String getidhero(){
        return idhero;
    }
    public String getnama(){
        return nama;
    }
    public void setnama(String nama){
        this.nama = nama;
    }
    public String gettipe(){
        return tipe;
    }
    public void settipe(String tipe){
        this.tipe = tipe;
    }
    public void setidhero(String idhero){
        this.idhero = idhero;
    }
    public int gethp(){
        return hp;
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("Insert into iventori " +
                    "(Idiventori,idhero,nama,tipe,Atk,Def,Spd,hp) values(?,?,?,?,?,?,?,?)");
            con.preparedStatement.setInt(1, this.id);
            con.preparedStatement.setString(2, this.idhero);
            con.preparedStatement.setString(3, this.nama);
            con.preparedStatement.setString(4, this.tipe);
            con.preparedStatement.setInt(5, this.atk);
            con.preparedStatement.setInt(6, this.def);
            con.preparedStatement.setInt(7, this.spd);
            con.preparedStatement.setInt(8, this.hp);
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

    public boolean delete(int Id){
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try{
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from iventori where Idiventori = ?");
            con.preparedStatement.setInt(1, Id);
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
    
    public boolean select(int Id){
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select idiventori,nama,tipe,Atk,Def,Spd,hp from iventori where Idiventori = '"+Id+"'");
            while(rs.next()){
                this.id = rs.getInt("idiventori");
                this.nama=rs.getString("nama");
                this.tipe=rs.getString("tipe");
                this.atk = rs.getInt("Atk");
                this.def = rs.getInt("Def");
                this.spd = rs.getInt("Spd");
                this.hp = rs.getInt("hp");
            }
            con.tutupKoneksi();
            return true;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return false;
        }
    }
    public int cekgear(String idhero){
        int val= 0;
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from iventori where idhero ='"+idhero+"' and (tipe ='boot' or tipe = 'armor' or tipe = 'helm')");
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
    public int cekpedang(String idhero,String tipe){
        int val= 0;
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from iventori where idhero ='"+idhero+"' and tipe ='"+tipe+"'");
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
    public int idbaru(){
        int val= 0;
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select max(idiventori) as jml from iventori");
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

    public String carinama(int Id){
        String nama="";
        try{
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select nama from iventori where Idiventori = '"+Id+"'");
            while(rs.next()){
                nama=rs.getString("nama");
            }
            con.tutupKoneksi();
            return nama;
        }
        catch(SQLException ex){
            ex.printStackTrace();
            return "";
        }
    }
    public Vector LookUp(String fld,String dt){
        try{
            Vector tableData = new Vector();
            Koneksi con=new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT Idiventori,Idhero,nama,tipe,Atk,Def,Spd,Hp FROM Iventori WHERE "+fld+" LIKE '%"+dt+"%'");
            int i=1;
            while(rs.next()){
            Vector<Object> row = new Vector <Object>();
            row.add(rs.getInt("IdIventori"));
            row.add(rs.getString("IdHero"));
            row.add(rs.getString("nama"));
            row.add(rs.getString("tipe"));
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
    public Vector Load(){
        try{
            Vector tableData = new Vector();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select Idiventori,Idhero,nama,tipe,Atk,Def,Spd,Hp from Iventori");
            int i = 1;
            while(rs.next()){
                Vector<Object> row = new Vector<Object>();
                row.add(rs.getString("IdIventori"));
                row.add(rs.getString("IdHero"));
                row.add(rs.getString("nama"));
                row.add(rs.getString("tipe"));
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
}
