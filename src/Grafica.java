import dvdmania.management.*;
import dvdmania.products.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

public class Grafica {
    static class GUI extends JFrame {

        // constructorul interfetei grafice
        GUI() {
            super("Login");
            LoginPanel();
            NewAccountPanel();
            MainPanel();
        }

        // variabilele ce tin cont de privilegiile utilizatorului curent
        // 0 - guest
        // 1 - client
        // 2 - vanzator
        // 3 - admin
        static int priv = 0;

//        DataManip bazaDate = new DataManip();
//        static Utilizator util;

        //Cache variabile
        static ImageIcon imagePathLog = new ImageIcon(new ImageIcon("logo.png").getImage().getScaledInstance(120, 130, Image.SCALE_DEFAULT));
        static JLabel imagine = new JLabel(imagePathLog);
        static Account account = null;
        static Employee employee = null;
        static Client client = null;
        static String categorieCurenta;
        static String magazinCurent;

        //componentele ferestrei de login
        static int[] loginSize = {350, 280};
        static JPanel logMainPanel, logDataPanel, logButtonPanel;
        static JLabel logUserLabel, logPasswordLabel;
        static JTextField logUsernameText;
        static JPasswordField logPasswordText;
        static JButton logSubmitButton, logExitButton, logNewButton, logGuestButton;

        //componentele ferestrei de creare cont
        static JPanel newMainPanel, newDataPanel, newBirthPanel, newButtonPanel;
        static JLabel newUserLabel, newPasswordLabel, newConfirmPasswordLabel, newEmailLabel, newNameLabel, newPrenLabel, newAddressLabel,
                newCityLabel, newBirthDayLabel, newBirthMonthLabel, newBirthYearLabel, newCNPLabel, newTelLabel;
        static JTextField newUsernameText, newEmailText, newNameText, newPrenText, newAddressText, newCityText, newBirthDayText, newBirthMonthText, newBirthYearText, newCNPText, newTelText;
        static JPasswordField newPasswordText, newConfirmPasswordText;
        static JButton newCreateButton, newCancelButton;

        //componentele meniului din fereastra principala
        static JMenu fileMenu, editMenu, viewMenu;
        static JMenuItem newOrder, finishOrder, logout, exit, newCustomer, newEmployee, newProduct, newStore, editCustomer, editEmployee, editProduct, editStore,
                viewAllOrders, viewAccountDetails;
        static JMenuBar mb;

        //componentele ferestrei principale
        static int[] size = {1200, 800};
        static JPanel mainMainPanel, mainButtonsPanel, mainSearchPanel, mainSearchPanelNorth;
        static JButton mainSearchButton, mainFilmeButton, mainJocuriButton, mainAlbumeButton, mainCheckButton;
        static JTextField mainSearchField;
        static DefaultComboBoxModel mainCategoryModel;
        static JComboBox mainCategoryBox, mainStoreBox;
        static JTable mainProdTable;
        static DefaultTableModel mainTableModel;
        static JScrollPane mainScrollPane;
        static ArrayList<String> mainCategoriesList;
        static ArrayList<Stock> mainProduseList;
        static ArrayList<Store> mainStoresList;


        //**Main Windows**

        private void LoginPanel() {
            //Username
            logUserLabel = new JLabel();
            logUserLabel.setText("Username: ");
            logUsernameText = new JTextField();

            //Password
            logPasswordLabel = new JLabel();
            logPasswordLabel.setText("Password: ");
            logPasswordText = new JPasswordField();

            logDataPanel = new JPanel(new GridLayout(2,2,2,2));
            logDataPanel.setSize(400,400);
            logDataPanel.add(logUserLabel);
            logDataPanel.add(logUsernameText);
            logDataPanel.add(logPasswordLabel);
            logDataPanel.add(logPasswordText);

            //Buttons
            logNewButton = new JButton("New account");
            logGuestButton = new JButton("Guest");
            logSubmitButton = new JButton("Submit");
            logExitButton = new JButton("Exit");
            logButtonPanel = new JPanel(new GridLayout(2,2,5,5));
            logButtonPanel.add(logNewButton);
            logButtonPanel.add(logGuestButton);
            logButtonPanel.add(logSubmitButton);
            logButtonPanel.add(logExitButton);

            //Introducerea componentelor in fereastra
            logMainPanel = new JPanel(new BorderLayout());
            logMainPanel.add(imagine, BorderLayout.NORTH);
            logMainPanel.add(logDataPanel, BorderLayout.CENTER);
            logMainPanel.add(logButtonPanel, BorderLayout.SOUTH);

            changePanel(logMainPanel,loginSize[0],loginSize[1]);

            logSubmitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String usrname = logUsernameText.getText();
                    String password = String.valueOf(logPasswordText.getPassword());

                    AccountManager accMan = new AccountManager();
                    account = new Account();
                    account.setUsername(usrname);
                    account.setPassword(password);

                    if (accMan.checkAccountExists(account)) {
                        account = accMan.getAccount(usrname, password);
                        priv = accMan.checkAccountPrivilege(account);

                        if (priv == 1) {
                            ClientManager clientMan = new ClientManager();
                            client = clientMan.getClientById(account.getIdUtil());
                        } else if (priv == 2 || priv == 3) {
                            EmployeeManager empMan = new EmployeeManager();
                            employee = empMan.getEmployeeById(account.getIdUtil());
                        }

                        setVisible(false);
                        dispose();
                        JFrame welcomeDialog = new JFrame();
                        if (client != null) {
                            JOptionPane.showMessageDialog(welcomeDialog, "Welcome, " + client.getNume() + " " + client.getPrenume() + "!", "Client", JOptionPane.INFORMATION_MESSAGE);
                        } else if (employee != null) {
                            JOptionPane.showMessageDialog(welcomeDialog, "Welcome, " + employee.getNume() + " " + employee.getPrenume() + "!", "Angajat", JOptionPane.INFORMATION_MESSAGE);
                        }

                        setVisible(false);
                        dispose();
                        changePanel(mainMainPanel, size[0], size[1]);
                        MenuBar();
                        setTitle("DVDMania");
                    } else {
                        JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "Account not found!", "Error", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            logNewButton.addActionListener((new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    changePanel(newMainPanel, 300 , 330);
                    setTitle("New account");
                }
            }));

            logGuestButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame f = new JFrame();
                    JOptionPane.showMessageDialog(f, "You have entered as a guest. All you will be able to do \nis see the list of products of every store!", "Guest", JOptionPane.INFORMATION_MESSAGE);

                    priv = 0;

                    setVisible(false);
                    dispose();
                    changePanel(mainMainPanel,size[0],size[1]);
                    MenuBar();
                    setTitle("DVDMania");
                }
            });

            logExitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
        }

        private void NewAccountPanel() {
            //Username
            newUserLabel = new JLabel();
            newUserLabel.setText("Username: ");
            newUsernameText = new JTextField();

            //Password
            newPasswordLabel = new JLabel();
            newPasswordLabel.setText("Password: ");
            newPasswordText = new JPasswordField();
            newConfirmPasswordLabel = new JLabel();
            newConfirmPasswordLabel.setText("Confirm password: ");
            newConfirmPasswordText = new JPasswordField();

            //Email
            newEmailLabel = new JLabel();
            newEmailLabel.setText("Email: ");
            newEmailText = new JTextField();

            //First Name
            newNameLabel = new JLabel();
            newNameLabel.setText("First Name: ");
            newNameText = new JTextField();

            //Last Name
            newPrenLabel = new JLabel();
            newPrenLabel.setText("Last Name: ");
            newPrenText = new JTextField();

            //Address
            newAddressLabel = new JLabel();
            newAddressLabel.setText("Address: ");
            newAddressText = new JTextField();

            //City
            newCityLabel = new JLabel();
            newCityLabel.setText("City: ");
            newCityText = new JTextField();

            //CNP
            newCNPLabel = new JLabel();
            newCNPLabel.setText("CNP: ");
            newCNPText = new JTextField();

            //Phone
            newTelLabel = new JLabel();
            newTelLabel.setText("Phone: ");
            newTelText = new JTextField();

            //Birthday
            newBirthYearLabel = new JLabel("Year(yyyy)");
            newBirthMonthLabel = new JLabel("Month(mm)");
            newBirthDayLabel = new JLabel("Day(dd)");
            newBirthYearText = new JTextField();
            newBirthMonthText = new JTextField();
            newBirthDayText = new JTextField();

            //Data panel
            newDataPanel = new JPanel(new GridLayout(9,2,5,5));
            newDataPanel.add(newUserLabel);
            newDataPanel.add(newUsernameText);
            newDataPanel.add(newPasswordLabel);
            newDataPanel.add(newPasswordText);
            newDataPanel.add(newEmailLabel);
            newDataPanel.add(newEmailText);
            newDataPanel.add(newNameLabel);
            newDataPanel.add(newNameText);
            newDataPanel.add(newPrenLabel);
            newDataPanel.add(newPrenText);
            newDataPanel.add(newAddressLabel);
            newDataPanel.add(newAddressText);
            newDataPanel.add(newCityLabel);
            newDataPanel.add(newCityText);
            newDataPanel.add(newCNPLabel);
            newDataPanel.add(newCNPText);
            newDataPanel.add(newTelLabel);
            newDataPanel.add(newTelText);

            //Birth Panel
            newBirthPanel = new JPanel(new GridLayout(2,3,5,5));
            newBirthPanel.add(newBirthYearLabel);
            newBirthPanel.add(newBirthMonthLabel);
            newBirthPanel.add(newBirthDayLabel);
            newBirthPanel.add(newBirthYearText);
            newBirthPanel.add(newBirthMonthText);
            newBirthPanel.add(newBirthDayText);

            //Button Panel
            newButtonPanel = new JPanel(new GridLayout(1,2,5,5));
            newCreateButton = new JButton("Create");
            newCancelButton = new JButton("Cancel");
            newButtonPanel.add(newCreateButton);
            newButtonPanel.add(newCancelButton);

            //Main Panel
            newMainPanel = new JPanel(new BorderLayout());
            newMainPanel.add(newDataPanel, BorderLayout.NORTH);
            newMainPanel.add(newBirthPanel, BorderLayout.CENTER);
            newMainPanel.add(newButtonPanel, BorderLayout.SOUTH);

            newCancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame f = new JFrame();
                    int a = JOptionPane.showConfirmDialog(f, "Are you sure?", "Exiting", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.YES_OPTION) {
                        changePanel(logMainPanel,loginSize[0],loginSize[1]);
                    }
                }
            });

            newCreateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String username = newUsernameText.getText();
                    String password = String.valueOf(newPasswordText.getPassword());
                    String email = newEmailText.getText();
                    String firstName = newNameText.getText();
                    String lastName = newPrenText.getText();
                    String address = newAddressText.getText();
                    String city = newCityText.getText();
                    String cnp = newCNPText.getText();
                    String phone = newTelText.getText();

                    String year = newBirthYearText.getText();
                    String month = newBirthMonthText.getText();
                    String day = newBirthDayText.getText();

                    boolean isValid = username.equals("") || password.equals("") || email.equals("") || firstName.equals("") || lastName.equals("") || address.equals("") || city.equals("")
                            || cnp.equals("") || phone.equals("") || year.equals("") || month.equals("") || day.equals("");

                    if (isValid) {
                        JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "You must complete all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String birthdate = new String(year + "-" + month + "-" + day);
                        LocalDate date = LocalDate.parse(birthdate);

                        client = new Client(0, lastName, firstName, address, city, date, cnp, phone, email, 5);
                        priv = 1;

                        ClientManager clientMan = new ClientManager();
                        AccountManager accMan = new AccountManager();

                        int clientInserted = clientMan.createClient(client);
                        account = new Account(0, username, password, null, 1, client.getId());
                        int accountInserted = accMan.createClientAccount(account);

                        if (clientInserted != 0 && accountInserted != 0) {
                            JFrame confirmDialog = new JFrame();
                            JOptionPane.showMessageDialog(confirmDialog, "Your account has successfully been created!");

                            changePanel(mainMainPanel, size[0], size[1]);
                            MenuBar();
                            setTitle("DVDMania");
                        } else {
                            JFrame warningDialog = new JFrame();
                            JOptionPane.showMessageDialog(warningDialog, "There was a problem in creating your profile.", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            });

        }

        private void MenuBar() {
            //Menu Bar
            mb = new JMenuBar();
            fileMenu = new JMenu("File");
            editMenu = new JMenu("Edit");
            viewMenu = new JMenu("View");

            //File menu items
            newOrder = new JMenuItem("New order");
            finishOrder = new JMenuItem("Return order");
            logout = new JMenuItem("Logout");
            exit = new JMenuItem("Exit");

            //Edit menu items
            newCustomer = new JMenuItem("New customer");
            editCustomer = new JMenuItem("Edit customer");
            newEmployee = new JMenuItem("New employee");
            editEmployee = new JMenuItem("Edit employee");
            newProduct = new JMenuItem("New product");
            editProduct = new JMenuItem("Edit product");
            newStore = new JMenuItem("New store");
            editStore = new JMenuItem("Edit store");

            //View menu items
            viewAllOrders = new JMenuItem("View all orders");
            viewAccountDetails = new JMenuItem("View account details");

            //Assign menus
            fileMenu.add(newOrder);
            fileMenu.add(finishOrder);
            fileMenu.addSeparator();
            fileMenu.add(logout);
            fileMenu.add(exit);
            editMenu.add(newProduct);
            editMenu.add(editProduct);
            editMenu.addSeparator();
            editMenu.add(newCustomer);
            editMenu.add(editCustomer);
            editMenu.addSeparator();
            editMenu.add(newEmployee);
            editMenu.add(editEmployee);
            editMenu.addSeparator();
            editMenu.add(newStore);
            editMenu.add(editStore);
            viewMenu.add(viewAllOrders);
            viewMenu.add(viewAccountDetails);

            //Assign menu bar
            mb.add(fileMenu);
            mb.add(editMenu);
            mb.add(viewMenu);

            setJMenuBar(mb);

            logout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    dispose();
                    JFrame f = new JFrame();
                    changePanel(logMainPanel,loginSize[0],loginSize[1]);
                    setJMenuBar(null);
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            newProduct.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv == 0 || priv == 1) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        newProductWindow();
                    }
                }
            });

            editProduct.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv == 0 || priv == 1) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        editProductWindow();
                    }
                }
            });

            newCustomer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv == 0 || priv == 1) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        newCustomerWindow();
                    }
                }
            });

            editCustomer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv == 0 || priv == 1) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        editCustomerWindow();
                    }
                }
            });

            newEmployee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv != 3) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        newEmployeeWindow();
                    }
                }
            });

            editEmployee.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv != 3) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        editEmployeeWindow();
                    }
                }
            });

            newStore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv != 3) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        newStoreWindow();
                    }
                }
            });

            editStore.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv != 3) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        editStoreWindow();
                    }
                }
            });

            newOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv == 0 || priv == 1) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        newOrderWindow();
                    }
                }
            });

            finishOrder.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv == 0 || priv == 1) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        finishOrderWindow();
                    }
                }
            });

            viewAllOrders.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv == 0) {
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        viewAllOrdersWindow();
                    }
                }
            });

            viewAccountDetails.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    viewAccountWindow();
                }
            });

        }

        private void MainPanel() {
            //Button panel
            mainFilmeButton = new JButton("Filme");
            mainJocuriButton = new JButton("Jocuri");
            mainAlbumeButton = new JButton("Muzica");

            MovieManager movieMan = new MovieManager();
            StoreManager storeMan = new StoreManager();
            SongManager songMan = new SongManager();
            StockManager stockMan = new StockManager();

            mainCategoriesList = movieMan.getGenres();
            mainStoresList = storeMan.getStores();
            mainCategoryModel = new DefaultComboBoxModel(mainCategoriesList.toArray());
            mainCategoryBox = new JComboBox(mainCategoryModel);
            mainStoreBox = new JComboBox(storeListToBox(mainStoresList));
            mainFilmeButton.setBorder(BorderFactory.createTitledBorder("Categorii:"));
            mainCategoryBox.setBorder(BorderFactory.createTitledBorder("Gen:"));
            mainStoreBox.setBorder(BorderFactory.createTitledBorder("Magazine:"));

            mainButtonsPanel = new JPanel();
            mainButtonsPanel.setLayout(new GridLayout(5, 1));
            mainButtonsPanel.setPreferredSize(new Dimension(120, 600));
            mainButtonsPanel.add(mainFilmeButton);
            mainButtonsPanel.add(mainJocuriButton);
            mainButtonsPanel.add(mainAlbumeButton);
            mainButtonsPanel.add(mainCategoryBox);
            mainButtonsPanel.add(mainStoreBox);

            //Search and table panel
            mainSearchField = new JTextField("Cautare");
            categorieCurenta = "Filme";
            magazinCurent = "Toate";
            mainProduseList = stockMan.getAllMovieStock();
            mainProdTable = initializeTable(mainProduseList);
            mainScrollPane = new JScrollPane(mainProdTable);
            mainSearchButton = new JButton("Cauta");
            mainCheckButton = new JButton("Verifica valabilitatea");
            mainCategoriesList = movieMan.getGenres();

            mainSearchPanel = new JPanel();
            mainSearchPanel.setLayout(new BorderLayout());
            mainSearchPanelNorth = new JPanel();
            mainSearchPanelNorth.setLayout(new GridLayout(1,2));
            mainSearchPanelNorth.add(mainSearchField);
            mainSearchPanelNorth.add(mainSearchButton);
            mainSearchPanel.add(mainSearchPanelNorth, BorderLayout.NORTH);
            mainSearchPanel.add(mainScrollPane, BorderLayout.CENTER);
            mainSearchPanel.add(mainCheckButton, BorderLayout.SOUTH);

            //Main Panel
            mainMainPanel = new JPanel();
            mainMainPanel.setLayout(new BorderLayout());
            mainMainPanel.add(mainSearchPanel, BorderLayout.CENTER);
            mainMainPanel.add(mainButtonsPanel, BorderLayout.WEST);

            mainProdTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JTable table = (JTable) e.getSource();
                    Point point = e.getPoint();
                    int row = table.rowAtPoint(point);
                    if(e.getClickCount() == 2 && table.getSelectedRow() != -1 && categorieCurenta.equals("Albume")) {
                        int id = Integer.parseInt(String.valueOf(mainProdTable.getValueAt(mainProdTable.getSelectedRow(), 0)));
                        String titlu = new String(mainProdTable.getValueAt(mainProdTable.getSelectedRow(), 2) + " - " + mainProdTable.getValueAt(mainProdTable.getSelectedRow(), 1));
                        ArrayList<Song> songs = songMan.getSongs(id);
                        JFrame songsWindow = new JFrame();

                        DefaultTableModel songModel = new DefaultTableModel() {
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };

                        String[] columns = {"Titlu", "Durata"};
                        songModel.setColumnIdentifiers(columns);

                        for(int i = 0; i < songs.size(); i++) {
                            String[] song = {songs.get(i).getNume(), "" + songs.get(i).getDuration()};
                            songModel.addRow(song);
                        }

                        JTable list = new JTable();
                        list.setModel(songModel);
                        JScrollPane scroll = new JScrollPane(list);

                        songsWindow.add(scroll);
                        songsWindow.setSize(500,500);
                        songsWindow.setVisible(true);
                        songsWindow.setTitle(titlu);

                    }
                }
            });

            //ComboBox action listener
            mainCategoryBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String selectedGen = mainCategoryBox.getSelectedItem().toString();
                        mainProdTable.setModel(updateList(selectedGen, mainProduseList, categorieCurenta));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            mainStoreBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        StoreManager storeMan = new StoreManager();
                        StockManager stockMan = new StockManager();

                        String selectedStore = mainStoreBox.getSelectedItem().toString();
                        switch (categorieCurenta) {
                            case "Filme":
                                if (!selectedStore.equals("Toate")) {
                                    Store store = storeMan.getStoreByCity(selectedStore);
                                    mainProduseList = stockMan.getAllMovieStock(store);
                                } else {
                                    mainProduseList = stockMan.getAllMovieStock();
                                }
                                break;
                            case "Jocuri":
                                if (!selectedStore.equals("Toate")) {
                                    Store store = storeMan.getStoreByCity(selectedStore);
                                    mainProduseList = stockMan.getAllGameStock(store);
                                } else {
                                    mainProduseList = stockMan.getAllGameStock();
                                }
                                break;
                            case "Albume":
                                if (!selectedStore.equals("Toate")) {
                                    Store store = storeMan.getStoreByCity(selectedStore);
                                    mainProduseList = stockMan.getAllAlbumStock(store);
                                } else {
                                    mainProduseList = stockMan.getAllAlbumStock();
                                }
                        }
                        mainProdTable.setModel(updateList(mainCategoryBox.getSelectedItem().toString(), mainProduseList, categorieCurenta));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            mainFilmeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    categorieCurenta = "Filme";
                    updateComboBox("Filme");
                    mainCategoryBox.setSelectedItem("Toate");
                    mainStoreBox.setSelectedItem("Toate");
                    StockManager stockMan = new StockManager();
                    mainProduseList = stockMan.getAllMovieStock();
                    mainProdTable.setModel(updateList("Toate", mainProduseList, categorieCurenta));
                }
            });

            mainJocuriButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    categorieCurenta = "Jocuri";
                    updateComboBox("Jocuri");
                    mainCategoryBox.setSelectedItem("Toate");
                    mainStoreBox.setSelectedItem("Toate");
                    StockManager stockMan = new StockManager();
                    mainProduseList = stockMan.getAllGameStock();
                    mainProdTable.setModel(updateList("Toate", mainProduseList, categorieCurenta));
                }
            });

            mainAlbumeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    categorieCurenta = "Albume";
                    updateComboBox("Muzica");
                    mainCategoryBox.setSelectedItem("Toate");
                    mainStoreBox.setSelectedItem("Toate");
                    StockManager stockMan = new StockManager();
                    mainProduseList = stockMan.getAllAlbumStock();
                    mainProdTable.setModel(updateList("Toate", mainProduseList, categorieCurenta));
                }
            });

            mainCheckButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(priv != 0) {
                        if(mainProdTable.getSelectionModel().isSelectionEmpty()) {
                            JFrame warningDialog = new JFrame();
                            JOptionPane.showMessageDialog(warningDialog, "Alegeti mai intai un produs din lista!", "Eroare", JOptionPane.WARNING_MESSAGE);
                        } else {
                            int id = Integer.parseInt(String.valueOf(mainProdTable.getValueAt(mainProdTable.getSelectedRow(), 0)));
                            int nrTot = 0;
                            StockManager stockMan = new StockManager();
                            StoreManager storeMan = new StoreManager();
                            Store store;
                            switch (categorieCurenta) {
                                case "Filme":
                                    MovieManager movieMan = new MovieManager();

                                    Movie movie = movieMan.getMovieById(id);
                                    store = storeMan.getStoreByCity(client.getOras());
                                    nrTot = stockMan.checkMovieStock(movie, store);
                                    break;
                                case "Jocuri":
                                    GameManager gameMan = new GameManager();

                                    Game game = gameMan.getGameById(id);
                                    store = storeMan.getStoreByCity(client.getOras());
                                    nrTot = stockMan.checkGameStock(game, store);
                                    break;
                                case "Albume":
                                    AlbumManager albumMan = new AlbumManager();

                                    Album album = albumMan.getAlbumById(id);
                                    store = storeMan.getStoreByCity(client.getOras());
                                    nrTot = stockMan.checkAlbumStock(album, store);
                            }

                            if (nrTot > 0) {
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Vesti bune! \n Mai sunt " + nrTot + " copii ale acestui produs in magazin!");
                            } else if (nrTot == 0) {
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Ne pare rau, dar acest produs nu exista in magazinul orasului dumneavoastra!");
                            } else {
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Ne pare rau, dar in acest moment nu mai exista nici o copie disponibila in magazin!");
                            }
                        }
                    } else {
                        JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "Este nevoie sa va logati pentru a accesa aceasta functie.", "Eroare", JOptionPane.WARNING_MESSAGE);
                    }
                }
            });

            mainSearchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    mainCategoryBox.setSelectedItem("Toate");
                    mainStoreBox.setSelectedItem("Toate");
                    ArrayList<Stock> newProduseList = new ArrayList<>();

                    if(!mainSearchField.equals("Cautare")) {
                        String toSearch = mainSearchField.getText();
                        Stock stock = null;
                        switch (categorieCurenta) {
                            case "Filme":
                                for (int i = 0; i < mainProduseList.size(); i++) {
                                    stock = mainProduseList.get(i);
                                    Movie movie = stock.getMovie();

                                    String title = movie.getTitle();
                                    String actor = movie.getMainActor();
                                    String director = movie.getDirector();

                                    if (title.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(stock);
                                    } else if (actor.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(stock);
                                    } else if (director.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(stock);
                                    }
                                }
                                break;
                            case "Jocuri":
                                for (int i = 0; i < mainProduseList.size(); i++) {
                                    stock = mainProduseList.get(i);
                                    Game game = stock.getGame();

                                    String title = game.getTitle();
                                    String platform = game.getPlatform();
                                    String developer = game.getDeveloper();
                                    String publisher = game.getPublisher();

                                    if (title.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(stock);
                                    } else if (platform.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(stock);
                                    } else if (developer.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(stock);
                                    } else if (publisher.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(stock);
                                    }
                                }
                                break;
                            case "Albume":
                                for (int i = 0; i < mainProduseList.size(); i++) {
                                    stock = mainProduseList.get(i);
                                    Album album = stock.getAlbum();

                                    String title = album.getTitle();
                                    String artist = album.getArtist();
                                    String producer = album.getProducer();

                                    if (title.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(stock);
                                    } else if (artist.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(stock);
                                    } else if (producer.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(stock);
                                    }
                                }
                        }

                        if (newProduseList.isEmpty()) {
                            JFrame warningDialog = new JFrame();
                            JOptionPane.showMessageDialog(warningDialog, "Nu am putut gasi nimic!", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            mainCategoryBox.setSelectedItem("Toate");
                            mainStoreBox.setSelectedItem("Toate");
                            mainProdTable.setModel(updateList("Toate", newProduseList, categorieCurenta));
                        }
                    }
                }
            });
        }

        //**Menu Windows**

        private void newProductWindow() {
            JFrame newProdWindow = new JFrame();
            newProdWindow.setTitle("Produs nou");

            //creare butoane
            JButton newFilm = new JButton("Film");
            JButton newJoc = new JButton("Joc");
            JButton newAlbum = new JButton("Album");
            JButton newSong = new JButton("Melodie");
            JButton exit = new JButton("Exit");

            //creare fereastra principala
            JPanel newProductButtons = new JPanel(new GridLayout(2,2,2,2));
            newProductButtons.add(newFilm);
            newProductButtons.add(newJoc);
            newProductButtons.add(newAlbum);
            newProductButtons.add(newSong);

            JPanel newProductMain = new JPanel(new BorderLayout());
            newProductMain.add(newProductButtons, BorderLayout.CENTER);
            newProductMain.add(exit, BorderLayout.SOUTH);

            newProdWindow.setVisible(true);
            newProdWindow.add(newProductMain);
            newProdWindow.setSize(300,300);
            newProdWindow.setLocationRelativeTo(null);

            //creare fereastra filme
            newFilm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField newFilmTitluField, newFilmActorField, newFilmDirectorField, newFilmDurataField, newFilmGenField, newFilmAnField, newFilmAudientaField, newFilmCantField, newFilmPretField;
                    JLabel newFilmTitluLabel, newFilmActorLabel, newFilmDirectorLabel, newFilmDurataLabel, newFilmGenLabel, newFilmAnLabel, newFilmAudientaLabel, newFilmCantLabel, newFilmPretLabel;
                    JButton save, exit;

                    newFilmTitluLabel = new JLabel("Titlu:");
                    newFilmTitluField = new JTextField();
                    newFilmActorLabel = new JLabel("Actor principal:");
                    newFilmActorField = new JTextField();
                    newFilmDirectorLabel = new JLabel("Director:");
                    newFilmDirectorField = new JTextField();
                    newFilmDurataLabel = new JLabel("Durata:");
                    newFilmDurataField = new JTextField();
                    newFilmGenField = new JTextField();
                    newFilmGenLabel = new JLabel("Gen:");
                    newFilmAnLabel = new JLabel("An:");
                    newFilmAnField = new JTextField();
                    newFilmAudientaLabel = new JLabel("Audienta:");
                    newFilmAudientaField = new JTextField();
                    newFilmCantLabel = new JLabel("Cantitate:");
                    newFilmCantField = new JTextField();
                    newFilmPretLabel = new JLabel("Pret:");
                    newFilmPretField = new JTextField();
                    save = new JButton("Save");
                    exit = new JButton("Exit");

                    JFrame newFilmWindow = new JFrame();
                    JPanel detaliiPanel = new JPanel(new GridLayout(9,9,5,5));
                    JPanel butoanePanel = new JPanel(new GridLayout(1,2,5,5));
                    JPanel newFilmMain = new JPanel(new BorderLayout());
                    detaliiPanel.add(newFilmTitluLabel);
                    detaliiPanel.add(newFilmTitluField);
                    detaliiPanel.add(newFilmActorLabel);
                    detaliiPanel.add(newFilmActorField);
                    detaliiPanel.add(newFilmDirectorLabel);
                    detaliiPanel.add(newFilmDirectorField);
                    detaliiPanel.add(newFilmDurataLabel);
                    detaliiPanel.add(newFilmDurataField);
                    detaliiPanel.add(newFilmGenLabel);
                    detaliiPanel.add(newFilmGenField);
                    detaliiPanel.add(newFilmAnLabel);
                    detaliiPanel.add(newFilmAnField);
                    detaliiPanel.add(newFilmAudientaLabel);
                    detaliiPanel.add(newFilmAudientaField);
                    detaliiPanel.add(newFilmCantLabel);
                    detaliiPanel.add(newFilmCantField);
                    detaliiPanel.add(newFilmPretLabel);
                    detaliiPanel.add(newFilmPretField);
                    butoanePanel.add(save);
                    butoanePanel.add(exit);
                    newFilmMain.add(detaliiPanel, BorderLayout.CENTER);
                    newFilmMain.add(butoanePanel, BorderLayout.SOUTH);

                    newFilmWindow.add(newFilmMain);
                    newFilmWindow.setSize(300,330);
                    newFilmWindow.setVisible(true);
                    newFilmWindow.setLocationRelativeTo(null);

                    save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String titlu, actor, director, durata, gen, an, audienta, cant, pret;
                            titlu = newFilmTitluField.getText();
                            actor = newFilmActorField.getText();
                            director = newFilmDirectorField.getText();
                            durata = newFilmDurataField.getText();
                            gen = newFilmGenField.getText();
                            an = newFilmAnField.getText();
                            audienta = newFilmAudientaField.getText();
                            cant = newFilmCantField.getText();
                            pret = newFilmPretField.getText();

                            if(titlu.isEmpty() || actor.isEmpty() || director.isEmpty() || durata.isEmpty() || gen.isEmpty() || an.isEmpty() || audienta.isEmpty() || cant.isEmpty() || pret.isEmpty()) {
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                            } else {
                                MovieManager movieMan = new MovieManager();
                                StockManager stockMan = new StockManager();
                                StoreManager storeMan = new StoreManager();

                                Movie movie = new Movie(0, titlu, actor, director, Integer.parseInt(durata), gen, an, Integer.parseInt(audienta));
                                Store store = storeMan.getStoreByCity(employee.getOras());

                                movieMan.createMovie(movie);
                                stockMan.insertMovieStock(movie, store, Integer.parseInt(cant), Integer.parseInt(pret));
                            }
                        }
                    });

                    exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            newFilmWindow.setVisible(false);
                            newFilmWindow.dispose();
                        }
                    });
                }
            });

            newJoc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField newJocTitluField, newJocPlatformaField, newJocDeveloperField, newJocPublisherField, newJocGenField, newJocAnField, newJocAudientaField, newJocCantField, newJocPretField;
                    JLabel newJocTitluLabel, newJocPlatformaLabel, newJocDeveloperLabel, newJocPublisherLabel, newJocGenLabel, newJocAnLabel, newJocAudientaLabel, newJocCantLabel, newJocPretLabel;
                    JButton save, exit;

                    newJocTitluLabel = new JLabel("Titlu:");
                    newJocTitluField = new JTextField();
                    newJocPlatformaLabel = new JLabel("Platforma:");
                    newJocPlatformaField = new JTextField();
                    newJocDeveloperLabel = new JLabel("Developer:");
                    newJocDeveloperField = new JTextField();
                    newJocPublisherLabel = new JLabel("Publisher:");
                    newJocPublisherField = new JTextField();
                    newJocGenField = new JTextField();
                    newJocGenLabel = new JLabel("Gen:");
                    newJocAnLabel = new JLabel("An:");
                    newJocAnField = new JTextField();
                    newJocAudientaLabel = new JLabel("Audienta:");
                    newJocAudientaField = new JTextField();
                    newJocCantLabel = new JLabel("Cantitate:");
                    newJocCantField = new JTextField();
                    newJocPretLabel = new JLabel("Pret:");
                    newJocPretField = new JTextField();
                    save = new JButton("Save");
                    exit = new JButton("Exit");

                    JFrame newJocWindow = new JFrame();
                    JPanel detaliiPanel = new JPanel(new GridLayout(9,9,5,5));
                    JPanel butoanePanel = new JPanel(new GridLayout(1,2,5,5));
                    JPanel newJocMain = new JPanel(new BorderLayout());
                    detaliiPanel.add(newJocTitluLabel);
                    detaliiPanel.add(newJocTitluField);
                    detaliiPanel.add(newJocPlatformaLabel);
                    detaliiPanel.add(newJocPlatformaField);
                    detaliiPanel.add(newJocDeveloperLabel);
                    detaliiPanel.add(newJocDeveloperField);
                    detaliiPanel.add(newJocPublisherLabel);
                    detaliiPanel.add(newJocPublisherField);
                    detaliiPanel.add(newJocGenLabel);
                    detaliiPanel.add(newJocGenField);
                    detaliiPanel.add(newJocAnLabel);
                    detaliiPanel.add(newJocAnField);
                    detaliiPanel.add(newJocAudientaLabel);
                    detaliiPanel.add(newJocAudientaField);
                    detaliiPanel.add(newJocCantLabel);
                    detaliiPanel.add(newJocCantField);
                    detaliiPanel.add(newJocPretLabel);
                    detaliiPanel.add(newJocPretField);
                    butoanePanel.add(save);
                    butoanePanel.add(exit);
                    newJocMain.add(detaliiPanel, BorderLayout.CENTER);
                    newJocMain.add(butoanePanel, BorderLayout.SOUTH);

                    newJocWindow.add(newJocMain);
                    newJocWindow.setSize(300,330);
                    newJocWindow.setVisible(true);
                    newJocWindow.setLocationRelativeTo(null);

                    save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String titlu, platforma, developer, publisher, gen, an, audienta, cant, pret;
                            titlu = newJocTitluField.getText();
                            platforma = newJocPlatformaField.getText();
                            developer = newJocDeveloperField.getText();
                            publisher = newJocPublisherField.getText();
                            gen = newJocGenField.getText();
                            an = newJocAnField.getText();
                            audienta = newJocAudientaField.getText();
                            cant = newJocCantField.getText();
                            pret = newJocPretField.getText();

                            if(titlu.isEmpty() || platforma.isEmpty() || developer.isEmpty() || publisher.isEmpty() || gen.isEmpty() || an.isEmpty() || audienta.isEmpty() || cant.isEmpty() || pret.isEmpty()) {
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                            } else {
                                GameManager gameMan = new GameManager();
                                StockManager stockMan = new StockManager();
                                StoreManager storeMan = new StoreManager();

                                Game game = new Game(0, titlu, platforma, developer, publisher, gen, an, Integer.parseInt(audienta));
                                Store store = storeMan.getStoreByCity(employee.getOras());

                                gameMan.createGame(game);
                                stockMan.insertGameStock(game, store, Integer.parseInt(cant), Integer.parseInt(pret));
                            }
                        }
                    });

                    exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            newJocWindow.setVisible(false);
                            newJocWindow.dispose();
                        }
                    });
                }
            });

            newAlbum.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField newAlbumTitluField, newAlbumTrupaField, newAlbumMelodiiField, newAlbumCasaDiscuriField, newAlbumGenField, newAlbumAnField, newAlbumCantField, newAlbumPretField;
                    JLabel newAlbumTitluLabel, newAlbumTrupaLabel, newAlbumMelodiiLabel, newAlbumCasaDiscuriLabel, newAlbumGenLabel, newAlbumAnLabel, newAlbumCantLabel, newAlbumPretLabel;
                    JButton save, exit;

                    newAlbumTitluLabel = new JLabel("Titlu:");
                    newAlbumTitluField = new JTextField();
                    newAlbumTrupaLabel = new JLabel("Trupa:");
                    newAlbumTrupaField = new JTextField();
                    newAlbumMelodiiLabel = new JLabel("Numar melodii:");
                    newAlbumMelodiiField = new JTextField();
                    newAlbumCasaDiscuriLabel = new JLabel("Casa discuri:");
                    newAlbumCasaDiscuriField = new JTextField();
                    newAlbumGenField = new JTextField();
                    newAlbumGenLabel = new JLabel("Gen:");
                    newAlbumAnLabel = new JLabel("An:");
                    newAlbumAnField = new JTextField();
                    newAlbumCantLabel = new JLabel("Cantitate:");
                    newAlbumCantField = new JTextField();
                    newAlbumPretLabel = new JLabel("Pret:");
                    newAlbumPretField = new JTextField();
                    save = new JButton("Save");
                    exit = new JButton("Exit");

                    JFrame newAlbumWindow = new JFrame();
                    JPanel detaliiPanel = new JPanel(new GridLayout(8,8,5,5));
                    JPanel butoanePanel = new JPanel(new GridLayout(1,2,5,5));
                    JPanel newAlbumMain = new JPanel(new BorderLayout());
                    detaliiPanel.add(newAlbumTitluLabel);
                    detaliiPanel.add(newAlbumTitluField);
                    detaliiPanel.add(newAlbumTrupaLabel);
                    detaliiPanel.add(newAlbumTrupaField);
                    detaliiPanel.add(newAlbumMelodiiLabel);
                    detaliiPanel.add(newAlbumMelodiiField);
                    detaliiPanel.add(newAlbumCasaDiscuriLabel);
                    detaliiPanel.add(newAlbumCasaDiscuriField);
                    detaliiPanel.add(newAlbumGenLabel);
                    detaliiPanel.add(newAlbumGenField);
                    detaliiPanel.add(newAlbumAnLabel);
                    detaliiPanel.add(newAlbumAnField);
                    detaliiPanel.add(newAlbumCantLabel);
                    detaliiPanel.add(newAlbumCantField);
                    detaliiPanel.add(newAlbumPretLabel);
                    detaliiPanel.add(newAlbumPretField);
                    butoanePanel.add(save);
                    butoanePanel.add(exit);
                    newAlbumMain.add(detaliiPanel, BorderLayout.CENTER);
                    newAlbumMain.add(butoanePanel, BorderLayout.SOUTH);

                    newAlbumWindow.add(newAlbumMain);
                    newAlbumWindow.setSize(300,330);
                    newAlbumWindow.setVisible(true);
                    newAlbumWindow.setLocationRelativeTo(null);

                    save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String titlu, trupa, melodii, casaDisc, gen, an, cant, pret;
                            titlu = newAlbumTitluField.getText();
                            trupa = newAlbumTrupaField.getText();
                            melodii = newAlbumMelodiiField.getText();
                            casaDisc = newAlbumCasaDiscuriField.getText();
                            gen = newAlbumGenField.getText();
                            an = newAlbumAnField.getText();
                            cant = newAlbumCantField.getText();
                            pret = newAlbumPretField.getText();

                            if(titlu.isEmpty() || trupa.isEmpty() || melodii.isEmpty() || casaDisc.isEmpty() || gen.isEmpty() || an.isEmpty() || cant.isEmpty() || pret.isEmpty()) {
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                            } else {
                                AlbumManager albumMan = new AlbumManager();
                                StockManager stockMan = new StockManager();
                                StoreManager storeMan = new StoreManager();

                                Album album = new Album(0, titlu, trupa, Integer.parseInt(melodii), casaDisc, gen, an);
                                Store store = storeMan.getStoreByCity(employee.getOras());

                                albumMan.createAlbum(album);
                                stockMan.insertAlbumStock(album, store, Integer.parseInt(cant), Integer.parseInt(pret));
                            }
                        }
                    });

                    exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            newAlbumWindow.setVisible(false);
                            newAlbumWindow.dispose();
                        }
                    });
                }
            });

            newSong.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTextField newSongNumeField, newSongDurataField;
                    JLabel newSongNumeLabel, newSongDurataLabel, newSongAlbumLabel;
                    JComboBox newSongAlbumBox;
                    JButton save, exit;

                    newSongNumeLabel = new JLabel("Nume:");
                    newSongNumeField = new JTextField();
                    newSongDurataLabel = new JLabel("Durata:");
                    newSongDurataField = new JTextField();
                    newSongAlbumLabel = new JLabel("Album");
                    AlbumManager albumMan = new AlbumManager();
                    ArrayList<Album> albume = albumMan.getAllAlbums();
                    ArrayList<String> numeAlbume = new ArrayList<>();
                    Iterator iterator = albume.iterator();
                    while (iterator.hasNext()) {
                        Album album = (Album) iterator.next();
                        numeAlbume.add(album.getTitle());
                    }
                    newSongAlbumBox = new JComboBox(albume.toArray());
                    save = new JButton("Save");
                    exit = new JButton("Exit");

                    JFrame newSongWindow = new JFrame();
                    JPanel detaliiPanel = new JPanel(new GridLayout(3, 3, 5, 5));
                    JPanel butoanePanel = new JPanel(new GridLayout(1, 2, 5, 5));
                    JPanel newSongMain = new JPanel(new BorderLayout());
                    detaliiPanel.add(newSongAlbumLabel);
                    detaliiPanel.add(newSongAlbumBox);
                    detaliiPanel.add(newSongNumeLabel);
                    detaliiPanel.add(newSongNumeField);
                    detaliiPanel.add(newSongDurataLabel);
                    detaliiPanel.add(newSongDurataField);
                    butoanePanel.add(save);
                    butoanePanel.add(exit);
                    newSongMain.add(detaliiPanel, BorderLayout.CENTER);
                    newSongMain.add(butoanePanel, BorderLayout.SOUTH);

                    newSongWindow.add(newSongMain);
                    newSongWindow.setSize(300,330);
                    newSongWindow.setVisible(true);
                    newSongWindow.setLocationRelativeTo(null);

                    save.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String albumName, nume, durata;
                            albumName = String.valueOf(newSongAlbumBox.getSelectedItem());
                            nume = newSongNumeField.getText();
                            durata = newSongDurataField.getText();

                            if (nume.isEmpty() || durata.isEmpty()) {
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                            } else {
                                SongManager songMan = new SongManager();
                                Iterator iterator = albume.iterator();
                                while (iterator.hasNext()) {
                                    Album album = (Album) iterator.next();
                                    if (album.getTitle().equals(albumName)) {
                                        Song song = new Song(0, nume, Integer.parseInt(durata));
                                        songMan.CreateSong(album, song);
                                        break;
                                    }
                                }
                            }
                        }
                    });

                    exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            newSongWindow.setVisible(false);
                            newSongWindow.dispose();
                        }
                    });
                }
            });
        }

        private void editProductWindow() {
            //first window
            JLabel editCategorieLabel, editIDLabel;
            JComboBox editCategorieBox;
            JTextField editIDField;
            JButton exit, proceed, saveSecond, exitSecond;
            JPanel firstPanel, detaliiPanel, buttonPanel;

            editCategorieLabel = new JLabel("Categorie:");
            editIDLabel = new JLabel("ID produs:");
            String[] items = new String[] {"Filme", "Jocuri", "Albume"};
            editCategorieBox = new JComboBox(items);
            editIDField = new JTextField();
            exit = new JButton("Exit");
            proceed = new JButton("Proceed");

            detaliiPanel = new JPanel(new GridLayout(2,2,5,5));
            detaliiPanel.add(editCategorieLabel);
            detaliiPanel.add(editCategorieBox);
            detaliiPanel.add(editIDLabel);
            detaliiPanel.add(editIDField);

            buttonPanel = new JPanel(new GridLayout(1,2,5,5));
            buttonPanel.add(proceed);
            buttonPanel.add(exit);

            firstPanel = new JPanel(new BorderLayout());
            firstPanel.add(detaliiPanel, BorderLayout.CENTER);
            firstPanel.add(buttonPanel, BorderLayout.SOUTH);

            JFrame firstWindow = new JFrame();
            firstWindow.add(firstPanel);
            firstWindow.setVisible(true);
            firstWindow.setSize(300,300);
            firstWindow.setLocationRelativeTo(null);

            proceed.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton save, exit, delete;
                    JPanel secondWindowMain, secondWindowDetalii, secondWindowButoane;
                    StockManager stockMan = new StockManager();
                    MovieManager movieMan = new MovieManager();
                    GameManager gameMan = new GameManager();
                    AlbumManager albumMan = new AlbumManager();

                    if (editCategorieBox.getSelectedItem().equals("Filme")) {
                        JLabel editProdTitluLabel, editProdActorLabel, editProdDirectorLabel, editProdDurataLabel, editProdGenLabel, editProdAnLabel, editProdAudientaLabel,
                                editProdCantLabel, editProdPretLabel;
                        JTextField editProdTitluField, editProdActorField, editProdDirectorField, editProdDurataField, editProdGenField, editProdAnField, editProdAudientaField,
                                editProdCantField, editProdPretField;

                        Movie movie = new Movie();
                        movie.setIdMovie(Integer.parseInt(editIDField.getText()));
                        Store store = new Store();
                        store.setId(employee.getIdMag());
                        Stock stock = stockMan.getMovieStock(movie, store);
                        stock.setMovie(movieMan.getMovieById(movie.getIdMovie()));
                        movie = stock.getMovie();
                        if (stock == null) {
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "ID-ul introdus nu exista in baza de date", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            editProdTitluLabel = new JLabel("Titlu:");
                            editProdTitluField = new JTextField();
                            editProdTitluField.setText(movie.getTitle());
                            editProdActorLabel = new JLabel("Actor:");
                            editProdActorField = new JTextField();
                            editProdActorField.setText(movie.getMainActor());
                            editProdDirectorLabel = new JLabel("Director:");
                            editProdDirectorField = new JTextField();
                            editProdDirectorField.setText(movie.getDirector());
                            editProdDurataLabel = new JLabel("Durata:");
                            editProdDurataField = new JTextField();
                            editProdDurataField.setText(movie.getDuration() + "");
                            editProdGenLabel = new JLabel("Gen:");
                            editProdGenField = new JTextField();
                            editProdGenField.setText(movie.getGenre());
                            editProdAnLabel = new JLabel("An:");
                            editProdAnField = new JTextField();
                            editProdAnField.setText(movie.getYear());
                            editProdAudientaLabel = new JLabel("Audienta:");
                            editProdAudientaField = new JTextField();
                            editProdAudientaField.setText(movie.getAudience() + "");
                            editProdCantLabel = new JLabel("Cantitate:");
                            editProdCantField = new JTextField();
                            editProdCantField.setText(stock.getQuantity() + "");
                            editProdPretLabel = new JLabel("Pret:");
                            editProdPretField = new JTextField();
                            editProdPretField.setText(stock.getPrice() + "");
                            save = new JButton("Save");
                            exit = new JButton("Exit");
                            delete = new JButton("Delete");

                            secondWindowDetalii = new JPanel(new GridLayout(9, 9, 5, 5));
                            secondWindowDetalii.add(editProdTitluLabel);
                            secondWindowDetalii.add(editProdTitluField);
                            secondWindowDetalii.add(editProdActorLabel);
                            secondWindowDetalii.add(editProdActorField);
                            secondWindowDetalii.add(editProdDirectorLabel);
                            secondWindowDetalii.add(editProdDirectorField);
                            secondWindowDetalii.add(editProdDurataLabel);
                            secondWindowDetalii.add(editProdDurataField);
                            secondWindowDetalii.add(editProdGenLabel);
                            secondWindowDetalii.add(editProdGenField);
                            secondWindowDetalii.add(editProdAnLabel);
                            secondWindowDetalii.add(editProdAnField);
                            secondWindowDetalii.add(editProdAudientaLabel);
                            secondWindowDetalii.add(editProdAudientaField);
                            secondWindowDetalii.add(editProdCantLabel);
                            secondWindowDetalii.add(editProdCantField);
                            secondWindowDetalii.add(editProdPretLabel);
                            secondWindowDetalii.add(editProdPretField);
                            secondWindowButoane = new JPanel(new GridLayout(1, 3, 5, 5));
                            secondWindowButoane.add(save);
                            secondWindowButoane.add(delete);
                            secondWindowButoane.add(exit);

                            secondWindowMain = new JPanel(new BorderLayout());
                            secondWindowMain.add(secondWindowDetalii, BorderLayout.CENTER);
                            secondWindowMain.add(secondWindowButoane, BorderLayout.SOUTH);
                            JFrame secondWindow = new JFrame();
                            secondWindow.add(secondWindowMain);
                            secondWindow.setVisible(true);
                            secondWindow.setSize(300, 300);

                            save.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFrame dialog = new JFrame();
                                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa modificati produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                                    if (a == JOptionPane.YES_OPTION) {
                                        Movie movie = stock.getMovie();
                                        movie.setIdMovie(Integer.parseInt(editIDField.getText()));
                                        movie.setTitle(editProdTitluField.getText());
                                        movie.setMainActor(editProdActorField.getText());
                                        movie.setDirector(editProdDirectorField.getText());
                                        movie.setDuration(Integer.parseInt(editProdDurataField.getText()));
                                        movie.setGenre(editProdGenField.getText());
                                        movie.setYear(editProdAnField.getText());
                                        movie.setAudience(Integer.parseInt(editProdAudientaField.getText()));
                                        stock.setPrice(Integer.parseInt(editProdPretField.getText()));
                                        stock.setQuantity(Integer.parseInt(editProdCantField.getText()));

                                        movieMan.updateMovie(movie);
                                        stockMan.updateMovieStock(movie, stock.getStore(), stock.getQuantity(), stock.getPrice());
                                    }
                                }
                            });

                            delete.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFrame dialog = new JFrame();
                                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa stergeti produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                                    if (a == JOptionPane.YES_OPTION) {
                                        stockMan.deleteMovieStock(stock.getMovie(), stock.getStore());
                                        secondWindow.setVisible(false);
                                        secondWindow.dispose();
                                    }
                                }
                            });

                            exit.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    secondWindow.setVisible(false);
                                    secondWindow.dispose();
                                }
                            });
                        }
                    } else if (editCategorieBox.getSelectedItem().equals("Jocuri")) {
                        JLabel editProdTitluLabel, editProdPlatformaLabel, editProdDeveloperLabel, editProdPublisherLabel, editProdGenLabel, editProdAnLabel, editProdAudientaLabel,
                                editProdCantLabel, editProdPretLabel;
                        JTextField editProdTitluField, editProdPlatformaField, editProdDeveloperField, editProdPublisherField, editProdGenField, editProdAnField, editProdAudientaField,
                                editProdCantField, editProdPretField;

                        Game game = new Game();
                        game.setIdGame(Integer.parseInt(editIDField.getText()));
                        Store store = new Store();
                        store.setId(employee.getIdMag());
                        Stock stock = stockMan.getGameStock(game, store);
                        stock.setGame(gameMan.getGameById(game.getIdGame()));

                        game = stock.getGame();
                        if (stock == null) {
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "ID-ul introdus nu exista in baza de date", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            editProdTitluLabel = new JLabel("Titlu:");
                            editProdTitluField = new JTextField();
                            editProdTitluField.setText(game.getTitle());
                            editProdPlatformaLabel = new JLabel("Platforma:");
                            editProdPlatformaField = new JTextField();
                            editProdPlatformaField.setText(game.getPlatform());
                            editProdDeveloperLabel = new JLabel("Developer:");
                            editProdDeveloperField = new JTextField();
                            editProdDeveloperField.setText(game.getDeveloper());
                            editProdPublisherLabel = new JLabel("Publisher:");
                            editProdPublisherField = new JTextField();
                            editProdPublisherField.setText(game.getPublisher());
                            editProdGenLabel = new JLabel("Gen:");
                            editProdGenField = new JTextField();
                            editProdGenField.setText(game.getGenre());
                            editProdAnLabel = new JLabel("An:");
                            editProdAnField = new JTextField();
                            editProdAnField.setText(game.getYear());
                            editProdAudientaLabel = new JLabel("Audienta:");
                            editProdAudientaField = new JTextField();
                            editProdAudientaField.setText(game.getAudience() + "");
                            editProdCantLabel = new JLabel("Cantitate:");
                            editProdCantField = new JTextField();
                            editProdCantField.setText(stock.getQuantity() + "");
                            editProdPretLabel = new JLabel("Pret:");
                            editProdPretField = new JTextField();
                            editProdPretField.setText(stock.getPrice() + "");
                            save = new JButton("Save");
                            exit = new JButton("Exit");
                            delete = new JButton("Delete");

                            secondWindowDetalii = new JPanel(new GridLayout(9, 9, 5, 5));
                            secondWindowDetalii.add(editProdTitluLabel);
                            secondWindowDetalii.add(editProdTitluField);
                            secondWindowDetalii.add(editProdPlatformaLabel);
                            secondWindowDetalii.add(editProdPlatformaField);
                            secondWindowDetalii.add(editProdDeveloperLabel);
                            secondWindowDetalii.add(editProdDeveloperField);
                            secondWindowDetalii.add(editProdPublisherLabel);
                            secondWindowDetalii.add(editProdPublisherField);
                            secondWindowDetalii.add(editProdGenLabel);
                            secondWindowDetalii.add(editProdGenField);
                            secondWindowDetalii.add(editProdAnLabel);
                            secondWindowDetalii.add(editProdAnField);
                            secondWindowDetalii.add(editProdAudientaLabel);
                            secondWindowDetalii.add(editProdAudientaField);
                            secondWindowDetalii.add(editProdCantLabel);
                            secondWindowDetalii.add(editProdCantField);
                            secondWindowDetalii.add(editProdPretLabel);
                            secondWindowDetalii.add(editProdPretField);
                            secondWindowButoane = new JPanel(new GridLayout(1, 3, 5, 5));
                            secondWindowButoane.add(save);
                            secondWindowButoane.add(delete);
                            secondWindowButoane.add(exit);

                            secondWindowMain = new JPanel(new BorderLayout());
                            secondWindowMain.add(secondWindowDetalii, BorderLayout.CENTER);
                            secondWindowMain.add(secondWindowButoane, BorderLayout.SOUTH);
                            JFrame secondWindow = new JFrame();
                            secondWindow.add(secondWindowMain);
                            secondWindow.setVisible(true);
                            secondWindow.setSize(300, 300);

                            save.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFrame dialog = new JFrame();
                                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa modificati produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                                    if (a == JOptionPane.YES_OPTION) {
                                        Game game = stock.getGame();
                                        game.setIdGame(Integer.parseInt(editIDField.getText()));
                                        game.setTitle(editProdTitluField.getText());
                                        game.setPlatform(editProdPlatformaField.getText());
                                        game.setDeveloper(editProdDeveloperField.getText());
                                        game.setPublisher(editProdPublisherField.getText());
                                        game.setYear(editProdAnField.getText());
                                        game.setGenre(editProdGenField.getText());
                                        game.setAudience(Integer.parseInt(editProdAudientaField.getText()));
                                        stock.setQuantity(Integer.parseInt(editProdCantField.getText()));
                                        stock.setPrice(Integer.parseInt(editProdPretField.getText()));

                                        gameMan.updateGame(game);
                                        stockMan.updateGameStock(game, stock.getStore(), stock.getQuantity(), stock.getPrice());
                                    }
                                }
                            });

                            delete.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFrame dialog = new JFrame();
                                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa stergeti produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                                    if (a == JOptionPane.YES_OPTION) {
                                        stockMan.deleteGameStock(stock.getGame(), stock.getStore());
                                        secondWindow.setVisible(false);
                                        secondWindow.dispose();
                                    }
                                }
                            });

                            exit.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    secondWindow.setVisible(false);
                                    secondWindow.dispose();
                                }
                            });
                        }
                    } else if (editCategorieBox.getSelectedItem().equals("Albume")) {
                        JLabel editProdTitluLabel, editProdTrupaLabel, editProdMelodiiLabel, editProdCasaDiscLabel, editProdGenLabel, editProdAnLabel,
                                editProdCantLabel, editProdPretLabel;
                        JTextField editProdTitluField, editProdTrupaField, editProdMelodiiField, editProdCasaDiscField, editProdGenField, editProdAnField,
                                editProdCantField, editProdPretField;

                        Album album = new Album();
                        album.setIdAlbum(Integer.parseInt(editIDField.getText()));
                        Store store = new Store();
                        store.setId(employee.getIdMag());
                        Stock stock = stockMan.getAlbumStock(album, store);
                        stock.setAlbum(albumMan.getAlbumById(album.getIdAlbum()));

                        album = stock.getAlbum();
                        if (stock == null) {
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "ID-ul introdus nu exista in baza de date", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            editProdTitluLabel = new JLabel("Titlu:");
                            editProdTitluField = new JTextField();
                            editProdTitluField.setText(album.getTitle());
                            editProdTrupaLabel = new JLabel("Trupa:");
                            editProdTrupaField = new JTextField();
                            editProdTrupaField.setText(album.getArtist());
                            editProdMelodiiLabel = new JLabel("Numar melodii:");
                            editProdMelodiiField = new JTextField();
                            editProdMelodiiField.setText(album.getNrMel() + "");
                            editProdCasaDiscLabel = new JLabel("Casa discuri:");
                            editProdCasaDiscField = new JTextField();
                            editProdCasaDiscField.setText(album.getProducer());
                            editProdGenLabel = new JLabel("Gen:");
                            editProdGenField = new JTextField();
                            editProdGenField.setText(album.getGenre());
                            editProdAnLabel = new JLabel("An:");
                            editProdAnField = new JTextField();
                            editProdAnField.setText(album.getYear());
                            editProdCantLabel = new JLabel("Cantitate:");
                            editProdCantField = new JTextField();
                            editProdCantField.setText(stock.getQuantity() + "");
                            editProdPretLabel = new JLabel("Pret:");
                            editProdPretField = new JTextField();
                            editProdPretField.setText(stock.getPrice() + "");
                            save = new JButton("Save");
                            exit = new JButton("Exit");
                            delete = new JButton("Delete");

                            secondWindowDetalii = new JPanel(new GridLayout(8, 8, 5, 5));
                            secondWindowDetalii.add(editProdTitluLabel);
                            secondWindowDetalii.add(editProdTitluField);
                            secondWindowDetalii.add(editProdTrupaLabel);
                            secondWindowDetalii.add(editProdTrupaField);
                            secondWindowDetalii.add(editProdMelodiiLabel);
                            secondWindowDetalii.add(editProdMelodiiField);
                            secondWindowDetalii.add(editProdCasaDiscLabel);
                            secondWindowDetalii.add(editProdCasaDiscField);
                            secondWindowDetalii.add(editProdGenLabel);
                            secondWindowDetalii.add(editProdGenField);
                            secondWindowDetalii.add(editProdAnLabel);
                            secondWindowDetalii.add(editProdAnField);
                            secondWindowDetalii.add(editProdCantLabel);
                            secondWindowDetalii.add(editProdCantField);
                            secondWindowDetalii.add(editProdPretLabel);
                            secondWindowDetalii.add(editProdPretField);
                            secondWindowButoane = new JPanel(new GridLayout(1, 3, 5, 5));
                            secondWindowButoane.add(save);
                            secondWindowButoane.add(delete);
                            secondWindowButoane.add(exit);

                            secondWindowMain = new JPanel(new BorderLayout());
                            secondWindowMain.add(secondWindowDetalii, BorderLayout.CENTER);
                            secondWindowMain.add(secondWindowButoane, BorderLayout.SOUTH);
                            JFrame secondWindow = new JFrame();
                            secondWindow.add(secondWindowMain);
                            secondWindow.setVisible(true);
                            secondWindow.setSize(300, 300);

                            save.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFrame dialog = new JFrame();
                                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa modificati produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                                    if (a == JOptionPane.YES_OPTION) {
                                        Album album = stock.getAlbum();
                                        album.setIdAlbum(Integer.parseInt(editIDField.getText()));
                                        album.setTitle(editProdTitluField.getText());
                                        album.setArtist(editProdTrupaField.getText());
                                        album.setNrMel(Integer.parseInt(editProdMelodiiField.getText()));
                                        album.setProducer(editProdCasaDiscField.getText());
                                        album.setYear(editProdAnField.getText());
                                        album.setGenre(editProdGenField.getText());
                                        stock.setQuantity(Integer.parseInt(editProdCantField.getText()));
                                        stock.setPrice(Integer.parseInt(editProdPretField.getText()));

                                        albumMan.updateAlbum(album);
                                        stockMan.updateAlbumStock(album, stock.getStore(), stock.getQuantity(), stock.getPrice());
                                    }
                                }
                            });

                            delete.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFrame dialog = new JFrame();
                                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa stergeti produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                                    if (a == JOptionPane.YES_OPTION) {
                                        stockMan.deleteAlbumStock(stock.getAlbum(), stock.getStore());
                                        secondWindow.setVisible(false);
                                        secondWindow.dispose();
                                    }
                                }
                            });

                            exit.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    secondWindow.setVisible(false);
                                    secondWindow.dispose();
                                }
                            });
                        }
                    }
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    firstWindow.setVisible(false);
                    firstWindow.dispose();
                }
            });

        }

        private void newCustomerWindow() {
            //Username
            JLabel newUserLabel = new JLabel();
            newUserLabel.setText("Username: ");
            JTextField newUsernameText = new JTextField();

            //Password
            JLabel newPasswordLabel = new JLabel();
            newPasswordLabel.setText("Password: ");
            JPasswordField newPasswordText = new JPasswordField();

            //Email
            JLabel newEmailLabel = new JLabel();
            newEmailLabel.setText("Email: ");
            JTextField newEmailText = new JTextField();

            //First Name
            JLabel newNameLabel = new JLabel();
            newNameLabel.setText("Nume: ");
            JTextField newNameText = new JTextField();

            //Last Name
            JLabel newPrenLabel = new JLabel();
            newPrenLabel.setText("Prenume: ");
            JTextField newPrenText = new JTextField();

            //Address
            JLabel newAddressLabel = new JLabel();
            newAddressLabel.setText("Address: ");
            JTextField newAddressText = new JTextField();

            //City
            JLabel newCityLabel = new JLabel();
            newCityLabel.setText("City: ");
            JTextField newCityText = new JTextField();

            //CNP
            JLabel newCNPLabel = new JLabel();
            newCNPLabel.setText("CNP: ");
            JTextField newCNPText = new JTextField();

            //Phone
            JLabel newTelLabel = new JLabel();
            newTelLabel.setText("Phone: ");
            JTextField newTelText = new JTextField();

            //Birthday
            JLabel newBirthYearLabel = new JLabel("Year(yyyy)");
            JLabel newBirthMonthLabel = new JLabel("Month(mm)");
            JLabel newBirthDayLabel = new JLabel("Day(dd)");
            JTextField newBirthYearText = new JTextField();
            JTextField newBirthMonthText = new JTextField();
            JTextField newBirthDayText = new JTextField();

            //Data panel
            JPanel newDataPanel = new JPanel(new GridLayout(9,2,5,5));
            newDataPanel.add(newUserLabel);
            newDataPanel.add(newUsernameText);
            newDataPanel.add(newPasswordLabel);
            newDataPanel.add(newPasswordText);
            newDataPanel.add(newEmailLabel);
            newDataPanel.add(newEmailText);
            newDataPanel.add(newNameLabel);
            newDataPanel.add(newNameText);
            newDataPanel.add(newPrenLabel);
            newDataPanel.add(newPrenText);
            newDataPanel.add(newAddressLabel);
            newDataPanel.add(newAddressText);
            newDataPanel.add(newCityLabel);
            newDataPanel.add(newCityText);
            newDataPanel.add(newCNPLabel);
            newDataPanel.add(newCNPText);
            newDataPanel.add(newTelLabel);
            newDataPanel.add(newTelText);

            //Birth Panel
            JPanel newBirthPanel = new JPanel(new GridLayout(2,3,5,5));
            newBirthPanel.add(newBirthYearLabel);
            newBirthPanel.add(newBirthMonthLabel);
            newBirthPanel.add(newBirthDayLabel);
            newBirthPanel.add(newBirthYearText);
            newBirthPanel.add(newBirthMonthText);
            newBirthPanel.add(newBirthDayText);

            //Button Panel
            JPanel newButtonPanel = new JPanel(new GridLayout(1,2,5,5));
            JButton newCreateButton = new JButton("Create");
            JButton newCancelButton = new JButton("Cancel");
            newButtonPanel.add(newCreateButton);
            newButtonPanel.add(newCancelButton);

            //Main Panel
            JPanel newMainPanel = new JPanel(new BorderLayout());
            newMainPanel.add(newDataPanel, BorderLayout.NORTH);
            newMainPanel.add(newBirthPanel, BorderLayout.CENTER);
            newMainPanel.add(newButtonPanel, BorderLayout.SOUTH);

            JFrame newFrame = new JFrame();
            newFrame.add(newMainPanel);
            newFrame.setVisible(true);
            newFrame.setSize(300,330);
            newFrame.setLocationRelativeTo(null);

            newCancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame f = new JFrame();
                    int a = JOptionPane.showConfirmDialog(f, "Are you sure?", "Exiting", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.YES_OPTION) {
                        newFrame.setVisible(false);
                        newFrame.dispose();
                    }
                }
            });

            newCreateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String username = newUsernameText.getText();
                    String password = String.valueOf(newPasswordText.getPassword());
                    String email = newEmailText.getText();
                    String firstName = newNameText.getText();
                    String lastName = newPrenText.getText();
                    String address = newAddressText.getText();
                    String city = newCityText.getText();
                    String cnp = newCNPText.getText();
                    String phone = newTelText.getText();

                    String year = newBirthYearText.getText();
                    String month = newBirthMonthText.getText();
                    String day = newBirthDayText.getText();

                    if(username.equals("") || password.equals("") || email.equals("") || firstName.equals("") || lastName.equals("") || address.equals("") || city.equals("")
                            || cnp.equals("") || phone.equals("") || year.equals("") || month.equals("") || day.equals("")) {
                        JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "You must complete all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String birthdate = new String(year + "-" + month + "-" + day);

                        Client client = new Client(0, lastName, firstName, address, city, LocalDate.parse(birthdate), cnp, phone, (email.isEmpty()) ? null : email, 5);
                        Account account = new Account(0, username, password, null, 1, client.getId());
                        AccountManager accountMan = new AccountManager();
                        ClientManager clientMan = new ClientManager();

                        clientMan.createClient(client);
                        accountMan.createClientAccount(account);

                        JFrame confirmDialog = new JFrame();
                        JOptionPane.showMessageDialog(confirmDialog, "Your account has successfully been created!");
                    }
                }
            });
        }

        private void editCustomerWindow() {
            ClientManager clientMan = new ClientManager();
            ArrayList<Client> customers = clientMan.getAllClients();
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[]{"ID", "Nume", "Prenume", "Adresa", "Oras", "Data nasterii", "CNP", "Telefon", "Email", "Loialitate", "Username", "Password"};
            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < customers.size(); i++) {
                Client client = customers.get(i);
                tableModel.addRow(clientMan.clientToRow(client));
            }

            table.setModel(tableModel);
            JScrollPane scroll = new JScrollPane(table);

            JButton edit, exit;
            JPanel firstWindowPanel, firstWindowButtonsPanel;
            edit = new JButton("Edit");
            exit = new JButton("Exit");
            firstWindowButtonsPanel = new JPanel(new GridLayout(1,2,5,5));
            firstWindowButtonsPanel.add(edit);
            firstWindowButtonsPanel.add(exit);
            firstWindowPanel = new JPanel(new BorderLayout());
            firstWindowPanel.add(scroll, BorderLayout.CENTER);
            firstWindowPanel.add(firstWindowButtonsPanel, BorderLayout.SOUTH);
            JFrame firstWindow = new JFrame();
            firstWindow.add(firstWindowPanel);
            firstWindow.setVisible(true);
            firstWindow.setSize(1000,300);
            firstWindow.setTitle("Clienti");
            firstWindow.setLocationRelativeTo(null);

            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(table.getSelectionModel().isSelectionEmpty()) {
                        JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "Alegeti mai intai un client din lista!", "Eroare", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JLabel custNumeLabel, custPrenumeLabel, custAdresaLabel, custOrasLabel, custDatNasLabel, custCNPLabel, custTelLabel, custEmailLabel,
                                custUserLabel, custPassLabel;
                        JTextField custNumeField, custPrenumeField, custAdresaField, custOrasField, custDatNasField, custCNPField, custTelField, custEmailField,
                                custUserField, custPassField;
                        JButton exit, save;

                        custNumeLabel = new JLabel("Nume:");
                        custNumeField = new JTextField();
                        custNumeField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
                        custPrenumeLabel = new JLabel("Prenume:");
                        custPrenumeField = new JTextField();
                        custPrenumeField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
                        custAdresaLabel = new JLabel("Adresa:");
                        custAdresaField = new JTextField();
                        custAdresaField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
                        custOrasLabel = new JLabel("Oras:");
                        custOrasField = new JTextField();
                        custOrasField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
                        custDatNasLabel = new JLabel("Data nasterii:");
                        custDatNasField = new JTextField();
                        custDatNasField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));
                        custCNPLabel = new JLabel("CNP:");
                        custCNPField = new JTextField();
                        custCNPField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));
                        custTelLabel = new JLabel("Telefon:");
                        custTelField = new JTextField();
                        custTelField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 7)));
                        custEmailLabel = new JLabel("Email:");
                        custEmailField = new JTextField();
                        custEmailField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 8)));
                        custUserLabel = new JLabel("Username:");
                        custUserField = new JTextField();
                        custUserField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 10)));
                        custPassLabel = new JLabel("Password:");
                        custPassField = new JTextField();
                        custPassField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 11)));

                        exit = new JButton("Exit");
                        save = new JButton("Save");

                        JPanel userDetaliiPanel, userMainPanel, userButtonPanel;
                        userDetaliiPanel = new JPanel(new GridLayout(10,10,5,5));
                        userDetaliiPanel.add(custNumeLabel);
                        userDetaliiPanel.add(custNumeField);
                        userDetaliiPanel.add(custPrenumeLabel);
                        userDetaliiPanel.add(custPrenumeField);
                        userDetaliiPanel.add(custAdresaLabel);
                        userDetaliiPanel.add(custAdresaField);
                        userDetaliiPanel.add(custOrasLabel);
                        userDetaliiPanel.add(custOrasField);
                        userDetaliiPanel.add(custDatNasLabel);
                        userDetaliiPanel.add(custDatNasField);
                        userDetaliiPanel.add(custCNPLabel);
                        userDetaliiPanel.add(custCNPField);
                        userDetaliiPanel.add(custTelLabel);
                        userDetaliiPanel.add(custTelField);
                        userDetaliiPanel.add(custEmailLabel);
                        userDetaliiPanel.add(custEmailField);
                        userDetaliiPanel.add(custUserLabel);
                        userDetaliiPanel.add(custUserField);
                        userDetaliiPanel.add(custPassLabel);
                        userDetaliiPanel.add(custPassField);
                        userButtonPanel = new JPanel(new GridLayout(1,2,5,5));
                        userButtonPanel.add(save);
                        userButtonPanel.add(exit);
                        userMainPanel = new JPanel(new BorderLayout());
                        userMainPanel.add(userDetaliiPanel, BorderLayout.CENTER);
                        userMainPanel.add(userButtonPanel, BorderLayout.SOUTH);

                        JFrame frame = new JFrame();
                        frame.add(userMainPanel);
                        frame.setVisible(true);
                        frame.setSize(300,300);
                        frame.setTitle("Client");

                        save.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFrame f = new JFrame();
                                int a = JOptionPane.showConfirmDialog(f, "Are you sure?", "Saving", JOptionPane.YES_NO_OPTION);
                                if(a == JOptionPane.YES_OPTION) {
                                    AccountManager accMan = new AccountManager();

                                    Client client = new Client();
                                    client.setId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
                                    client.setNume(custNumeField.getText());
                                    client.setPrenume(custPrenumeField.getText());
                                    client.setAdresa(custAdresaField.getText());
                                    client.setOras(custOrasField.getText());
                                    client.setDatan(LocalDate.parse(custDatNasField.getText()));
                                    client.setCnp(custCNPField.getText());
                                    client.setTel(custTelField.getText());
                                    client.setEmail((custEmailField.getText().isEmpty()) ? null : custEmailField.getText());
                                    client.setAccount(accMan.getClientAccount(client));
                                    client.getAccount().setUsername(custUserField.getText());
                                    client.getAccount().setPassword(custPassField.getText());

                                    accMan.updateClientAccount(client.getAccount());
                                    clientMan.updateClient(client);
                                }
                            }
                        });

                        exit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                frame.setVisible(false);
                                frame.dispose();
                            }
                        });
                    }
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    firstWindow.setVisible(false);
                    firstWindow.dispose();
                }
            });
        }

        private void newEmployeeWindow() {
            //Username
            JLabel newUserLabel = new JLabel();
            newUserLabel.setText("Username: ");
            JTextField newUsernameText = new JTextField();

            //Password
            JLabel newPasswordLabel = new JLabel();
            newPasswordLabel.setText("Password: ");
            JPasswordField newPasswordText = new JPasswordField();

            //Email
            JLabel newEmailLabel = new JLabel();
            newEmailLabel.setText("Email: ");
            JTextField newEmailText = new JTextField();

            //Nume
            JLabel newNameLabel = new JLabel();
            newNameLabel.setText("Nume: ");
            JTextField newNameText = new JTextField();

            //Prenume
            JLabel newPrenLabel = new JLabel();
            newPrenLabel.setText("Prenume: ");
            JTextField newPrenText = new JTextField();

            //Adresa
            JLabel newAddressLabel = new JLabel();
            newAddressLabel.setText("Adresa: ");
            JTextField newAddressText = new JTextField();

            //Oras
            JLabel newCityLabel = new JLabel();
            newCityLabel.setText("Oras: ");
            JTextField newCityText = new JTextField();

            //CNP
            JLabel newCNPLabel = new JLabel();
            newCNPLabel.setText("CNP: ");
            JTextField newCNPText = new JTextField();

            //Telefon
            JLabel newTelLabel = new JLabel();
            newTelLabel.setText("Telefon: ");
            JTextField newTelText = new JTextField();

            //Functie
            JLabel newFuncLabel = new JLabel("Functie:");
            JTextField newFuncField = new JTextField();

            //Salariu
            JLabel newSalLabel = new JLabel("Salariu:");
            JTextField newSalField = new JTextField();

            //Magazin
            JLabel newMagLabel = new JLabel("Magazin:");
            StoreManager storeMan = new StoreManager();
            ArrayList<Store> stores = storeMan.getStores();
            ArrayList<String> cities = new ArrayList<>();
            Iterator iter = stores.iterator();
            while (iter.hasNext()) {
                Store store = (Store) iter.next();
                cities.add(store.getOras());
            }
            JComboBox newMagBox = new JComboBox(cities.toArray());

            //Birthday
            JLabel newBirthYearLabel = new JLabel("An(yyyy)");
            JLabel newBirthMonthLabel = new JLabel("Luna(mm)");
            JLabel newBirthDayLabel = new JLabel("Zi(dd)");
            JTextField newBirthYearText = new JTextField();
            JTextField newBirthMonthText = new JTextField();
            JTextField newBirthDayText = new JTextField();

            //Data panel
            JPanel newDataPanel = new JPanel(new GridLayout(12,2,5,5));
            newDataPanel.add(newUserLabel);
            newDataPanel.add(newUsernameText);
            newDataPanel.add(newPasswordLabel);
            newDataPanel.add(newPasswordText);
            newDataPanel.add(newEmailLabel);
            newDataPanel.add(newEmailText);
            newDataPanel.add(newNameLabel);
            newDataPanel.add(newNameText);
            newDataPanel.add(newPrenLabel);
            newDataPanel.add(newPrenText);
            newDataPanel.add(newAddressLabel);
            newDataPanel.add(newAddressText);
            newDataPanel.add(newCityLabel);
            newDataPanel.add(newCityText);
            newDataPanel.add(newCNPLabel);
            newDataPanel.add(newCNPText);
            newDataPanel.add(newTelLabel);
            newDataPanel.add(newTelText);
            newDataPanel.add(newFuncLabel);
            newDataPanel.add(newFuncField);
            newDataPanel.add(newSalLabel);
            newDataPanel.add(newSalField);
            newDataPanel.add(newMagLabel);
            newDataPanel.add(newMagBox);

            //Birth Panel
            JPanel newBirthPanel = new JPanel(new GridLayout(2,3,5,5));
            newBirthPanel.add(newBirthYearLabel);
            newBirthPanel.add(newBirthMonthLabel);
            newBirthPanel.add(newBirthDayLabel);
            newBirthPanel.add(newBirthYearText);
            newBirthPanel.add(newBirthMonthText);
            newBirthPanel.add(newBirthDayText);

            //Button Panel
            JPanel newButtonPanel = new JPanel(new GridLayout(1,2,5,5));
            JButton newCreateButton = new JButton("Create");
            JButton newCancelButton = new JButton("Cancel");
            newButtonPanel.add(newCreateButton);
            newButtonPanel.add(newCancelButton);

            //Main Panel
            JPanel newMainPanel = new JPanel(new BorderLayout());
            newMainPanel.add(newDataPanel, BorderLayout.NORTH);
            newMainPanel.add(newBirthPanel, BorderLayout.CENTER);
            newMainPanel.add(newButtonPanel, BorderLayout.SOUTH);

            JFrame newFrame = new JFrame();
            newFrame.add(newMainPanel);
            newFrame.setVisible(true);
            newFrame.setSize(300,480);
            newFrame.setTitle("Creare angajat");
            newFrame.setLocationRelativeTo(null);

            newCancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame f = new JFrame();
                    int a = JOptionPane.showConfirmDialog(f, "Are you sure?", "Exiting", JOptionPane.YES_NO_OPTION);
                    if(a == JOptionPane.YES_OPTION) {
                        newFrame.setVisible(false);
                        newFrame.dispose();
                    }
                }
            });

            newCreateButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    String username = newUsernameText.getText();
                    String password = String.valueOf(newPasswordText.getPassword());
                    String email = newEmailText.getText();
                    String firstName = newNameText.getText();
                    String lastName = newPrenText.getText();
                    String address = newAddressText.getText();
                    String city = newCityText.getText();
                    String cnp = newCNPText.getText();
                    String phone = newTelText.getText();
                    String functie = newFuncField.getText();
                    String salariu = newSalField.getText();
                    String mag = newMagBox.getSelectedItem().toString();

                    String year = newBirthYearText.getText();
                    String month = newBirthMonthText.getText();
                    String day = newBirthDayText.getText();

                    if(username.equals("") || password.equals("") || email.equals("") || firstName.equals("") || lastName.equals("") || address.equals("") || city.equals("")
                            || cnp.equals("") || phone.equals("") || year.equals("") || month.equals("") || day.equals("") || functie.equals("") || salariu.equals("")) {
                        JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "Trebuie completate toate campurile!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        String birthdate = new String(year + "-" + month + "-" + day);

                        EmployeeManager empMan = new EmployeeManager();
                        AccountManager accMan = new AccountManager();
                        StoreManager storeMan = new StoreManager();
                        Store store = storeMan.getStoreByCity(mag);

                        Employee emp = new Employee(0, lastName, firstName, address, city, LocalDate.parse(birthdate), cnp, phone, email, functie, Integer.parseInt(salariu), true, store.getId());
                        empMan.createEmployee(emp);
                        Account acc = new Account(0, username, password, null, 2, emp.getIdEmp());
                        accMan.createEmployeeAccount(acc);

                        JFrame confirmDialog = new JFrame();
                        JOptionPane.showMessageDialog(confirmDialog, "Your account has successfully been created!");

                        changePanel(mainMainPanel, size[0], size[1]);
                        MenuBar();
                        setTitle("DVDMania");
                    }
                }
            });
        }

        private void editEmployeeWindow() {
            EmployeeManager empMan = new EmployeeManager();
            ArrayList<Employee> employees = empMan.getEmployees();
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[]{"ID", "Nume", "Prenume", "Adresa", "Oras", "Data nasterii", "CNP", "Telefon", "Email", "Functie", "Salariu", "Adresa magazin", "Username", "Parola"};
            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < employees.size(); i++) {
                Employee emp = employees.get(i);
                tableModel.addRow(empMan.employeeToRow(emp));
            }

            table.setModel(tableModel);
            JScrollPane scroll = new JScrollPane(table);

            JButton edit, exit;
            JPanel firstWindowPanel, firstWindowButtonsPanel;
            edit = new JButton("Edit");
            exit = new JButton("Exit");
            firstWindowButtonsPanel = new JPanel(new GridLayout(1,2,5,5));
            firstWindowButtonsPanel.add(edit);
            firstWindowButtonsPanel.add(exit);
            firstWindowPanel = new JPanel(new BorderLayout());
            firstWindowPanel.add(scroll, BorderLayout.CENTER);
            firstWindowPanel.add(firstWindowButtonsPanel, BorderLayout.SOUTH);
            JFrame firstWindow = new JFrame();
            firstWindow.add(firstWindowPanel);
            firstWindow.setVisible(true);
            firstWindow.setSize(1000,300);
            firstWindow.setTitle("Angajati");
            firstWindow.setLocationRelativeTo(null);

            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(table.getSelectionModel().isSelectionEmpty()) {
                        JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "Alegeti mai intai un client din lista!", "Eroare", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JLabel empNumeLabel, empPrenumeLabel, empAdresaLabel, empOrasLabel, empDatNasLabel, empCNPLabel, empTelLabel, empEmailLabel,
                                empUserLabel, empPassLabel, empFuncLabel, empSalLabel, empStoreLabel, empActvLabel;
                        JTextField empNumeField, empPrenumeField, empAdresaField, empOrasField, empDatNasField, empCNPField, empTelField, empEmailField,
                                empUserField, empPassField, empFuncField, empSalField, empStoreField, empActivField;
                        JButton exit, save;

                        empNumeLabel = new JLabel("Nume:");
                        empNumeField = new JTextField();
                        empNumeField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
                        empPrenumeLabel = new JLabel("Prenume:");
                        empPrenumeField = new JTextField();
                        empPrenumeField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
                        empAdresaLabel = new JLabel("Adresa:");
                        empAdresaField = new JTextField();
                        empAdresaField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));
                        empOrasLabel = new JLabel("Oras:");
                        empOrasField = new JTextField();
                        empOrasField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
                        empDatNasLabel = new JLabel("Data nasterii:");
                        empDatNasField = new JTextField();
                        empDatNasField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 5)));
                        empCNPLabel = new JLabel("CNP:");
                        empCNPField = new JTextField();
                        empCNPField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 6)));
                        empTelLabel = new JLabel("Telefon:");
                        empTelField = new JTextField();
                        empTelField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 7)));
                        empEmailLabel = new JLabel("Email:");
                        empEmailField = new JTextField();
                        empEmailField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 8)));
                        empFuncLabel = new JLabel("Functie:");
                        empFuncField = new JTextField();
                        empFuncField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 9)));
                        empSalLabel = new JLabel("Salariu:");
                        empSalField = new JTextField();
                        empSalField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 10)));
                        empStoreLabel = new JLabel("Magazin:");
                        empStoreField = new JTextField();
                        empStoreField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 11)));
                        empUserLabel = new JLabel("Username:");
                        empUserField = new JTextField();
                        empUserField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 12)));
                        empPassLabel = new JLabel("Password:");
                        empPassField = new JTextField();
                        empPassField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 13)));
                        empActvLabel = new JLabel("Activ/Inactiv:");
                        empActivField = new JTextField();
                        empActivField.setText("Activ");

                        exit = new JButton("Exit");
                        save = new JButton("Save");

                        JPanel userDetaliiPanel, userMainPanel, userButtonPanel;
                        userDetaliiPanel = new JPanel(new GridLayout(14,2,5,5));
                        userDetaliiPanel.add(empNumeLabel);
                        userDetaliiPanel.add(empNumeField);
                        userDetaliiPanel.add(empPrenumeLabel);
                        userDetaliiPanel.add(empPrenumeField);
                        userDetaliiPanel.add(empAdresaLabel);
                        userDetaliiPanel.add(empAdresaField);
                        userDetaliiPanel.add(empOrasLabel);
                        userDetaliiPanel.add(empOrasField);
                        userDetaliiPanel.add(empDatNasLabel);
                        userDetaliiPanel.add(empDatNasField);
                        userDetaliiPanel.add(empCNPLabel);
                        userDetaliiPanel.add(empCNPField);
                        userDetaliiPanel.add(empTelLabel);
                        userDetaliiPanel.add(empTelField);
                        userDetaliiPanel.add(empEmailLabel);
                        userDetaliiPanel.add(empEmailField);
                        userDetaliiPanel.add(empFuncLabel);
                        userDetaliiPanel.add(empFuncField);
                        userDetaliiPanel.add(empSalLabel);
                        userDetaliiPanel.add(empSalField);
                        userDetaliiPanel.add(empStoreLabel);
                        userDetaliiPanel.add(empStoreField);
                        userDetaliiPanel.add(empUserLabel);
                        userDetaliiPanel.add(empUserField);
                        userDetaliiPanel.add(empPassLabel);
                        userDetaliiPanel.add(empPassField);
                        userDetaliiPanel.add(empActvLabel);
                        userDetaliiPanel.add(empActivField);
                        userButtonPanel = new JPanel(new GridLayout(1,2,5,5));
                        userButtonPanel.add(save);
                        userButtonPanel.add(exit);
                        userMainPanel = new JPanel(new BorderLayout());
                        userMainPanel.add(userDetaliiPanel, BorderLayout.CENTER);
                        userMainPanel.add(userButtonPanel, BorderLayout.SOUTH);

                        JFrame frame = new JFrame();
                        frame.add(userMainPanel);
                        frame.setVisible(true);
                        frame.setSize(300,480);
                        frame.setTitle("Angajat");
                        frame.setLocationRelativeTo(null);

                        save.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFrame f = new JFrame();
                                int a = JOptionPane.showConfirmDialog(f, "Are you sure?", "Saving", JOptionPane.YES_NO_OPTION);
                                if(a == JOptionPane.YES_OPTION) {
                                    Employee emp = new Employee();
                                    emp.setIdEmp(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
                                    emp.setNume(empNumeField.getText());
                                    emp.setPrenume(empPrenumeField.getText());
                                    emp.setAdresa(empAdresaField.getText());
                                    emp.setOras(empOrasField.getText());
                                    emp.setDatan(LocalDate.parse(empDatNasField.getText()));
                                    emp.setCnp(empCNPField.getText());
                                    emp.setTelefon(empTelField.getText());
                                    emp.setEmail(empEmailField.getText());
                                    emp.setFunctie(empFuncField.getText());
                                    emp.setSalariu(Integer.parseInt(empSalField.getText()));

                                    StoreManager storeMan = new StoreManager();
                                    Store store = storeMan.getStoreByCity(empStoreField.getText());

                                    emp.setActiv((empActivField.getText().equals("Activ")) ? true : false);
                                    empMan.updateEmployee(emp);
                                    Account account = new Account(0, empUserField.getText(), empPassField.getText(), null, 0, emp.getIdEmp());
                                    AccountManager accMan = new AccountManager();
                                    accMan.updateEmployeeAccount(account);
                                }
                            }
                        });

                        exit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                frame.setVisible(false);
                                frame.dispose();
                            }
                        });
                    }
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    firstWindow.setVisible(false);
                    firstWindow.dispose();
                }
            });
        }

        private void newStoreWindow() {
            JLabel newAdresaLabel, newOrasLabel, newTelLabel;
            JTextField newAdresaField, newOrasField, newTelField;
            JButton save, exit;
            JPanel newMainPanel, newButtonPanel, newDetaliiPanel;

            newAdresaLabel = new JLabel("Adresa:");
            newAdresaField = new JTextField();
            newOrasLabel = new JLabel("Oras:");
            newOrasField = new JTextField();
            newTelLabel = new JLabel("Telefon:");
            newTelField = new JTextField();
            save = new JButton("Save");
            exit = new JButton("Exit");

            newDetaliiPanel = new JPanel(new GridLayout(3,2,5,5));
            newDetaliiPanel.add(newAdresaLabel);
            newDetaliiPanel.add(newAdresaField);
            newDetaliiPanel.add(newOrasLabel);
            newDetaliiPanel.add(newOrasField);
            newDetaliiPanel.add(newTelLabel);
            newDetaliiPanel.add(newTelField);
            newButtonPanel = new JPanel(new GridLayout(1,2,5,5));
            newButtonPanel.add(save);
            newButtonPanel.add(exit);
            newMainPanel = new JPanel(new BorderLayout());
            newMainPanel.add(newDetaliiPanel, BorderLayout.CENTER);
            newMainPanel.add(newButtonPanel, BorderLayout.SOUTH);

            JFrame frame = new JFrame();
            frame.add(newMainPanel);
            frame.setVisible(true);
            frame.setSize(300,300);
            frame.setTitle("Magazin nou");
            frame.setLocationRelativeTo(null);

            save.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String adresa = newAdresaField.getText();
                    String oras = newOrasField.getText();
                    String tel = newTelField.getText();

                    if(adresa.equals("") || oras.equals("") || tel.equals("")) {
                        JFrame f = new JFrame();
                        JOptionPane.showMessageDialog(f, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JFrame f = new JFrame();
                        int a = JOptionPane.showConfirmDialog(f, "Are you sure?", "Exiting", JOptionPane.YES_NO_OPTION);
                        if(a == JOptionPane.YES_OPTION) {
                            Store store = new Store(0, adresa, oras, tel);
                            StoreManager storeMan = new StoreManager();
                            storeMan.createStore(store);
                        }
                    }
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    frame.dispose();
                }
            });
        }

        private void editStoreWindow() {
            StoreManager storeMan = new StoreManager();
            ArrayList<Store> stores = storeMan.getStores();
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[]{"ID", "Adresa", "Oras", "Telefon"};
            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < stores.size(); i++) {
                Store store = stores.get(i);
                tableModel.addRow(storeMan.storeToRow(store));
            }

            table.setModel(tableModel);
            JScrollPane scroll = new JScrollPane(table);

            JButton edit, exit;
            JPanel firstWindowPanel, firstWindowButtonsPanel;
            edit = new JButton("Edit");
            exit = new JButton("Exit");
            firstWindowButtonsPanel = new JPanel(new GridLayout(1,2,5,5));
            firstWindowButtonsPanel.add(edit);
            firstWindowButtonsPanel.add(exit);
            firstWindowPanel = new JPanel(new BorderLayout());
            firstWindowPanel.add(scroll, BorderLayout.CENTER);
            firstWindowPanel.add(firstWindowButtonsPanel, BorderLayout.SOUTH);
            JFrame firstWindow = new JFrame();
            firstWindow.add(firstWindowPanel);
            firstWindow.setVisible(true);
            firstWindow.setSize(500,300);
            firstWindow.setTitle("Magazine");
            firstWindow.setLocationRelativeTo(null);

            edit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(table.getSelectionModel().isSelectionEmpty()) {
                        JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "Alegeti mai intai un magazin din lista!", "Eroare", JOptionPane.WARNING_MESSAGE);
                    } else {
                        JLabel magAdresaLabel, magOrasLabel, magTelLabel;
                        JTextField magAdresaField, magOrasField, magTelField;
                        JButton exit, save;

                        magAdresaLabel = new JLabel("Adresa:");
                        magAdresaField = new JTextField();
                        magAdresaField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 1)));
                        magOrasLabel = new JLabel("Oras:");
                        magOrasField = new JTextField();
                        magOrasField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)));
                        magTelLabel = new JLabel("Telefon:");
                        magTelField = new JTextField();
                        magTelField.setText(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)));

                        exit = new JButton("Exit");
                        save = new JButton("Save");

                        JPanel magDetaliiPanel, magMainPanel, magButtonPanel;
                        magDetaliiPanel = new JPanel(new GridLayout(3,2,5,5));
                        magDetaliiPanel.add(magAdresaLabel);
                        magDetaliiPanel.add(magAdresaField);
                        magDetaliiPanel.add(magOrasLabel);
                        magDetaliiPanel.add(magOrasField);
                        magDetaliiPanel.add(magTelLabel);
                        magDetaliiPanel.add(magTelField);
                        magButtonPanel = new JPanel(new GridLayout(1,2,5,5));
                        magButtonPanel.add(save);
                        magButtonPanel.add(exit);
                        magMainPanel = new JPanel(new BorderLayout());
                        magMainPanel.add(magDetaliiPanel, BorderLayout.CENTER);
                        magMainPanel.add(magButtonPanel, BorderLayout.SOUTH);

                        JFrame frame = new JFrame();
                        frame.add(magMainPanel);
                        frame.setVisible(true);
                        frame.setSize(300,300);
                        frame.setTitle("Magazin");
                        frame.setLocationRelativeTo(null);

                        save.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JFrame f = new JFrame();
                                int a = JOptionPane.showConfirmDialog(f, "Are you sure?", "Saving", JOptionPane.YES_NO_OPTION);
                                if(a == JOptionPane.YES_OPTION) {
                                    Store store = new Store();
                                    store.setId(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));
                                    store.setAdresa(magAdresaField.getText());
                                    store.setOras(magOrasField.getText());
                                    store.setTelefon(magTelField.getText());

                                    storeMan.updateStore(store);
                                }
                            }
                        });

                        exit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                frame.setVisible(false);
                                frame.dispose();
                            }
                        });
                    }
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    firstWindow.setVisible(false);
                    firstWindow.dispose();
                }
            });
        }

        private void newOrderWindow() {
            ClientManager clientMan = new ClientManager();
            ArrayList<Client> customers = clientMan.getAllClients();
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[]{"ID", "Nume", "Prenume", "Adresa", "Oras", "Data nasterii", "CNP", "Telefon", "Email"};
            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < customers.size(); i++) {
                Client client = customers.get(i);
                tableModel.addRow(clientMan.clientToRow(client));
            }

            table.setModel(tableModel);
            JScrollPane scroll = new JScrollPane(table);

            JButton order, exit;
            JPanel firstWindowPanel, firstWindowButtonsPanel;
            order = new JButton("New order");
            exit = new JButton("Exit");
            firstWindowButtonsPanel = new JPanel(new GridLayout(1,2,5,5));
            firstWindowButtonsPanel.add(order);
            firstWindowButtonsPanel.add(exit);
            firstWindowPanel = new JPanel(new BorderLayout());
            firstWindowPanel.add(scroll, BorderLayout.CENTER);
            firstWindowPanel.add(firstWindowButtonsPanel, BorderLayout.SOUTH);
            JFrame firstWindow = new JFrame();
            firstWindow.add(firstWindowPanel);
            firstWindow.setVisible(true);
            firstWindow.setSize(1000,300);
            firstWindow.setTitle("Clienti");
            firstWindow.setLocationRelativeTo(null);

            order.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel detalii, butoane, main;
                    JLabel categorie = new JLabel("Categorie:"), id = new JLabel("ID:");
                    JTextField idField = new JTextField();
                    JComboBox categorieBox = new JComboBox(new String[] {"Filme", "Jocuri", "Albume"});
                    JButton order = new JButton("Order"), exit = new JButton("Exit");

                    detalii = new JPanel(new GridLayout(2,2,5,5));
                    detalii.add(categorie);
                    detalii.add(categorieBox);
                    detalii.add(id);
                    detalii.add(idField);
                    butoane = new JPanel(new GridLayout(1,2,5,5));
                    butoane.add(order);
                    butoane.add(exit);
                    main = new JPanel(new BorderLayout());
                    main.add(detalii, BorderLayout.CENTER);
                    main.add(butoane, BorderLayout.SOUTH);

                    JFrame orderFrame = new JFrame();
                    orderFrame.add(main);
                    orderFrame.setVisible(true);
                    orderFrame.setSize(300,200);
                    orderFrame.setLocationRelativeTo(null);

                    order.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(idField.getText().equals("")) {
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Introduceti ID-ul produsului", "Warning", JOptionPane.WARNING_MESSAGE);
                            } else {
                                //TODO: write order registration
//                                Order order = new Order();
//
//                                String res = bazaDate.newOrder(idField.getText(), String.valueOf(table.getValueAt(table.getSelectedRow(), 0)),
//                                        util.getCNP(), categorieBox.getSelectedItem().toString());
//                                JFrame dialog = new JFrame();
//                                JOptionPane.showMessageDialog(dialog, "Imprumutul a fost efectuat cu succes! \n Data scadenta este pe " + res);
                            }
                        }
                    });

                    exit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            orderFrame.setVisible(false);
                            orderFrame.dispose();
                        }
                    });


                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    firstWindow.setVisible(false);
                    firstWindow.dispose();
                }
            });
        }

        private void finishOrderWindow() {
            OrderManager orderMan = new OrderManager();
            StoreManager storeMan = new StoreManager();
            Store store = storeMan.getStoreByCity(employee.getOras());
            ArrayList<Order> orders = orderMan.getStoreActiveOrders(store);
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[]{"Nume", "Prenume", "CNP", "ID produs", "Data imprumutului"};
            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                tableModel.addRow(orderMan.orderToRow(order));
            }

            table.setModel(tableModel);
            JScrollPane scroll = new JScrollPane(table);

            JButton order, exit;
            JPanel firstWindowPanel, firstWindowButtonsPanel;
            order = new JButton("Return order");
            exit = new JButton("Exit");
            firstWindowButtonsPanel = new JPanel(new GridLayout(1,2,5,5));
            firstWindowButtonsPanel.add(order);
            firstWindowButtonsPanel.add(exit);
            firstWindowPanel = new JPanel(new BorderLayout());
            firstWindowPanel.add(scroll, BorderLayout.CENTER);
            firstWindowPanel.add(firstWindowButtonsPanel, BorderLayout.SOUTH);
            JFrame firstWindow = new JFrame();
            firstWindow.add(firstWindowPanel);
            firstWindow.setVisible(true);
            firstWindow.setSize(1000,300);
            firstWindow.setTitle("Imprumuturi");
            firstWindow.setLocationRelativeTo(null);

            order.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(table.getSelectionModel().isSelectionEmpty()) {
                        JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "Alegeti mai intai o comanda din lista!", "Eroare", JOptionPane.WARNING_MESSAGE);
                    } else {
                        Iterator iter = orders.iterator();
                        Order order = null;
                        while (iter.hasNext()) {
                            Order ord = (Order) iter.next();
                            int idProd = ord.getStock().getIdProduct();
                            String cnp = ord.getClient().getCnp();
                            LocalDate dataImp = ord.getBorrowDate();
                            if (idProd == Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 3))) &&
                                    cnp.equals(String.valueOf(table.getValueAt(table.getSelectedRow(), 2))) &&
                                    dataImp.equals(LocalDate.parse(String.valueOf(table.getValueAt(table.getSelectedRow(), 4))))) {
                                order = ord;
                            }
                        }
                        int rez = orderMan.checkInOrder(order.getStock(), order.getClient(), order.getBorrowDate());
                        if (rez <= 7) {
                            ClientManager clientMan = new ClientManager();
                            clientMan.rewardClient(order.getClient());
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Clientul a adus produsul la timp!");
                        } else {
                            ClientManager clientMan = new ClientManager();
                            clientMan.punishClient(order.getClient());
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Atentie! Clientul nu a adus produsul la timp!", "Warning", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                }
            });

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    firstWindow.setVisible(false);
                    firstWindow.dispose();
                }
            });
        }

        private void viewAllOrdersWindow() {
            OrderManager orderMan = new OrderManager();
            ArrayList<Order> orders = new ArrayList<>();
            String[] columns = new String[]{};
            if (priv == 1) {
                orders = orderMan.getAllClientOrders(client);
                columns = new String[]{"Nume", "Prenume", "ID produs", "Data imprumutului", "Data returnarii", "Pret"};
            } else if (priv == 2 || priv == 3) {
                StoreManager storeMan = new StoreManager();
                orders = orderMan.getAllStoreOrders(storeMan.getStoreById(employee.getIdMag()));
                columns = new String[]{"Nume", "Prenume", "CNP", "ID produs", "Data imprumutului", "Data returnarii", "Pret"};
            }
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < orders.size(); i++) {
                Order order = orders.get(i);
                if (priv == 1) {
                    String[] row = new String[6];
                    row[0] = order.getClient().getNume();
                    row[1] = order.getClient().getPrenume();
                    row[2] = order.getStock().getIdProduct() + "";
                    row[3] = order.getBorrowDate().toString();
                    row[4] = (order.getReturnDate() == null) ? "Imprumutat" : order.getReturnDate().toString();
                    row[5] = order.getPrice() + "";
                    tableModel.addRow(row);
                } else {
                    String[] row = new String[7];
                    row[0] = order.getClient().getNume();
                    row[1] = order.getClient().getPrenume();
                    row[2] = order.getClient().getCnp();
                    row[3] = order.getStock().getIdProduct() + "";
                    row[4] = order.getBorrowDate().toString();
                    row[5] = (order.getReturnDate() == null) ? "Imprumutat" : order.getReturnDate().toString();
                    row[6] = order.getPrice() + "";
                    tableModel.addRow(row);
                }
            }

            table.setModel(tableModel);
            JScrollPane scroll = new JScrollPane(table);

            JButton exit;
            JPanel firstWindowPanel;

            exit = new JButton("Exit");
            firstWindowPanel = new JPanel(new BorderLayout());
            firstWindowPanel.add(scroll, BorderLayout.CENTER);
            firstWindowPanel.add(exit, BorderLayout.SOUTH);
            JFrame firstWindow = new JFrame();
            firstWindow.add(firstWindowPanel);
            firstWindow.setVisible(true);
            firstWindow.setSize(1000,300);
            firstWindow.setTitle("Istoric");
            firstWindow.setLocationRelativeTo(null);

            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    firstWindow.setVisible(false);
                    firstWindow.dispose();
                }
            });
        }

        private void viewAccountWindow() {
            if(priv == 1) {
                JLabel nume, prenume, adresa, oras, datan, cnp, tel, email, loialitate;
                JButton exit;

                nume = new JLabel("Nume: " + client.getNume());
                prenume = new JLabel("Prenume: " + client.getPrenume());
                adresa = new JLabel("Adresa: " + client.getAdresa());
                oras = new JLabel("Oras: " + client.getOras());
                datan = new JLabel("Data nasterii: " + client.getDatan());
                cnp = new JLabel("CNP: " + client.getCnp());
                tel = new JLabel("Telefon: " + client.getTel());
                email = new JLabel("Email: " + client.getEmail());
                loialitate = new JLabel("Loialitate: " + client.getLoialitate());
                exit = new JButton("Exit");

                JPanel main, detail;
                detail = new JPanel(new GridLayout(9, 1, 5, 5));
                detail.add(nume);
                detail.add(prenume);
                detail.add(adresa);
                detail.add(oras);
                detail.add(datan);
                detail.add(cnp);
                detail.add(tel);
                detail.add(email);
                detail.add(loialitate);
                main = new JPanel(new BorderLayout());
                main.add(detail, BorderLayout.CENTER);
                main.add(exit, BorderLayout.SOUTH);

                JFrame frame = new JFrame();
                frame.add(main);
                frame.setVisible(true);
                frame.setSize(300,300);
                frame.setLocationRelativeTo(null);
                frame.setTitle("Detalii cont");

                exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.setVisible(false);
                        frame.dispose();
                    }
                });
            } else if(priv != 0) {
                JLabel nume, prenume, adresa, oras, datan, cnp, tel, email, functie;
                JButton exit;

                nume = new JLabel("Nume: " + employee.getNume());
                prenume = new JLabel("Prenume: " + employee.getPrenume());
                adresa = new JLabel("Adresa: " + employee.getAdresa());
                oras = new JLabel("Oras: " + employee.getOras());
                datan = new JLabel("Data nasterii: " + employee.getDatan());
                cnp = new JLabel("CNP: " + employee.getCnp());
                tel = new JLabel("Telefon: " + employee.getTelefon());
                email = new JLabel("Email: " + employee.getEmail());
                functie = new JLabel("Functie: " + employee.getFunctie());
                exit = new JButton("Exit");

                JPanel main, detail;
                detail = new JPanel(new GridLayout(9, 1, 5, 5));
                detail.add(nume);
                detail.add(prenume);
                detail.add(adresa);
                detail.add(oras);
                detail.add(datan);
                detail.add(cnp);
                detail.add(tel);
                detail.add(email);
                detail.add(functie);
                main = new JPanel(new BorderLayout());
                main.add(detail, BorderLayout.CENTER);
                main.add(exit, BorderLayout.SOUTH);

                JFrame frame = new JFrame();
                frame.add(main);
                frame.setVisible(true);
                frame.setSize(300,300);
                frame.setLocationRelativeTo(null);
                frame.setTitle("Detalii cont");

                exit.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.setVisible(false);
                        frame.dispose();
                    }
                });
            }
        }

        //**Utility methods**

        //change panels of window
        private void changePanel(JPanel panel, int sizeX, int sizeY) {
            getContentPane().removeAll();
            getContentPane().add(panel, BorderLayout.CENTER);
            getContentPane().doLayout();
            update(getGraphics());
            setVisible(true);
            setSize(sizeX,sizeY);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        //change contents of the main table based on the arraylist and string inputs
        private JTable initializeTable(ArrayList<Stock> contents) {

            mainTableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[]{"ID", "Titlu", "Actor principal", "Director", "Durata", "Gen", "An", "Audienta", "Pret"};
            mainTableModel.setColumnIdentifiers(columns);

            StockManager stockMan = new StockManager();
            MovieManager movieMan = new MovieManager();

            for (int i = 0; i < contents.size(); i++) {
                Stock stock = contents.get(i);
                Movie movie = stock.getMovie();
                int price = stock.getPrice();

                mainTableModel.addRow(movieMan.movieToRow(movie, price));
            }

            JTable table = new JTable();
            table.setModel(mainTableModel);
            return table;
        }

        private DefaultTableModel updateList(String gen, ArrayList<Stock> contents, String cat) {

            DefaultTableModel model = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns;

            if (cat.equals("Filme")) {
                columns = new String[]{"ID", "Titlu", "Actor principal", "Director", "Durata", "Gen", "An", "Audienta", "Pret"};
                model.setColumnIdentifiers(columns);

                MovieManager movieMan = new MovieManager();

                for (int i = 0; i < contents.size(); i++) {
                    Stock stock = contents.get(i);
                    Movie movie = stock.getMovie();
                    int price = stock.getPrice();

                    if (gen.equals("Toate")) {
                        model.addRow(movieMan.movieToRow(movie, price));
                    } else if (movie.getGenre().equals(gen)) {
                        model.addRow(movieMan.movieToRow(movie, price));
                    }
                }
            } else if(cat.equals("Jocuri")) {
                columns = new String[]{"ID", "Titlu", "Platforma", "Developer", "Publisher", "Gen", "An", "Audienta", "Pret"};
                model.setColumnIdentifiers(columns);

                GameManager gameMan = new GameManager();

                for (int i = 0; i < contents.size(); i++) {
                    Stock stock = contents.get(i);
                    Game game = stock.getGame();
                    int price = stock.getPrice();

                    if (gen.equals("Toate")) {
                        model.addRow(gameMan.gameToRow(game, price));
                    } else if (game.getGenre().equals(gen)) {
                        model.addRow(gameMan.gameToRow(game, price));
                    }
                }
            } else if(cat.equals("Albume")) {
                columns = new String[]{"ID", "Trupa", "Titlu", "Casa discuri", "Nr. Melodii", "Gen", "An", "Pret"};
                model.setColumnIdentifiers(columns);

                AlbumManager albumMan = new AlbumManager();

                for (int i = 0; i < contents.size(); i++) {
                    Stock stock = contents.get(i);
                    Album album = stock.getAlbum();
                    int price = stock.getPrice();

                    if (gen.equals("Toate")) {
                        model.addRow(albumMan.albumToRow(album, price));
                    } else if (album.getGenre().equals(gen)) {
                        model.addRow(albumMan.albumToRow(album, price));
                    }
                }
            } else if(cat.equals("Melodii")) {
                columns = new String[]{"ID", "Nume", "Durata"};
                model.setColumnIdentifiers(columns);

                SongManager songMan = new SongManager();

                for (int i = 0; i < contents.size(); i++) {
                    Stock stock = contents.get(i);
                    Album album = stock.getAlbum();
                    ArrayList<Song> songs = album.getSongs();

                    Iterator iterator = songs.iterator();
                    while (iterator.hasNext()) {
                        model.addRow(songMan.songToRow((Song) iterator.next()));
                    }
                }
            }

            return model;
        }

        private void updateComboBox(String gen) {

            for (int i = 0; i < mainCategoriesList.size(); i++) {
                if (i == mainCategoriesList.size() - 1) {
                    mainCategoryModel.addElement("Toate");
                }
                mainCategoryModel.removeElement(mainCategoriesList.get(i));
            }

            switch (gen) {
                case "Filme":
                    MovieManager movieMan = new MovieManager();
                    mainCategoriesList = movieMan.getGenres();
                    break;
                case "Jocuri":
                    GameManager gameMan = new GameManager();
                    mainCategoriesList = gameMan.getGenres();
                    break;
                case "Muzica":
                    AlbumManager albumManager = new AlbumManager();
                    mainCategoriesList = albumManager.getGenres();
            }

            for (int i = 1; i < mainCategoriesList.size(); i++) {
                mainCategoryModel.addElement(mainCategoriesList.get(i));
            }
        }

        private String[] storeListToBox(ArrayList<Store> string) {
            String[] result = new String[string.size() + 1];
            result[0] = "Toate";

            for (int i = 0; i < string.size(); i++) {
                result[i + 1] = string.get(i).getOras();
            }

            return result;
        }
    }

    public static void main(String[] args) {
        GUI interfata = new GUI();
    }
}
