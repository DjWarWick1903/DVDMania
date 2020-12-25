package dvdmania.windows;

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


public class GUI extends JFrame {

    private static GUI instance = null;

    // constructorul interfetei grafice
    private GUI() {
        super("Login");
        MainPanel();
        LoginPanel();
        NewAccountPanel();
    }

    public static GUI getInstance() {
        if (instance == null) {
            instance = new GUI();
        }

        return instance;
    }

    // variabilele ce tin cont de privilegiile utilizatorului curent
    // 0 - guest
    // 1 - client
    // 2 - vanzator
    // 3 - admin
    static int priv = 0;

    //Cache variabile
    static ImageIcon imagePathLog = new ImageIcon(new ImageIcon("logo.png").getImage().getScaledInstance(120, 130, Image.SCALE_DEFAULT));
    static JLabel imagine = new JLabel(imagePathLog);
    static Account account = null;
    static Employee employee = null;
    static Client client = null;
    static String categorieBoxValue;
    static String magazinBoxValue;
    static boolean canModify = true;

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
            viewAllOrders, viewAccountDetails, viewOrdersChart, viewProductChart, exportOrders, exportStock;
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

                AccountManager accMan = AccountManager.getInstance();
                account = new Account();
                account.setUsername(usrname);
                account.setPassword(password);

                if (accMan.checkAccountExists(account)) {
                    account = accMan.getAccount(usrname, password);
                    priv = accMan.checkAccountPrivilege(account);

                    if (priv == 1) {
                        ClientManager clientMan = ClientManager.getInstance();
                        client = clientMan.getClientById(account.getIdUtil());
                    } else if (priv == 2 || priv == 3) {
                        EmployeeManager empMan = EmployeeManager.getInstance();
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

        //dvdmania.tools.Main Panel
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

                    ClientManager clientMan = ClientManager.getInstance();
                    AccountManager accMan = AccountManager.getInstance();

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
        exportOrders = new JMenuItem("Export order");
        exportStock = new JMenuItem("Export stock");
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
        viewOrdersChart = new JMenuItem("Order chart");
        viewProductChart = new JMenuItem("Product chart");

        //Assign menus
        fileMenu.add(newOrder);
        fileMenu.add(finishOrder);
        fileMenu.add(exportOrders);
        fileMenu.add(exportStock);
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
        viewMenu.add(viewOrdersChart);
        viewMenu.add(viewProductChart);

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
                changePanel(logMainPanel, loginSize[0], loginSize[1]);
                employee = null;
                client = null;
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
                if (priv == 0 || priv == 1) {
                    JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    NewProductWindow.getInstance();
                }
            }
        });

        exportOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priv == 0 || priv == 1) {
                    JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    ExportOrdersWindow exportWindow = ExportOrdersWindow.getInstance();
                }
            }
        });

        exportStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priv == 0 || priv == 1) {
                    JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    ExportStockWindow stockWindow = ExportStockWindow.getInstance();
                }
            }
        });

        editProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priv == 0 || priv == 1) {
                    JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    EditProductWindow.getInstance();
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
                    NewCustomerWindow.getInstance();
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
                    EditCustomerWindow.getInstance();
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

        viewOrdersChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priv == 0 || priv == 1) {
                    JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    SaleChartWindow.getInstance();
                }
            }
        });

        viewProductChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priv == 0 || priv == 1) {
                    JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    ProductChartWindow.getInstance();
                }
            }
        });

    }

    private void MainPanel() {
        //Button panel
        mainFilmeButton = new JButton("Filme");
        mainJocuriButton = new JButton("Jocuri");
        mainAlbumeButton = new JButton("Albume");

        MovieManager movieMan = MovieManager.getInstance();
        StoreManager storeMan = StoreManager.getInstance();
        SongManager songMan = SongManager.getInstance();
        StockManager stockMan = StockManager.getInstance();

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
        categorieBoxValue = "Filme";
        magazinBoxValue = "Toate";
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

        //dvdmania.tools.Main Panel
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
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1 && categorieBoxValue.equals("Albume")) {
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
                if (canModify) {
                    try {
                        String selectedGen = mainCategoryBox.getSelectedItem().toString();
                        mainProdTable.setModel(updateList(selectedGen, mainProduseList, categorieBoxValue));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        mainStoreBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (canModify) {
                    try {
                        StoreManager storeMan = StoreManager.getInstance();
                        StockManager stockMan = StockManager.getInstance();

                        String selectedStore = mainStoreBox.getSelectedItem().toString();
                        switch (categorieBoxValue) {
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
                        mainProdTable.setModel(updateList(mainCategoryBox.getSelectedItem().toString(), mainProduseList, categorieBoxValue));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        mainFilmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!categorieBoxValue.equals("Filme")) {
                    canModify = false;
                    categorieBoxValue = "Filme";
                    updateComboBox("Filme");
                    mainCategoryBox.setSelectedItem("Toate");
                    mainStoreBox.setSelectedItem("Toate");
                    StockManager stockMan = StockManager.getInstance();
                    mainProduseList = stockMan.getAllMovieStock();
                    mainProdTable.setModel(updateList("Toate", mainProduseList, categorieBoxValue));
                    canModify = true;
                }
            }
        });

        mainJocuriButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!categorieBoxValue.equals("Jocuri")) {
                    canModify = false;
                    categorieBoxValue = "Jocuri";
                    updateComboBox(categorieBoxValue);
                    mainCategoryBox.setSelectedItem("Toate");
                    mainStoreBox.setSelectedItem("Toate");
                    StockManager stockMan = StockManager.getInstance();
                    mainProduseList = stockMan.getAllGameStock();
                    mainProdTable.setModel(updateList("Toate", mainProduseList, categorieBoxValue));
                    canModify = true;
                }
            }
        });

        mainAlbumeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!categorieBoxValue.equals("Albume")) {
                    canModify = false;
                    categorieBoxValue = "Albume";
                    updateComboBox("Albume");
                    mainCategoryBox.setSelectedItem("Toate");
                    mainStoreBox.setSelectedItem("Toate");
                    StockManager stockMan = StockManager.getInstance();
                    mainProduseList = stockMan.getAllAlbumStock();
                    mainProdTable.setModel(updateList("Toate", mainProduseList, categorieBoxValue));
                    canModify = true;
                }
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
                        StockManager stockMan = StockManager.getInstance();
                        StoreManager storeMan = StoreManager.getInstance();
                        Store store = null;
                        switch (categorieBoxValue) {
                            case "Filme":
                                MovieManager movieMan = MovieManager.getInstance();

                                Movie movie = movieMan.getMovieById(id);
                                if (priv == 1) {
                                    store = storeMan.getStoreByCity(client.getOras());
                                } else if (priv == 2 || priv == 3) {
                                    store = storeMan.getStoreByCity(employee.getOras());
                                }
                                nrTot = stockMan.checkMovieStock(movie, store);
                                break;
                            case "Jocuri":
                                GameManager gameMan = GameManager.getInstance();

                                Game game = gameMan.getGameById(id);
                                if (priv == 1) {
                                    store = storeMan.getStoreByCity(client.getOras());
                                } else if (priv == 2 || priv == 3) {
                                    store = storeMan.getStoreByCity(employee.getOras());
                                }
                                nrTot = stockMan.checkGameStock(game, store);
                                break;
                            case "Albume":
                                AlbumManager albumMan = AlbumManager.getInstance();

                                Album album = albumMan.getAlbumById(id);
                                if (priv == 1) {
                                    store = storeMan.getStoreByCity(client.getOras());
                                } else if (priv == 2 || priv == 3) {
                                    store = storeMan.getStoreByCity(employee.getOras());
                                }
                                nrTot = stockMan.checkAlbumStock(album, store);
                        }

                        if (nrTot > 0) {
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Vesti bune! \n Mai sunt " + nrTot + " copii ale acestui produs in magazin!");
                        } else if (nrTot == -1) {
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Ne pare rau, dar acest produs nu exista in magazinul orasului dumneavoastra!");
                        } else if (nrTot == 0) {
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
                    switch (categorieBoxValue) {
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
                        mainProdTable.setModel(updateList("Toate", newProduseList, categorieBoxValue));
                    }
                }
            }
        });
    }

    protected static Employee getLoggedEmployee() {
        return employee;
    }

    //**Menu Windows**

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
        StoreManager storeMan = StoreManager.getInstance();
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

        //dvdmania.tools.Main Panel
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
                    String birthdate = year + "-" + month + "-" + day;

                    EmployeeManager empMan = EmployeeManager.getInstance();
                    AccountManager accMan = AccountManager.getInstance();
                    StoreManager storeMan = StoreManager.getInstance();
                    Store store = storeMan.getStoreByCity(mag);

                    Employee emp = new Employee(0, lastName, firstName, address, city, LocalDate.parse(birthdate), cnp, phone, email, functie, Integer.parseInt(salariu), true, store.getId());
                    empMan.createEmployee(emp);
                    Account acc = new Account(0, username, password, null, 2, emp.getIdEmp());
                    accMan.createEmployeeAccount(acc);

                    JFrame confirmDialog = new JFrame();
                    JOptionPane.showMessageDialog(confirmDialog, "Your account has successfully been created!");

//                        changePanel(mainMainPanel, size[0], size[1]);
//                        MenuBar();
//                        setTitle("DVDMania");
                }
            }
        });
    }

    private void editEmployeeWindow() {
        EmployeeManager empMan = EmployeeManager.getInstance();
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

                                StoreManager storeMan = StoreManager.getInstance();
                                Store store = storeMan.getStoreByCity(empStoreField.getText());

                                emp.setActiv((empActivField.getText().equals("Activ")) ? true : false);
                                empMan.updateEmployee(emp);
                                Account account = new Account(0, empUserField.getText(), empPassField.getText(), null, 0, emp.getIdEmp());
                                AccountManager accMan = AccountManager.getInstance();
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
                        StoreManager storeMan = StoreManager.getInstance();
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
        StoreManager storeMan = StoreManager.getInstance();
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
        ClientManager clientMan = ClientManager.getInstance();
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
                            String cat = categorieBox.getSelectedItem().toString();
                            StockManager stockMan = StockManager.getInstance();
                            StoreManager storeMan = StoreManager.getInstance();
                            Store store = storeMan.getStoreByEmployee(employee);
                            switch (cat) {
                                case "Filme":
                                    MovieManager movieMan = MovieManager.getInstance();
                                    Movie movie = movieMan.getMovieById(Integer.parseInt(idField.getText()));
                                    if (movie != null) {
                                        int nrFilme = stockMan.checkMovieStock(movie, store);
                                        if (nrFilme > 0) {
                                            OrderManager orderMan = OrderManager.getInstance();

                                            ClientManager clientMan = ClientManager.getInstance();
                                            Client client = clientMan.getClientById(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));

                                            if (client != null) {
                                                LocalDate dataRet = orderMan.checkOutMovieOrder(movie, client, employee);
                                                JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul a fost inchiriat! Aceste trebuie returnat pana in data de " + dataRet, "Succes", JOptionPane.WARNING_MESSAGE);
                                            } else {
                                                JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul nu a fost inchiriat deoarece a aparut o problema. Incercati din nou!", "Warning", JOptionPane.WARNING_MESSAGE);
                                            }
                                        } else {
                                            JFrame dialog = new JFrame();
                                            JOptionPane.showMessageDialog(dialog, "Acest produs nu mai este in stoc.", "Warning", JOptionPane.WARNING_MESSAGE);
                                        }
                                    } else {
                                        JFrame dialog = new JFrame();
                                        JOptionPane.showMessageDialog(dialog, "Acest produs nu exista in baza de date!", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                    break;

                                case "Jocuri":
                                    GameManager gameMan = GameManager.getInstance();
                                    Game game = gameMan.getGameById(Integer.parseInt(idField.getText()));
                                    if (game != null) {
                                        int nrJocuri = stockMan.checkGameStock(game, store);
                                        if (nrJocuri > 0) {
                                            OrderManager orderMan = OrderManager.getInstance();

                                            ClientManager clientMan = ClientManager.getInstance();
                                            Client client = clientMan.getClientById(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));

                                            if (client != null) {
                                                LocalDate dataRet = orderMan.checkOutGameOrder(game, client, employee);
                                                JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul a fost inchiriat! Aceste trebuie returnat pana in data de " + dataRet, "Succes", JOptionPane.WARNING_MESSAGE);
                                            } else {
                                                JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul nu a fost inchiriat deoarece a aparut o problema. Incercati din nou!", "Warning", JOptionPane.WARNING_MESSAGE);
                                            }
                                        } else {
                                            JFrame dialog = new JFrame();
                                            JOptionPane.showMessageDialog(dialog, "Acest produs nu mai este in stoc.", "Warning", JOptionPane.WARNING_MESSAGE);
                                        }
                                    } else {
                                        JFrame dialog = new JFrame();
                                        JOptionPane.showMessageDialog(dialog, "Acest produs nu exista in baza de date!", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                                    break;
                                case "Albume":
                                    AlbumManager albumManager = AlbumManager.getInstance();
                                    Album album = albumManager.getAlbumById(Integer.parseInt(idField.getText()));
                                    if (album != null) {
                                        int nrAlbume = stockMan.checkAlbumStock(album, store);
                                        if (nrAlbume > 0) {
                                            OrderManager orderMan = OrderManager.getInstance();

                                            ClientManager clientMan = ClientManager.getInstance();
                                            Client client = clientMan.getClientById(Integer.parseInt(String.valueOf(table.getValueAt(table.getSelectedRow(), 0))));

                                            if (client != null) {
                                                LocalDate dataRet = orderMan.checkOutAlbumOrder(album, client, employee);
                                                JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul a fost inchiriat! Aceste trebuie returnat pana in data de " + dataRet, "Succes", JOptionPane.WARNING_MESSAGE);
                                            } else {
                                                JFrame dialog = new JFrame();
                                                JOptionPane.showMessageDialog(dialog, "Produsul nu a fost inchiriat deoarece a aparut o problema. Incercati din nou!", "Warning", JOptionPane.WARNING_MESSAGE);
                                            }
                                        } else {
                                            JFrame dialog = new JFrame();
                                            JOptionPane.showMessageDialog(dialog, "Acest produs nu mai este in stoc.", "Warning", JOptionPane.WARNING_MESSAGE);
                                        }
                                    } else {
                                        JFrame dialog = new JFrame();
                                        JOptionPane.showMessageDialog(dialog, "Acest produs nu exista in baza de date!", "Warning", JOptionPane.WARNING_MESSAGE);
                                    }
                            }
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
        OrderManager orderMan = OrderManager.getInstance();
        StoreManager storeMan = StoreManager.getInstance();
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
                        ClientManager clientMan = ClientManager.getInstance();
                        clientMan.rewardClient(order.getClient());
                        JFrame dialog = new JFrame();
                        JOptionPane.showMessageDialog(dialog, "Clientul a adus produsul la timp!");
                    } else {
                        ClientManager clientMan = ClientManager.getInstance();
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
        OrderManager orderMan = OrderManager.getInstance();
        ArrayList<Order> orders = new ArrayList<>();
        String[] columns = new String[]{};
        if (priv == 1) {
            orders = orderMan.getAllClientOrders(client);
            columns = new String[]{"Nume", "Prenume", "ID produs", "Data imprumutului", "Data returnarii", "Pret"};
        } else if (priv == 2 || priv == 3) {
            StoreManager storeMan = StoreManager.getInstance();
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
        } else {
            JFrame dialog = new JFrame();
            JOptionPane.showMessageDialog(dialog, "Sunteti logat ca si musafir. Creati mai intai un cont personal!", "Warning", JOptionPane.WARNING_MESSAGE);
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

        MovieManager movieMan = MovieManager.getInstance();

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

        Iterator iter = contents.iterator();
        while (iter.hasNext()) {
            Stock stock = (Stock) iter.next();
        }
        DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] columns;

        if (cat.equals("Filme")) {
            columns = new String[]{"ID", "Titlu", "Actor principal", "Director", "Durata", "Gen", "An", "Audienta", "Pret"};
            model.setColumnIdentifiers(columns);

            MovieManager movieMan = MovieManager.getInstance();

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

            GameManager gameMan = GameManager.getInstance();

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

            AlbumManager albumMan = AlbumManager.getInstance();

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

            SongManager songMan = SongManager.getInstance();

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
                MovieManager movieMan = MovieManager.getInstance();
                mainCategoriesList = movieMan.getGenres();
                break;
            case "Jocuri":
                GameManager gameMan = GameManager.getInstance();
                mainCategoriesList = gameMan.getGenres();
                break;
            case "Albume":
                AlbumManager albumManager = AlbumManager.getInstance();
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

