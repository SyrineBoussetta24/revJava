package application;

import java.util.ArrayList;

public class Enseignant {
	private int id; 
	private String nom;                            
	private String grade; 
	ArrayList <Matiere>listmat ; 
	 
	public Enseignant(String m, String grade, ArrayList<Matiere>list1) { 
	  nom=m; 
	  this.grade=grade;                        
	  listmat=list1; 
	  } 
	public String getNom() { return nom;} 
	public int  getId() { return id;} 
	public String getGrade() { return grade;}}

