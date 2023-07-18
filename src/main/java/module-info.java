module com.empty.pm3000_fx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens org.application.objects.user to com.fasterxml.jackson.databind;
    exports org.application.objects.user;
    opens org.application.objects.website to com.fasterxml.jackson.databind;
    exports org.application.objects.website;

    opens org.application;
    exports org.application;
    exports org.application.gui.fx.controller;
    opens org.application.gui.fx.controller to javafx.fxml;
    exports org.application.gui.fx;
    opens org.application.gui.fx to javafx.fxml;
}