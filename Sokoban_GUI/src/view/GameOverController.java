package view;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

import db.DbInterface;
import db.DbManager;
import db.Records;
import javafx.beans.binding.BooleanBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

public class GameOverController implements Initializable {

	// TABLE
	@FXML
	TableView<Records> table;
	@FXML
	TableColumn<Records, String> playerCol;
	@FXML
	TableColumn<Records, Integer> stepsCol;
	@FXML
	TableColumn<Records, Integer> timeCol;
	@FXML
	TableColumn<Records, String> levelCol;

	// DATA TABLE
	ObservableList<Records> _tableData;

	// HEAD TEXT
	@FXML
	Text timeText;
	@FXML
	Text stepsText;
	@FXML
	Text levelIdText;
	@FXML
	TextField nicknameField;
	@FXML
	TextField searchField;
	@FXML
	Button submitButton;
	@FXML
	Button searchButton;
	@FXML
	Text title;

	// HEAD DATA
	private int _time;
	private int _steps;
	private String _levelId;

	private DbManager _dbm;
	private int _numOfRecords;
	private String _defaultSort;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		_numOfRecords = 10;
		_defaultSort = "Steps";

		// INIT DATA
		this._dbm = new DbManager();
		_tableData = FXCollections.observableArrayList();
		// list to ObservableList
		// topRecordsInitialize(_numOfRecords);

		table.getColumns().clear();
		table.setItems(_tableData);

		table.getColumns().add(playerCol);
		table.getColumns().add(stepsCol);
		table.getColumns().add(timeCol);
		table.getColumns().add(levelCol);

		playerCol.setCellValueFactory(new PropertyValueFactory("nickname"));
		stepsCol.setCellValueFactory(new PropertyValueFactory("steps"));
		timeCol.setCellValueFactory(new PropertyValueFactory("time"));
		levelCol.setCellValueFactory(new PropertyValueFactory("levelId"));
		//Click on Table
		table.setRowFactory(tv->{
			TableRow <Records> row=new TableRow<>();
			row.setOnMouseClicked(event->{
				if(event.getClickCount()==2 &&(!row.isEmpty()) ){
					Records rowData=row.getItem();
					search(rowData.getNickname());
				}
			});
			return row;
		});

		// Set Title ("Top Scores:") action:
		title.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				topRecordsInitialize(_numOfRecords);
				;
			}
		});

	}

	public void topRecordsInitialize(int numOfRecords) {
		_tableData.clear();
		List<Records> l = this._dbm.recordsByLevels(this._levelId, this._defaultSort, this._numOfRecords);
		for (int i = 0; i < l.size(); i++) {
			_tableData.add(l.get(i));
		}
	}

	public void initData(String LevelId, int time, int steps) {
		this._levelId = LevelId;
		this._time = time;
		this._steps = steps;

		this.levelIdText.setText(String.valueOf(LevelId));
		this.timeText.setText(String.valueOf(time));
		this.stepsText.setText(String.valueOf(steps));

		topRecordsInitialize(_numOfRecords);



		// Buttons
		DropShadow shadow = new DropShadow();
		// Adding the shadow when the mouse cursor is on
		submitButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				submitButton.setEffect(shadow);
			}
		});

		// Removing the shadow when the mouse cursor is off
		submitButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				submitButton.setEffect(null);
			}
		});

		submitButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				submitButton.setText("OK!");
				submitButton.setDisable(true);
				submit();
			}
		});
		submitButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> submitButton.requestFocus());

		// Adding the shadow when the mouse cursor is on
		searchButton.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				searchButton.setEffect(shadow);
			}
		});

		// Removing the shadow when the mouse cursor is off
		searchButton.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				searchButton.setEffect(null);
			}
		});

		searchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				search(searchField.getText());
			}
		});
		searchButton.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> searchButton.requestFocus());
		// END Buttons

		//Title
		title.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				title.setEffect(null);
			}
		});

		// Removing the shadow when the mouse cursor is off
		title.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				title.setEffect(shadow);
			}
		});
		//END Title
	}

	public void submit() {
		if(nicknameField.getText()!=null)
		_dbm.addRecord(nicknameField.getText(), _levelId, _time, _steps);
	}

	public void search(String name) {
		List<Records> l = this._dbm.recordByNickname(name, _defaultSort);
		_tableData.clear();
		for (int i = 0; i < l.size(); i++) {
			_tableData.add(l.get(i));
		}
	}

}
