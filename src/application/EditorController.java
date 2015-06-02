package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

public class EditorController implements Initializable {
	@FXML
	private GridPane gridPane;
	@FXML
	private TabPane tabPaneCode;

	private List<CodeArea> codeAreas = new ArrayList<CodeArea>();

	// tree file viewer to be implemented
	@FXML
	private TreeView<File> filesTree;

	@FXML
	protected void btnOpen(ActionEvent event) {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Program File");
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Text Files", "*.txt"),
					new FileChooser.ExtensionFilter("Ruby File", "*.rb",".rbw"),
					new FileChooser.ExtensionFilter("All File", "*.*"));

			File selectedFile = fileChooser.showOpenDialog(null);

			if (selectedFile != null) {
				Tab tab = new Tab(selectedFile.getPath());

				CodeArea codeFileArea = new CodeArea(readFile(selectedFile));
				codeFileArea.setParagraphGraphicFactory(LineNumberFactory
						.get(codeFileArea));

				tab.setContent(codeFileArea);

				tabPaneCode.getTabs().add(tab);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
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

		codeAreas.add(new CodeArea());
		codeAreas.get(0).setParagraphGraphicFactory(
				LineNumberFactory.get(codeAreas.get(0)));

		startPage.setContent(codeAreas.get(0));
		tabPaneCode.getTabs().add(startPage);

		File defaultDirectory = new File("/home/jose/workspace/");

		try {
			findFiles(defaultDirectory, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String readFile(File file) {
		StringBuilder stringBuffer = new StringBuilder();
		BufferedReader bf = null;

		try {
			bf = new BufferedReader(new FileReader(file));
			String text;
			while ((text = bf.readLine()) != null) {
				stringBuffer.append(text);
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(EditorController.class.getName()).log(
					Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(EditorController.class.getName()).log(
					Level.SEVERE, null, ex);
		} finally {
			try {
				bf.close();
			} catch (IOException ex) {
				Logger.getLogger(EditorController.class.getName()).log(
						Level.SEVERE, null, ex);
			}
		}

		return stringBuffer.toString();
	}

	private void findFiles(File dir, TreeItem<File> parent) throws IOException {
		TreeItem<File> root = new TreeItem<>(dir);
		root.setExpanded(false);

		File[] files = dir.listFiles();

		for (File file : files) {
			if (file.isDirectory()) {
				// when the file found is a directory it calls itself
				// recursively to find all files inside that one
				findFiles(file, root);
			} else {
				root.getChildren().add(new TreeItem<>(file));
			}
			if (parent == null) {
				filesTree.setRoot(root);
			} else {
				parent.getChildren().add(root);
			}

			System.out.println(file.getCanonicalPath());

		}
	}

}
