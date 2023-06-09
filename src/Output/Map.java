/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Output;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import models.Hero;
import models.Iventori;
import models.Koneksi;
import models.Monster;

/**
 *
 * @author Johan
 */
public class Map extends javax.swing.JFrame  {
    public static String[] amap = {"ruangan.png", "luarrumah.png", "wild.png","bos.png","Dungon.png"}; //daftar map
    public static String[] amapbattle = {"1", "2", "mapforest.png","mapbos.png","mapdungon.png"};// daftar background battle
    private int cskill2=0;
    private int cskill3=0;
    Iventori selecteditem = new Iventori();
    private boolean gerak=true;
    private int map = 1;
    private String arah = "";
    private int heroani = 0;
    private int[] iventori=new int[48];
    private int[][] posisicara;
    private int[][] batas;
    private int[][][] monster = new int[16][12][1];
    private int animasiherohit=1;
    Iventori pedang = new Iventori();
    Iventori helm = new Iventori();
    Iventori armor = new Iventori();
    Iventori boot = new Iventori();
    Iventori detaildrop = new Iventori();
    Monster m = new Monster();
    static Hero hero = new Hero();
    
    Timer t= new Timer(40, new ActionListener() { //animasi progress bar
        int animasi = 30;
        @Override
        public void actionPerformed(ActionEvent e) {
            if(m.gethp()<1){ // coding jika monster mati
                if(m.getId()==6){
                    dragon=true;
                    batas[10][6]=0;
                    batas[11][6]=0;
                    batas[12][6]=0;
                    batas[10][7]=0;
                    batas[11][7]=0;
                    batas[12][7]=0;
                    batas[10][8]=0;
                    batas[11][8]=0;
                    batas[12][8]=0;
                }
            System.out.println(m.getId());
            hero.setExp(hero.getExp()+100);
            updateexp();
            labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+amap[map-1])));
            bghpbarmonster.setVisible(false);
            hpbarmonster.setVisible(false);
            monster0.setVisible(true);
            pmonster.setVisible(false);
            pcara.setVisible(false);
            progres.setVisible(false);
            if(map==3||map==5){
                monster1.setVisible(true);
            monster2.setVisible(true);
            
            }
            if( map==5){
                monster3.setVisible(true);
            }
            cara.setVisible(true);
            modelcara.setVisible(false);
            monster4.setVisible(false);
            skill1.setVisible(false);
            skill2.setVisible(false);
            skill3.setVisible(false);
            gerak=true;
            drop();
            buang.setVisible(false);
            ((Timer)e.getSource()).stop();
        }
            int spdh = hero.getSpd()+pedang.getSpd()+helm.getSpd()+armor.getSpd()+boot.getSpd();
            int spdm = m.getSpd();
            int jrkh = 0;
            int jrkm = 0;
            if(spdm>spdh){
                jrkh = (10*spdh)/spdm;
                jrkm = 10;
            }else{
                jrkm = (10*spdm)/spdh;
                jrkh = 10;
            }
            pcara.setLocation(pcara.getX(), pcara.getY()+jrkh);
            pmonster.setLocation(pmonster.getX(), pmonster.getY()+jrkm);
            if(pcara.getY()>pmonster.getY()){
                if(pcara.getY()>=400){
                pcara.setLocation(pcara.getX(), 100+(pcara.getY()-400));
                skill1.setEnabled(true);
                if(cskill2<1){
                    skill2.setEnabled(true);
                } else System.out.println(cskill2);
                if(cskill3<1){
                    skill3.setEnabled(true);
                } else System.out.println(cskill3);
                ((Timer)e.getSource()).stop();
            }
            } else{
               if(pmonster.getY()>=400){ //monster menyerang
                hero.sethp(hero.gethp()-(m.getAtk()-hero.getDef()));
                if(hero.gethp()<=0){
                    gameover();
                    ((Timer)e.getSource()).stop();
                }
                updatehp();
                pmonster.setLocation(pcara.getX(), 100+(pmonster.getY()-400));
                animasiherohit = 1;
                animasimonster.start();
                ((Timer)e.getSource()).stop();
            } 
            }
      }
    });
    Timer animasimonster= new Timer(100, new ActionListener() { //animasi serangan monster

        @Override
        public void actionPerformed(ActionEvent e) {
            animasi.setVisible(true);
            animasi.setSize(48, 48);
            animasi.setLocation(modelcara.getX(), modelcara.getY());
            animasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/herohit"+animasiherohit+".png")));
            animasiherohit++;
            if(animasiherohit==5){
                animasi.setVisible(false);
                t.start();
              ((Timer)e.getSource()).stop();  
            }
        }
    });
    boolean mision = false;
    boolean dragon = false;
    boolean selamat = false;
    boolean selesai = false;
    public Map(Hero hero) {
        initComponents();
        this.hero = hero; //import data hero saat login
        pertama();
    }

    private void loadmap(int map){
switch(map){//load map
    case 1:{
        cara.setLocation(336, 476-15);
        labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+amap[map-1])));
        npc.setVisible(false);
        posisicara = new int[][]{
        {7,10}
    };
        batas = new int[][]{ //untuk batas gerakan hero
        {0,0,0,0,0,0,0,0,0,0,0,0,0},//0
        {0,0,0,0,0,0,0,0,0,0,0,0,0},//1
        {0,0,1,1,1,1,1,1,1,1,1,0,0},//2
        {0,0,1,1,1,0,0,0,0,0,1,0,0},//3
        {0,1,1,1,1,20,20,0,0,0,1,0,0},//4
        {0,1,1,1,0,0,0,0,0,0,1,0,0},//5
        {0,1,1,1,0,0,0,0,0,0,1,0,0},//6
        {0,1,1,1,0,1,1,0,0,0,0,2,1},//7
        {0,1,1,1,0,0,0,0,0,0,1,0,0},//8
        {0,1,1,1,1,1,1,1,0,0,1,0,0},//9
        {0,1,1,1,0,0,1,0,0,0,1,0,0},//10
        {0,1,1,1,1,0,0,0,0,0,1,1,0},//11
        {0,1,1,1,0,0,0,0,0,0,1,1,0},//12
        {0,1,1,1,1,0,0,0,0,0,0,1,0},//13
        {0,1,1,1,1,0,1,0,1,0,0,1,0},//14
        {0,0,1,1,1,0,1,0,1,1,1,1,0},//15
        {0,0,1,1,1,1,1,1,1,1,1,1,0}//16
        };
        break;
    }
    case 2:{
        monster0.setVisible(false);
        monster1.setVisible(false);
        monster2.setVisible(false);
        monster4.setVisible(false);
        cara.setLocation(48, 240-15);
        labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+amap[map-1])));
        posisicara = new int[][]{
        {1,5}
    };
        batas = new int[][]{
        {1,1,1,1,1,1,1,1,1,1,1,1,1},//0
        {1,1,1,1,3,0,0,0,1,0,0,0,0},//1
        {1,1,1,1,1,0,0,0,1,0,0,0,0},//2
        {1,0,0,0,0,0,0,0,1,0,0,0,0},//3
        {1,0,1,0,0,0,0,0,1,0,0,0,0},//4
        {1,0,0,0,0,0,0,0,1,0,0,0,0},//5
        {1,0,0,0,1,5,0,0,1,0,0,0,0},//6
        {1,0,0,0,1,6,0,0,1,0,0,0,0},//7
        {1,0,0,0,1,7,0,0,1,0,0,0,0},//8
        {1,0,0,0,0,0,0,0,1,0,0,0,0},//9
        {1,0,0,0,0,0,0,0,1,0,0,0,0},//10
        {1,0,0,0,0,0,0,0,1,0,0,0,0},//11
        {1,0,0,0,0,0,0,0,1,0,0,0,0},//12
        {1,0,0,0,0,0,0,0,1,0,0,0,0},//13
        {1,1,1,1,0,15,0,0,1,0,0,0,0},//14
        {1,1,1,1,1,1,0,0,1,0,0,0,0},//15
        {1,1,1,1,1,1,2,1,1,0,0,0,0}//16
        };
        npc.setVisible(true);
        npc.setSize(48,48);
        npc.setLocation(48*14, 48*5);
        npc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/npc.png")));
        
        break;
    }
    case 3:{
        monster2.setVisible(false);
        monster4.setVisible(false);
        monster3.setVisible(false);
        npc.setVisible(false);
        cara.setLocation(48, 288-15);
        labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+amap[map-1])));
        posisicara = new int[][]{
        {1,6}
    };
        batas = new int[][]{
        {1,1,1,1,1,1,3,1,1,1,1,1,1},//0
        {1,1,1,1,1,0,0,0,1,1,1,1,1},//1
        {1,1,1,1,1,0,0,0,0,1,1,1,1},//2
        {1,1,0,0,0,0,0,0,0,0,0,1,1},//3
        {1,0,0,0,0,0,0,0,1,1,0,0,1},//4
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//5
        {1,10,0,0,0,0,0,0,0,0,0,0,1},//6
        {1,1,0,0,0,0,0,0,0,1,1,0,1},//7
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//8
        {1,0,0,0,0,0,0,0,0,0,0,0,1},//9
        {1,0,0,0,0,1,1,1,0,0,0,0,1},//10
        {1,0,0,0,1,0,0,0,1,0,0,0,1},//11
        {1,0,0,0,1,0,0,0,0,0,0,0,1},//12
        {1,0,0,0,1,0,0,0,1,0,0,1,1},//13
        {1,1,0,1,1,1,0,1,1,0,0,0,1},//14
        {1,1,1,1,1,1,0,1,1,1,1,1,1},//15
        {1,1,1,1,1,1,2,1,1,1,1,1,1}//16
         };
        respawnmonster();
        break;
    }
    case 4:{
        monster1.setVisible(false);
        monster2.setVisible(false);
        monster4.setVisible(false);
        cara.setLocation(48, 288-15);
        labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+amap[map-1])));
        posisicara = new int[][]{{1,6}};
        batas = new int[][]{
        {1,1,1,1,1,1,3,1,1,1,1,1,1},//0
        {1,1,1,1,1,1,0,1,1,1,1,1,1},//1
        {1,1,1,1,1,1,0,1,1,1,1,1,1},//2
        {1,1,1,1,0,0,0,0,1,1,1,1,1},//3
        {1,1,1,1,0,0,0,0,0,0,1,1,1},//4
        {1,1,1,1,0,0,0,0,1,0,1,1,1},//5
        {1,1,1,1,1,1,1,1,1,0,1,1,1},//6
        {1,1,1,1,1,0,0,0,0,0,1,1,1},//7
        {1,1,1,1,1,0,0,0,0,0,1,1,1},//8
        {1,1,1,1,1,0,0,0,0,0,1,1,1},//9
        {1,1,1,1,1,0,0,0,0,0,1,1,1},//10
        {1,1,1,1,1,0,0,0,0,0,1,1,1},//11
        {1,1,1,1,1,0,0,0,0,0,1,1,1},//12
        {1,1,1,1,1,0,0,0,0,0,1,1,1},//13
        {1,1,1,1,1,0,0,0,0,0,1,1,1},//14
        {1,1,1,1,1,1,1,1,1,1,1,1,1},//15
        {1,1,1,1,1,1,1,1,1,1,1,1,1}//16
        };
        if(mision){
            npc.setVisible(true);
        npc.setSize(48,48);
        npc.setLocation(48*14,(48*7)-15);
        npc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/npc1.png")));
        batas[14][7]=16;
        }
        respawnmonster();
        break;
    }
    case 5:{
        cara.setLocation(48*8, (48*11)-15);
        labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/dungon.png")));
        posisicara = new int[][]{{8,11}};
        npc.setVisible(false);
        batas = new int[][]{
        {0,0,0,0,0,0,0,0,0,0,0,0,0},//0
        {1,1,1,1,1,1,1,1,1,1,1,1,1},//1
        {1,1,1,0,0,0,0,0,1,0,0,0,1},//2
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//3
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//4
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//5
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//6
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//7
        {1,1,0,0,0,0,0,0,0,0,0,0,11},//8
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//9
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//10
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//11
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//12
        {1,1,0,0,0,0,0,0,0,0,0,1,1},//13
        {1,1,0,0,0,0,0,0,0,0,0,0,1},//14
        {1,1,0,0,0,0,1,0,0,0,0,0,1},//15
        {1,1,1,1,1,1,1,1,1,1,1,1,1}//16
        };
        respawnmonster();
        break;
    }
} 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        animasi = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        closedrop = new javax.swing.JLabel();
        buang = new javax.swing.JButton();
        simpan = new javax.swing.JButton();
        desspd = new javax.swing.JLabel();
        desatk = new javax.swing.JLabel();
        destipe = new javax.swing.JLabel();
        desdef = new javax.swing.JLabel();
        destingkat = new javax.swing.JLabel();
        deshp = new javax.swing.JLabel();
        spd = new javax.swing.JLabel();
        dhp = new javax.swing.JLabel();
        def = new javax.swing.JLabel();
        atk = new javax.swing.JLabel();
        tipe = new javax.swing.JLabel();
        tingkat = new javax.swing.JLabel();
        drop = new javax.swing.JLabel();
        tambahan = new javax.swing.JLabel();
        monster3 = new javax.swing.JLabel();
        cara = new javax.swing.JLabel();
        npc = new javax.swing.JLabel();
        iven0 = new javax.swing.JLabel();
        iven1 = new javax.swing.JLabel();
        iven2 = new javax.swing.JLabel();
        iven3 = new javax.swing.JLabel();
        iven4 = new javax.swing.JLabel();
        iven5 = new javax.swing.JLabel();
        iven6 = new javax.swing.JLabel();
        iven7 = new javax.swing.JLabel();
        iven8 = new javax.swing.JLabel();
        iven9 = new javax.swing.JLabel();
        iven10 = new javax.swing.JLabel();
        iven11 = new javax.swing.JLabel();
        iven12 = new javax.swing.JLabel();
        iven13 = new javax.swing.JLabel();
        iven14 = new javax.swing.JLabel();
        iven15 = new javax.swing.JLabel();
        iven16 = new javax.swing.JLabel();
        iven17 = new javax.swing.JLabel();
        iven18 = new javax.swing.JLabel();
        iven19 = new javax.swing.JLabel();
        iven20 = new javax.swing.JLabel();
        iven21 = new javax.swing.JLabel();
        iven22 = new javax.swing.JLabel();
        iven23 = new javax.swing.JLabel();
        iven24 = new javax.swing.JLabel();
        iven25 = new javax.swing.JLabel();
        iven26 = new javax.swing.JLabel();
        iven27 = new javax.swing.JLabel();
        iven28 = new javax.swing.JLabel();
        iven29 = new javax.swing.JLabel();
        iven30 = new javax.swing.JLabel();
        iven31 = new javax.swing.JLabel();
        iven32 = new javax.swing.JLabel();
        iven33 = new javax.swing.JLabel();
        iven34 = new javax.swing.JLabel();
        iven35 = new javax.swing.JLabel();
        iven36 = new javax.swing.JLabel();
        iven37 = new javax.swing.JLabel();
        iven38 = new javax.swing.JLabel();
        iven39 = new javax.swing.JLabel();
        iven40 = new javax.swing.JLabel();
        iven41 = new javax.swing.JLabel();
        iven42 = new javax.swing.JLabel();
        iven43 = new javax.swing.JLabel();
        iven44 = new javax.swing.JLabel();
        iven45 = new javax.swing.JLabel();
        iven46 = new javax.swing.JLabel();
        iven47 = new javax.swing.JLabel();
        monster0 = new javax.swing.JLabel();
        monster1 = new javax.swing.JLabel();
        monster2 = new javax.swing.JLabel();
        monster4 = new javax.swing.JLabel();
        modelcara = new javax.swing.JLabel();
        iconcara = new javax.swing.JLabel();
        hp = new javax.swing.JLabel();
        hpbar = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        expbar = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        skill1 = new javax.swing.JButton();
        skill2 = new javax.swing.JButton();
        skill3 = new javax.swing.JButton();
        pcara = new javax.swing.JLabel();
        pmonster = new javax.swing.JLabel();
        progres = new javax.swing.JPanel();
        hpbarmonster = new javax.swing.JPanel();
        bghpbarmonster = new javax.swing.JPanel();
        labelmap = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        getContentPane().setLayout(null);

        animasi.setText("jLabel2");
        getContentPane().add(animasi);
        animasi.setBounds(70, 20, 34, 14);

        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });
        getContentPane().add(close);
        close.setBounds(0, 0, 48, 48);

        closedrop.setText("jLabel2");
        closedrop.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closedropMouseClicked(evt);
            }
        });
        getContentPane().add(closedrop);
        closedrop.setBounds(110, 40, 34, 14);

        buang.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        buang.setText("Buang");
        buang.setFocusable(false);
        buang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buangActionPerformed(evt);
            }
        });
        getContentPane().add(buang);
        buang.setBounds(500, 470, 100, 31);

        simpan.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        simpan.setText("Simpan");
        simpan.setFocusable(false);
        simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanActionPerformed(evt);
            }
        });
        getContentPane().add(simpan);
        simpan.setBounds(370, 470, 100, 30);

        desspd.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        desspd.setText("jLabel2");
        getContentPane().add(desspd);
        desspd.setBounds(480, 370, 120, 40);

        desatk.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        desatk.setText("jLabel2");
        getContentPane().add(desatk);
        desatk.setBounds(480, 300, 100, 30);

        destipe.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        destipe.setText("jLabel2");
        getContentPane().add(destipe);
        destipe.setBounds(480, 220, 110, 30);

        desdef.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        desdef.setText("jLabel2");
        getContentPane().add(desdef);
        desdef.setBounds(480, 340, 110, 29);

        destingkat.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        destingkat.setText("jLabel2");
        getContentPane().add(destingkat);
        destingkat.setBounds(480, 179, 110, 40);

        deshp.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        deshp.setText("jLabel2");
        getContentPane().add(deshp);
        deshp.setBounds(480, 250, 80, 40);

        spd.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        spd.setText("Spd :");
        getContentPane().add(spd);
        spd.setBounds(410, 370, 60, 40);

        dhp.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        dhp.setText("Hp :");
        getContentPane().add(dhp);
        dhp.setBounds(420, 250, 60, 40);

        def.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        def.setText("Def :");
        getContentPane().add(def);
        def.setBounds(410, 330, 90, 50);

        atk.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        atk.setText("Atk :");
        getContentPane().add(atk);
        atk.setBounds(410, 300, 60, 30);

        tipe.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tipe.setText("Tipe :");
        getContentPane().add(tipe);
        tipe.setBounds(400, 220, 70, 29);

        tingkat.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        tingkat.setText("Tingkat :");
        getContentPane().add(tingkat);
        tingkat.setBounds(370, 180, 100, 40);

        drop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/detpedang.png"))); // NOI18N
        drop.setText("jLabel2");
        drop.setMaximumSize(new java.awt.Dimension(480, 480));
        drop.setMinimumSize(new java.awt.Dimension(480, 480));
        drop.setPreferredSize(new java.awt.Dimension(480, 480));
        getContentPane().add(drop);
        drop.setBounds(160, 190, 190, 190);

        tambahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/drop.png"))); // NOI18N
        tambahan.setText("jLabel2");
        tambahan.setPreferredSize(new java.awt.Dimension(480, 480));
        getContentPane().add(tambahan);
        tambahan.setBounds(144, 48, 480, 480);

        monster3.setText("jLabel2");
        getContentPane().add(monster3);
        monster3.setBounds(30, 0, 34, 14);

        cara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bawah2.png"))); // NOI18N
        getContentPane().add(cara);
        cara.setBounds(340, 480, 50, 50);

        npc.setText("jLabel2");
        getContentPane().add(npc);
        npc.setBounds(760, 10, 34, 14);

        iven0.setText("jLabel1");
        iven0.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven0MouseClicked(evt);
            }
        });
        getContentPane().add(iven0);
        iven0.setBounds(0, 0, 34, 14);

        iven1.setText("jLabel2");
        iven1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven1MouseClicked(evt);
            }
        });
        getContentPane().add(iven1);
        iven1.setBounds(750, 10, 34, 14);

        iven2.setText("jLabel3");
        iven2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven2MouseClicked(evt);
            }
        });
        getContentPane().add(iven2);
        iven2.setBounds(760, 30, 34, 14);

        iven3.setText("jLabel4");
        iven3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven3MouseClicked(evt);
            }
        });
        getContentPane().add(iven3);
        iven3.setBounds(630, 10, 34, 14);

        iven4.setText("jLabel5");
        iven4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven4MouseClicked(evt);
            }
        });
        getContentPane().add(iven4);
        iven4.setBounds(530, 10, 34, 14);

        iven5.setText("jLabel6");
        iven5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven5MouseClicked(evt);
            }
        });
        getContentPane().add(iven5);
        iven5.setBounds(460, 20, 34, 14);

        iven6.setText("jLabel7");
        iven6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven6MouseClicked(evt);
            }
        });
        getContentPane().add(iven6);
        iven6.setBounds(370, 10, 34, 14);

        iven7.setText("jLabel8");
        iven7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven7MouseClicked(evt);
            }
        });
        getContentPane().add(iven7);
        iven7.setBounds(320, 10, 34, 14);

        iven8.setText("jLabel9");
        iven8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven8MouseClicked(evt);
            }
        });
        getContentPane().add(iven8);
        iven8.setBounds(270, 10, 34, 14);

        iven9.setText("jLabel10");
        iven9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven9MouseClicked(evt);
            }
        });
        getContentPane().add(iven9);
        iven9.setBounds(190, 10, 40, 14);

        iven10.setText("jLabel11");
        iven10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven10MouseClicked(evt);
            }
        });
        getContentPane().add(iven10);
        iven10.setBounds(120, 20, 40, 14);

        iven11.setText("jLabel12");
        iven11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven11MouseClicked(evt);
            }
        });
        getContentPane().add(iven11);
        iven11.setBounds(510, 10, 40, 14);

        iven12.setText("jLabel13");
        iven12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven12MouseClicked(evt);
            }
        });
        getContentPane().add(iven12);
        iven12.setBounds(480, 10, 40, 14);

        iven13.setText("jLabel14");
        iven13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven13MouseClicked(evt);
            }
        });
        getContentPane().add(iven13);
        iven13.setBounds(630, 20, 40, 14);

        iven14.setText("jLabel15");
        iven14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven14MouseClicked(evt);
            }
        });
        getContentPane().add(iven14);
        iven14.setBounds(570, 20, 40, 14);

        iven15.setText("jLabel16");
        iven15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven15MouseClicked(evt);
            }
        });
        getContentPane().add(iven15);
        iven15.setBounds(750, 20, 40, 14);

        iven16.setText("jLabel17");
        iven16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven16MouseClicked(evt);
            }
        });
        getContentPane().add(iven16);
        iven16.setBounds(680, 20, 40, 14);

        iven17.setText("jLabel18");
        iven17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven17MouseClicked(evt);
            }
        });
        getContentPane().add(iven17);
        iven17.setBounds(740, 70, 40, 14);

        iven18.setText("jLabel19");
        iven18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven18MouseClicked(evt);
            }
        });
        getContentPane().add(iven18);
        iven18.setBounds(740, 20, 40, 14);

        iven19.setText("jLabel20");
        iven19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven19MouseClicked(evt);
            }
        });
        getContentPane().add(iven19);
        iven19.setBounds(760, 20, 40, 14);

        iven20.setText("jLabel21");
        iven20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven20MouseClicked(evt);
            }
        });
        getContentPane().add(iven20);
        iven20.setBounds(640, 10, 40, 14);

        iven21.setText("jLabel22");
        iven21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven21MouseClicked(evt);
            }
        });
        getContentPane().add(iven21);
        iven21.setBounds(680, 10, 40, 14);

        iven22.setText("jLabel23");
        iven22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven22MouseClicked(evt);
            }
        });
        getContentPane().add(iven22);
        iven22.setBounds(710, 30, 40, 14);

        iven23.setText("jLabel24");
        iven23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven23MouseClicked(evt);
            }
        });
        getContentPane().add(iven23);
        iven23.setBounds(740, 10, 40, 14);

        iven24.setText("jLabel25");
        iven24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven24MouseClicked(evt);
            }
        });
        getContentPane().add(iven24);
        iven24.setBounds(760, 60, 40, 14);

        iven25.setText("jLabel26");
        iven25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven25MouseClicked(evt);
            }
        });
        getContentPane().add(iven25);
        iven25.setBounds(740, 30, 40, 14);

        iven26.setText("jLabel27");
        iven26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven26MouseClicked(evt);
            }
        });
        getContentPane().add(iven26);
        iven26.setBounds(730, 0, 60, 40);

        iven27.setText("jLabel28");
        iven27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven27MouseClicked(evt);
            }
        });
        getContentPane().add(iven27);
        iven27.setBounds(760, 30, 40, 14);

        iven28.setText("jLabel29");
        iven28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven28MouseClicked(evt);
            }
        });
        getContentPane().add(iven28);
        iven28.setBounds(720, 80, 40, 14);

        iven29.setText("jLabel30");
        iven29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven29MouseClicked(evt);
            }
        });
        getContentPane().add(iven29);
        iven29.setBounds(580, 10, 40, 14);

        iven30.setText("jLabel31");
        iven30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven30MouseClicked(evt);
            }
        });
        getContentPane().add(iven30);
        iven30.setBounds(780, 80, 40, 14);

        iven31.setText("jLabel32");
        iven31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven31MouseClicked(evt);
            }
        });
        getContentPane().add(iven31);
        iven31.setBounds(750, 30, 40, 14);

        iven32.setText("jLabel33");
        iven32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven32MouseClicked(evt);
            }
        });
        getContentPane().add(iven32);
        iven32.setBounds(700, 0, 40, 14);

        iven33.setText("jLabel34");
        iven33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven33MouseClicked(evt);
            }
        });
        getContentPane().add(iven33);
        iven33.setBounds(730, 30, 40, 14);

        iven34.setText("jLabel35");
        iven34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven34MouseClicked(evt);
            }
        });
        getContentPane().add(iven34);
        iven34.setBounds(760, 40, 40, 14);

        iven35.setText("jLabel36");
        iven35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven35MouseClicked(evt);
            }
        });
        getContentPane().add(iven35);
        iven35.setBounds(730, 70, 40, 14);

        iven36.setText("jLabel37");
        iven36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven36MouseClicked(evt);
            }
        });
        getContentPane().add(iven36);
        iven36.setBounds(730, 40, 40, 14);

        iven37.setText("jLabel38");
        iven37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven37MouseClicked(evt);
            }
        });
        getContentPane().add(iven37);
        iven37.setBounds(760, 20, 40, 14);

        iven38.setText("jLabel39");
        iven38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven38MouseClicked(evt);
            }
        });
        getContentPane().add(iven38);
        iven38.setBounds(720, 40, 40, 14);

        iven39.setText("jLabel40");
        iven39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven39MouseClicked(evt);
            }
        });
        getContentPane().add(iven39);
        iven39.setBounds(740, 40, 40, 14);

        iven40.setText("jLabel41");
        iven40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven40MouseClicked(evt);
            }
        });
        getContentPane().add(iven40);
        iven40.setBounds(580, 0, 40, 14);

        iven41.setText("jLabel42");
        iven41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven41MouseClicked(evt);
            }
        });
        getContentPane().add(iven41);
        iven41.setBounds(760, 40, 40, 14);

        iven42.setText("jLabel43");
        iven42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven42MouseClicked(evt);
            }
        });
        getContentPane().add(iven42);
        iven42.setBounds(740, 30, 40, 14);

        iven43.setText("jLabel44");
        iven43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven43MouseClicked(evt);
            }
        });
        getContentPane().add(iven43);
        iven43.setBounds(750, 20, 40, 14);

        iven44.setText("jLabel45");
        iven44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven44MouseClicked(evt);
            }
        });
        getContentPane().add(iven44);
        iven44.setBounds(570, 10, 40, 14);

        iven45.setText("jLabel46");
        iven45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven45MouseClicked(evt);
            }
        });
        getContentPane().add(iven45);
        iven45.setBounds(740, 30, 40, 14);

        iven46.setText("jLabel47");
        iven46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven46MouseClicked(evt);
            }
        });
        getContentPane().add(iven46);
        iven46.setBounds(750, 30, 40, 14);

        iven47.setText("jLabel48");
        iven47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iven47MouseClicked(evt);
            }
        });
        getContentPane().add(iven47);
        iven47.setBounds(750, 40, 40, 14);

        monster0.setText("jLabel1");
        getContentPane().add(monster0);
        monster0.setBounds(770, 80, 34, 14);

        monster1.setText("jLabel1");
        getContentPane().add(monster1);
        monster1.setBounds(560, 10, 34, 14);

        monster2.setText("jLabel2");
        getContentPane().add(monster2);
        monster2.setBounds(620, 10, 34, 14);
        getContentPane().add(monster4);
        monster4.setBounds(770, 80, 0, 0);

        modelcara.setText("jLabel1");
        getContentPane().add(modelcara);
        modelcara.setBounds(10, 90, 34, 14);

        iconcara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/iconcara.png"))); // NOI18N
        iconcara.setText("jLabel1");
        iconcara.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                iconcaraMouseClicked(evt);
            }
        });
        getContentPane().add(iconcara);
        iconcara.setBounds(680, 480, 140, 140);

        hp.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        hp.setForeground(new java.awt.Color(255, 0, 0));
        hp.setText("HP :");
        getContentPane().add(hp);
        hp.setBounds(450, 600, 40, 20);

        hpbar.setBackground(new java.awt.Color(0, 204, 0));
        getContentPane().add(hpbar);
        hpbar.setBounds(490, 600, 200, 20);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("EXP :");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 600, 50, 22);

        expbar.setBackground(new java.awt.Color(255, 255, 255));
        expbar.setPreferredSize(new java.awt.Dimension(200, 20));
        expbar.setRequestFocusEnabled(false);
        getContentPane().add(expbar);
        expbar.setBounds(50, 600, 200, 20);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        getContentPane().add(jPanel1);
        jPanel1.setBounds(490, 600, 200, 20);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        getContentPane().add(jPanel2);
        jPanel2.setBounds(50, 600, 200, 20);

        skill1.setText("Skill 1");
        skill1.setFocusable(false);
        skill1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skill1ActionPerformed(evt);
            }
        });
        getContentPane().add(skill1);
        skill1.setBounds(450, 560, 73, 23);

        skill2.setText("Skill 2");
        skill2.setFocusable(false);
        skill2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skill2ActionPerformed(evt);
            }
        });
        getContentPane().add(skill2);
        skill2.setBounds(530, 560, 70, 23);

        skill3.setText("Skill 3");
        skill3.setFocusable(false);
        skill3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skill3ActionPerformed(evt);
            }
        });
        getContentPane().add(skill3);
        skill3.setBounds(610, 560, 73, 23);

        pcara.setText("jLabel2");
        getContentPane().add(pcara);
        pcara.setBounds(770, 10, 34, 14);

        pmonster.setText("jLabel2");
        getContentPane().add(pmonster);
        pmonster.setBounds(570, 10, 34, 14);
        getContentPane().add(progres);
        progres.setBounds(50, 100, 10, 348);
        getContentPane().add(hpbarmonster);
        hpbarmonster.setBounds(490, 20, 60, 10);

        bghpbarmonster.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 0, 0)));
        getContentPane().add(bghpbarmonster);
        bghpbarmonster.setBounds(60, 40, 30, 20);

        labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ruangan.png"))); // NOI18N
        labelmap.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelmapMouseClicked(evt);
            }
        });
        getContentPane().add(labelmap);
        labelmap.setBounds(0, 0, 816, 620);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if(gerak==true){
        switch(evt.getKeyCode()){
            case KeyEvent.VK_SPACE :{
                int isi=0;
                int tipemonster=0;
                switch(arah){
                    case "atas":{
                        isi=batas[posisicara[0][0]][posisicara[0][1]-1]; //mendeteksi adanya monster / yang lainnya
                        tipemonster=monster[posisicara[0][0]][posisicara[0][1]-1][0]; // mengambil data monster (Id)
                        break;
                    }
                    case "bawah":{
                        isi=batas[posisicara[0][0]][posisicara[0][1]+1];
                        tipemonster=monster[posisicara[0][0]][posisicara[0][1]+1][0];
                        break;
                    }
                    case "kanan":{
                        isi=batas[posisicara[0][0]+1][posisicara[0][1]];
                        tipemonster=monster[posisicara[0][0]+1][posisicara[0][1]][0];
                        break;
                    }
                    case "kiri":{
                        isi=batas[posisicara[0][0]-1][posisicara[0][1]];
                        tipemonster=monster[posisicara[0][0]-1][posisicara[0][1]][0];
                        break;
                    }
                }
                switch(isi){
                            case 5:{
                                iven("weapon"); //membuka iventori pedang
                                break;
                            }
                            case 6:{
                                iven("food"); // membuka iventori food
                                break;
                            }
                            case 7:{
                                iven("gear"); // membuka iventori helm armor dan boot
                                break;
                            }
                            case 9 :{
                                battle(tipemonster); 
                                break;
                            }
                            case 15:{
                                if(selesai){
                                    JOptionPane.showMessageDialog(this, "Terimakasih pahlawan "+hero.getnickname()); // misi selesai
                                }else if(selamat){
                                    JOptionPane.showMessageDialog(this, "Terimakasih pahlawan "+hero.getnickname()); 
                                    hero.setExp(hero.getExp()+1000);
                                    updateexp();
                                    selesai = true;
                                }else if(mision){
                                    JOptionPane.showMessageDialog(this, "Dia di bawa masuk ke arah hutan "); // petunjuk misi
                                } else {
                                 JOptionPane.showMessageDialog(this, "Pahlawan "+hero.getnickname()+" tolong selamatkan teman saya dibawa oleh dragon !"); //misi start
                                 mision = true;
                                }
                                    
                                
                                
                                break;
                                    
                            }
                            case 16 :{
                                if(dragon){
                                    JOptionPane.showMessageDialog(this, "Terimakasih Pahlawan "+hero.getnickname()+" sekarang Mari Pulang"); // terselamatkan
                                    npc.setVisible(false);
                                    batas[14][7]=0;
                                    selamat = true;
                                }else {
                                    JOptionPane.showMessageDialog(this, "Tolong Kalahkan Dragon itu "); // jika misi utama belum terselesaikan
                                }
                                break;
                            }
                            case 20:{
                        save(); // untuk save (ditempat tidur)
                        break;
                    }
                        }
                break;
            }
            case KeyEvent.VK_DOWN : {
                arah="bawah";
                animasi(arah);
                switch(batas[posisicara[0][0]][posisicara[0][1]+1]){
                    case 0 :{
                        cara.setLocation(cara.getLocation().x, cara.getLocation().y+48);
                        posisicara[0][1]=posisicara[0][1]+1;
                        break;
                    }
                    case 2 :{
                        map++;//pindah map
                        loadmap(map);
                        break;
                    }
                    case 3 :{
                        switch(map){
                            case 3 : {
                                map--; //pindah map
                                loadmap(map);
                                cara.setLocation(720, 288-15);
                                posisicara = new int[][]{{15,6}};
                                break;
                            }
                            case 4 : {
                                map--;//pindah map
                                loadmap(map);
                                cara.setLocation(720, 288-15);
                                posisicara = new int[][]{{15,6}};
                                break;
                            }
                            default : {
                                map--;//pindah map
                                loadmap(map);
                                break;
                            }
                        }
                        break;
                    }
                    case 11:{
                        map =3;
                        loadmap(map);//pindah map
                        cara.setLocation(48*6, (48*2)-15);
                        posisicara = new int[][]{{6,2}};
                        break;
                    }
                    
                }
                break;
            }
            case KeyEvent.VK_LEFT : {
                arah="kiri";
                animasi(arah);
                switch(batas[posisicara[0][0]-1][posisicara[0][1]]){
                    case 0 :{
                        posisicara[0][0]=posisicara[0][0]-1;
                        cara.setLocation(cara.getLocation().x-48, cara.getLocation().y); 
                        break;
                    }
                    case 2 :{
                        map++;//pindah map
                        loadmap(map);
                        break;
                    }
                    case 3 :{
                        switch(map){
                            case 3 : {
                                map--;//pindah map
                                loadmap(map);
                                cara.setLocation(720, 288-15);
                                posisicara = new int[][]{{15,6}};
                                break;
                            }
                            case 4 : {
                                map--;//pindah map
                                loadmap(map);
                                cara.setLocation(720, 288-15);
                                posisicara = new int[][]{{15,6}};
                                break;
                            }
                            default : {
                                map--;//pindah map
                                loadmap(map);
                                break;
                            }
                            
                        }
                        break;
                    }
                }
                break;
            }
            case KeyEvent.VK_RIGHT: {
                arah="kanan";
                animasi(arah);
                switch(batas[posisicara[0][0]+1][posisicara[0][1]]){
                    case 0 :{
                        posisicara[0][0]=posisicara[0][0]+1;
                        cara.setLocation(cara.getLocation().x+48, cara.getLocation().y);
                        break;
                    }
                    case 2 :{
                        map++;//pindah map
                        loadmap(map);
                        break;
                    }
                    case 3 :{
                        switch(map){
                            case 3 : {
                                map--;//pindah map
                                loadmap(map);
                                cara.setLocation(720, 288-15);
                                posisicara = new int[][]{{15,6}};
                                break;
                            }
                            case 4 : {
                                map--;//pindah map
                                loadmap(map);
                                cara.setLocation(720, 288-15);
                                posisicara = new int[][]{{15,6}};
                                break;
                            }
                            default : {
                                map--;//pindah map
                                loadmap(map);
                                break;
                            }
                            
                        }
                        break;
                    }
                }
                break;
            }
            case KeyEvent.VK_UP : {
                arah = "atas";
                animasi(arah);
                switch(batas[posisicara[0][0]][posisicara[0][1]-1]){
                    case 0 :{
                        posisicara[0][1]=posisicara[0][1]-1;
                        cara.setLocation(cara.getLocation().x, cara.getLocation().y-48);
                        break;
                    }
                    case 2 :{
                        map++;//pindah map
                        loadmap(map);
                        break;
                    }
                    case 3 :{
                        switch(map){
                            case 3 : {
                                map--;//pindah map
                                loadmap(map);
                                cara.setLocation(720, 288-15);
                                posisicara = new int[][]{{15,6}};
                                break;
                            }
                            case 4 : {
                                map--;//pindah map
                                loadmap(map);
                                cara.setLocation(720, 288-15);
                                posisicara = new int[][]{{15,6}};
                                break;
                            }
                            default : {
                                map--;//pindah map
                                loadmap(map);
                                break;
                            }
                            
                        }
                        break;
                    }
                    case 10:{
                        map=5;//pindah map
                        loadmap(5);
                    }
                }
                break;
            }
        }
    }
   
    }//GEN-LAST:event_formKeyPressed
    private void battle(int tipemonster){ // battle
        switch(arah){
                    case "atas":{
                       batas[posisicara[0][0]][posisicara[0][1]-1]=0; //menghapus monster
                        break;
                    }
                    case "bawah":{
                        batas[posisicara[0][0]][posisicara[0][1]+1]=0;
                        break;
                    }
                    case "kanan":{
                        batas[posisicara[0][0]+1][posisicara[0][1]]=0;
                        break;
                    }
                    case "kiri":{
                        batas[posisicara[0][0]-1][posisicara[0][1]]=0;
                        break;
                    }
        }
        gerak=false;
        bghpbarmonster.setVisible(true);
        hpbarmonster.setVisible(true);
        monster0.setVisible(false);
        pmonster.setVisible(true);
        monster3.setVisible(false);
        pcara.setVisible(true);
        progres.setVisible(true);
        monster1.setVisible(false);
        monster2.setVisible(false);
        cara.setVisible(false);
        modelcara.setVisible(true);
        monster4.setVisible(true);
        skill1.setVisible(true);
        skill2.setVisible(true);
        skill3.setVisible(true);
        cskill2 = 0;
        cskill3 = 0;
        m.select(Integer.toString(tipemonster));
        hpbarmonster.setSize(700, 20);
        bghpbarmonster.setSize(700, 20);
        hpbarmonster.setLocation(50, 50);
        bghpbarmonster.setLocation(50, 50);
        hpbarmonster.setBackground(Color.green);
        pmonster.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+m.getnama()+"icon.png")));
        pmonster.setSize(48, 48);
        pmonster.setLocation(30, 100);
        pcara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/bawah1.png")));
        pcara.setSize(48, 48);
        pcara.setLocation(30, 100);
        labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+amapbattle[map-1])));
        modelcara.setLocation(288,384);
        modelcara.setSize(48, 48);
        modelcara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/kanan1.png")));
        monster4.setLocation(480,288);
        monster4.setSize(192, 192);
        monster4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+m.getnama()+".png")));
        int spdc = hero.getSpd();
        int spdm = m.getSpd();
        t.start();
        
       
    }
               
    private void pertama(){
        loadmap(map);
        setExtendedState(Map.MAXIMIZED_BOTH);
        npc.setVisible(false);
        simpan.setVisible(false);
        buang.setVisible(false);
        tingkat.setVisible(false);
        tipe.setVisible(false);
        monster3.setVisible(false);
        dhp.setVisible(false);
        atk.setVisible(false);
        def.setVisible(false);
        spd.setVisible(false);
        destingkat.setVisible(false);
        destipe.setVisible(false);
        deshp.setVisible(false);
        desatk.setVisible(false);
        desdef.setVisible(false);
        desspd.setVisible(false);
        
        drop.setVisible(false);
        closedrop.setVisible(false);
        tambahan.setVisible(false);
        skill1.setEnabled(false);
        skill2.setEnabled(false);
        skill3.setEnabled(false);
        hpbarmonster.setVisible(false);
        bghpbarmonster.setVisible(false);
        pmonster.setVisible(false);
        progres.setVisible(false);
        monster0.setVisible(false);
        monster1.setVisible(false);
        monster2.setVisible(false);
        monster4.setVisible(false);
        modelcara.setVisible(false);
        pcara.setVisible(false);
        iven0.setVisible(false);
        iven1.setVisible(false);
        iven2.setVisible(false);
        iven3.setVisible(false);
        iven4.setVisible(false);
        iven5.setVisible(false);
        iven6.setVisible(false);
        iven7.setVisible(false);
        iven8.setVisible(false);
        iven9.setVisible(false);
        iven10.setVisible(false);
        iven11.setVisible(false);
        iven12.setVisible(false);
        iven13.setVisible(false);
        iven14.setVisible(false);
        iven15.setVisible(false);
        iven16.setVisible(false);
        iven17.setVisible(false);
        iven18.setVisible(false);
        iven19.setVisible(false);
        iven20.setVisible(false);
        iven21.setVisible(false);
        iven22.setVisible(false);
        iven23.setVisible(false);
        iven24.setVisible(false);
        iven25.setVisible(false);
        iven26.setVisible(false);
        iven27.setVisible(false);
        iven28.setVisible(false);
        iven29.setVisible(false);
        iven30.setVisible(false);
        iven31.setVisible(false);
        iven32.setVisible(false);
        iven33.setVisible(false);
        iven34.setVisible(false);
        iven35.setVisible(false);
        iven36.setVisible(false);
        iven37.setVisible(false);
        iven38.setVisible(false);
        iven39.setVisible(false);
        iven40.setVisible(false);
        iven41.setVisible(false);
        iven42.setVisible(false);
        iven43.setVisible(false);
        iven44.setVisible(false);
        iven45.setVisible(false);
        iven46.setVisible(false);
        iven47.setVisible(false); 
        skill1.setVisible(false);
        skill2.setVisible(false);
        skill3.setVisible(false);
        animasi.setVisible(false);
        hpbar.setSize(100, 20);
        expbar.setSize(100,20);
        updatehp();
        updateexp();
    }
    private void iven(String tipe){
        switch(tipe){
            case "weapon":{
                labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ivenweapon.png")));
                cara.setVisible(false);
                iconcara.setVisible(false);
                iven0.setLocation(48, 48);
                iven1.setLocation(48*4, 48);
                iven2.setLocation(48*7, 48);
                iven3.setLocation(48*10, 48);
                iven4.setLocation(48*13, 48);
                iven5.setLocation(48, 48*4);
                iven6.setLocation(48*4, 48*4);
                iven7.setLocation(48*7, 48*4);
                iven8.setLocation(48*10, 48*4);
                iven9.setLocation(48*13, 48*4);
                iven10.setLocation(48, 48*7);
                iven11.setLocation(48*4, 48*7);
                iven12.setLocation(48*7, 48*7);
                iven13.setLocation(48*10, 48*7);
                iven14.setLocation(48*13, 48*7);
                iven15.setLocation(48, 48*10);
                iven16.setLocation(48*4, 48*10);
                iven17.setLocation(48*7, 48*10);
                iven18.setLocation(48*10, 48*10);
                iven19.setLocation(48*13, 48*10);
                iven0.setVisible(true);
                iven1.setVisible(true);
                iven2.setVisible(true);
                iven3.setVisible(true);
                iven4.setVisible(true);
                iven5.setVisible(true);
                iven6.setVisible(true);
                iven7.setVisible(true);
                iven8.setVisible(true);
                iven9.setVisible(true);
                iven10.setVisible(true);
                iven11.setVisible(true);
                iven12.setVisible(true);
                iven13.setVisible(true);
                iven14.setVisible(true);
                iven15.setVisible(true);
                iven16.setVisible(true);
                iven17.setVisible(true);
                iven18.setVisible(true);
                iven19.setVisible(true);
                close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/close.png")));
                close.setVisible(true);
                close.setLocation(768, 0);
                for (int i = 0; i < iventori.length; i++) {
                    iventori[i]=0;
                }
                try{
                Koneksi con = new Koneksi();
                con.bukaKoneksi();
                con.statement = con.dbKoneksi.createStatement();
                ResultSet rs = con.statement.executeQuery("Select idiventori from iventori where idhero = '"+hero.getid()+"' and tipe='pedang'");
                int i=0;
                while(rs.next()){
                iventori[i]=rs.getInt("idiventori");
                i++;
                ;
            }
            con.tutupKoneksi();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
                Iventori i = new Iventori();
                System.out.println(i.carinama(iventori[16])); // load iventori di munculkan
                if(iventori[0]!=0){iven0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[0])+".png"))); iven0.setSize(92, 92);} else {iven0.setVisible(false);}
                if(iventori[1]!=0){iven1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[1])+".png"))); iven1.setSize(92, 92);}else {iven1.setVisible(false);}
                if(iventori[2]!=0){iven2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[2])+".png"))); iven2.setSize(92, 92);}else {iven2.setVisible(false);}
                if(iventori[3]!=0){iven3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[3])+".png"))); iven3.setSize(92, 92);}else {iven3.setVisible(false);}
                if(iventori[4]!=0){iven4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[4])+".png"))); iven4.setSize(92, 92);}else {iven4.setVisible(false);}
                if(iventori[5]!=0){iven5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[5])+".png"))); iven5.setSize(92, 92);}else {iven5.setVisible(false);}
                if(iventori[6]!=0){iven6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[6])+".png"))); iven6.setSize(92, 92);}else {iven6.setVisible(false);}
                if(iventori[7]!=0){iven7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[7])+".png"))); iven7.setSize(92, 92);}else {iven7.setVisible(false);}
                if(iventori[8]!=0){iven8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[8])+".png"))); iven8.setSize(92, 92);}else {iven8.setVisible(false);}
                if(iventori[9]!=0){iven9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[9])+".png"))); iven9.setSize(92, 92);}else {iven9.setVisible(false);}
                if(iventori[10]!=0){iven10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[10])+".png"))); iven10.setSize(92, 92);}else {iven10.setVisible(false);}
                if(iventori[11]!=0){iven11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[11])+".png"))); iven11.setSize(92, 92);}else {iven11.setVisible(false);}
                if(iventori[12]!=0){iven12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[12])+".png"))); iven12.setSize(92, 92);}else {iven12.setVisible(false);}
                if(iventori[13]!=0){iven13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[13])+".png"))); iven13.setSize(92, 92);}else {iven13.setVisible(false);}
                if(iventori[14]!=0){iven14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[14])+".png"))); iven14.setSize(92, 92);}else {iven14.setVisible(false);}
                if(iventori[15]!=0){iven15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[15])+".png"))); iven15.setSize(92, 92);}else {iven15.setVisible(false);}
                if(iventori[16]!=0){iven16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[16])+".png"))); iven16.setSize(92, 92);}else {iven16.setVisible(false);}
                if(iventori[17]!=0){iven17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[17])+".png"))); iven17.setSize(92, 92);}else {iven17.setVisible(false);}
                if(iventori[18]!=0){iven18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[18])+".png"))); iven18.setSize(92, 92);}else {iven18.setVisible(false);}
                if(iventori[19]!=0){iven19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[19])+".png"))); iven19.setSize(92, 92);}else {iven19.setVisible(false);}
                iven0.setText(Integer.toString(iventori[0]));
                iven1.setText(Integer.toString(iventori[1]));
                iven2.setText(Integer.toString(iventori[2]));
                iven3.setText(Integer.toString(iventori[3]));
                iven4.setText(Integer.toString(iventori[4]));
                iven5.setText(Integer.toString(iventori[5]));
                iven6.setText(Integer.toString(iventori[6]));
                iven7.setText(Integer.toString(iventori[7]));
                iven8.setText(Integer.toString(iventori[8]));
                iven9.setText(Integer.toString(iventori[9]));
                iven10.setText(Integer.toString(iventori[10]));
                iven11.setText(Integer.toString(iventori[11]));
                iven12.setText(Integer.toString(iventori[12]));
                iven13.setText(Integer.toString(iventori[13]));
                iven14.setText(Integer.toString(iventori[14]));
                iven15.setText(Integer.toString(iventori[15]));
                iven16.setText(Integer.toString(iventori[16]));
                iven17.setText(Integer.toString(iventori[17]));
                iven18.setText(Integer.toString(iventori[18]));
                iven19.setText(Integer.toString(iventori[19]));
                
                break;
            }
            case "food" :{
                for (int i = 0; i < iventori.length; i++) {
                    iventori[i]=0;
                }
                try{
                Koneksi con = new Koneksi();
                con.bukaKoneksi();
                con.statement = con.dbKoneksi.createStatement();
                ResultSet rs = con.statement.executeQuery("Select idiventori from iventori where idhero = '"+hero.getid()+"' and tipe='food'");
                int i=0;
                while(rs.next()){
                iventori[i]=rs.getInt("idiventori");
                i++;
                ;
            }
            con.tutupKoneksi();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
                Iventori i = new Iventori();
                loadiven();
                if(iventori[0]!=0){iven0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[0])+".png"))); iven0.setSize(48, 48);} else {iven0.setVisible(false);}
                if(iventori[1]!=0){iven1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[1])+".png"))); iven1.setSize(48, 48);}else {iven1.setVisible(false);}
                if(iventori[2]!=0){iven2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[2])+".png"))); iven2.setSize(48, 48);}else {iven2.setVisible(false);}
                if(iventori[3]!=0){iven3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[3])+".png"))); iven3.setSize(48, 48);}else {iven3.setVisible(false);}
                if(iventori[4]!=0){iven4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[4])+".png"))); iven4.setSize(48, 48);}else {iven4.setVisible(false);}
                if(iventori[5]!=0){iven5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[5])+".png"))); iven5.setSize(48, 48);}else {iven5.setVisible(false);}
                if(iventori[6]!=0){iven6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[6])+".png"))); iven6.setSize(48, 48);}else {iven6.setVisible(false);}
                if(iventori[7]!=0){iven7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[7])+".png"))); iven7.setSize(48, 48);}else {iven7.setVisible(false);}
                if(iventori[8]!=0){iven8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[8])+".png"))); iven8.setSize(48, 48);}else {iven8.setVisible(false);}
                if(iventori[9]!=0){iven9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[9])+".png"))); iven9.setSize(48, 48);}else {iven9.setVisible(false);}
                if(iventori[10]!=0){iven10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[10])+".png"))); iven10.setSize(48, 48);}else {iven10.setVisible(false);}
                if(iventori[11]!=0){iven11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[11])+".png"))); iven11.setSize(48, 48);}else {iven11.setVisible(false);}
                if(iventori[12]!=0){iven12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[12])+".png"))); iven12.setSize(48, 48);}else {iven12.setVisible(false);}
                if(iventori[13]!=0){iven13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[13])+".png"))); iven13.setSize(48, 48);}else {iven13.setVisible(false);}
                if(iventori[14]!=0){iven14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[14])+".png"))); iven14.setSize(48, 48);}else {iven14.setVisible(false);}
                if(iventori[15]!=0){iven15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[15])+".png"))); iven15.setSize(48, 48);}else {iven15.setVisible(false);}
                if(iventori[16]!=0){iven16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[16])+".png"))); iven16.setSize(48, 48);}else {iven16.setVisible(false);}
                if(iventori[17]!=0){iven17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[17])+".png"))); iven17.setSize(48, 48);}else {iven17.setVisible(false);}
                if(iventori[18]!=0){iven18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[18])+".png"))); iven18.setSize(48, 48);}else {iven18.setVisible(false);}
                if(iventori[19]!=0){iven19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[19])+".png"))); iven19.setSize(48, 48);}else {iven19.setVisible(false);}
                if(iventori[20]!=0){iven20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[20])+".png"))); iven20.setSize(48, 48);}else {iven20.setVisible(false);}
                if(iventori[21]!=0){iven21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[21])+".png"))); iven21.setSize(48, 48);}else {iven21.setVisible(false);}
                if(iventori[22]!=0){iven22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[22])+".png"))); iven22.setSize(48, 48);}else {iven22.setVisible(false);}
                if(iventori[23]!=0){iven23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[23])+".png"))); iven23.setSize(48, 48);}else {iven23.setVisible(false);}
                if(iventori[24]!=0){iven24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[24])+".png"))); iven24.setSize(48, 48);}else {iven24.setVisible(false);}
                if(iventori[25]!=0){iven25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[25])+".png"))); iven25.setSize(48, 48);}else {iven25.setVisible(false);}
                if(iventori[26]!=0){iven26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[26])+".png"))); iven26.setSize(48, 48);}else {iven26.setVisible(false);}
                if(iventori[27]!=0){iven27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[27])+".png"))); iven27.setSize(48, 48);}else {iven27.setVisible(false);}
                if(iventori[28]!=0){iven28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[28])+".png"))); iven28.setSize(48, 48);}else {iven28.setVisible(false);}
                if(iventori[29]!=0){iven29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[29])+".png"))); iven29.setSize(48, 48);}else {iven29.setVisible(false);}
                if(iventori[30]!=0){iven30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[30])+".png"))); iven30.setSize(48, 48);}else {iven30.setVisible(false);}
                if(iventori[31]!=0){iven31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[31])+".png"))); iven31.setSize(48, 48);}else {iven31.setVisible(false);}
                if(iventori[32]!=0){iven32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[32])+".png"))); iven32.setSize(48, 48);}else {iven32.setVisible(false);}
                if(iventori[33]!=0){iven33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[33])+".png"))); iven33.setSize(48, 48);}else {iven33.setVisible(false);}
                if(iventori[34]!=0){iven34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[34])+".png"))); iven34.setSize(48, 48);}else {iven34.setVisible(false);}
                if(iventori[35]!=0){iven35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[35])+".png"))); iven35.setSize(48, 48);}else {iven35.setVisible(false);}
                if(iventori[36]!=0){iven36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[36])+".png"))); iven36.setSize(48, 48);}else {iven36.setVisible(false);}
                if(iventori[37]!=0){iven37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[37])+".png"))); iven37.setSize(48, 48);}else {iven37.setVisible(false);}
                if(iventori[38]!=0){iven38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[38])+".png"))); iven38.setSize(48, 48);}else {iven38.setVisible(false);}
                if(iventori[39]!=0){iven39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[39])+".png"))); iven39.setSize(48, 48);}else {iven39.setVisible(false);}
                if(iventori[40]!=0){iven40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[40])+".png"))); iven40.setSize(48, 48);}else {iven40.setVisible(false);}
                if(iventori[41]!=0){iven41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[41])+".png"))); iven41.setSize(48, 48);}else {iven41.setVisible(false);}
                if(iventori[42]!=0){iven42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[42])+".png"))); iven42.setSize(48, 48);}else {iven42.setVisible(false);}
                if(iventori[43]!=0){iven43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[43])+".png"))); iven43.setSize(48, 48);}else {iven43.setVisible(false);}
                if(iventori[44]!=0){iven44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[44])+".png"))); iven44.setSize(48, 48);}else {iven44.setVisible(false);}
                if(iventori[45]!=0){iven45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[45])+".png"))); iven45.setSize(48, 48);}else {iven45.setVisible(false);}
                if(iventori[46]!=0){iven46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[46])+".png"))); iven46.setSize(48, 48);}else {iven46.setVisible(false);}
                if(iventori[47]!=0){iven47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[47])+".png"))); iven47.setSize(48, 48);}else {iven47.setVisible(false);}
                iven0.setText(Integer.toString(iventori[0]));
                iven1.setText(Integer.toString(iventori[1]));
                iven2.setText(Integer.toString(iventori[2]));
                iven3.setText(Integer.toString(iventori[3]));
                iven4.setText(Integer.toString(iventori[4]));
                iven5.setText(Integer.toString(iventori[5]));
                iven6.setText(Integer.toString(iventori[6]));
                iven7.setText(Integer.toString(iventori[7]));
                iven8.setText(Integer.toString(iventori[8]));
                iven9.setText(Integer.toString(iventori[9]));
                iven10.setText(Integer.toString(iventori[10]));
                iven11.setText(Integer.toString(iventori[11]));
                iven12.setText(Integer.toString(iventori[12]));
                iven13.setText(Integer.toString(iventori[13]));
                iven14.setText(Integer.toString(iventori[14]));
                iven15.setText(Integer.toString(iventori[15]));
                iven16.setText(Integer.toString(iventori[16]));
                iven17.setText(Integer.toString(iventori[17]));
                iven18.setText(Integer.toString(iventori[18]));
                iven19.setText(Integer.toString(iventori[19]));
                iven20.setText(Integer.toString(iventori[20]));
                iven21.setText(Integer.toString(iventori[21]));
                iven22.setText(Integer.toString(iventori[22]));
                iven23.setText(Integer.toString(iventori[23]));
                iven24.setText(Integer.toString(iventori[24]));
                iven25.setText(Integer.toString(iventori[25]));
                iven26.setText(Integer.toString(iventori[26]));
                iven27.setText(Integer.toString(iventori[27]));
                iven28.setText(Integer.toString(iventori[28]));
                iven29.setText(Integer.toString(iventori[29]));
                iven30.setText(Integer.toString(iventori[30]));
                iven31.setText(Integer.toString(iventori[31]));
                iven32.setText(Integer.toString(iventori[32]));
                iven33.setText(Integer.toString(iventori[33]));
                iven34.setText(Integer.toString(iventori[34]));
                iven35.setText(Integer.toString(iventori[35]));
                iven36.setText(Integer.toString(iventori[36]));
                iven37.setText(Integer.toString(iventori[37]));
                iven38.setText(Integer.toString(iventori[38]));
                iven39.setText(Integer.toString(iventori[39]));
                iven40.setText(Integer.toString(iventori[40]));
                iven41.setText(Integer.toString(iventori[41]));
                iven42.setText(Integer.toString(iventori[42]));
                iven43.setText(Integer.toString(iventori[43]));
                iven44.setText(Integer.toString(iventori[44]));
                iven45.setText(Integer.toString(iventori[45]));
                iven46.setText(Integer.toString(iventori[46]));
                iven47.setText(Integer.toString(iventori[47]));
                labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ivenitem.png")));
                cara.setVisible(false);
                close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/close.png")));
                close.setVisible(true);
                close.setLocation(768, 0);
                
                break;
            }
            case "gear":{
                
                for (int i = 0; i < iventori.length; i++) {
                    iventori[i]=0;
                }
                try{
                Koneksi con = new Koneksi();
                con.bukaKoneksi();
                con.statement = con.dbKoneksi.createStatement();
                ResultSet rs = con.statement.executeQuery("Select idiventori from iventori where idhero = '"+hero.getid()+"' and (tipe='boot' or tipe='armor' or tipe='helm')");
                int i=0;
                while(rs.next()){
                iventori[i]=rs.getInt("idiventori");
                i++;
                ;
            }
            con.tutupKoneksi();
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
                Iventori i = new Iventori();
                System.out.println(iventori[47]);
                loadiven();
                if(iventori[0]!=0){iven0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[0])+".png"))); iven0.setSize(48, 48);} else {iven0.setVisible(false);}
                if(iventori[1]!=0){iven1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[1])+".png"))); iven1.setSize(48, 48);}else {iven1.setVisible(false);}
                if(iventori[2]!=0){iven2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[2])+".png"))); iven2.setSize(48, 48);}else {iven2.setVisible(false);}
                if(iventori[3]!=0){iven3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[3])+".png"))); iven3.setSize(48, 48);}else {iven3.setVisible(false);}
                if(iventori[4]!=0){iven4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[4])+".png"))); iven4.setSize(48, 48);}else {iven4.setVisible(false);}
                if(iventori[5]!=0){iven5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[5])+".png"))); iven5.setSize(48, 48);}else {iven5.setVisible(false);}
                if(iventori[6]!=0){iven6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[6])+".png"))); iven6.setSize(48, 48);}else {iven6.setVisible(false);}
                if(iventori[7]!=0){iven7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[7])+".png"))); iven7.setSize(48, 48);}else {iven7.setVisible(false);}
                if(iventori[8]!=0){iven8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[8])+".png"))); iven8.setSize(48, 48);}else {iven8.setVisible(false);}
                if(iventori[9]!=0){iven9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[9])+".png"))); iven9.setSize(48, 48);}else {iven9.setVisible(false);}
                if(iventori[10]!=0){iven10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[10])+".png"))); iven10.setSize(48, 48);}else {iven10.setVisible(false);}
                if(iventori[11]!=0){iven11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[11])+".png"))); iven11.setSize(48, 48);}else {iven11.setVisible(false);}
                if(iventori[12]!=0){iven12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[12])+".png"))); iven12.setSize(48, 48);}else {iven12.setVisible(false);}
                if(iventori[13]!=0){iven13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[13])+".png"))); iven13.setSize(48, 48);}else {iven13.setVisible(false);}
                if(iventori[14]!=0){iven14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[14])+".png"))); iven14.setSize(48, 48);}else {iven14.setVisible(false);}
                if(iventori[15]!=0){iven15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[15])+".png"))); iven15.setSize(48, 48);}else {iven15.setVisible(false);}
                if(iventori[16]!=0){iven16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[16])+".png"))); iven16.setSize(48, 48);}else {iven16.setVisible(false);}
                if(iventori[17]!=0){iven17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[17])+".png"))); iven17.setSize(48, 48);}else {iven17.setVisible(false);}
                if(iventori[18]!=0){iven18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[18])+".png"))); iven18.setSize(48, 48);}else {iven18.setVisible(false);}
                if(iventori[19]!=0){iven19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[19])+".png"))); iven19.setSize(48, 48);}else {iven19.setVisible(false);}
                if(iventori[20]!=0){iven20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[20])+".png"))); iven20.setSize(48, 48);}else {iven20.setVisible(false);}
                if(iventori[21]!=0){iven21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[21])+".png"))); iven21.setSize(48, 48);}else {iven21.setVisible(false);}
                if(iventori[22]!=0){iven22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[22])+".png"))); iven22.setSize(48, 48);}else {iven22.setVisible(false);}
                if(iventori[23]!=0){iven23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[23])+".png"))); iven23.setSize(48, 48);}else {iven23.setVisible(false);}
                if(iventori[24]!=0){iven24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[24])+".png"))); iven24.setSize(48, 48);}else {iven24.setVisible(false);}
                if(iventori[25]!=0){iven25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[25])+".png"))); iven25.setSize(48, 48);}else {iven25.setVisible(false);}
                if(iventori[26]!=0){iven26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[26])+".png"))); iven26.setSize(48, 48);}else {iven26.setVisible(false);}
                if(iventori[27]!=0){iven27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[27])+".png"))); iven27.setSize(48, 48);}else {iven27.setVisible(false);}
                if(iventori[28]!=0){iven28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[28])+".png"))); iven28.setSize(48, 48);}else {iven28.setVisible(false);}
                if(iventori[29]!=0){iven29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[29])+".png"))); iven29.setSize(48, 48);}else {iven29.setVisible(false);}
                if(iventori[30]!=0){iven30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[30])+".png"))); iven30.setSize(48, 48);}else {iven30.setVisible(false);}
                if(iventori[31]!=0){iven31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[31])+".png"))); iven31.setSize(48, 48);}else {iven31.setVisible(false);}
                if(iventori[32]!=0){iven32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[32])+".png"))); iven32.setSize(48, 48);}else {iven32.setVisible(false);}
                if(iventori[33]!=0){iven33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[33])+".png"))); iven33.setSize(48, 48);}else {iven33.setVisible(false);}
                if(iventori[34]!=0){iven34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[34])+".png"))); iven34.setSize(48, 48);}else {iven34.setVisible(false);}
                if(iventori[35]!=0){iven35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[35])+".png"))); iven35.setSize(48, 48);}else {iven35.setVisible(false);}
                if(iventori[36]!=0){iven36.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[36])+".png"))); iven36.setSize(48, 48);}else {iven36.setVisible(false);}
                if(iventori[37]!=0){iven37.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[37])+".png"))); iven37.setSize(48, 48);}else {iven37.setVisible(false);}
                if(iventori[38]!=0){iven38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[38])+".png"))); iven38.setSize(48, 48);}else {iven38.setVisible(false);}
                if(iventori[39]!=0){iven39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[39])+".png"))); iven39.setSize(48, 48);}else {iven39.setVisible(false);}
                if(iventori[40]!=0){iven40.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[40])+".png"))); iven40.setSize(48, 48);}else {iven40.setVisible(false);}
                if(iventori[41]!=0){iven41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[41])+".png"))); iven41.setSize(48, 48);}else {iven41.setVisible(false);}
                if(iventori[42]!=0){iven42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[42])+".png"))); iven42.setSize(48, 48);}else {iven42.setVisible(false);}
                if(iventori[43]!=0){iven43.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[43])+".png"))); iven43.setSize(48, 48);}else {iven43.setVisible(false);}
                if(iventori[44]!=0){iven44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[44])+".png"))); iven44.setSize(48, 48);}else {iven44.setVisible(false);}
                if(iventori[45]!=0){iven45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[45])+".png"))); iven45.setSize(48, 48);}else {iven45.setVisible(false);}
                if(iventori[46]!=0){iven46.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[46])+".png"))); iven46.setSize(48, 48);}else {iven46.setVisible(false); System.out.println("aaaa");}
                if(iventori[47]!=0){iven47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+i.carinama(iventori[47])+".png"))); iven47.setSize(48, 48);}else {iven47.setVisible(false);}
                iven0.setText(Integer.toString(iventori[0]));
                iven1.setText(Integer.toString(iventori[1]));
                iven2.setText(Integer.toString(iventori[2]));
                iven3.setText(Integer.toString(iventori[3]));
                iven4.setText(Integer.toString(iventori[4]));
                iven5.setText(Integer.toString(iventori[5]));
                iven6.setText(Integer.toString(iventori[6]));
                iven7.setText(Integer.toString(iventori[7]));
                iven8.setText(Integer.toString(iventori[8]));
                iven9.setText(Integer.toString(iventori[9]));
                iven10.setText(Integer.toString(iventori[10]));
                iven11.setText(Integer.toString(iventori[11]));
                iven12.setText(Integer.toString(iventori[12]));
                iven13.setText(Integer.toString(iventori[13]));
                iven14.setText(Integer.toString(iventori[14]));
                iven15.setText(Integer.toString(iventori[15]));
                iven16.setText(Integer.toString(iventori[16]));
                iven17.setText(Integer.toString(iventori[17]));
                iven18.setText(Integer.toString(iventori[18]));
                iven19.setText(Integer.toString(iventori[19]));
                iven20.setText(Integer.toString(iventori[20]));
                iven21.setText(Integer.toString(iventori[21]));
                iven22.setText(Integer.toString(iventori[22]));
                iven23.setText(Integer.toString(iventori[23]));
                iven24.setText(Integer.toString(iventori[24]));
                iven25.setText(Integer.toString(iventori[25]));
                iven26.setText(Integer.toString(iventori[26]));
                iven27.setText(Integer.toString(iventori[27]));
                iven28.setText(Integer.toString(iventori[28]));
                iven29.setText(Integer.toString(iventori[29]));
                iven30.setText(Integer.toString(iventori[30]));
                iven31.setText(Integer.toString(iventori[31]));
                iven32.setText(Integer.toString(iventori[32]));
                iven33.setText(Integer.toString(iventori[33]));
                iven34.setText(Integer.toString(iventori[34]));
                iven35.setText(Integer.toString(iventori[35]));
                iven36.setText(Integer.toString(iventori[36]));
                iven37.setText(Integer.toString(iventori[37]));
                iven38.setText(Integer.toString(iventori[38]));
                iven39.setText(Integer.toString(iventori[39]));
                iven40.setText(Integer.toString(iventori[40]));
                iven41.setText(Integer.toString(iventori[41]));
                iven42.setText(Integer.toString(iventori[42]));
                iven43.setText(Integer.toString(iventori[43]));
                iven44.setText(Integer.toString(iventori[44]));
                iven45.setText(Integer.toString(iventori[45]));
                iven46.setText(Integer.toString(iventori[46]));
                iven47.setText(Integer.toString(iventori[47]));
                labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ivenitem.png")));
                cara.setVisible(false);
                close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/close.png")));
                close.setVisible(true);
                close.setLocation(768, 0);
                
                break;
            }
        }
    }
    private void loadiven(){
        iconcara.setVisible(false);
        iven0.setVisible(true);
        iven1.setVisible(true);
        iven2.setVisible(true);
        iven3.setVisible(true);
        iven4.setVisible(true);
        iven5.setVisible(true);
        iven6.setVisible(true);
        iven7.setVisible(true);
        iven8.setVisible(true);
        iven9.setVisible(true);
        iven10.setVisible(true);
        iven11.setVisible(true);
        iven12.setVisible(true);
        iven13.setVisible(true);
        iven14.setVisible(true);
        iven15.setVisible(true);
        iven16.setVisible(true);
        iven17.setVisible(true);
        iven18.setVisible(true);
        iven19.setVisible(true);
        iven20.setVisible(true);
        iven21.setVisible(true);
        iven22.setVisible(true);
        iven23.setVisible(true);
        iven24.setVisible(true);
        iven25.setVisible(true);
        iven26.setVisible(true);
        iven27.setVisible(true);
        iven28.setVisible(true);
        iven29.setVisible(true);
        iven30.setVisible(true);
        iven31.setVisible(true);
        iven32.setVisible(true);
        iven33.setVisible(true);
        iven34.setVisible(true);
        iven35.setVisible(true);
        iven36.setVisible(true);
        iven37.setVisible(true);
        iven38.setVisible(true);
        iven39.setVisible(true);
        iven40.setVisible(true);
        iven41.setVisible(true);
        iven42.setVisible(true);
        iven43.setVisible(true);
        iven44.setVisible(true);
        iven45.setVisible(true);
        iven46.setVisible(true);
        iven47.setVisible(true);
        iven0.setLocation(48, 48);
        iven1.setLocation(48*3, 48);
        iven2.setLocation(48*5, 48);
        iven3.setLocation(48*7, 48);
        iven4.setLocation(48*9, 48);
        iven5.setLocation(48*11, 48);
        iven6.setLocation(48*13, 48);
        iven7.setLocation(48*15, 48);
        iven8.setLocation(48, 48*3);
        iven9.setLocation(48*3, 48*3);
        iven10.setLocation(48*5, 48*3);
        iven11.setLocation(48*7, 48*3);
        iven12.setLocation(48*9, 48*3);
        iven13.setLocation(48*11, 48*3);
        iven14.setLocation(48*13, 48*3);
        iven15.setLocation(48*15, 48*3);
        iven16.setLocation(48, 48*5);
        iven17.setLocation(48*3, 48*5);
        iven18.setLocation(48*5, 48*5);
        iven19.setLocation(48*7, 48*5);
        iven20.setLocation(48*9, 48*5);
        iven21.setLocation(48*11, 48*5);
        iven22.setLocation(48*13, 48*5);
        iven23.setLocation(48*15, 48*5);
        iven24.setLocation(48, 48*7);
        iven25.setLocation(48*3, 48*7);
        iven26.setLocation(48*5, 48*7);
        iven27.setLocation(48*7, 48*7);
        iven28.setLocation(48*9, 48*7);
        iven29.setLocation(48*11, 48*7);
        iven30.setLocation(48*13, 48*7);
        iven31.setLocation(48*15, 48*7);
        iven32.setLocation(48, 48*9);
        iven33.setLocation(48*3, 48*9);
        iven34.setLocation(48*5, 48*9);
        iven35.setLocation(48*7, 48*9);
        iven36.setLocation(48*9, 48*9);
        iven37.setLocation(48*11, 48*9);
        iven38.setLocation(48*13, 48*9);
        iven39.setLocation(48*15, 48*9);
        iven40.setLocation(48, 48*11);
        iven41.setLocation(48*3, 48*11);
        iven42.setLocation(48*5, 48*11);
        iven43.setLocation(48*7, 48*11);
        iven44.setLocation(48*9, 48*11);
        iven45.setLocation(48*11, 48*11);
        iven46.setLocation(48*13, 48*11);
        iven47.setLocation(48*15, 48*11);
        iven0.setSize(48, 48);
        
    }
    private void animasi(String arah){
    switch(arah){// animasi arah hero
        case "kanan" : { 
            cara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+hero.imgR[heroani])));
            heroani++;
            if(heroani==3){
                heroani=0;
            }
            break;
        }
        case "atas" : {
            cara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+hero.imgU[heroani])));
            heroani++;
            if(heroani==3){
                heroani=0;
            }            
            break;
        }
        case "bawah" : {
            cara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+hero.imgD[heroani])));
            heroani++;
            if(heroani==3){
                heroani=0;
            }
            break;
        }
        case "kiri" : {
            cara.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/"+hero.imgL[heroani])));
            heroani++;
            if(heroani==3){
                heroani=0;
            }
            break;
        }
    }
}
    private void labelmapMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelmapMouseClicked

    }//GEN-LAST:event_labelmapMouseClicked
    private void respawnmonster(){ //merespawn monster disetiap map
        switch(map){
            case 3:{
                Random random = new Random();
                int x = random.nextInt(11)+3;
                int y = random.nextInt(2)+2;
                monster[x][y][0]=0;
                monster0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/ayamicon.png")));
                monster0.setLocation(x*48, y*48);
                batas[x][y] = 9;
                monster0.setSize(48, 48);
                monster0.setVisible(true);
                x = random.nextInt(7)+8;
                y = random.nextInt(2)+9;
                monster[x][y][0]=1;
                monster1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/garudaicon.png")));
                monster1.setLocation(x*48, y*48);
                batas[x][y]=9;
                monster1.setSize(48, 48);
                monster1.setVisible(true);
                break;
            }
            case 4:{
                if(mision){
                batas[10][6]=9;
                batas[11][6]=9;
                batas[12][6]=9;
                batas[10][7]=9;
                batas[11][7]=9;
                batas[12][7]=9;
                batas[10][8]=9;
                batas[11][8]=9;
                batas[12][8]=9;
                monster[10][6][0]=6;
                monster[11][6][0]=6;
                monster[12][6][0]=6;
                monster[10][7][0]=6;
                monster[11][7][0]=6;
                monster[12][7][0]=6;
                monster[10][8][0]=6;
                monster[11][8][0]=6;
                monster[12][8][0]=6;
                monster0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/Dragon.png")));
                monster0.setLocation(480, 288);
                monster0.setSize(144, 144);
                } else monster0.setVisible(false);
                break;
            }
            case 5:{
                Random random = new Random();
                int x = random.nextInt(5)+3;
                int y = random.nextInt(3)+5;
                monster[x][y][0]=4;
                monster0.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/kelalawaricon.png")));
                monster0.setLocation(x*48, y*48);
                batas[x][y] = 9;
                monster0.setSize(48, 48);
                monster0.setVisible(true);

                 x = random.nextInt(5)+3;
                 y = random.nextInt(3)+9;
                monster[x][y][0]=2;
                monster1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/slimeicon.png")));
                monster1.setLocation(x*48, y*48);
                batas[x][y] = 9;
                monster1.setSize(48, 48);
                monster1.setVisible(true);
                
                 x = random.nextInt(7)+8;
                 y = random.nextInt(4)+3;
                monster[x][y][0]=5;
                monster2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/mimicicon.png")));
                monster2.setLocation(x*48, y*48);
                batas[x][y] = 9;
                monster2.setSize(48, 48);
                monster2.setVisible(true);
                
                 x = random.nextInt(6)+9;
                 y = random.nextInt(3)+7;
                monster[x][y][0]=3;
                monster3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/gazericon.png")));
                monster3.setLocation(x*48, y*48);
                batas[x][y] = 9;
                monster3.setSize(48, 48);
                monster3.setVisible(true);
                break;
            }
        }
        
    }
    private void updatehp(){ // tampilan bar hp hero
        hpbar.setSize(hero.gethp()/(hero.getmaxhp()/200), 20);
    }
    private void updateexp(){ // tampilan bar exp hero
        if(hero.getExp()>=1000){
            herolevelup();
            expbar.setSize(hero.getExp()/(1000/200), 20); 
            JOptionPane.showMessageDialog(this, "Naik Level !"); 
        }else {
           expbar.setSize(hero.getExp()/(1000/200), 20); 
        }
        
    }
    private void herolevelup(){// hero naik level setiap statnya naik 10%
        hero.setExp(0);
        hero.setLevel(hero.getLevel()+1);
        hero.setAtk(hero.getAtk()+(hero.getAtk()/10));
        hero.setDef(hero.getDef()+(hero.getDef()/10));
        hero.setSpd(hero.getSpd()+(hero.getSpd()/10));
        hero.sethp(hero.gethp()+(hero.gethp()/10));
    }
    private void gameover(){ //jika hero mati
        t.stop();
        labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/GameOver.png")));
        hpbarmonster.setVisible(false);
        bghpbarmonster.setVisible(false);
        progres.setVisible(false);
        skill1.setVisible(false);
        skill2.setVisible(false);
        skill3.setVisible(false);
        pcara.setVisible(false);
        pmonster.setVisible(false);
        monster4.setVisible(false);
        modelcara.setVisible(false);
        
    }
    private void viewstat(){ //saat klik icon hero menampilkan setiap stat
        closedrop.setLocation(48*12, 48);
        closedrop.setVisible(true);
        closedrop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/close.png")));
        closedrop.setSize(48, 48);
        tambahan.setVisible(true);
        tambahan.setSize(480,480);
        tambahan.setLocation(48*3, 48);
        tambahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/drop.png")));
        drop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/iconcaraa.png")));
        drop.setText("");
        tipe.setText("Lvl :");
        tipe.setVisible(true);
        destipe.setVisible(true);
        destipe.setText(Integer.toString(hero.getLevel()));
        drop.setVisible(true);
        dhp.setVisible(true);
        atk.setVisible(true);
        def.setVisible(true);
        spd.setVisible(true);
        deshp.setVisible(true);
        desatk.setVisible(true);
        desdef.setVisible(true);
        desspd.setVisible(true);
        deshp.setText(Integer.toString(hero.gethp()));
        desatk.setText(Integer.toString(hero.getAtk()+pedang.getAtk()+armor.getAtk()+helm.getAtk()+boot.getAtk()));
        desdef.setText(Integer.toString(hero.getDef()+pedang.getDef()+armor.getDef()+helm.getDef()+boot.getDef()));
        desspd.setText(Integer.toString(hero.getSpd()+pedang.getSpd()+armor.getSpd()+helm.getSpd()+boot.getSpd()));
    }
    private void save(){ // save di tempat tidur
        int temp = JOptionPane.showConfirmDialog(this, "Save ?");
        if(temp==0&&hero.update(hero.getid())){
            JOptionPane.showMessageDialog(this, "Berhasil di save !");
        }
    }
    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        close.setVisible(false);
        iconcara.setVisible(true);
        labelmap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/luarrumah.png")));
        cara.setVisible(true);
        iven0.setVisible(false);
        iven1.setVisible(false);
        iven2.setVisible(false);
        iven3.setVisible(false);
        iven4.setVisible(false);
        iven5.setVisible(false);
        iven6.setVisible(false);
        iven7.setVisible(false);
        iven8.setVisible(false);
        iven9.setVisible(false);
        iven10.setVisible(false);
        iven11.setVisible(false);
        iven12.setVisible(false);
        iven13.setVisible(false);
        iven14.setVisible(false);
        iven15.setVisible(false);
        iven16.setVisible(false);
        iven17.setVisible(false);
        iven18.setVisible(false);
        iven19.setVisible(false);
        iven20.setVisible(false);
        iven21.setVisible(false);
        iven22.setVisible(false);
        iven23.setVisible(false);
        iven24.setVisible(false);
        iven25.setVisible(false);
        iven26.setVisible(false);
        iven27.setVisible(false);
        iven28.setVisible(false);
        iven29.setVisible(false);
        iven30.setVisible(false);
        iven31.setVisible(false);
        iven32.setVisible(false);
        iven33.setVisible(false);
        iven34.setVisible(false);
        iven35.setVisible(false);
        iven36.setVisible(false);
        iven37.setVisible(false);
        iven38.setVisible(false);
        iven39.setVisible(false);
        iven40.setVisible(false);
        iven41.setVisible(false);
        iven42.setVisible(false);
        iven43.setVisible(false);
        iven44.setVisible(false);
        iven45.setVisible(false);
        iven46.setVisible(false);
        iven47.setVisible(false);
    }//GEN-LAST:event_closeMouseClicked

    private void skill1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skill1ActionPerformed
        m.sethp(m.gethp()-((hero.getAtk()+pedang.getAtk()+armor.getAtk()+helm.getAtk()+boot.getAtk())-m.getDef()));//animasi skill hero
        hpbarmonster.setSize(m.gethp()/(m.getmaxhp()/700), 20);
        Timer askill1= new Timer(100, new ActionListener() { //animasi serangan
        int a = 1;
        @Override
        public void actionPerformed(ActionEvent e) {
            animasi.setVisible(true);
            animasi.setSize(192, 192);
            animasi.setLocation(monster4.getX(), monster4.getY());
            animasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/skill1"+a+".png")));
            a++;
            if(a==5){
                animasi.setVisible(false);
                t.start();
              ((Timer)e.getSource()).stop();  
            }
        }
    });
        cskill2--;
        cskill3--;
        skill1.setEnabled(false);
        skill2.setEnabled(false);
        skill3.setEnabled(false);
           askill1.start(); 
    }//GEN-LAST:event_skill1ActionPerformed

    private void skill2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skill2ActionPerformed
        //animasi hero serangan skill 2
        m.sethp(m.gethp()-((2*(hero.getAtk()+pedang.getAtk()+armor.getAtk()+helm.getAtk()+boot.getAtk()))-m.getDef()));
        hpbarmonster.setSize(m.gethp()/(m.getmaxhp()/700), 20);
        Timer askill1= new Timer(100, new ActionListener() { //animasi serangan
        int a = 1;
        @Override
        public void actionPerformed(ActionEvent e) {
            animasi.setVisible(true);
            animasi.setSize(192, 192);
            animasi.setLocation(monster4.getX(), monster4.getY());
            animasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/skill2"+a+".png")));
            a++;
            if(a==11){
                animasi.setVisible(false);
                t.start();
              ((Timer)e.getSource()).stop();  
            }
        }
    });
        cskill3--;
        cskill2 = 2;
        skill1.setEnabled(false);
        skill2.setEnabled(false);
        skill3.setEnabled(false);
        askill1.start();
    }//GEN-LAST:event_skill2ActionPerformed

    private void skill3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_skill3ActionPerformed
        m.sethp(m.gethp()-((3*(hero.getAtk()+pedang.getAtk()+armor.getAtk()+helm.getAtk()+boot.getAtk()))-m.getDef()));//animasi hero serangan skill 3
        hpbarmonster.setSize(m.gethp()/(m.getmaxhp()/700), 20);
        Timer askill1= new Timer(80, new ActionListener() { //animasi serangan
        int a = 1;
        @Override
        public void actionPerformed(ActionEvent e) {
            animasi.setVisible(true);
            animasi.setSize(192, 192);
            animasi.setLocation(monster4.getX(), monster4.getY());
            animasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/skill3"+a+".png")));
            a++;
            if(a==31){
                animasi.setVisible(false);
                t.start();
              ((Timer)e.getSource()).stop();  
            }
        }
    });
        cskill2--;
        cskill3 =4;
        skill1.setEnabled(false);
        skill2.setEnabled(false);
        skill3.setEnabled(false);
        askill1.start();
    }//GEN-LAST:event_skill3ActionPerformed

    private void closedropMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closedropMouseClicked
        closedrop.setVisible(false);
        tambahan.setVisible(false);
        drop.setVisible(false);
        buang.setVisible(false);
        simpan.setVisible(false);
        tingkat.setVisible(false);
        tipe.setVisible(false);
        dhp.setVisible(false);
        atk.setVisible(false);
        def.setVisible(false);
        spd.setVisible(false);
        destingkat.setVisible(false);
        destipe.setVisible(false);
        deshp.setVisible(false);
        desatk.setVisible(false);
        desdef.setVisible(false);
        desspd.setVisible(false);
    }//GEN-LAST:event_closedropMouseClicked

    private void simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanActionPerformed
        System.out.println(detaildrop.getid()); //tombol simpan pada drop
        if(simpan.getText().equals("Pakai")){
            switch(selecteditem.gettipe()){
                case "pedang":{
                    pedang.select(selecteditem.getid());
                    
                    break;
                }
                case "helm":{
                    helm.select(selecteditem.getid());
                    break;
                }
                case "armor":{
                    armor.select(selecteditem.getid());
                    break;
            }
                case "boot":{
                    boot.select(selecteditem.getid());
                }
        }
        } else if(simpan.getText().equals("Makan")){
            if((hero.gethp()+selecteditem.gethp())>hero.getmaxhp()){
                hero.sethp(hero.getmaxhp());
                updatehp();
                
            }else {
                hero.sethp(hero.gethp()+selecteditem.gethp());
                updatehp();
            }
            selecteditem.delete(selecteditem.getid());
            iven("food");
        } else if(simpan.getText().equals("Simpan")){
           if(detaildrop.gettipe().equals("pedang")){
          if(detaildrop.cekpedang(hero.getid(), detaildrop.gettipe())<20){
              System.out.println(detaildrop.gettipe());
            detaildrop.insert();
        }else{JOptionPane.showMessageDialog(this, "Iventori Penuh !");}
        }else {
            if(detaildrop.cekgear(hero.getid())<48){
            detaildrop.insert();
                System.out.println(detaildrop.gettipe());
        }else{JOptionPane.showMessageDialog(this, "Iventori Penuh !");}  
        } 
        }
        
        closedrop.setVisible(false);
        tambahan.setVisible(false);
        drop.setVisible(false);
        buang.setVisible(false);
        simpan.setVisible(false);
        tingkat.setVisible(false);
        tipe.setVisible(false);
        dhp.setVisible(false);
        atk.setVisible(false);
        def.setVisible(false);
        spd.setVisible(false);
        destingkat.setVisible(false);
        destipe.setVisible(false);
        deshp.setVisible(false);
        desatk.setVisible(false);
        desdef.setVisible(false);
        desspd.setVisible(false);
        
    }//GEN-LAST:event_simpanActionPerformed

    private void buangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buangActionPerformed
        selecteditem.delete(selecteditem.getid());//tombol buang pada drop
        closedrop.setVisible(false);
        tambahan.setVisible(false);
        drop.setVisible(false);
        buang.setVisible(false);
        simpan.setVisible(false);
        tingkat.setVisible(false);
        tipe.setVisible(false);
        dhp.setVisible(false);
        atk.setVisible(false);
        def.setVisible(false);
        spd.setVisible(false);
        destingkat.setVisible(false);
        destipe.setVisible(false);
        deshp.setVisible(false);
        desatk.setVisible(false);
        desdef.setVisible(false);
        desspd.setVisible(false);
        switch(selecteditem.gettipe()){
                case "pedang":{
                    iven("weapon");
                    break;
                }
                case "helm":{
                    iven("gear");
                    break;
                }
                case "armor":{
                    iven("gear");
                    break;
            }
                case "boot":{
                    iven("gear");
                    break;
                }
                case "food":{
                    iven("food");
                }
               
        }
    }//GEN-LAST:event_buangActionPerformed

    private void iven47MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven47MouseClicked
        detailitem(Integer.parseInt(iven47.getText()));
    }//GEN-LAST:event_iven47MouseClicked

    private void iven46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven46MouseClicked
        detailitem(Integer.parseInt(iven46.getText()));
    }//GEN-LAST:event_iven46MouseClicked

    private void iven45MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven45MouseClicked
        detailitem(Integer.parseInt(iven45.getText()));
    }//GEN-LAST:event_iven45MouseClicked

    private void iven44MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven44MouseClicked
        detailitem(Integer.parseInt(iven44.getText()));
    }//GEN-LAST:event_iven44MouseClicked

    private void iven43MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven43MouseClicked
        detailitem(Integer.parseInt(iven43.getText()));
    }//GEN-LAST:event_iven43MouseClicked

    private void iven42MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven42MouseClicked
        detailitem(Integer.parseInt(iven42.getText()));
    }//GEN-LAST:event_iven42MouseClicked

    private void iven41MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven41MouseClicked
        detailitem(Integer.parseInt(iven41.getText()));
    }//GEN-LAST:event_iven41MouseClicked

    private void iven40MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven40MouseClicked
        detailitem(Integer.parseInt(iven40.getText()));
    }//GEN-LAST:event_iven40MouseClicked

    private void iven39MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven39MouseClicked
        detailitem(Integer.parseInt(iven39.getText()));
    }//GEN-LAST:event_iven39MouseClicked

    private void iven38MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven38MouseClicked
        detailitem(Integer.parseInt(iven38.getText()));
    }//GEN-LAST:event_iven38MouseClicked

    private void iven37MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven37MouseClicked
        detailitem(Integer.parseInt(iven37.getText()));
    }//GEN-LAST:event_iven37MouseClicked

    private void iven36MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven36MouseClicked
        detailitem(Integer.parseInt(iven36.getText()));
    }//GEN-LAST:event_iven36MouseClicked

    private void iven35MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven35MouseClicked
        detailitem(Integer.parseInt(iven35.getText()));
    }//GEN-LAST:event_iven35MouseClicked

    private void iven34MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven34MouseClicked
        detailitem(Integer.parseInt(iven34.getText()));
    }//GEN-LAST:event_iven34MouseClicked

    private void iven33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven33MouseClicked
        detailitem(Integer.parseInt(iven33.getText()));
    }//GEN-LAST:event_iven33MouseClicked

    private void iven32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven32MouseClicked
        detailitem(Integer.parseInt(iven32.getText()));
    }//GEN-LAST:event_iven32MouseClicked

    private void iven31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven31MouseClicked
        detailitem(Integer.parseInt(iven31.getText()));
    }//GEN-LAST:event_iven31MouseClicked

    private void iven30MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven30MouseClicked
        detailitem(Integer.parseInt(iven30.getText()));
    }//GEN-LAST:event_iven30MouseClicked

    private void iven29MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven29MouseClicked
        detailitem(Integer.parseInt(iven29.getText()));
    }//GEN-LAST:event_iven29MouseClicked

    private void iven28MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven28MouseClicked
        detailitem(Integer.parseInt(iven28.getText()));
    }//GEN-LAST:event_iven28MouseClicked

    private void iven27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven27MouseClicked
        detailitem(Integer.parseInt(iven27.getText()));
    }//GEN-LAST:event_iven27MouseClicked

    private void iven26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven26MouseClicked
        detailitem(Integer.parseInt(iven26.getText()));
    }//GEN-LAST:event_iven26MouseClicked

    private void iven25MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven25MouseClicked
        detailitem(Integer.parseInt(iven25.getText()));
    }//GEN-LAST:event_iven25MouseClicked

    private void iven24MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven24MouseClicked
        detailitem(Integer.parseInt(iven24.getText()));
    }//GEN-LAST:event_iven24MouseClicked

    private void iven23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven23MouseClicked
        detailitem(Integer.parseInt(iven23.getText()));
    }//GEN-LAST:event_iven23MouseClicked

    private void iven22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven22MouseClicked
        detailitem(Integer.parseInt(iven22.getText()));
    }//GEN-LAST:event_iven22MouseClicked

    private void iven21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven21MouseClicked
        detailitem(Integer.parseInt(iven21.getText()));
    }//GEN-LAST:event_iven21MouseClicked

    private void iven20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven20MouseClicked
       detailitem(Integer.parseInt(iven20.getText()));
    }//GEN-LAST:event_iven20MouseClicked

    private void iven19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven19MouseClicked
        detailitem(Integer.parseInt(iven19.getText()));
    }//GEN-LAST:event_iven19MouseClicked

    private void iven18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven18MouseClicked
        detailitem(Integer.parseInt(iven18.getText()));
    }//GEN-LAST:event_iven18MouseClicked

    private void iven17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven17MouseClicked
        detailitem(Integer.parseInt(iven17.getText()));
    }//GEN-LAST:event_iven17MouseClicked

    private void iven16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven16MouseClicked
        detailitem(Integer.parseInt(iven16.getText()));
    }//GEN-LAST:event_iven16MouseClicked

    private void iven15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven15MouseClicked
        detailitem(Integer.parseInt(iven15.getText()));
    }//GEN-LAST:event_iven15MouseClicked

    private void iven14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven14MouseClicked
        detailitem(Integer.parseInt(iven14.getText()));
    }//GEN-LAST:event_iven14MouseClicked

    private void iven13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven13MouseClicked
        detailitem(Integer.parseInt(iven13.getText()));
    }//GEN-LAST:event_iven13MouseClicked

    private void iven12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven12MouseClicked
        detailitem(Integer.parseInt(iven12.getText()));
    }//GEN-LAST:event_iven12MouseClicked

    private void iven11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven11MouseClicked
        detailitem(Integer.parseInt(iven11.getText()));
    }//GEN-LAST:event_iven11MouseClicked

    private void iven10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven10MouseClicked
        detailitem(Integer.parseInt(iven10.getText()));
    }//GEN-LAST:event_iven10MouseClicked

    private void iven9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven9MouseClicked
        detailitem(Integer.parseInt(iven9.getText()));
    }//GEN-LAST:event_iven9MouseClicked

    private void iven8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven8MouseClicked
        detailitem(Integer.parseInt(iven8.getText()));
    }//GEN-LAST:event_iven8MouseClicked

    private void iven7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven7MouseClicked
        detailitem(Integer.parseInt(iven7.getText()));
    }//GEN-LAST:event_iven7MouseClicked

    private void iven6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven6MouseClicked
        detailitem(Integer.parseInt(iven6.getText()));
    }//GEN-LAST:event_iven6MouseClicked

    private void iven5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven5MouseClicked
        detailitem(Integer.parseInt(iven5.getText()));
    }//GEN-LAST:event_iven5MouseClicked

    private void iven4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven4MouseClicked
        detailitem(Integer.parseInt(iven4.getText()));
    }//GEN-LAST:event_iven4MouseClicked

    private void iven3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven3MouseClicked
        detailitem(Integer.parseInt(iven3.getText()));
    }//GEN-LAST:event_iven3MouseClicked

    private void iven2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven2MouseClicked
        detailitem(Integer.parseInt(iven2.getText()));
    }//GEN-LAST:event_iven2MouseClicked

    private void iven1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven1MouseClicked
        detailitem(Integer.parseInt(iven1.getText()));
    }//GEN-LAST:event_iven1MouseClicked

    private void iven0MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iven0MouseClicked
        detailitem(Integer.parseInt(iven0.getText()));
    }//GEN-LAST:event_iven0MouseClicked

    private void iconcaraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_iconcaraMouseClicked
        viewstat();
    }//GEN-LAST:event_iconcaraMouseClicked

    private void drop(){ //jika mengalahkan monster mendapatkan drop yang random
        simpan.setText("Simpan");
        simpan.setVisible(true);
        buang.setVisible(true);
        closedrop.setLocation(48*12, 48);
        closedrop.setVisible(true);
        closedrop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/close.png")));
        closedrop.setSize(48, 48);
        tambahan.setVisible(true);
        tambahan.setSize(480,480);
        tambahan.setLocation(48*3, 48);
        tambahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/drop.png")));
        drop.setVisible(true);
        tingkat.setVisible(true);
        tipe.setVisible(true);
        tipe.setText("Tipe :");
        dhp.setVisible(true);
        atk.setVisible(true);
        def.setVisible(true);
        spd.setVisible(true);
        destingkat.setVisible(true);
        destipe.setVisible(true);
        destipe.setText("Tipe :");
        deshp.setVisible(true);
        desatk.setVisible(true);
        desdef.setVisible(true);
        desspd.setVisible(true);
        Random r = new Random();
        int res = r.nextInt(27)+1;
        if(res<4){ //drop rate setiap item
            int atk=r.nextInt(10);
            int spd=r.nextInt(5);
            detaildrop("pedang","pedang","common","pedang",atk,0,spd,0);
        }else if(res<6){
            int atk=r.nextInt(50)+10;
            int spd=r.nextInt(10);
            detaildrop("pedang1","pedang","rare","pedang",atk,0,spd,0);
        }else if(res==6){
            int atk=r.nextInt(100)+50;
            int spd=r.nextInt(15);
            detaildrop("pedang2","pedang","epic","pedang",atk,0,spd,0);
        }else if(res<10){
            int def = r.nextInt(5);
            detaildrop("helm","gear","common","helm",0,def,0,0);
        }else if(res<12){
            int def = r.nextInt(15)+5;
            detaildrop("helm1","gear","rare","helm",0,def,0,0);
        }else if(res==12){
            int def = r.nextInt(40)+15;
            detaildrop("helm2","gear","epic","helm",0,def,0,0);
        }else if(res<16){
            int atk=r.nextInt(5);
            int def=r.nextInt(20);
            detaildrop("armor","gear","common","armor",atk,def,0,0);
        }else if(res<18){
            int atk=r.nextInt(10);
            int def=r.nextInt(50)+20;
            detaildrop("armor1","gear","rare","armor",atk,def,0,0);
        }else if(res==18){
            int atk=r.nextInt(20);
            int def=r.nextInt(100)+50;
            detaildrop("armor2","gear","epic","armor",atk,def,0,0);
        }else if(res<22){
            int spd = r.nextInt(5);
            detaildrop("boot","gear","common","boot",0,0,spd,0);
        }else if(res<24){
            int spd = r.nextInt(20);
            detaildrop("boot1","gear","rare","boot",0,0,spd,0);
        }else if(res==24){
            int spd = r.nextInt(50);
            detaildrop("boot2","gear","epic","boot",0,0,spd,0);
        }else if(res==25){
            int hp = 200;
            detaildrop("food","food","common","food",0,0,0,hp);
        }else if(res==26){
            int hp = 500;
            detaildrop("food1","food","rare","food",0,0,0,hp);
        }else if(res==27){
            int hp = 1000;
            detaildrop("food2","food","epic","food",0,0,0,hp);
        }
    }
    private void detaildrop(String item,String tippe,String tingkat,String tipe,int atk,int def,int spd,int hp){
        drop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/det"+item+".png")));
        desatk.setText(Integer.toString(atk));
        desdef.setText(Integer.toString(def));
        desspd.setText(Integer.toString(spd));
        deshp.setText(Integer.toString(hp));
        destipe.setText(tipe);
        destingkat.setText(tingkat);
        detaildrop.setAtk(atk);
        detaildrop.setid(detaildrop.idbaru()+1);
        detaildrop.setDef(def);
        detaildrop.setSpd(spd);
        detaildrop.sethp(hp);
        detaildrop.settipe(tipe);
        detaildrop.setnama(item);
        detaildrop.setidhero(hero.getid());
    }
    private void detailitem(int id){ //jika item di iventori diklik akan muncul detail
        simpan.setVisible(true);
        simpan.setText("Pakai");
        buang.setVisible(true);
        closedrop.setLocation(48*12, 48);
        closedrop.setVisible(true);
        closedrop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/close.png")));
        closedrop.setSize(48, 48);
        tambahan.setVisible(true);
        tambahan.setSize(480,480);
        tambahan.setLocation(48*3, 48);
        tambahan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/drop.png")));
        drop.setVisible(true);
        tipe.setVisible(true);
        dhp.setVisible(true);
        atk.setVisible(true);
        def.setVisible(true);
        spd.setVisible(true);
        //destingkat.setVisible(true);
        destipe.setVisible(true);
        tipe.setText("Tipe :");
        deshp.setVisible(true);
        desatk.setVisible(true);
        desdef.setVisible(true);
        desspd.setVisible(true);
        Iventori sitem = new Iventori();
        sitem.select(id);
        selecteditem.select(id);
        if(sitem.gettipe().equals("food")){
            simpan.setText("Makan");
        }
        drop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/det"+sitem.getnama()+".png")));
        desatk.setText(Integer.toString(sitem.getAtk()));
        desdef.setText(Integer.toString(sitem.getDef()));
        desspd.setText(Integer.toString(sitem.getSpd()));
        deshp.setText(Integer.toString(sitem.gethp()));
        destipe.setText(sitem.gettipe());

    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Map.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Map(hero).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel animasi;
    private javax.swing.JLabel atk;
    private javax.swing.JPanel bghpbarmonster;
    private javax.swing.JButton buang;
    private javax.swing.JLabel cara;
    private javax.swing.JLabel close;
    private javax.swing.JLabel closedrop;
    private javax.swing.JLabel def;
    private javax.swing.JLabel desatk;
    private javax.swing.JLabel desdef;
    private javax.swing.JLabel deshp;
    private javax.swing.JLabel desspd;
    private javax.swing.JLabel destingkat;
    private javax.swing.JLabel destipe;
    private javax.swing.JLabel dhp;
    private javax.swing.JLabel drop;
    private javax.swing.JPanel expbar;
    private javax.swing.JLabel hp;
    private javax.swing.JPanel hpbar;
    private javax.swing.JPanel hpbarmonster;
    private javax.swing.JLabel iconcara;
    private javax.swing.JLabel iven0;
    private javax.swing.JLabel iven1;
    private javax.swing.JLabel iven10;
    private javax.swing.JLabel iven11;
    private javax.swing.JLabel iven12;
    private javax.swing.JLabel iven13;
    private javax.swing.JLabel iven14;
    private javax.swing.JLabel iven15;
    private javax.swing.JLabel iven16;
    private javax.swing.JLabel iven17;
    private javax.swing.JLabel iven18;
    private javax.swing.JLabel iven19;
    private javax.swing.JLabel iven2;
    private javax.swing.JLabel iven20;
    private javax.swing.JLabel iven21;
    private javax.swing.JLabel iven22;
    private javax.swing.JLabel iven23;
    private javax.swing.JLabel iven24;
    private javax.swing.JLabel iven25;
    private javax.swing.JLabel iven26;
    private javax.swing.JLabel iven27;
    private javax.swing.JLabel iven28;
    private javax.swing.JLabel iven29;
    private javax.swing.JLabel iven3;
    private javax.swing.JLabel iven30;
    private javax.swing.JLabel iven31;
    private javax.swing.JLabel iven32;
    private javax.swing.JLabel iven33;
    private javax.swing.JLabel iven34;
    private javax.swing.JLabel iven35;
    private javax.swing.JLabel iven36;
    private javax.swing.JLabel iven37;
    private javax.swing.JLabel iven38;
    private javax.swing.JLabel iven39;
    private javax.swing.JLabel iven4;
    private javax.swing.JLabel iven40;
    private javax.swing.JLabel iven41;
    private javax.swing.JLabel iven42;
    private javax.swing.JLabel iven43;
    private javax.swing.JLabel iven44;
    private javax.swing.JLabel iven45;
    private javax.swing.JLabel iven46;
    private javax.swing.JLabel iven47;
    private javax.swing.JLabel iven5;
    private javax.swing.JLabel iven6;
    private javax.swing.JLabel iven7;
    private javax.swing.JLabel iven8;
    private javax.swing.JLabel iven9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel labelmap;
    private javax.swing.JLabel modelcara;
    private javax.swing.JLabel monster0;
    private javax.swing.JLabel monster1;
    private javax.swing.JLabel monster2;
    private javax.swing.JLabel monster3;
    private javax.swing.JLabel monster4;
    private javax.swing.JLabel npc;
    private javax.swing.JLabel pcara;
    private javax.swing.JLabel pmonster;
    private javax.swing.JPanel progres;
    private javax.swing.JButton simpan;
    private javax.swing.JButton skill1;
    private javax.swing.JButton skill2;
    private javax.swing.JButton skill3;
    private javax.swing.JLabel spd;
    private javax.swing.JLabel tambahan;
    private javax.swing.JLabel tingkat;
    private javax.swing.JLabel tipe;
    // End of variables declaration//GEN-END:variables
}
