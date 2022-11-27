package cv;

import java.util.ArrayList;

public class Model {

    private String nombre = "";
    private String apellidos = "";
    private String dni = "";
    private String fecha = "";
    private String direccion = "";
    private String codigoPostal = "";
    private String localidad = "";
    private String pais = "";

    private ArrayList<String> nacionalidad = new ArrayList<String>();

    private ArrayList<String> email = new ArrayList<String>();
    private ArrayList<ArrayList<String>> telefono = new ArrayList<ArrayList<String>>();
    private ArrayList<String> web = new ArrayList<String>();

    private ArrayList<ArrayList<String>> experiencia = new ArrayList<ArrayList<String>>();

    private ArrayList<ArrayList<String>> formacion = new ArrayList<ArrayList<String>>();

    private ArrayList<ArrayList<String>> conocimientos = new ArrayList<ArrayList<String>>();

    public Model(String nombre, String apellidos, String dni, String fecha, String direccion, String codigoPostal,
            String localidad, String pais, ArrayList<String> nacionalidad, ArrayList<String> email,
            ArrayList<ArrayList<String>> telefono, ArrayList<String> web, ArrayList<ArrayList<String>> experiencia,
            ArrayList<ArrayList<String>> formacion) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.dni = dni;
        this.fecha = fecha;
        this.direccion = direccion;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.pais = pais;
        this.nacionalidad = nacionalidad;
        this.email = email;
        this.telefono = telefono;
        this.web = web;
        this.experiencia = experiencia;
        this.formacion = formacion;

    }

    public Model() {
    }

    public void setConocimientos(ArrayList<ArrayList<String>> conocimientos) {
        this.conocimientos = conocimientos;
    }

    public void setExperiencia(ArrayList<ArrayList<String>> experiencia) {
        this.experiencia = experiencia;
    }

    public void setFormacion(ArrayList<ArrayList<String>> formacion) {
        this.formacion = formacion;
    }

    public void setEmail(ArrayList<String> email) {
        this.email = email;
    }

    public void setTelefono(ArrayList<ArrayList<String>> telefono) {
        this.telefono = telefono;
    }

    public void setWeb(ArrayList<String> web) {
        this.web = web;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public void setNacionalidad(ArrayList<String> nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getDni() {
        return dni;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getPais() {
        return pais;
    }

    public ArrayList<String> getNacionalidad() {
        return nacionalidad;
    }

    public ArrayList<String> getEmail() {
        return email;
    }

    public ArrayList<ArrayList<String>> getTelefono() {
        return telefono;
    }

    public ArrayList<String> getWeb() {
        return web;
    }

    public ArrayList<ArrayList<String>> getExperiencia() {
        return experiencia;
    }

    public ArrayList<ArrayList<String>> getFormacion() {
        return formacion;
    }

    public ArrayList<ArrayList<String>> getConocimientos() {
        return conocimientos;
    }

    public void reset() {
        this.nombre = "";
        this.apellidos = "";
        this.dni = "";
        this.fecha = "";
        this.direccion = "";
        this.codigoPostal = "";
        this.localidad = "";
        this.pais = "";
        this.nacionalidad = new ArrayList<String>();
        this.email = new ArrayList<String>();
        this.telefono = new ArrayList<ArrayList<String>>();
        this.web = new ArrayList<String>();
        this.experiencia = new ArrayList<ArrayList<String>>();
        this.formacion = new ArrayList<ArrayList<String>>();
        this.conocimientos = new ArrayList<ArrayList<String>>();
    }

}
