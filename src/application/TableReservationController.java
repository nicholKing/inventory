package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.StringConverter;
import javafx.scene.effect.ColorAdjust;

public class TableReservationController implements Initializable {
	
    @FXML
    private AnchorPane slider;
    @FXML
    private ImageView menuClose;
    @FXML
    private ImageView menu;
    @FXML
    private AnchorPane slider2;
    @FXML
    private ImageView accountClose;
    @FXML
    private ImageView account;
    @FXML
    private VBox orderLayout;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private VBox vBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button timeButton;
    @FXML
    private Label tableNumberLabel;
    @FXML
    private ImageView oneSeat;
    @FXML
    private ImageView twoSeat;
    @FXML
    private ImageView fourSeat;
    @FXML
    private ImageView fourSeat2;
    @FXML
    private ImageView fiveSeat;
    @FXML
    private ImageView sixSeat;
    @FXML
    private ImageView eightSeat;
    @FXML
    private ImageView eightSeat2;
    @FXML
    private ImageView tenSeat;
    @FXML
    private Button bookNow;
    @FXML
    private Label tryLabel;
    
   
	private Stage stage;
	private Scene scene;
	private Parent root;
	private LocalDate selectedDate;
    private LocalTime selectedTime;
	
	List<OrderData> orderList = new ArrayList<>();
	OrderData orderData = new OrderData();
	
	int id;
	String role;
	String name;
	String username = "";
	String orderPage = "OrderPage.fxml";
    String homePage = "HomePage.fxml";
    String accPage = "AccountDetails.fxml";
    String cartPage = "MyCart.fxml";
    String tablePage = "TablePage.fxml";
    String rewardsPage = "RewardsPage.fxml";
    String query = "SELECT name FROM user_tbl WHERE id = ?";
    String dbName;
    public int reservedTable;
    
	boolean hasAccount = false;
	boolean isHomeBtn = false;
	boolean isOrderBtn = false;
	boolean isTableBtn = false;
	boolean isAccBtn = false;
	boolean isRewardBtn = false;
	
	boolean clicked = false;
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	private final Popup timePopup = new Popup();
    private final ListView<String> timeListView = new ListView<>();
	
    public void initialize() {
        // Disable dates before today in the DatePicker
        datePicker.setDayCellFactory(getDayCellFactory());
        
        datePicker.setConverter(new StringConverter<LocalDate>() {
            String pattern = "MMM dd, yyyy"; // Customize the format as needed
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        });


        // Setup the time popup
        timePopup.setAutoHide(true);
        timePopup.getContent().add(timeListView);
        timeListView.setOnMouseClicked(event -> {
        	String selectedTimeString = timeListView.getSelectionModel().getSelectedItem();
            if (selectedTimeString != null) {
                timeButton.setText(selectedTimeString);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
                selectedTime = LocalTime.parse(selectedTimeString, formatter);
                timePopup.hide();
            }
        });

        // Set preferred size of the ListView (width and height)
        timeListView.setPrefSize(100, 300);  // Adjust the width and height as needed

        // Populate the time list when the date picker value changes
        datePicker.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
            	selectedDate = newDate;
                populateTimeList(newDate);
            }
        });

        // Initial population with today's date
        datePicker.setValue(LocalDate.now());
        populateTimeList(LocalDate.now());

     // Show the popup when the button is clicked
        timeButton.setOnAction(event -> {
            if (!timePopup.isShowing()) {
                // Calculate popup position relative to the button
                double buttonX = timeButton.localToScreen(timeButton.getBoundsInLocal()).getMinX();
                double buttonY = timeButton.localToScreen(timeButton.getBoundsInLocal()).getMaxY(); // Change this line
                double popupX = buttonX;  
                double popupY = buttonY;  

                timePopup.show(timeButton, popupX, popupY);
            }
        });
       
        tableNumber();
    }
   
    public void tableNumber() {
    	
    	EventHandler<MouseEvent> clearHighlight = event -> {
            oneSeat.setStyle("");
            twoSeat.setStyle("");
            fourSeat.setStyle("");
            fourSeat2.setStyle("");
            fiveSeat.setStyle("");
            sixSeat.setStyle("");
            eightSeat.setStyle("");
            eightSeat2.setStyle("");
            tenSeat.setStyle("");
        };
    	
    	oneSeat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	clearHighlight.handle(event);
            	oneSeat.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0); -fx-border-color: #561C24; -fx-border-width: 2;");
                // Update the label text
                tableNumberLabel.setText("1");
                // Update int reservedTable
                reservedTable = 1;
                if(!clicked) {
                	 slideWindow();
                	 clicked = true;
                }
               
            }
        });

    	twoSeat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	clearHighlight.handle(event);
            	twoSeat.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0); -fx-border-color: #561C24; -fx-border-width: 2;");
                // Update the label text
                tableNumberLabel.setText("2");
                reservedTable = 2;
                if(!clicked) {
               	 slideWindow();
               	 clicked = true;
               }
            }
        });
    	
    	fourSeat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	clearHighlight.handle(event);
            	fourSeat.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0); -fx-border-color: #561C24; -fx-border-width: 2;");
                // Update the label text
                tableNumberLabel.setText("4");
                reservedTable = 40;
                if(!clicked) {
               	 slideWindow();
               	 clicked = true;
               }
            }
        });
    	
    	fourSeat2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	clearHighlight.handle(event);
            	fourSeat2.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0); -fx-border-color: #561C24; -fx-border-width: 2;");
                // Update the label text
                tableNumberLabel.setText("4");
                reservedTable = 41;
                if(!clicked) {
               	 slideWindow();
               	 clicked = true;
               }
            }
        });
    	
    	fiveSeat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	clearHighlight.handle(event);
            	fiveSeat.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0); -fx-border-color: #561C24; -fx-border-width: 2;");
                // Update the label text
                tableNumberLabel.setText("5");
                reservedTable = 5;
                if(!clicked) {
               	 slideWindow();
               	 clicked = true;
               }
            }
        });
    	
    	sixSeat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	clearHighlight.handle(event);
            	sixSeat.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0); -fx-border-color: #561C24; -fx-border-width: 2;");
                // Update the label text
                tableNumberLabel.setText("6");
                reservedTable = 6;
                if(!clicked) {
               	 slideWindow();
               	 clicked = true;
               }
            }
        });
    	
    	eightSeat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	clearHighlight.handle(event);
            	eightSeat.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0); -fx-border-color: #561C24; -fx-border-width: 2;");
                // Update the label text
                tableNumberLabel.setText("8");
                reservedTable = 80;
                if(!clicked) {
               	 slideWindow();
               	 clicked = true;
               }
            }
        });
    	
    	eightSeat2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	clearHighlight.handle(event);
            	eightSeat2.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0); -fx-border-color: #561C24; -fx-border-width: 2;");
                // Update the label text
                tableNumberLabel.setText("8");
                reservedTable = 81;
                if(!clicked) {
               	 slideWindow();
               	 clicked = true;
               }
            }
        });
    	
    	tenSeat.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
            	clearHighlight.handle(event);
            	tenSeat.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0); -fx-border-color: #561C24; -fx-border-width: 2;");
                // Update the label text
                tableNumberLabel.setText("10");
                reservedTable = 10;
                if(!clicked) {
               	 slideWindow();
               	 clicked = true;
               }
            }
        });
    	
    	ColorAdjust grayscale = new ColorAdjust();
        grayscale.setSaturation(-1);
    	
		bookNow.setOnAction(event -> {
				saveSelectedDateTime();
                switch (reservedTable) {
                case 1:
                	oneSeat.setEffect(grayscale);
                	oneSeat.setDisable(true);
                	break;
                case 2:
                	twoSeat.setEffect(grayscale);
                	twoSeat.setDisable(true);
                	break;
                case 40:
                	fourSeat.setEffect(grayscale);
                	fourSeat.setDisable(true);
                	break;
                case 41:
                	fourSeat2.setEffect(grayscale);
                	fourSeat2.setDisable(true);
                	break;
                case 5:
                	fiveSeat.setEffect(grayscale);
                	fiveSeat.setDisable(true);
                	break;	
                case 6:
                	sixSeat.setEffect(grayscale);
                	sixSeat.setDisable(true);
                	break;
                case 80:
                	eightSeat.setEffect(grayscale);
                	eightSeat.setDisable(true);
                	break;
                case 81:
                	eightSeat2.setEffect(grayscale);
                	eightSeat2.setDisable(true);
                	break;
                case 10:
                	tenSeat.setEffect(grayscale);
                	tenSeat.setDisable(true);
                	break;
                }
        });
    }
    
    private void populateTimeList(LocalDate selectedDate) {
        timeListView.getItems().clear();
        List<String> timeItems = generateTimeItems(selectedDate);
        timeListView.getItems().addAll(timeItems);
        timeListView.setStyle("-fx-font-size: 12px;");
    }

    private List<String> generateTimeItems(LocalDate selectedDate) {
        List<String> timeItems = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startDateTime;
        LocalDateTime endDateTime = selectedDate.atTime(LocalTime.of(23, 30));

        if (selectedDate.isEqual(now.toLocalDate())) {
            // If the selected date is today, start from the next available half-hour interval
            int minutes = now.getMinute();
            int adjustment = (minutes < 30) ? 30 - minutes : 60 - minutes;
            startDateTime = now.plusMinutes(adjustment).withSecond(0).withNano(0);
        } else {
            // If the selected date is not today, start from 10:00 AM
            startDateTime = selectedDate.atTime(LocalTime.of(10, 0));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        while (!startDateTime.isAfter(endDateTime)) {
            timeItems.add(startDateTime.format(formatter));
            startDateTime = startDateTime.plusMinutes(30);
        }

        return timeItems;
    }

    private Callback<DatePicker, DateCell> getDayCellFactory() {
        return new Callback<>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Optional: adds a pink background to disabled dates
                        }
                    }
                };
            }
        };
    }
    
    private void saveSelectedDateTime() {
        if (selectedDate != null && selectedTime != null) {
        	LocalDateTime selectedDateTime = LocalDateTime.of(selectedDate, selectedTime);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy hh:mm a");
            tryLabel.setText("Selected Date and Time: " + selectedDateTime.format(formatter));
        } else {
            showAlert("Please select both date and time", AlertType.ERROR);
        }
    }

	
	//TOP BUTTONS
	public void homeBtn(ActionEvent event) throws IOException, SQLException {
			System.out.println("Home");
			isHomeBtn = true;
			changeScene(event, homePage);
	}
	public void orderBtn(ActionEvent event) throws IOException, SQLException {
			
			System.out.println("Order");
			isOrderBtn = true;
			changeScene(event, orderPage);
	}
	public void signIn(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
	    root = loader.load();
		if(hasAccount) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Logout");
			alert.setHeaderText("You're about to logout");
			alert.setContentText("Are you sure you want to logout?");
			if(alert.showAndWait().get() == ButtonType.OK) {
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			    scene = new Scene(root);
			    stage.setScene(scene);
			    stage.show();
			    hasAccount = false;
			}
		}
		else {
	    Scene1Controller loginPage = loader.getController();
	    loginPage.Connect();
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		}
	}
	public void signUp(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
	    root = loader.load();
		if(hasAccount) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Logout");
			alert.setHeaderText("You're about to logout");
			alert.setContentText("Are you sure you want to logout?");
			if(alert.showAndWait().get() == ButtonType.OK) {
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			    scene = new Scene(root);
			    stage.setScene(scene);
			    stage.show();
			    hasAccount = false;
			}
			
		}
		else {
			SignUpController signUpPage = loader.getController();
			signUpPage.Connect();
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	}
		
	//SIDE BUTTONS
	public void showAccount(ActionEvent event) throws IOException, SQLException {
		if(hasAccount) {
			isAccBtn = true;
			changeScene(event, accPage);
		}else {
			showAlert("Login or register to edit your information.", AlertType.INFORMATION);}
	}
	public void showCart(ActionEvent event) throws IOException, SQLException {
		changeScene(event, cartPage);
	}
	public void showTable(ActionEvent event) throws IOException, SQLException {
			isTableBtn = true;
			changeScene(event, tablePage);
	}
	public void showRewards(ActionEvent event) throws IOException, SQLException {
			if(hasAccount) {
				isRewardBtn = true;
				changeScene(event, rewardsPage);
			}
			else {showAlert("Create an account to unlock exciting rewards!", AlertType.INFORMATION);}
	}
	public void logout(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
	    root = loader.load();
		if(hasAccount) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Logout");
			alert.setHeaderText("You're about to logout");
			alert.setContentText("Are you sure you want to logout?");
			if(alert.showAndWait().get() == ButtonType.OK) {
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			    scene = new Scene(root);
			    stage.setScene(scene);
			    stage.show();
			    hasAccount = false;
			}
		}
		else {
		    Scene1Controller loginPage = loader.getController();
		    loginPage.Connect();
		    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		    
		}
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
	public void changeScene(ActionEvent event, String page) throws IOException, SQLException {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
		root = loader.load();

		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		if(isHomeBtn) {
			HomeController homePage = loader.getController();
			homePage.setOrderList(orderList);
			homePage.setUserDetails(role, hasAccount, dbName, reservedTable);
			homePage.displayName();
		}
		else if(isOrderBtn) {
			OrderController orderPage = loader.getController();
			orderPage.setOrders(orderList);
			orderPage.setUserDetails(role, hasAccount, dbName, reservedTable);
			
		}else if(isTableBtn) {
			TableReservationController tablePage = loader.getController();
			tablePage.setUserDetails(role, hasAccount, dbName, reservedTable);
			
			
		}else if(isAccBtn) {
			AccountDetailsController accPage = loader.getController();
			accPage.setOrders(orderList);
			accPage.setUserDetails(role, hasAccount, dbName, reservedTable);
			accPage.displayName();
			
		}else if(isRewardBtn) {
			RewardsController rewardPage = loader.getController();
			rewardPage.setOrderList(orderList);
			rewardPage.setUserDetails(role, hasAccount, dbName, reservedTable);
			rewardPage.displayName();
		}else {
			CartController cartPage = loader.getController();
			cartPage.setOrders(orderList);
			cartPage.setUserDetails(role, hasAccount, dbName, reservedTable);
			cartPage.displayName();
		}
		
		
}
	public void setUserDetails(String role, boolean hasAccount, String dbName, int id) {
	    this.role = role;
	    this.hasAccount = hasAccount;
	    this.dbName = dbName;
	    this.id = id;
	}
	
	private void showAlert(String contentText, AlertType alertType) {
	
		 Alert alert = new Alert(alertType);
	        alert.setTitle("Notice");
	        alert.setHeaderText(null);
	        alert.setContentText(contentText);
	        Scene scenes = alert.getDialogPane().getScene();
	        scenes.getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
	        alert.show();
	  
	        Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	Platform.runLater(() -> {
	                   alert.close();
	                });
	                
	                timer.cancel(); // Cancel the timer after closing the alert
	            }
	        }, 2 * 1000);
	    }
	public void slideWindow() {
		 slider2.setTranslateX(400);
           TranslateTransition slide = new TranslateTransition();
          
           slide.setDuration(Duration.seconds(0.5));
           slide.setNode(slider2);
           
           slide.setToX(0);
           slide.play();
           
           slide.setOnFinished((ActionEvent e)-> {
        	   account.setVisible(false);
               accountClose.setVisible(true);
           });
       
	}
	private void setSlides() {
		slider.setTranslateX(-200);
		menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-200);

            slide.setOnFinished((ActionEvent e)-> {
            	
                menu.setVisible(false);
                menuClose.setVisible(true);
            });
        });

        menuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-200);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
            	
                menu.setVisible(true);
                menuClose.setVisible(false);
            });
        });
        
        
        slider2.setTranslateX(400);
       
		account.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.5));
            slide.setNode(slider2);
            
            slide.setToX(0);
            slide.play();
           
            slider2.setTranslateX(400);
           
            slide.setOnFinished((ActionEvent e)-> {
                account.setVisible(false);
                accountClose.setVisible(true);
            });
        });
		
		accountClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider2);

            slide.setToX(400);
            slide.play();
            
            slider2.setTranslateX(0);
            
     
            slide.setOnFinished((ActionEvent e)-> {
            	account.setVisible(true);
            	accountClose.setVisible(false);
            });
        });
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		setSlides();
	}
	public void setHasAccount(boolean hasAccount) {
		this.hasAccount = hasAccount;
}
	public void setName(String dbName) {
		this.dbName = dbName;
}
	public void setOrderList(List<OrderData> orderList) {
		this.orderList = orderList;
	}
}