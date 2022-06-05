module Converter {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    exports com.project.converter;
    opens com.project.converter to javafx.fxml, com.google.gson;
}