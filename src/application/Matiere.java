package application;

public class Matiere {
	private String nom;
	private int niveau;
	private String description;
	private float nbreHeure;

	public Matiere(String nom, int niveau, String description, float nbh) {
		 this.nom=nom; 
		 this.niveau=niveau;                
		 this.description=description; 
		 this.nbreHeure=nbh;   }

	public String getNom() { 
		return nom;}

	public int  getNiveau() { return niveau;}

	public String getDescription() { return description;}

	public float  getnbreHeure() { return nbreHeure;}
}