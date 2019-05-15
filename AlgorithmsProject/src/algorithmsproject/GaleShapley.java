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
    
    public void parsingData() {

        readfile.readFile();
        antsmap = readfile.getMap();
        protimiseis = new HashMap<>();
        
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
        
        sortprotimiseis();

        protimiseis.values().forEach((key) -> {
            System.out.println(key);
        });
        
        katharismos();
        
        protimiseis.values().forEach((key) -> {
            System.out.println(key);
        });
        
        
    }
    
    private void katharismos(){
        
        System.out.println("eoeoeoeoeoeo");
        
        for (int i=1;i<protimiseis.size()+1;i++){
            int epanalipseis = protimiseis.get(i).size();
            for (int j=epanalipseis-1;j>1;j-=2){
                protimiseis.get(i).remove(j);
            }
        }
        
    }
    
    private void sortprotimiseis(){
        System.out.println("Ksekinaei h sort");
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
