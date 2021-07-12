package michael.plath.GUI;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import michael.plath.core.*;
import michael.plath.core.dao.FileSource;
import michael.plath.core.dao.FileSourceImpl;
import michael.plath.model.*;

import java.io.File;
import java.io.FileInputStream;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        //FILL RETURN FORMS AND OTHER FORMS PROPERLY. TEST WITH OTHER DATA
        //FIX R08 PAGES ISSUES.
        //ADD PROPERTIES FOR FILE OUTPUT PATHS
        //ADD TABLEVIEW WITH CHECKBOXES AFTER LOADING DATASET

        //TURN INTO TWO COMBOBOXES.STATE AND FORM TYPE. LOAD DATA FROM ELSEWHERE
        String[] selectableFormNames = {"New Jersey Bi-Monthly Tax", "New York Monthly Tax", "California Tax"};
        Label selectableFormsLabel = new Label("Select:");
        ComboBox<PreparedForm> selectableForms = new ComboBox(FXCollections.observableArrayList(selectableFormNames));
        //BooleanBinding selectableFormsValid = Bindings.isEmpty(selectableForms.getSelectionModel().getSelectedIndex());
        selectableForms.setOnAction(e -> {
            int selectedIndex = selectableForms.getSelectionModel().getSelectedIndex();
            //PreparedForm selectedItem = selectableForms.getSelectionModel().getSelectedItem();
        });


        HBox selectableFormsHBox = new HBox(10,selectableFormsLabel,selectableForms);
        selectableFormsHBox.setAlignment(Pos.CENTER);

        TextField toLoad = new TextField();
        toLoad.setMinWidth(200);
        toLoad.setEditable(false);
        BooleanBinding toLoadValid = Bindings.isEmpty(toLoad.textProperty());
        Button fileSelectionButton = new Button("Select");
        fileSelectionButton.setOnAction(e ->{
        File id = new File("C:/");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Excel File","*.xlsx","*.xls"));
        fileChooser.setInitialDirectory(id);
        //next line and if/else statement opens a file selection window to choose current list
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            toLoad.setText(selectedFile.toString());
            System.out.println(selectedFile.toString() + " selected!");
        } else {
            System.out.println("You must select a file");
        }});
        HBox toLoadHBox = new HBox(10,toLoad,fileSelectionButton);
        toLoadHBox.setAlignment(Pos.CENTER);


        Button runButton = new Button("Run"); //disable if no form or toLoad is selected
        runButton.disableProperty().bind(toLoadValid);
        runButton.setOnAction(e ->{
            FileSource ourData = new FileSourceImpl();
            //get data from source
            File loadableFile = new File(toLoad.textProperty().getValue());
            ourData.setSource(loadableFile);
            ourData.connect();
            FileInputStream dataStream = ourData.getData();
            //arrange and calculate data. ADD ABSTRACT FACTORY TO DETERMINE DATASORTER & PREPARED FORM
            NJDataSorter dataSorter = new NJDataSorter(dataStream);
            //load fileSource in DAO package. create model with DataSetImpl in model package
            DataSet dataSet = new DataSetImpl(dataSorter);
            PreparedForm nj = new NJBiMonthlyTax();
            nj.build();
            Report report = new PortfolioPaymentReport();
            report.produce();
        });
        HBox runHBox = new HBox(10,runButton);
        runHBox.setAlignment(Pos.CENTER);

        VBox masterVBox = new VBox(10,selectableFormsHBox,toLoadHBox,runHBox);
        masterVBox.setPadding(new Insets(10));

        //add something to tell where output of PDF file is and breakdown of portfolio file

        Scene scene = new Scene(masterVBox);

        //Set the window's title.
        primaryStage.setTitle ("Tax Form Filler");
        primaryStage.setResizable(false);
        primaryStage.setMinWidth(300);
        primaryStage.setMinHeight(200);


        //Set the scene to the stage and display it
        primaryStage.setScene (scene);
        primaryStage.show();

    }
}
