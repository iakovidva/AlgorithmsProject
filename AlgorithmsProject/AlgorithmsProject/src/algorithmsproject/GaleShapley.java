/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithmsproject;

import java.util.*;


/**
 *
 * @author vasilis
 */
public class GaleShapley {
    
    ReadFile readfile = new ReadFile();
    HashMap<Integer, ArrayList<Float>> antsmap;
    HashMap<Integer, LinkedList <Float>> protimiseis;
    HashMap<Integer, LinkedList <Integer>> intprotimiseis;
    
    public void parsingData() {

        readfile.readFile();
        antsmap = readfile.getMap();
        protimiseis = new HashMap<>();
        intprotimiseis = new HashMap<>();
        
        for (int i=1;i<antsmap.size()+1;i++){
            LinkedList<Float> temp = new LinkedList();
            temp.add((float)i);
            if (i%2==1){
                for (int j=2;j<antsmap.size()+1;j+=2){
                    temp.add((float)j);
                    temp.add(euclDist(antsmap.get(i).get(1), antsmap.get(j).get(1), antsmap.get(i).get(2), antsmap.get(j).get(2)));
                }
            }
            if (i%2==0){
                for (int j=1;j<antsmap.size();j+=2){
                    temp.add((float)j);
                    temp.add(euclDist(antsmap.get(i).get(1), antsmap.get(j).get(1), antsmap.get(i).get(2), antsmap.get(j).get(2)));
                }
            }
            protimiseis.put(i, temp);
        }
        
        System.out.println("ARXIKO");
        protimiseis.values().forEach((key) -> {
            System.out.println(key);
        });
        
        
        sortprotimiseis();
        System.out.println("META SORT");
        protimiseis.values().forEach((key) -> {
            System.out.println(key);
        });
        
        
        katharismos();
        System.out.println("META KATHARISMO");
        protimiseis.values().forEach((key) -> {
            System.out.println(key);
        });
        
        
        System.out.println("THELW NA ANTIGRAPSW SE INT");
        
        
        for (int i=1;i<protimiseis.size()+1;i++){
            LinkedList<Integer> alist = new LinkedList<>();
            for (int j=0;j<protimiseis.get(i).size();j++){
                float temp=protimiseis.get(i).get(j);
                int add = (int) temp;
                alist.add(add);
//                System.out.println(i+" "+j);
            }
            intprotimiseis.put(i, alist);
        }
        System.out.println("META INT");
        intprotimiseis.values().forEach((key) -> {
            System.out.println(key);
        });
        
        tairiasma();
        
    }
    
    private void tairiasma(){
        
        //bazw oti oloi einai eleutheroi. 0=eleutheros
        for (int i=1;i<intprotimiseis.size()+1;i++){
            intprotimiseis.get(i).add(0);
        }
        
        boolean flag = true;
        
        
        //*/
        while (flag == true){
            // elegxw an uparxei estw kai enas me 0.
            flag = false; // estw oti den uparxei eleutheros.
            for (int i=1;i<intprotimiseis.size();i+=2){
                int check=intprotimiseis.get(i).getLast();
                //System.out.println(i+"  " + check);
                if (check==0){
                    //System.out.println("ELEUTHEROS o: "+ i);
                    flag=true;
                    break; // gia na bgei apo for.
                }
            }
            
            // DIALEKSE ANTRA. protimiseis.get(1) == Prwtos antras. Antres ana duo.
            for (int i=1;i<intprotimiseis.size();i+=2){
                if (intprotimiseis.get(i).getLast()==0 ){ // AN ANTRAS ELEUTHEROS
                    int first = intprotimiseis.get(i).get(1); // PRWTH EPILOGH TOU ANTRA
                    if (intprotimiseis.get(first).getLast()==0){ // SHMAINEI GOMENA ELEUTHERH 
                        
                        intprotimiseis.get(i).set(intprotimiseis.get(i).size()-1, 1); // thetw antras pantremenos
                        int thesi_antra = intprotimiseis.get(first).indexOf(i);
                        intprotimiseis.get(first).set(intprotimiseis.get(first).size()-1, thesi_antra); // thetw gunaika pantremenh
                        System.out.println("\n\n\n"+i+" <3 "+first);
                        //System.out.println(" ta eftiaxe me ton noumero: "+thesi_antra);
                        
                        
                    }
                    else if(intprotimiseis.get(first).getLast()!=0){ //GOMENA PANTREMENH
                        int thesi_twrinou = intprotimiseis.get(first).getLast();
                        int twrinos_antras= intprotimiseis.get(first).get(thesi_twrinou);
                        int thesi_antra = intprotimiseis.get(first).indexOf(i);
                        if (thesi_antra<thesi_twrinou){ // KARIOLA
                            intprotimiseis.get(i).set(intprotimiseis.get(i).size()-1, 1); // thetw neo antras pantremenos
                            intprotimiseis.get(first).set(intprotimiseis.get(first).size()-1, thesi_antra); // thetw gunaika pantremenh me ton neo
                            intprotimiseis.get(twrinos_antras).set(intprotimiseis.get(twrinos_antras).size()-1, 0); //PROHGOUMENOS MENEI ELEUTHEROS
                            intprotimiseis.get(twrinos_antras).removeFirst(); // SBHNEI THN KARIOLA APO THN ZWH TOU.
                        }
                        else{
                            System.out.println("PANTREMENH ALLA OXI KERATO");
                            intprotimiseis.get(i).removeFirst(); // APPORIPTEI    
                        }
                    }
                }
            }
            
            
        }//*/
        
        
        
        System.out.println("PANTREMENOI OLOI MOUNARES");

        
    }
    
    private void katharismos(){
            
        for (int i=1;i<protimiseis.size()+1;i++){
            int epanalipseis = protimiseis.get(i).size();
            for (int j=epanalipseis-1;j>1;j-=2){
                protimiseis.get(i).remove(j);
            }
        }
        
    }
    
    private void sortprotimiseis(){
        float temp,temp1;
        for (int i=1;i<protimiseis.size()+1;i++){
            
            for (int j=2;j<protimiseis.size()+1;j+=2){
                for (int u=j+2;u<protimiseis.size()+1;u+=2){
                    if ((protimiseis.get(i).get(j))>(protimiseis.get(i).get(u))){
                        temp=protimiseis.get(i).get(u-1);
                        temp1=protimiseis.get(i).get(u);
                        protimiseis.get(i).set(u-1, protimiseis.get(i).get(j-1));
                        protimiseis.get(i).set(u, protimiseis.get(i).get(j));
                        protimiseis.get(i).set(j-1, temp);
                        protimiseis.get(i).set(j,temp1);               
                    }
                }
            }
        }
    }
    
    private float euclDist(float a, float b, float c, float d) {
        float euclDist;
        euclDist = (float) Math.sqrt(Math.pow(a - b, 2) + Math.pow(c - d, 2));
        return euclDist;
    }
}
