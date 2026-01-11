package Modelo;

public class DetalleVenta {

    private int idVenta;
    private String numSerie;
    private String fecha;
    private String nomCliente;
    private String apellCliente;
    private double montoTotal;

    private String descripcion;
    private String categoria;
    private int cantidadProd;
    private double precioProd;
    private double subtotalProd;

    // Contructor vacio
    public DetalleVenta() {
    }

    //getter y setter
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getNumSerie() {
        return numSerie;
    }

    public void setNumSerie(String numSerie) {
        this.numSerie = numSerie;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNomCliente() {
        return nomCliente;
    }

    public void setNomCliente(String nomCliente) {
        this.nomCliente = nomCliente;
    }

    public String getApellCliente() {
        return apellCliente;
    }

    public void setApellCliente(String apellCliente) {
        this.apellCliente = apellCliente;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getCantidadProd() {
        return cantidadProd;
    }

    public void setCantidadProd(int cantidadProd) {
        this.cantidadProd = cantidadProd;
    }

    public double getPrecioProd() {
        return precioProd;
    }

    public void setPrecioProd(double precioProd) {
        this.precioProd = precioProd;
    }

    public double getSubtotalProd() {
        return subtotalProd;
    }

    public void setSubtotalProd(double subtotalProd) {
        this.subtotalProd = subtotalProd;
    }
}
