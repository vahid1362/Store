import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Product extends  JFrame  {
    private JTextField txtName;
    private JTextField txtDescription;
    private JButton btnSave;
    private JTable tblCategory;
    private JPanel Main;
    private JButton btnLoad;
    String[] columnNames = {"CategoryName", "Description"};

    Statement statement = null; // query statement
    ResultSet resultSet = null; // manages results
    String DATABASE_URL = "jdbc:sqlserver://localhost;encrypt=true;trustServerCertificate=true;databaseName=store;";
    Connection connection=null;
    public void Connect()
    {

        try {
            connection = DriverManager.getConnection(DATABASE_URL, "sa", "123456");
        } catch (SQLException e) {
          e.printStackTrace();
        }
    }

    public  void Load()
    {
        try {
            var statement= connection.prepareStatement("select * from categories");
            ResultSet result=statement.executeQuery();
            ResultSetMetaData metaData=result.getMetaData();
            DefaultTableModel model = (DefaultTableModel) tblCategory.getModel();
            int cols=metaData.getColumnCount();
            String[] columns=new String[cols];
            for(int i=0; i<cols;i++)
                columns[i]=metaData.getColumnName(i+1);
            model.setColumnIdentifiers(columns);
            while (result.next())
            {

            }




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Product() {

    setContentPane(Main);
    this.Connect();
    btnSave.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        String name,description;
        name=txtName.getText();
        description=txtDescription.getText();
            try {
                var statement= connection.prepareStatement("insert into Categories (CategoryName,Description) values (?,?)");
                statement.setString(1,name);
                statement.setString(2,description);
                statement.executeUpdate();
                JOptionPane.showMessageDialog(null,"Record Added");
                txtName.setText("");
                txtDescription.setText("");
                txtName.requestFocus();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    });
        btnLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Load();
            }
        });
    }
}
