

package dbManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBManager implements AutoCloseable {
    private Connection connection;
 
    public DBManager() throws SQLException {
        connect();
    }
 
    private void connect() throws SQLException {
       
        String url="jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC";
        String user="root";
        String password="3929770";
        connection = DriverManager.getConnection(url, user, password);
    }
 
   
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
    }
    
    public boolean agregarOrden(List<String> orden){
        try {
            String sql="INSERT INTO orden (fecha, cedulaCliente, matricula, modeloAuto, color, numeroChasis, marca, medidorGasolina, descripcion, valorInsumos, valorServicio, valorTotal) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
            try(PreparedStatement statement=connection.prepareStatement(sql) ){
                statement.setString(1, orden.get(0));
                statement.setString(2, orden.get(1));
                statement.setString(3, orden.get(2));
                statement.setString(4, orden.get(3));
                statement.setString(5, orden.get(4));
                statement.setString(6, orden.get(5));
                statement.setString(7, orden.get(6));
                statement.setInt(8, Integer.parseInt(orden.get(7)));
                statement.setString(9, orden.get(8));
                statement.setInt(10, Integer.parseInt(orden.get(9)));
                statement.setInt(11, Integer.parseInt(orden.get(10)));
                statement.setInt(12, Integer.parseInt(orden.get(11)));
 

                statement.executeUpdate();
            }
           
        } catch (SQLException ex) {
            return false;
        }
 
        return true;
    }
    
    public boolean eliminarOrden(String orden) {
        try {
            String sql="DELETE FROM orden WHERE idOrden = ?";
            try(PreparedStatement statement=connection.prepareStatement(sql) ){
                statement.setInt(1, Integer.parseInt(orden));
                statement.executeUpdate();
            }
           
        } catch (SQLException ex) {
            return false;
        }
 
        return true;
    }
    
    public boolean actualizarOrden(List<String> orden, String numeroOrden){
        try {
            String sql="UPDATE orden\n" +
                       "SET fecha = ?, cedulaCliente = ?, matricula = ?,modeloAuto = ?, color = ?, numeroChasis = ?, marca = ?, medidorGasolina = ?, descripcion = ?, valorInsumos = ?, valorServicio = ?,valorTotal = ?\n" +
                       "WHERE idOrden = ?;";
            try(PreparedStatement statement=connection.prepareStatement(sql) ){
                statement.setString(1, orden.get(0));
                statement.setString(2, orden.get(1));
                statement.setString(3, orden.get(2));
                statement.setString(4, orden.get(3));
                statement.setString(5, orden.get(4));
                statement.setString(6, orden.get(5));
                statement.setString(7, orden.get(6));
                statement.setInt(8, Integer.parseInt(orden.get(7)));
                statement.setString(9, orden.get(8));
                statement.setInt(10, Integer.parseInt(orden.get(9)));
                statement.setInt(11, Integer.parseInt(orden.get(10)));
                statement.setInt(12, Integer.parseInt(orden.get(11)));
                statement.setInt(13, Integer.parseInt(numeroOrden));              
                statement.executeUpdate();
                return true;
            } 
           
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean actualizarCliente(String cedula, String nombre, String telefono, String direccion){
        try {
            String sql="UPDATE cliente\n" +
                       "SET nombre = ?, telefono = ?, direccion = ?\n" +
                       "WHERE cedula = ?;";
            try(PreparedStatement statement=connection.prepareStatement(sql) ){
                statement.setString(1, nombre);
                statement.setString(2, telefono);
                statement.setString(4, cedula);              
                statement.setString(3, direccion);           
                statement.executeUpdate();
                return true;
            } 
           
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean agregarCliente(String cedula, String nombre, String telefono, String direccion){
        try {
            String sql="INSERT INTO cliente VALUES(?,?,?,?)";
            try(PreparedStatement statement=connection.prepareStatement(sql) ){
                statement.setString(1, cedula);
                statement.setString(2, nombre);
                statement.setString(3, telefono);
                statement.setString(4, direccion);
                statement.executeUpdate();
            }
           
        } catch (SQLException ex) {
            return false;
        }
 
        return true;
    }   
    
    public boolean eliminarCliente(String cedula) {
        try {
            String sql="DELETE FROM cliente WHERE cedula = ?";
            try(PreparedStatement statement=connection.prepareStatement(sql) ){
                statement.setString(1, cedula);
                statement.executeUpdate();
            }
           
        } catch (SQLException ex) {
            return false;
        }
 
        return true;
    }
    
    public List<List<String>> getInfoClientes(){
        List<String> cliente = new ArrayList<>();
        List<List<String>> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    
                    String cedula = resultSet.getString("cedula");
                    String nombre = resultSet.getString("nombre");
                    String telefono = resultSet.getString("telefono");
                    String direccion = resultSet.getString("direccion");
                    
                    cliente.add(cedula);
                    cliente.add(nombre);
                    cliente.add(telefono);
                    cliente.add(direccion);

                    }
               

                for (int i = 0; i < cliente.size(); i += 4) {
                    List<String> subLista = new ArrayList<>();
                    for (int j = i; j < i + 4 && j < cliente.size(); j++) {
                        subLista.add(cliente.get(j));
                    }
                    clientes.add(subLista);
            }
                
                }
            } catch (SQLException ex) {
                System.out.println("Error");
                System.out.println("Excepción SQL: " + ex.getMessage());
                System.out.println("Estado SQL: " + ex.getSQLState());
                System.out.println("Código de error: " + ex.getErrorCode());
        }
        return clientes;
       
    }
    
    public List<String> encontrarCliente(String cedula){
        List<String> clienteEncontrado = new ArrayList<>();
        List<List<String>> clientes = getInfoClientes();
        for (List<String> cliente : clientes) {
            if (cliente.get(0).equals(cedula)){
                clienteEncontrado = cliente;
            }
        }
  
        return clienteEncontrado;
    }
    
    public List<List<String>> getInfoOrdenes(){
        List<String> orden = new ArrayList<>();
        List<List<String>> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM orden";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    
                    int idOrden = resultSet.getInt("idOrden");
                    String idString = String.valueOf(idOrden);
                    String fecha = resultSet.getString("fecha");
                    String cedulaCliente = resultSet.getString("cedulaCliente");
                    String matricula = resultSet.getString("matricula");
                    String modeloAuto = resultSet.getString("modeloAuto");
                    String color = resultSet.getString("color");
                    String numeroChasis = resultSet.getString("numeroChasis");
                    String marca = resultSet.getString("marca");
                    int medidorGasolina = resultSet.getInt("medidorGasolina");
                    String descripcion = resultSet.getString("descripcion");
                    int valorInsumos = resultSet.getInt("valorInsumos");
                    int valorServicio = resultSet.getInt("valorServicio");
                    int valorTotal = resultSet.getInt("valorTotal");
                    
                    String marcaString = String.valueOf(marca);
                    String medidorGasolinaString = String.valueOf(medidorGasolina);
                    String valorInsumosString = String.valueOf(valorInsumos);
                    String valorServicioString = String.valueOf(valorServicio);
                    String valorTotalString = String.valueOf(valorTotal);
                    
                    orden.add(idString);
                    orden.add(fecha);
                    orden.add(cedulaCliente);
                    orden.add(matricula);
                    orden.add(modeloAuto);
                    orden.add(color);
                    orden.add(numeroChasis);
                    orden.add(marcaString);
                    orden.add(medidorGasolinaString);
                    orden.add(descripcion);
                    orden.add(valorInsumosString);
                    orden.add(valorServicioString);
                    orden.add(valorTotalString);

                    }
               

                for (int i = 0; i < orden.size(); i += 13) {
                    List<String> subLista = new ArrayList<>();
                    for (int j = i; j < i + 14 && j < orden.size(); j++) {
                        subLista.add(orden.get(j));
                    }
                    ordenes.add(subLista);
            }
                
                }
            } catch (SQLException ex) {
                System.out.println("Error");
                System.out.println("Excepción SQL: " + ex.getMessage());
                System.out.println("Estado SQL: " + ex.getSQLState());
                System.out.println("Código de error: " + ex.getErrorCode());
        }
        return ordenes;
       
    }
    
    public int getInfoValorTotal(){
        String consulta = "SELECT SUM(valorTotal) AS Total FROM orden";
        int valorTotal = 0;
            try (PreparedStatement statement = connection.prepareStatement(consulta);
                 ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    // Recopila la información de la resta
                    valorTotal = resultSet.getInt("Total");
                }
            }   catch (SQLException e) {
               e.printStackTrace();
               }
        

        return valorTotal;
    }
    
    public int getInfoIngresos(){
        String consulta = "SELECT SUM(valorServicio) AS Total FROM orden";
        int valorTotal = 0;
            try (PreparedStatement statement = connection.prepareStatement(consulta);
                 ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    // Recopila la información de la resta
                    valorTotal = resultSet.getInt("Total");
                }
            }   catch (SQLException e) {
               e.printStackTrace();
               }
        

        return valorTotal;
    }
    public int getInfoInsumos(){
        String consulta = "SELECT SUM(valorInsumos) AS Total FROM orden";
        int valor = 0;
            try (PreparedStatement statement = connection.prepareStatement(consulta);
                 ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    // Recopila la información de la resta
                    valor = resultSet.getInt("Total");
                }
            }   catch (SQLException e) {
               e.printStackTrace();
               }
        

        return valor;
    }
    
    public int getInfoOrdenesTotal(){
        String consulta = "SELECT COUNT(*) AS total FROM orden";
        int ordenesTotales = 0;
            try (PreparedStatement statement = connection.prepareStatement(consulta);
                 ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    // Recopila la información de la resta
                    ordenesTotales = resultSet.getInt("total");
                }
            }   catch (SQLException e) {
               e.printStackTrace();
               }
        

        return ordenesTotales;
    }
}