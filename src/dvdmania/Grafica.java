package dvdmania;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Grafica {
    static class GUI extends JFrame {

        // constructorul interfetei grafice
        GUI()
        {
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

        //Initializare clasa baza de date
        static DataManip bazaDate = new DataManip();
        
        //Cache variabile
        static ImageIcon imagePathLog = new ImageIcon(new ImageIcon("logo.png").getImage().getScaledInstance(120,130, Image.SCALE_DEFAULT));
        static JLabel imagine = new JLabel(imagePathLog);
        static Utilizator util;
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
        static JMenuItem newOrder, finishOrder, logout, exit, newCustomer, newEmployee, newProduct, newStore
                , editCustomer, editEmployee, editProduct, editStore,
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
        static ArrayList<String[]> mainProduseList;
        static ArrayList<String[]> mainStoresList;


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
                    char[] pswrd = logPasswordText.getPassword();
                    String fullPasswrd = String.valueOf(pswrd);

                    if (bazaDate.checkAccount(usrname, fullPasswrd) == true) {
                        int privGet = bazaDate.checkPriviledge(usrname, fullPasswrd);

                        ArrayList<String[]> user = bazaDate.getUser(usrname, fullPasswrd);
                        String[] usr;

                        switch(privGet) {
                            case 1:
                                usr = user.get(0);
                                util = new Utilizator(usr[0], usr[1], usr[2], usr[3], usr[4], usr[5], usr[6], usr[7], Integer.parseInt(usr[8]));
                                priv = 1;
                                break;
                            case 2:
                                usr = user.get(0);
                                util = new Utilizator(usr[0], usr[1], usr[2], usr[3], usr[4], usr[5], usr[6], usr[7], usr[8], usr[9], usr[10]);
                                priv = 2;
                                break;
                            case 3:
                                usr = user.get(0);
                                util = new Utilizator(usr[0], usr[1], usr[2], usr[3], usr[4], usr[5], usr[6], usr[7], usr[8], usr[9], usr[10]);
                                priv = 3;
                        }

                        setVisible(false);
                        dispose();
                        JFrame welcomeDialog = new JFrame();
                        JOptionPane.showMessageDialog(welcomeDialog, "Welcome, " + util.getNume() + " " + util.getPrenume() + "!");

                        setVisible(false);
                        dispose();
                        changePanel(mainMainPanel,size[0],size[1]);
                        MenuBar();
                        setTitle("DVDMania");
                    }
                    else {
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

                    if(username.equals("") || password.equals("") || email.equals("") || firstName.equals("") || lastName.equals("") || address.equals("") || city.equals("")
                    || cnp.equals("") || phone.equals("") || year.equals("") || month.equals("") || day.equals("")) {
                        JFrame warningDialog = new JFrame();
                        JOptionPane.showMessageDialog(warningDialog, "You must complete all fields!", "Warning", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        String birthdate = new String(year + "-" + month + "-" + day);
                        util = new Utilizator(firstName, lastName, address, city, birthdate, cnp, phone, email, 5);

                        bazaDate.createClient(util);
                        bazaDate.createAccount(util, username, password, true);

                        JFrame confirmDialog = new JFrame();
                        JOptionPane.showMessageDialog(confirmDialog, "Your account has successfully been created!");

                        changePanel(mainMainPanel,size[0], size[1]);
                        MenuBar();
                        setTitle("DVDMania");
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

            mainCategoriesList = bazaDate.getGen("Filme");
            mainStoresList =  bazaDate.getStores();
            mainCategoryModel = new DefaultComboBoxModel(mainCategoriesList.toArray());
            mainCategoryBox = new JComboBox(mainCategoryModel);
            mainStoreBox = new JComboBox(storeListToBox(mainStoresList));
            mainFilmeButton.setBorder(BorderFactory.createTitledBorder("Categorii:"));
            mainCategoryBox.setBorder(BorderFactory.createTitledBorder("Gen:"));
            mainStoreBox.setBorder(BorderFactory.createTitledBorder("Magazine:"));

            mainButtonsPanel = new JPanel();
            mainButtonsPanel.setLayout(new GridLayout(5,1));
            mainButtonsPanel.setPreferredSize(new Dimension(120,600));
            mainButtonsPanel.add(mainFilmeButton);
            mainButtonsPanel.add(mainJocuriButton);
            mainButtonsPanel.add(mainAlbumeButton);
            mainButtonsPanel.add(mainCategoryBox);
            mainButtonsPanel.add(mainStoreBox);

            //Search and table panel
            mainSearchField = new JTextField("Cautare");
            categorieCurenta = "Filme";
            magazinCurent = "Toate";
            mainProduseList = bazaDate.getProducts("Filme", "Toate");
            mainProdTable = initializeTable(mainProduseList);
            mainScrollPane = new JScrollPane(mainProdTable);
            mainSearchButton = new JButton("Cauta");
            mainCheckButton = new JButton("Verifica valabilitatea");
            mainCategoriesList = bazaDate.getGen("Filme");

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
                        ArrayList<String[]> songs = bazaDate.getSongs(id);
                        JFrame songsWindow = new JFrame();

                        DefaultTableModel songModel = new DefaultTableModel() {
                            public boolean isCellEditable(int row, int column) {
                                return false;
                            }
                        };

                        String[] columns = {"Titlu", "Durata"};
                        songModel.setColumnIdentifiers(columns);

                        for(int i = 0; i < songs.size(); i++) {
                            songModel.addRow(songs.get(i));
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
                        String selectedStore = mainStoreBox.getSelectedItem().toString();
                        mainProduseList = bazaDate.getProducts(categorieCurenta, selectedStore);
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
                    mainProduseList = bazaDate.getProducts("Filme", "Toate");
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
                    mainProduseList = bazaDate.getProducts("Jocuri", "Toate");
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
                    mainProduseList = bazaDate.getProducts("Albume", "Toate");
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
                            //Object id = mainProdTable.getValueAt(mainProdTable.getSelectedRow(), 0);
                            int id = Integer.parseInt(String.valueOf(mainProdTable.getValueAt(mainProdTable.getSelectedRow(), 0)));
                            int nrTot = bazaDate.checkProduct(id, categorieCurenta, util.getOras());
                            if(nrTot > 0) {
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Vesti bune! \n Mai sunt " + nrTot + " copii ale acestui produs in magazin!");
                            } else if(nrTot == -1){
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
                    ArrayList<String[]> newProduseList = new ArrayList<>();

                    if(!mainSearchField.equals("Cautare")) {
                        if(categorieCurenta.equals("Filme")) {
                            String toSearch = mainSearchField.getText();

                            for(int i = 0; i < mainProduseList.size(); i++) {
                                for(int j = 1; j <= 3; j++) {
                                    String search = mainProduseList.get(i)[j];
                                    if(search.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(mainProduseList.get(i));
                                        break;
                                    }
                                }
                            }
                        } else if(categorieCurenta.equals("Jocuri")) {
                            String toSearch = mainSearchField.getText();

                            for(int i = 0; i < mainProduseList.size(); i++) {
                                for(int j = 1; j <= 4; j++) {
                                    String search = mainProduseList.get(i)[j];
                                    if(search.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(mainProduseList.get(i));
                                        break;
                                    }
                                }
                            }
                        } else if(categorieCurenta.equals("Albume")) {
                            String toSearch = mainSearchField.getText();

                            for(int i = 0; i < mainProduseList.size(); i++) {
                                for(int j = 1; j <= 3; j++) {
                                    String search = mainProduseList.get(i)[j];
                                    if(search.toLowerCase().contains(toSearch.trim().toLowerCase())) {
                                        newProduseList.add(mainProduseList.get(i));
                                        break;
                                    }
                                }
                            }
                        }

                        if(newProduseList.isEmpty()) {
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
                                bazaDate.createFilm(titlu, actor, director, durata, gen, an, audienta, util.getOras(), cant, pret);
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
                                bazaDate.createJoc(titlu, platforma, developer, publisher, gen, an, audienta, util.getOras(), cant, pret);
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
                                bazaDate.createAlbum(titlu, trupa, melodii, casaDisc, gen, an, util.getOras(), cant, pret);
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
                    ArrayList<String> albume = bazaDate.getAlbume();
                    newSongAlbumBox = new JComboBox(albume.toArray());
                    save = new JButton("Save");
                    exit = new JButton("Exit");

                    JFrame newSongWindow = new JFrame();
                    JPanel detaliiPanel = new JPanel(new GridLayout(3,3,5,5));
                    JPanel butoanePanel = new JPanel(new GridLayout(1,2,5,5));
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
                            String album, nume, durata;
                            album = String.valueOf(newSongAlbumBox.getSelectedItem());
                            nume = newSongNumeField.getText();
                            durata = newSongDurataField.getText();

                            if(nume.isEmpty() || durata.isEmpty()) {
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Toate campurile trebuie completate!", "Warning", JOptionPane.WARNING_MESSAGE);
                            } else {
                                bazaDate.createSong(album, nume, durata);
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

                    if (editCategorieBox.getSelectedItem().equals("Filme")) {
                        JLabel editProdTitluLabel, editProdActorLabel, editProdDirectorLabel, editProdDurataLabel, editProdGenLabel, editProdAnLabel, editProdAudientaLabel,
                                editProdCantLabel, editProdPretLabel;
                        JTextField editProdTitluField, editProdActorField, editProdDirectorField, editProdDurataField, editProdGenField, editProdAnField, editProdAudientaField,
                                editProdCantField, editProdPretField;

                        ArrayList<String> produs = bazaDate.getProduct(editIDField.getText(), String.valueOf(editCategorieBox.getSelectedItem()), util.getOras());
                        if (produs.isEmpty()) {
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "ID-ul introdus nu exista in baza de date", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            editProdTitluLabel = new JLabel("Titlu:");
                            editProdTitluField = new JTextField();
                            editProdTitluField.setText(produs.get(0));
                            editProdActorLabel = new JLabel("Actor:");
                            editProdActorField = new JTextField();
                            editProdActorField.setText(produs.get(1));
                            editProdDirectorLabel = new JLabel("Director:");
                            editProdDirectorField = new JTextField();
                            editProdDirectorField.setText(produs.get(2));
                            editProdDurataLabel = new JLabel("Durata:");
                            editProdDurataField = new JTextField();
                            editProdDurataField.setText(produs.get(3));
                            editProdGenLabel = new JLabel("Gen:");
                            editProdGenField = new JTextField();
                            editProdGenField.setText(produs.get(4));
                            editProdAnLabel = new JLabel("An:");
                            editProdAnField = new JTextField();
                            editProdAnField.setText(produs.get(5));
                            editProdAudientaLabel = new JLabel("Audienta:");
                            editProdAudientaField = new JTextField();
                            editProdAudientaField.setText(produs.get(6));
                            editProdCantLabel = new JLabel("Cantitate:");
                            editProdCantField = new JTextField();
                            editProdCantField.setText(produs.get(7));
                            editProdPretLabel = new JLabel("Pret:");
                            editProdPretField = new JTextField();
                            editProdPretField.setText(produs.get(8));
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
                                        bazaDate.modifyFilm(editIDField.getText(), util.getOras(), editProdTitluField.getText(), editProdActorField.getText(), editProdDirectorField.getText(),
                                                editProdDurataField.getText(), editProdGenField.getText(), editProdAnField.getText(), editProdAudientaField.getText(), editProdCantField.getText(),
                                                editProdPretField.getText());
                                    }
                                }
                            });

                            delete.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFrame dialog = new JFrame();
                                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa stergeti produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                                    if (a == JOptionPane.YES_OPTION) {
                                        bazaDate.deleteFilm(editIDField.getText(), util.getOras());
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

                        ArrayList<String> produs = bazaDate.getProduct(editIDField.getText(), String.valueOf(editCategorieBox.getSelectedItem()), util.getOras());
                        if (produs.isEmpty()) {
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "ID-ul introdus nu exista in baza de date", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            editProdTitluLabel = new JLabel("Titlu:");
                            editProdTitluField = new JTextField();
                            editProdTitluField.setText(produs.get(0));
                            editProdPlatformaLabel = new JLabel("Platforma:");
                            editProdPlatformaField = new JTextField();
                            editProdPlatformaField.setText(produs.get(1));
                            editProdDeveloperLabel = new JLabel("Developer:");
                            editProdDeveloperField = new JTextField();
                            editProdDeveloperField.setText(produs.get(2));
                            editProdPublisherLabel = new JLabel("Publisher:");
                            editProdPublisherField = new JTextField();
                            editProdPublisherField.setText(produs.get(3));
                            editProdGenLabel = new JLabel("Gen:");
                            editProdGenField = new JTextField();
                            editProdGenField.setText(produs.get(4));
                            editProdAnLabel = new JLabel("An:");
                            editProdAnField = new JTextField();
                            editProdAnField.setText(produs.get(5));
                            editProdAudientaLabel = new JLabel("Audienta:");
                            editProdAudientaField = new JTextField();
                            editProdAudientaField.setText(produs.get(6));
                            editProdCantLabel = new JLabel("Cantitate:");
                            editProdCantField = new JTextField();
                            editProdCantField.setText(produs.get(7));
                            editProdPretLabel = new JLabel("Pret:");
                            editProdPretField = new JTextField();
                            editProdPretField.setText(produs.get(8));
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
                                        bazaDate.modifyJoc(editIDField.getText(), util.getOras(), editProdTitluField.getText(), editProdPlatformaField.getText(), editProdDeveloperField.getText(),
                                                editProdPublisherField.getText(), editProdAnField.getText(), editProdGenField.getText(), editProdAudientaField.getText(), editProdCantField.getText(),
                                                editProdPretField.getText());
                                    }
                                }
                            });

                            delete.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFrame dialog = new JFrame();
                                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa stergeti produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                                    if (a == JOptionPane.YES_OPTION) {
                                        bazaDate.deleteJoc(editIDField.getText(), util.getOras());
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

                        ArrayList<String> produs = bazaDate.getProduct(editIDField.getText(), String.valueOf(editCategorieBox.getSelectedItem()), util.getOras());
                        if (produs.isEmpty()) {
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "ID-ul introdus nu exista in baza de date", "Warning", JOptionPane.WARNING_MESSAGE);
                        } else {
                            editProdTitluLabel = new JLabel("Titlu:");
                            editProdTitluField = new JTextField();
                            editProdTitluField.setText(produs.get(0));
                            editProdTrupaLabel = new JLabel("Trupa:");
                            editProdTrupaField = new JTextField();
                            editProdTrupaField.setText(produs.get(1));
                            editProdMelodiiLabel = new JLabel("Numar melodii:");
                            editProdMelodiiField = new JTextField();
                            editProdMelodiiField.setText(produs.get(2));
                            editProdCasaDiscLabel = new JLabel("Casa discuri:");
                            editProdCasaDiscField = new JTextField();
                            editProdCasaDiscField.setText(produs.get(3));
                            editProdGenLabel = new JLabel("Gen:");
                            editProdGenField = new JTextField();
                            editProdGenField.setText(produs.get(4));
                            editProdAnLabel = new JLabel("An:");
                            editProdAnField = new JTextField();
                            editProdAnField.setText(produs.get(5));
                            editProdCantLabel = new JLabel("Cantitate:");
                            editProdCantField = new JTextField();
                            editProdCantField.setText(produs.get(6));
                            editProdPretLabel = new JLabel("Pret:");
                            editProdPretField = new JTextField();
                            editProdPretField.setText(produs.get(7));
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
                                        bazaDate.modifyAlbum(editIDField.getText(), util.getOras(), editProdTitluField.getText(), editProdTrupaField.getText(), editProdMelodiiField.getText(),
                                                editProdCasaDiscField.getText(), editProdAnField.getText(), editProdGenField.getText(), editProdCantField.getText(),
                                                editProdPretField.getText());
                                    }
                                }
                            });

                            delete.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JFrame dialog = new JFrame();
                                    int a = JOptionPane.showConfirmDialog(dialog, "Sunteti sigur ca doriti sa stergeti produsul?", "Warning", JOptionPane.YES_NO_OPTION);
                                    if (a == JOptionPane.YES_OPTION) {
                                        bazaDate.deleteAlbum(editIDField.getText(), util.getOras());
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
                    }
                    else {
                        String birthdate = new String(year + "-" + month + "-" + day);

                        bazaDate.createClient(new Utilizator(firstName, lastName, address, city, birthdate, cnp, phone, email, 5));
                        bazaDate.createAccount(new Utilizator(firstName, lastName, address, city, birthdate, cnp, phone, email, 5), username, password, true);

                        JFrame confirmDialog = new JFrame();
                        JOptionPane.showMessageDialog(confirmDialog, "Your account has successfully been created!");
                    }
                }
            });
        }

        private void editCustomerWindow() {
            ArrayList<String[]> customers = bazaDate.getCustomers();
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[] {"ID", "Nume", "Prenume", "Adresa", "Oras", "Data nasterii", "CNP", "Telefon", "Email", "Loialitate", "Username", "Password"};
            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < customers.size(); i++) {
                tableModel.addRow(customers.get(i));
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
                                    bazaDate.modifyClient(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), custNumeField.getText(), custPrenumeField.getText(),
                                            custAdresaField.getText(), custOrasField.getText(), custDatNasField.getText(), custCNPField.getText(), custTelField.getText(),
                                            String.valueOf(custEmailField.getText()), custUserField.getText(), custPassField.getText());
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
            JComboBox newMagBox = new JComboBox(bazaDate.getOrase().toArray());

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
                    }
                    else {
                        String birthdate = new String(year + "-" + month + "-" + day);

                        bazaDate.createEmployee(new Utilizator(firstName, lastName, address, city, birthdate, cnp, phone, email, functie, salariu, mag));
                        bazaDate.createAccount(new Utilizator(firstName, lastName, address, city, birthdate, cnp, phone, email, functie, salariu, mag), username, password, false);

                        JFrame confirmDialog = new JFrame();
                        JOptionPane.showMessageDialog(confirmDialog, "Your account has successfully been created!");

                        changePanel(mainMainPanel,size[0], size[1]);
                        MenuBar();
                        setTitle("DVDMania");
                    }
                }
            });
        }

        private void editEmployeeWindow() {
            ArrayList<String[]> employees = bazaDate.getEmployees();
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[] {"ID", "Nume", "Prenume", "Adresa", "Oras", "Data nasterii", "CNP", "Telefon", "Email", "Functie", "Salariu", "Adresa magazin", "Username", "Parola"};
            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < employees.size(); i++) {
                tableModel.addRow(employees.get(i));
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
                                    bazaDate.modifyAngajat(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), empNumeField.getText(), empPrenumeField.getText(),
                                            empAdresaField.getText(), empOrasField.getText(), empDatNasField.getText(), empCNPField.getText(), empTelField.getText(),
                                            String.valueOf(empEmailField.getText()), empFuncField.getText(), empSalField.getText(), empStoreField.getText(), empActivField.getText(),
                                            empUserField.getText(), empPassField.getText());
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
                            bazaDate.createStore(adresa, oras, tel);
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
            ArrayList<String[]> stores = bazaDate.getStores();
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[] {"ID", "Adresa", "Oras", "Telefon"};
            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < stores.size(); i++) {
                tableModel.addRow(stores.get(i));
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
                                    bazaDate.modifyMagazin(String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), magAdresaField.getText(), magOrasField.getText(), magTelField.getText());
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
            ArrayList<String[]> customers = bazaDate.getCustomers();
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[] {"ID", "Nume", "Prenume", "Adresa", "Oras", "Data nasterii", "CNP", "Telefon", "Email"};
            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < customers.size(); i++) {
                tableModel.addRow(customers.get(i));
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
                                String res = bazaDate.newOrder(idField.getText(), String.valueOf(table.getValueAt(table.getSelectedRow(), 0)), util.getCNP(), categorieBox.getSelectedItem().toString());
                                JFrame dialog = new JFrame();
                                JOptionPane.showMessageDialog(dialog, "Imprumutul a fost efectuat cu succes! \n Data scadenta este pe " + res);
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
            ArrayList<String[]> orders = bazaDate.getActiveOrders(util.getOras());
            JTable table = new JTable();
            DefaultTableModel tableModel;

            tableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[] {"Nume", "Prenume", "CNP", "ID produs", "Data imprumutului"};
            tableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < orders.size(); i++) {
                tableModel.addRow(orders.get(i));
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
                        int rez = bazaDate.returnOrder(String.valueOf(table.getValueAt(table.getSelectedRow(), 3)), String.valueOf(table.getValueAt(table.getSelectedRow(), 2)),
                                String.valueOf(table.getValueAt(table.getSelectedRow(), 4)));
                        if(rez <= 7) {
                            bazaDate.rewardClient(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)), true);
                            JFrame dialog = new JFrame();
                            JOptionPane.showMessageDialog(dialog, "Clientul a adus produsul la timp!");
                        } else {
                            bazaDate.rewardClient(String.valueOf(table.getValueAt(table.getSelectedRow(), 2)), false);
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
            ArrayList<String[]> orders = new ArrayList<>();
            String[] columns = new String[] {};
            if(priv == 1) {
                orders = bazaDate.getAllOrdersClient(util.getCNP());
                columns = new String[] {"Nume", "Prenume", "ID produs", "Data imprumutului", "Data returnarii", "Pret"};
            } else if (priv == 2 || priv == 3) {
                orders = bazaDate.getAllOrders(util.getOras());
                columns = new String[] {"Nume", "Prenume", "CNP", "ID produs", "Data imprumutului", "Data returnarii", "Pret"};
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
                tableModel.addRow(orders.get(i));
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

                nume = new JLabel("Nume: " + util.getNume());
                prenume = new JLabel("Prenume: " + util.getPrenume());
                adresa = new JLabel("Adresa: " + util.getAdresa());
                oras = new JLabel("Oras: " + util.getOras());
                datan = new JLabel("Data nasterii: " + util.getDataNasterii());
                cnp = new JLabel("CNP: " + util.getCNP());
                tel = new JLabel("Telefon: " + util.getTelefon());
                email = new JLabel("Email: " + util.getEmail());
                loialitate = new JLabel("Loialitate: " + util.getLoialitate());
                exit = new JButton("Exit");

                JPanel main, detail;
                detail = new JPanel(new GridLayout(9,1,5,5));
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

                nume = new JLabel("Nume: " + util.getNume());
                prenume = new JLabel("Prenume: " + util.getPrenume());
                adresa = new JLabel("Adresa: " + util.getAdresa());
                oras = new JLabel("Oras: " + util.getOras());
                datan = new JLabel("Data nasterii: " + util.getDataNasterii());
                cnp = new JLabel("CNP: " + util.getCNP());
                tel = new JLabel("Telefon: " + util.getTelefon());
                email = new JLabel("Email: " + bazaDate.getEmail(util.getCNP()));
               functie = new JLabel("Functie: " + bazaDate.getFunctie(util.getCNP()));
                exit = new JButton("Exit");

                JPanel main, detail;
                detail = new JPanel(new GridLayout(9,1,5,5));
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
        private JTable initializeTable(ArrayList<String[]> contents) {

            mainTableModel = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns = new String[] {"ID", "Titlu", "Actor principal", "Director", "Durata", "Gen", "An", "Audienta", "Pret"};
            mainTableModel.setColumnIdentifiers(columns);

            for(int i = 0; i < contents.size(); i++) {
                mainTableModel.addRow(contents.get(i));
            }

            JTable table = new JTable();
            table.setModel(mainTableModel);
            return table;
        }

        private DefaultTableModel updateList(String gen, ArrayList<String[]> contents, String cat) {

            DefaultTableModel model = new DefaultTableModel() {
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            String[] columns;

            if(cat.equals("Filme")) {
                columns = new String[] {"ID", "Titlu", "Actor principal", "Director", "Durata", "Gen", "An", "Audienta", "Pret"};
                model.setColumnIdentifiers(columns);

                for(int i = 0; i < contents.size(); i++) {
                    String[] mid= contents.get(i);
                    if(gen.equals("Toate")) {
                        model.addRow(mid);
                    } else if(mid[5].equals(gen)) {
                        model.addRow(mid);
                    }
                }
            } else if(cat.equals("Jocuri")) {
                columns = new String[] {"ID", "Titlu", "Platforma", "Developer", "Publisher", "Gen", "An", "Audienta", "Pret"};
                model.setColumnIdentifiers(columns);

                for(int i = 0; i < contents.size(); i++) {
                    String[] mid= contents.get(i);
                    if(gen.equals("Toate")) {
                        model.addRow(mid);
                    } else if(mid[5].equals(gen)) {
                        model.addRow(mid);
                    }
                }
            } else if(cat.equals("Albume")) {
                columns = new String[] {"ID", "Trupa", "Titlu", "Casa discuri", "Nr. Melodii", "Gen", "An", "Pret"};
                model.setColumnIdentifiers(columns);

                for(int i = 0; i < contents.size(); i++) {
                    String[] mid= contents.get(i);
                    if(gen.equals("Toate")) {
                        model.addRow(mid);
                    } else if(mid[5].equals(gen)) {
                        model.addRow(mid);
                    }
                }
            } else if(cat.equals("Melodii")) {
                columns = new String[] {"ID", "Nume", "Durata"};
                model.setColumnIdentifiers(columns);

                for(int i = 0; i < contents.size(); i++) {
                    String[] mid= contents.get(i);
                    model.addRow(mid);
                }
            }

            return model;
        }

        private void updateComboBox(String gen) {

            for(int i = 0; i < mainCategoriesList.size() ; i++) {
                if(i == mainCategoriesList.size() - 1) {
                    mainCategoryModel.addElement("Toate");
                }
                mainCategoryModel.removeElement(mainCategoriesList.get(i));
            }

            mainCategoriesList = bazaDate.getGen(gen);

            for(int i = 1; i < mainCategoriesList.size() ; i++) {
                mainCategoryModel.addElement(mainCategoriesList.get(i));
            }
        }

        private String[] storeListToBox(ArrayList<String[]> string) {
            String[] result = new String[string.size() + 1];
            result[0] = "Toate";

            for(int i = 0; i < string.size(); i++) {
                result[i + 1] = string.get(i)[2];
            }

            return result;
        }
    }

    public static void main(String[] args) {
        GUI interfata = new GUI();
    }
}
