package application;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import Connexion.connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class EnseignantController  implements Initializable  {
	@FXML
	private Button btnModifier;

	@FXML
	private Button btnRecherche;

	@FXML
	private Button btnSupp;

	@FXML
	private ComboBox<Integer> cbEnseignants;

	@FXML
	private TableColumn<Matiere, String> colDescMat;

	@FXML
	private TableColumn<Matiere, Integer> colNiveauMat;

	@FXML
	private TableColumn<Matiere, String> colnomMat;

	@FXML
	private TableView<Matiere> tableV;

	@FXML
	private TextField tfChargeH;

	@FXML
	private TextField tfGrade;

	@FXML
	private TextField tfNewGrade;

	@FXML
	private TextField tfNom;

	ObservableList<Matiere> listM = FXCollections.observableArrayList();

    


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		ArrayList<Integer> list = new ArrayList<Integer>();

		 try {
		        Connection conn = connexion.getConnection();
		        Statement stmt = conn.createStatement();
		        String query = "SELECT * FROM enseignant";
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		        	list.add(rs.getInt("id")); 
		        }
				 ObservableList<Integer> listE = FXCollections.observableArrayList(list); 
		        cbEnseignants.setItems(listE); 
		    } catch (SQLException e) {
		        System.err.println("Error executing query: " + e.getMessage());
		    }
	    
	    
	     colnomMat.setCellValueFactory(new PropertyValueFactory<Matiere, String>("nom"));
	     colNiveauMat.setCellValueFactory(new PropertyValueFactory<Matiere, Integer>("niveau"));
	     colDescMat.setCellValueFactory(new PropertyValueFactory<Matiere, String>("description"));

		 tableV.setItems(listM);

		
	}
	@FXML
    public void getEnseignant(ActionEvent event) {
        Integer selectedE = cbEnseignants.getValue();
        try {
            Connection con = connexion.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nom, grade FROM enseignant WHERE id = " + selectedE);
            if (rs.next()) {
                String nom = rs.getString("nom");
                String grade = rs.getString("grade");
                tfNom.setText(nom);
                tfGrade.setText(grade);
            }
            ResultSet rs2 = stmt.executeQuery("SELECT nom, niveau, description, nbreHeure FROM matière WHERE idE = " + selectedE);
            listM.clear();
            int tot = 0;
            while (rs2.next()) {
                String nomM = rs2.getString("nom");
                int niveauM = rs2.getInt("niveau");
                String descriptionM = rs2.getString("description");
                int heuresM = rs2.getInt("nbreHeure");

                listM.add(new Matiere(nomM, niveauM, descriptionM, heuresM));
                tot += heuresM;
            }

            tfChargeH.setText(String.valueOf(tot));
            tableV.setItems(listM);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	public void deleteMatiere() {
		Matiere selected=tableV.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(AlertType.INFORMATION);
		 alert.setTitle("Form confirmation!");
		 alert.setHeaderText(null);
		 alert.setContentText("tu veux supprimer ?");
		Optional<ButtonType>o= alert.showAndWait();
		if(o.get().equals(ButtonType.OK))
		{
			tableV.getItems().remove(selected)	;
		}
		try {
			Connection conn = connexion.getConnection();
			PreparedStatement stmt = conn.prepareStatement("delete from matière WHERE nom=?");
			stmt.setString(1,selected.getNom());
			stmt.executeUpdate();
			
		}
		catch (SQLException e) {
			System.err.println("Error executing query: " + e.getMessage());
		}
		 
    }
	@FXML
	void updateGrade(ActionEvent event) {
	    Integer selectedE = cbEnseignants.getValue();
	    String ng = tfNewGrade.getText();
	    Alert confirmAlert = new Alert(AlertType.CONFIRMATION);
	    confirmAlert.setTitle("Confirmation de up date");
	    confirmAlert.setContentText("Êtes-vous sûr de  mettre à jour le grade?");
	    Optional<ButtonType> result = confirmAlert.showAndWait();
	    if (result.isPresent() && result.get() == ButtonType.OK) {
	        try {
	            Connection con = connexion.getConnection();
	            Statement stmt = con.createStatement();
	            String updateQuery = "update enseignant set grade = '" + ng + "' where id = " + selectedE;
	            stmt.executeUpdate(updateQuery);
	            tfGrade.setText(ng);
	            FileWriter writer = new FileWriter("C:\\DSI23 PART2\\SEM 2\\JAVA\\tpRevision\\enseignant.txt", true); 
	            writer.write("id enseignant: " + selectedE + " nouveau grade: " + ng);
	            writer.close();
	            Alert successAlert = new Alert(AlertType.INFORMATION);
	            successAlert.setContentText("Le grade a été mis à jour avec succès.");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	}
	

}
