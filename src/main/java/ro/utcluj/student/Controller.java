package ro.utcluj.student;

import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Controller {
    @FXML
    private AnchorPane root;
    @FXML
    private TextField virtualMemorySizeTxt;
    @FXML
    private TextField tlbEntriesTxt;
    @FXML
    private TableView<TLBEntry> tlbTable;
    @FXML
    private TableColumn vpColumn;
    @FXML
    private TableColumn ppColumn;
    @FXML
    private TableColumn ptIndexColumn;
    @FXML
    private TableColumn ptPpColumn;
    @FXML
    private TableView pageTable;
    @FXML
    private TableView memoryTable;
    @FXML
    private TextField physicalPageSizeTxt;
    @FXML
    private TextField physicalMemorySizeTxt;
    @FXML
    private TextArea txtLog;
    @FXML
    private TextField currrentInstr;
    private TranslationLookasideBuffer translationLookasideBuffer;
    private int physicalPageSize;
    private int physicalMemorySize;
    private int virtualMemorySize;
    private int tlbEntries;
    private int instructionLength;
    private int offsetBits;
    private int physicalMemoryAddressBits;
    private int instruction;
    private HashMap<Integer, Integer> pageTableMap;

    @FXML
    public void initialize() {
        txtLog.setWrapText(true);
    }

    private boolean isPowerOfTwo(int number) {

        return number > 0 && ((number & (number - 1)) == 0);

    }

    @FXML
    public void submitInstr() {
        pageTableMap.put(1, 3333);
        pageTable.refresh();
    }

    @FXML
    public void submitInput() {
        physicalPageSize = Integer.valueOf(physicalPageSizeTxt.getText());
        physicalMemorySize = Integer.valueOf(physicalMemorySizeTxt.getText());
        virtualMemorySize = Integer.valueOf(virtualMemorySizeTxt.getText());
        tlbEntries = Integer.valueOf(tlbEntriesTxt.getText());
        if (!isPowerOfTwo(physicalPageSize)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("The physical page size must be a power of two!");

            alert.showAndWait();
        } else if (!isPowerOfTwo(virtualMemorySize)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setHeaderText(null);
            alert.setContentText("The virtual memory size must be a power of two!");

            alert.showAndWait();
        } else {
            instructionLength = (int) (Math.log(virtualMemorySize * Math.pow(2, 20)) / Math.log(2));
            offsetBits = (int) (Math.log(physicalPageSize * Math.pow(2, 10)) / Math.log(2));
            physicalMemoryAddressBits = (int) (Math.log(physicalMemorySize * Math.pow(2, 20)) / Math.log(2));
            txtLog.setText(txtLog.getText() + "->The physical page size is " + physicalPageSize + " KB, so there will be " + offsetBits + " offset bits.\n");
            txtLog.setText(txtLog.getText() + "->The virtual memory size is " + virtualMemorySize + " MB, so the instruction will be " +
                    " in a " + instructionLength + " bits address space while the physical memory will be in an only " + physicalMemoryAddressBits + " bits address" +
                    "space.\n");
            txtLog.setText(txtLog.getText() + "->So the first " + (instructionLength - offsetBits) + " bits of the instruction will be used to find the requested page" +
                    ", while the offset bits will be used to choose the requested word from the specific page.\n");
            loadPageTable();
            loadTLBTable();
        }

    }

    @FXML
    public void generateRandomInstructions() {
        instruction = Utils.generateRandomIntructions(instructionLength / 4);
        currrentInstr.setText(String.format("0x%0" + String.valueOf(instructionLength / 4) + "X", instruction));
    }

    public void loadPageTable() {
        pageTableMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < Math.pow(2, instructionLength - offsetBits); i++) {
            pageTableMap.put(i, -1);
            System.out.println(pageTableMap.get(i));
        }

        ptIndexColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String> param) {
                return new SimpleStringProperty(String.format("0x%0" + String.valueOf((instructionLength-offsetBits) / 4) + "X", param.getValue().getKey()));
            }
        });
        ptPpColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String> param) {
                if (param.getValue().getValue() == -1)
                    return new SimpleStringProperty("-----");
                else
                    return new SimpleStringProperty(String.valueOf(param.getValue().getValue()));
            }
        });
        ObservableList<Map.Entry<Integer, Integer>> items = FXCollections.observableArrayList(pageTableMap.entrySet());
        pageTable.setItems(items);
    }


    public void loadTLBTable() {
        translationLookasideBuffer = new TranslationLookasideBuffer(tlbEntries);
        for (int i=0; i<tlbEntries; i++){
            translationLookasideBuffer.addNewEntry(new TLBEntry(-1,-1));
        }
        ppColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TLBEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TLBEntry, String> param) {
                if (param.getValue().getPhysicalPageNumber()==-1)
                    return new SimpleStringProperty("n/a");
                else
                    return new SimpleStringProperty(String.valueOf(param.getValue().getPhysicalPageNumber()));
            }

        });
        vpColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TLBEntry, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TLBEntry, String> param) {
                if (param.getValue().getPhysicalPageNumber()==-1)
                    return new SimpleStringProperty("n/a");
                else
                    return new SimpleStringProperty(String.valueOf(param.getValue().getVirtualPageNumber()));
            }

        });
        ObservableList<TLBEntry> items = FXCollections.observableArrayList(translationLookasideBuffer.getTlb());
        tlbTable.setItems(items);

    }
}
