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
    final static ImageIcon imagePathLog = new ImageIcon(new ImageIcon("logo.png").getImage().getScaledInstance(120, 130, Image.SCALE_DEFAULT));
    final static JLabel imagine = new JLabel(imagePathLog);
    static Account account = null;
    static Employee employee = null;
    static Client client = null;
    static String categorieBoxValue;
    static String magazinBoxValue;
    static boolean canModify = true;

    //componentele ferestrei de login
    final static int[] loginWindowSize = {350, 280};
    static JPanel logMainPanel = null;

    //componentele ferestrei de creare cont
    static JPanel newMainPanel = null;

    //componentele ferestrei principale
    final static int[] size = {1200, 800};
    static JPanel mainMainPanel;
    static DefaultComboBoxModel mainCategoryModel;
    static JComboBox mainCategoryBox, mainStoreBox;
    static JTable mainProdTable;
    static ArrayList<String> mainCategoriesList;
    static ArrayList<Stock> mainProduseList;
    static ArrayList<Store> mainStoresList;

    private void LoginPanel() {

        //Username
        final JLabel logUserLabel = new JLabel();
        logUserLabel.setText("Username: ");
        final JTextField logUsernameText = new JTextField();

        //Password
        final JLabel logPasswordLabel = new JLabel();
        logPasswordLabel.setText("Password: ");
        final JPasswordField logPasswordText = new JPasswordField();

        final JPanel logDataPanel = new JPanel(new GridLayout(2, 2, 2, 2));
        logDataPanel.setSize(400, 400);
        logDataPanel.add(logUserLabel);
        logDataPanel.add(logUsernameText);
        logDataPanel.add(logPasswordLabel);
        logDataPanel.add(logPasswordText);

        //Buttons
        final JButton logNewButton = new JButton("New account");
        final JButton logGuestButton = new JButton("Guest");
        final JButton logSubmitButton = new JButton("Submit");
        final JButton logExitButton = new JButton("Exit");
        final JPanel logButtonPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        logButtonPanel.add(logNewButton);
        logButtonPanel.add(logGuestButton);
        logButtonPanel.add(logSubmitButton);
        logButtonPanel.add(logExitButton);

        //Introducerea componentelor in fereastra
        logMainPanel = new JPanel(new BorderLayout());
        logMainPanel.add(imagine, BorderLayout.NORTH);
        logMainPanel.add(logDataPanel, BorderLayout.CENTER);
        logMainPanel.add(logButtonPanel, BorderLayout.SOUTH);

        changePanel(logMainPanel, loginWindowSize[0], loginWindowSize[1]);

        logSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String usrname = logUsernameText.getText();
                final String password = String.valueOf(logPasswordText.getPassword());

                final AccountManager accMan = AccountManager.getInstance();
                account = new Account();
                account.setUsername(usrname);
                account.setPassword(password);

                if (accMan.checkAccountExists(account)) {
                    account = accMan.getAccount(usrname, password);
                    priv = accMan.checkAccountPrivilege(account);

                    if (priv == 1) {
                        final ClientManager clientMan = ClientManager.getInstance();
                        client = clientMan.getClientById(account.getIdUtil());
                    } else if (priv == 2 || priv == 3) {
                        final EmployeeManager empMan = EmployeeManager.getInstance();
                        employee = empMan.getEmployeeById(account.getIdUtil());
                    }

                    setVisible(false);
                    dispose();
                    final JFrame welcomeDialog = new JFrame();
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
                    final JFrame warningDialog = new JFrame();
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
                final JFrame frame = new JFrame();
                JOptionPane.showMessageDialog(frame, "You have entered as a guest. All you will be able to do \nis see the list of products of every store!", "Guest", JOptionPane.INFORMATION_MESSAGE);

                priv = 0;

                setVisible(false);
                dispose();
                changePanel(mainMainPanel, size[0], size[1]);
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
        final JLabel newUserLabel = new JLabel();
        newUserLabel.setText("Username: ");
        final JTextField newUsernameText = new JTextField();

        //Password
        final JLabel newPasswordLabel = new JLabel();
        newPasswordLabel.setText("Password: ");
        final JPasswordField newPasswordText = new JPasswordField();
        final JLabel newConfirmPasswordLabel = new JLabel();
        newConfirmPasswordLabel.setText("Confirm password: ");
        final JPasswordField newConfirmPasswordText = new JPasswordField();

        //Email
        final JLabel newEmailLabel = new JLabel();
        newEmailLabel.setText("Email: ");
        final JTextField newEmailText = new JTextField();

        //First Name
        final JLabel newNameLabel = new JLabel();
        newNameLabel.setText("First Name: ");
        final JTextField newNameText = new JTextField();

        //Last Name
        final JLabel newPrenLabel = new JLabel();
        newPrenLabel.setText("Last Name: ");
        final JTextField newPrenText = new JTextField();

        //Address
        final JLabel newAddressLabel = new JLabel();
        newAddressLabel.setText("Address: ");
        final JTextField newAddressText = new JTextField();

        //City
        final JLabel newCityLabel = new JLabel();
        newCityLabel.setText("City: ");
        final JTextField newCityText = new JTextField();

        //CNP
        final JLabel newCNPLabel = new JLabel();
        newCNPLabel.setText("CNP: ");
        final JTextField newCNPText = new JTextField();

        //Phone
        final JLabel newTelLabel = new JLabel();
        newTelLabel.setText("Phone: ");
        final JTextField newTelText = new JTextField();

        //Birthday
        final JLabel newBirthYearLabel = new JLabel("Year(yyyy)");
        final JLabel newBirthMonthLabel = new JLabel("Month(mm)");
        final JLabel newBirthDayLabel = new JLabel("Day(dd)");
        final JTextField newBirthYearText = new JTextField();
        final JTextField newBirthMonthText = new JTextField();
        final JTextField newBirthDayText = new JTextField();

        //Data panel
        final JPanel newDataPanel = new JPanel(new GridLayout(9, 2, 5, 5));
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
        final JPanel newBirthPanel = new JPanel(new GridLayout(2, 3, 5, 5));
        newBirthPanel.add(newBirthYearLabel);
        newBirthPanel.add(newBirthMonthLabel);
        newBirthPanel.add(newBirthDayLabel);
        newBirthPanel.add(newBirthYearText);
        newBirthPanel.add(newBirthMonthText);
        newBirthPanel.add(newBirthDayText);

        //Button Panel
        final JPanel newButtonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        final JButton newCreateButton = new JButton("Create");
        final JButton newCancelButton = new JButton("Cancel");
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
                final JFrame frame = new JFrame();
                final int dialogResponse = JOptionPane.showConfirmDialog(frame, "Are you sure?", "Exiting", JOptionPane.YES_NO_OPTION);
                if (dialogResponse == JOptionPane.YES_OPTION) {
                    changePanel(logMainPanel, loginWindowSize[0], loginWindowSize[1]);
                }
            }
        });

        newCreateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final String username = newUsernameText.getText();
                final String password = String.valueOf(newPasswordText.getPassword());
                final String email = newEmailText.getText();
                final String firstName = newNameText.getText();
                final String lastName = newPrenText.getText();
                final String address = newAddressText.getText();
                final String city = newCityText.getText();
                final String cnp = newCNPText.getText();
                final String phone = newTelText.getText();

                final String year = newBirthYearText.getText();
                final String month = newBirthMonthText.getText();
                final String day = newBirthDayText.getText();

                boolean isValid = username.equals("") || password.equals("") || email.equals("") || firstName.equals("") || lastName.equals("") || address.equals("") || city.equals("")
                        || cnp.equals("") || phone.equals("") || year.equals("") || month.equals("") || day.equals("");

                if (isValid) {
                    final JFrame warningDialog = new JFrame();
                    JOptionPane.showMessageDialog(warningDialog, "You must complete all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final String birthdate = new String(year + "-" + month + "-" + day);
                    final LocalDate date = LocalDate.parse(birthdate);

                    client = new Client(0, lastName, firstName, address, city, date, cnp, phone, email, 5);
                    priv = 1;

                    final ClientManager clientMan = ClientManager.getInstance();
                    final AccountManager accMan = AccountManager.getInstance();

                    final int clientInserted = clientMan.createClient(client);
                    account = new Account(0, username, password, null, 1, client.getId());
                    final int accountInserted = accMan.createClientAccount(account);

                    if (clientInserted != 0 && accountInserted != 0) {
                        final JFrame confirmDialog = new JFrame();
                        JOptionPane.showMessageDialog(confirmDialog, "Your account has successfully been created!");

                        changePanel(mainMainPanel, size[0], size[1]);
                        MenuBar();
                        setTitle("DVDMania");
                    } else {
                        final JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "There was a problem in creating your profile.", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });

    }

    private void MenuBar() {
        //Menu Bar
        final JMenuBar mb = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        final JMenu editMenu = new JMenu("Edit");
        final JMenu viewMenu = new JMenu("View");

        //File menu items
        final JMenuItem newOrder = new JMenuItem("New order");
        final JMenuItem finishOrder = new JMenuItem("Return order");
        final JMenuItem logout = new JMenuItem("Logout");
        final JMenuItem exportOrders = new JMenuItem("Export order");
        final JMenuItem exportStock = new JMenuItem("Export stock");
        final JMenuItem exit = new JMenuItem("Exit");

        //Edit menu items
        final JMenuItem newCustomer = new JMenuItem("New customer");
        final JMenuItem editCustomer = new JMenuItem("Edit customer");
        final JMenuItem newEmployee = new JMenuItem("New employee");
        final JMenuItem editEmployee = new JMenuItem("Edit employee");
        final JMenuItem newProduct = new JMenuItem("New product");
        final JMenuItem editProduct = new JMenuItem("Edit product");
        final JMenuItem newStore = new JMenuItem("New store");
        final JMenuItem editStore = new JMenuItem("Edit store");

        //View menu items
        final JMenuItem viewAllOrders = new JMenuItem("View all orders");
        final JMenuItem viewAccountDetails = new JMenuItem("View account details");
        final JMenuItem viewOrdersChart = new JMenuItem("Order chart");
        final JMenuItem viewProductChart = new JMenuItem("Product chart");

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
                changePanel(logMainPanel, loginWindowSize[0], loginWindowSize[1]);
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
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final NewProductWindow window = new NewProductWindow();
                }
            }
        });

        exportOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priv == 0 || priv == 1) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final ExportOrdersWindow exportWindow = new ExportOrdersWindow();
                }
            }
        });

        exportStock.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priv == 0 || priv == 1) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final ExportStockWindow stockWindow = new ExportStockWindow();
                }
            }
        });

        editProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priv == 0 || priv == 1) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final EditProductWindow window = new EditProductWindow();
                }
            }
        });

        newCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(priv == 0 || priv == 1) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final NewCustomerWindow window = new NewCustomerWindow();
                }
            }
        });

        editCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(priv == 0 || priv == 1) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final EditCustomerWindow window = new EditCustomerWindow();
                }
            }
        });

        newEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(priv != 3) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final NewEmployeeWindow window = new NewEmployeeWindow();
                }
            }
        });

        editEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(priv != 3) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final EditEmployeeWindow window = new EditEmployeeWindow();
                }
            }
        });

        newStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(priv != 3) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final NewStoreWindow window = new NewStoreWindow();
                }
            }
        });

        editStore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(priv != 3) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final EditStoreWindow window = new EditStoreWindow();
                }
            }
        });

        newOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(priv == 0 || priv == 1) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final NewOrderWindow window = new NewOrderWindow();
                }
            }
        });

        finishOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(priv == 0 || priv == 1) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final FinishOrderWindow window = new FinishOrderWindow();
                }
            }
        });

        viewAllOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(priv == 0) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final ViewAllOrdersWindow window = new ViewAllOrdersWindow();
                }
            }
        });

        viewAccountDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final ViewAccountWindow window = new ViewAccountWindow();
            }
        });

        viewOrdersChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priv == 0 || priv == 1) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final SaleChartWindow window = new SaleChartWindow();
                }
            }
        });

        viewProductChart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (priv == 0 || priv == 1) {
                    final JFrame dialog = new JFrame();
                    JOptionPane.showMessageDialog(dialog, "You do not have permission to use this function!", "Warning", JOptionPane.WARNING_MESSAGE);
                } else {
                    final ProductChartWindow window = new ProductChartWindow();
                }
            }
        });

    }

    private void MainPanel() {
        //Button panel
        final JButton mainFilmeButton = new JButton("Filme");
        final JButton mainJocuriButton = new JButton("Jocuri");
        final JButton mainAlbumeButton = new JButton("Albume");

        final MovieManager movieMan = MovieManager.getInstance();
        final StoreManager storeMan = StoreManager.getInstance();
        final SongManager songMan = SongManager.getInstance();
        final StockManager stockMan = StockManager.getInstance();

        mainCategoriesList = movieMan.getGenres();
        mainStoresList = storeMan.getStores();
        mainCategoryModel = new DefaultComboBoxModel(mainCategoriesList.toArray());
        mainCategoryBox = new JComboBox(mainCategoryModel);
        mainStoreBox = new JComboBox(storeListToBox(mainStoresList));
        mainFilmeButton.setBorder(BorderFactory.createTitledBorder("Categorii:"));
        mainCategoryBox.setBorder(BorderFactory.createTitledBorder("Gen:"));
        mainStoreBox.setBorder(BorderFactory.createTitledBorder("Magazine:"));

        final JPanel mainButtonsPanel = new JPanel();
        mainButtonsPanel.setLayout(new GridLayout(5, 1));
        mainButtonsPanel.setPreferredSize(new Dimension(120, 600));
        mainButtonsPanel.add(mainFilmeButton);
        mainButtonsPanel.add(mainJocuriButton);
        mainButtonsPanel.add(mainAlbumeButton);
        mainButtonsPanel.add(mainCategoryBox);
        mainButtonsPanel.add(mainStoreBox);

        //Search and table panel
        final JTextField mainSearchField = new JTextField("Cautare");
        categorieBoxValue = "Filme";
        magazinBoxValue = "Toate";
        mainProduseList = stockMan.getAllMovieStock();
        mainProdTable = initializeTable(mainProduseList);
        final JScrollPane mainScrollPane = new JScrollPane(mainProdTable);
        final JButton mainSearchButton = new JButton("Cauta");
        final JButton mainCheckButton = new JButton("Verifica valabilitatea");
        mainCategoriesList = movieMan.getGenres();

        final JPanel mainSearchPanel = new JPanel();
        mainSearchPanel.setLayout(new BorderLayout());
        final JPanel mainSearchPanelNorth = new JPanel();
        mainSearchPanelNorth.setLayout(new GridLayout(1, 2));
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
                final JTable table = (JTable) e.getSource();
                final Point point = e.getPoint();
                final int row = table.rowAtPoint(point);
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1 && categorieBoxValue.equals("Albume")) {
                    final int id = Integer.parseInt(String.valueOf(mainProdTable.getValueAt(mainProdTable.getSelectedRow(), 0)));
                    final String titlu = new String(mainProdTable.getValueAt(mainProdTable.getSelectedRow(), 2) + " - " + mainProdTable.getValueAt(mainProdTable.getSelectedRow(), 1));
                    final ArrayList<Song> songs = songMan.getSongs(id);
                    final JFrame songsWindow = new JFrame();

                    final DefaultTableModel songModel = new DefaultTableModel() {
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };

                    final String[] columns = {"Titlu", "Durata"};
                    songModel.setColumnIdentifiers(columns);

                    for(int i = 0; i < songs.size(); i++) {
                        final String[] song = {songs.get(i).getNume(), "" + songs.get(i).getDuration()};
                        songModel.addRow(song);
                    }

                    final JTable list = new JTable();
                    list.setModel(songModel);
                    final JScrollPane scroll = new JScrollPane(list);

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
                        final String selectedGen = mainCategoryBox.getSelectedItem().toString();
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
                        final String selectedStore = mainStoreBox.getSelectedItem().toString();
                        switch (categorieBoxValue) {
                            case "Filme":
                                if (!selectedStore.equals("Toate")) {
                                    final Store store = storeMan.getStoreByCity(selectedStore);
                                    mainProduseList = stockMan.getAllMovieStock(store);
                                } else {
                                    mainProduseList = stockMan.getAllMovieStock();
                                }
                                break;
                            case "Jocuri":
                                if (!selectedStore.equals("Toate")) {
                                    final Store store = storeMan.getStoreByCity(selectedStore);
                                    mainProduseList = stockMan.getAllGameStock(store);
                                } else {
                                    mainProduseList = stockMan.getAllGameStock();
                                }
                                break;
                            case "Albume":
                                if (!selectedStore.equals("Toate")) {
                                    final Store store = storeMan.getStoreByCity(selectedStore);
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
                        final JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "Alegeti mai intai un produs din lista!", "Eroare", JOptionPane.WARNING_MESSAGE);
                    } else {
                        final int id = Integer.parseInt(String.valueOf(mainProdTable.getValueAt(mainProdTable.getSelectedRow(), 0)));
                        int nrTot = 0;
                        Store store = null;
                        switch (categorieBoxValue) {
                            case "Filme":
                                final Movie movie = movieMan.getMovieById(id);
                                if (priv == 1) {
                                    store = storeMan.getStoreByCity(client.getOras());
                                } else if (priv == 2 || priv == 3) {
                                    store = storeMan.getStoreByCity(employee.getOras());
                                }
                                nrTot = stockMan.checkMovieStock(movie, store);
                                break;
                            case "Jocuri":
                                final GameManager gameMan = GameManager.getInstance();

                                final Game game = gameMan.getGameById(id);
                                if (priv == 1) {
                                    store = storeMan.getStoreByCity(client.getOras());
                                } else if (priv == 2 || priv == 3) {
                                    store = storeMan.getStoreByCity(employee.getOras());
                                }
                                nrTot = stockMan.checkGameStock(game, store);
                                break;
                            case "Albume":
                                final AlbumManager albumMan = AlbumManager.getInstance();

                                final Album album = albumMan.getAlbumById(id);
                                if (priv == 1) {
                                    store = storeMan.getStoreByCity(client.getOras());
                                } else if (priv == 2 || priv == 3) {
                                    store = storeMan.getStoreByCity(employee.getOras());
                                }
                                nrTot = stockMan.checkAlbumStock(album, store);
                        }

                        if (nrTot > 0) {
                            final JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Vesti bune! \n Mai sunt " + nrTot + " copii ale acestui produs in magazin!");
                        } else if (nrTot == -1) {
                            final JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Ne pare rau, dar acest produs nu exista in magazinul orasului dumneavoastra!");
                        } else if (nrTot == 0) {
                            final JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Ne pare rau, dar in acest moment nu mai exista nici o copie disponibila in magazin!");
                        }
                    }
                } else {
                    final JFrame warningDialog = new JFrame();
                    JOptionPane.showMessageDialog(warningDialog, "Este nevoie sa va logati pentru a accesa aceasta functie.", "Eroare", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        mainSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainCategoryBox.setSelectedItem("Toate");
                mainStoreBox.setSelectedItem("Toate");
                final ArrayList<Stock> newProduseList = new ArrayList<>();

                if(!mainSearchField.equals("Cautare")) {
                    final String toSearch = mainSearchField.getText();
                    Stock stock = null;
                    switch (categorieBoxValue) {
                        case "Filme":
                            for (int i = 0; i < mainProduseList.size(); i++) {
                                stock = mainProduseList.get(i);
                                final Movie movie = stock.getMovie();

                                final String title = movie.getTitle();
                                final String actor = movie.getMainActor();
                                final String director = movie.getDirector();

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
                                final Game game = stock.getGame();

                                final String title = game.getTitle();
                                final String platform = game.getPlatform();
                                final String developer = game.getDeveloper();
                                final String publisher = game.getPublisher();

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
                                final Album album = stock.getAlbum();

                                final String title = album.getTitle();
                                final String artist = album.getArtist();
                                final String producer = album.getProducer();

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
                        final JFrame warningDialog = new JFrame();
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

    //**Utility methods**

    protected static Employee getLoggedEmployee() {
        return employee;
    }

    protected static Client getLoggedClient() {
        return client;
    }

    protected static int getPriv() {
        return priv;
    }

    //change panels of window
    private void changePanel(JPanel panel, int sizeX, int sizeY) {
        getContentPane().removeAll();
        getContentPane().add(panel, BorderLayout.CENTER);
        getContentPane().doLayout();
        update(getGraphics());
        setVisible(true);
        setSize(sizeX, sizeY);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //change contents of the main table based on the arraylist and string inputs
    private JTable initializeTable(ArrayList<Stock> contents) {

        final DefaultTableModel mainTableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final String[] columns = new String[]{"ID", "Titlu", "Actor principal", "Director", "Durata", "Gen", "An", "Audienta", "Pret"};
        mainTableModel.setColumnIdentifiers(columns);

        final MovieManager movieMan = MovieManager.getInstance();

        for (int i = 0; i < contents.size(); i++) {
            final Stock stock = contents.get(i);
            final Movie movie = stock.getMovie();
            final int price = stock.getPrice();

            mainTableModel.addRow(movieMan.movieToRow(movie, price));
        }

        final JTable table = new JTable();
        table.setModel(mainTableModel);
        return table;
    }

    private DefaultTableModel updateList(String gen, ArrayList<Stock> contents, String cat) {

        final Iterator iter = contents.iterator();
        while (iter.hasNext()) {
            final Stock stock = (Stock) iter.next();
        }
        final DefaultTableModel model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        final String[] columns;

        if (cat.equals("Filme")) {
            columns = new String[]{"ID", "Titlu", "Actor principal", "Director", "Durata", "Gen", "An", "Audienta", "Pret"};
            model.setColumnIdentifiers(columns);

            final MovieManager movieMan = MovieManager.getInstance();

            for (int i = 0; i < contents.size(); i++) {
                final Stock stock = contents.get(i);
                final Movie movie = stock.getMovie();
                final int price = stock.getPrice();

                if (gen.equals("Toate")) {
                    model.addRow(movieMan.movieToRow(movie, price));
                } else if (movie.getGenre().equals(gen)) {
                    model.addRow(movieMan.movieToRow(movie, price));
                }
            }
        } else if(cat.equals("Jocuri")) {
            columns = new String[]{"ID", "Titlu", "Platforma", "Developer", "Publisher", "Gen", "An", "Audienta", "Pret"};
            model.setColumnIdentifiers(columns);

            final GameManager gameMan = GameManager.getInstance();

            for (int i = 0; i < contents.size(); i++) {
                final Stock stock = contents.get(i);
                final Game game = stock.getGame();
                final int price = stock.getPrice();

                if (gen.equals("Toate")) {
                    model.addRow(gameMan.gameToRow(game, price));
                } else if (game.getGenre().equals(gen)) {
                    model.addRow(gameMan.gameToRow(game, price));
                }
            }
        } else if(cat.equals("Albume")) {
            columns = new String[]{"ID", "Trupa", "Titlu", "Casa discuri", "Nr. Melodii", "Gen", "An", "Pret"};
            model.setColumnIdentifiers(columns);

            final AlbumManager albumMan = AlbumManager.getInstance();

            for (int i = 0; i < contents.size(); i++) {
                final Stock stock = contents.get(i);
                final Album album = stock.getAlbum();
                final int price = stock.getPrice();

                if (gen.equals("Toate")) {
                    model.addRow(albumMan.albumToRow(album, price));
                } else if (album.getGenre().equals(gen)) {
                    model.addRow(albumMan.albumToRow(album, price));
                }
            }
        } else if(cat.equals("Melodii")) {
            columns = new String[]{"ID", "Nume", "Durata"};
            model.setColumnIdentifiers(columns);

            final SongManager songMan = SongManager.getInstance();

            for (int i = 0; i < contents.size(); i++) {
                final Stock stock = contents.get(i);
                final Album album = stock.getAlbum();
                final ArrayList<Song> songs = album.getSongs();

                final Iterator iterator = songs.iterator();
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
                final MovieManager movieMan = MovieManager.getInstance();
                mainCategoriesList = movieMan.getGenres();
                break;
            case "Jocuri":
                final GameManager gameMan = GameManager.getInstance();
                mainCategoriesList = gameMan.getGenres();
                break;
            case "Albume":
                final AlbumManager albumManager = AlbumManager.getInstance();
                mainCategoriesList = albumManager.getGenres();
        }

        for (int i = 1; i < mainCategoriesList.size(); i++) {
            mainCategoryModel.addElement(mainCategoriesList.get(i));
        }
    }

    private String[] storeListToBox(ArrayList<Store> string) {
        final String[] result = new String[string.size() + 1];
        result[0] = "Toate";

        for (int i = 0; i < string.size(); i++) {
            result[i + 1] = string.get(i).getOras();
        }

        return result;
    }
}

