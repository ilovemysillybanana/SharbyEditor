package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;

public class EditorController implements Initializable {
	@FXML
	private GridPane gridPane; 
	@FXML
	private TabPane tabPaneCode;
	// tree file viewer to be implemented
	@FXML
	private TreeView<File> filesTree;

	@FXML
	private void btnOpen(ActionEvent event) {

	}

	@FXML
	private void btnAbout(ActionEvent event) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("SharbyIDE: Information");
		alert.setHeaderText(null);
		alert.setContentText("This is SharbyIDE, a simple and soon to be elegant cross platform RubyIDE.");
		alert.showAndWait();
	}

	public void initialize(URL url, ResourceBundle rb) {
		Tab startPage = new Tab("Start Page");
		tabPaneCode.getTabs().add(startPage);

		File defaultDirectory = new File("/home/jose/workspace/");
		try {
			findFiles(defaultDirectory, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Override
	private void findFiles(File dir, TreeItem<File> parent) throws IOException {
		TreeItem<File> root = new TreeItem<>(dir);
		root.setExpanded(false);
				
		File[] files = dir.listFiles();
		
        for (File file : files) {
            if (file.isDirectory()) {
                //System.out.println("directory:" + file.getCanonicalPath());
                findFiles(file, root);
            } else {
                //System.out.println("     file:" + file.getCanonicalPath());
                root.getChildren().add(new TreeItem<>(file));
            }
            if(parent==null){
            	filesTree.setRoot(root);
            }else{
            	parent.getChildren().add(root);
            }
            
            System.out.println(file.getCanonicalPath());

        }
	}

}
