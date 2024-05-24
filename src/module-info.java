module tpRevision {
	requires javafx.controls;
	requires java.sql;
	requires javafx.fxml;
	requires javafx.base;
	
	opens application to javafx.base,javafx.controls, javafx.graphics, javafx.fxml;
}
