package Modelo;

public class ComprobantePago {

    private int idComprobantePago;
    private String tipoComprobantePago;
    private String numComprobantePago;
    private int idVenta;
    
    // Contructor vacio
    public ComprobantePago() {
    }
    
    //getter y setter
    public int getIdComprobantePago() {
        return idComprobantePago;
    }

    public void setIdComprobantePago(int idComprobantePago) {
        this.idComprobantePago = idComprobantePago;
    }

    public String getTipoComprobantePago() {
        return tipoComprobantePago;
    }

    public void setTipoComprobantePago(String tipoComprobantePago) {
        this.tipoComprobantePago = tipoComprobantePago;
    }

    public String getNumComprobantePago() {
        return numComprobantePago;
    }

    public void setNumComprobantePago(String numComprobantePago) {
        this.numComprobantePago = numComprobantePago;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
}
