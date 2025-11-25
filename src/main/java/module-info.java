module com.mycompany.aplikasipenjadwalan {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.aplikasipenjadwalan to javafx.fxml;
    exports com.mycompany.aplikasipenjadwalan;
}
