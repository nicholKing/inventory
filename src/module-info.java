module SampleJavaFX {
	requires javafx.controls;
	
	opens application to javafx.graphics, javafx.fxml;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.graphics;
	requires mysql.connector.java;
	requires java.sql;
}
